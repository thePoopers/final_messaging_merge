package materialtest.theartistandtheengineer.co.materialtest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.Arrays;
import java.util.List;

import materialtest.theartistandtheengineer.co.materialtest.activities.RatingsActivity;
import materialtest.theartistandtheengineer.co.materialtest.helper.MessageAdapter;
import materialtest.theartistandtheengineer.co.materialtest.helper.MessageService;

public class MessagingActivity extends ActionBarActivity {

    private String recipientId;
    private String recipientUserName;
    private EditText messageBodyField;
    private String messageBody;
    private MessageService.MessageServiceInterface messageService;
    private MessageAdapter messageAdapter;
    private ListView messagesList;
    private String currentUserId;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private MessageClientListener messageClientListener = new MyMessageClientListener();
    private TextView recipientBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);

        Intent intent = getIntent();
        recipientId = intent.getStringExtra("RECIPIENT_ID");
        currentUserId = ParseUser.getCurrentUser().getObjectId();

        messagesList = (ListView) findViewById(R.id.listMessages);
        messageAdapter = new MessageAdapter(this);
        messagesList.setAdapter(messageAdapter);

        populateMessageHistory();

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

//        recipientBar = (TextView)findViewById(R.id.user_name_text_box);
//        recipientBar.setText(intent.getStringExtra("RECIPIENT_USER_NAME"));
        recipientUserName = intent.getStringExtra("RECIPIENT_USER_NAME");
        getSupportActionBar().setTitle(recipientUserName);
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        Log.d(this.getClass().toString(), "Got to refresh call");
        VersionHelper.refreshActionBarMenu(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MessagingActivity", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_messaging, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        switch (item.getItemId()) {
            case R.id.action_complete:
                //TODO start ratings activity
                Intent intent = new Intent(getApplicationContext(), RatingsActivity.class);
                //add seller id to intent. for now use recipient ID.
                intent.putExtra("RECIPIENT_ID", recipientId);
                intent.putExtra("RECIPIENT_USER_NAME", recipientUserName);
                startActivity(intent);
                break;
            case R.id.action_cancel:
                //TODO start ratings activity
                break;
            case R.id.action_log_out:
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //get previous messages from parse & display
    private void populateMessageHistory() {
        String[] userIds = {currentUserId, recipientId};
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessage");
        query.whereContainedIn("senderId", Arrays.asList(userIds));
        query.whereContainedIn("recipientId", Arrays.asList(userIds));
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < messageList.size(); i++) {
                        WritableMessage message = new WritableMessage(messageList.get(i).get("recipientId").toString(), messageList.get(i).get("messageText").toString());
                        if (messageList.get(i).get("senderId").toString().equals(currentUserId)) {
                            messageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
                        } else {
                            messageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING);
                        }
                    }
                }
            }
        });
    }



    private void sendMessage() {
        messageBody = messageBodyField.getText().toString();
        if (messageBody.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_LONG).show();
            return;
        }

        messageService.sendMessage(recipientId, messageBody);
        messageBodyField.setText("");
    }

    @Override
    public void onDestroy() {
        messageService.removeMessageClientListener(messageClientListener);
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messageService = (MessageService.MessageServiceInterface) iBinder;
            messageService.addMessageClientListener(messageClientListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messageService = null;
        }
    }

    private class MyMessageClientListener implements MessageClientListener {
        @Override
        public void onMessageFailed(MessageClient client, Message message,
                                    MessageFailureInfo failureInfo) {
            Toast.makeText(MessagingActivity.this, "Message failed to send.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onIncomingMessage(MessageClient client, Message message) {
            if (message.getSenderId().equals(recipientId)) {
                WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
                messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_INCOMING);

                //Notify user that message was received.
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.bookitnotificationimage)
                                .setContentTitle("BookIt "+message.getSenderId())
                                .setContentText(message.getTextBody())
                                .setAutoCancel(true);


                //Intent resultIntent = getSenderMessagingActivity(getApplicationContext(), message.getSenderId());
                Intent resultIntent = new Intent(getApplicationContext(), MessagingActivity.class);
                resultIntent.putExtra("RECIPIENT_ID", message.getSenderId());
                PendingIntent resultPending = PendingIntent.getActivity(getApplicationContext(),
                        0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPending);
                int notificationId = 001;
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //check to make sure application is not in foreground before sending notification.
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> services = activityManager
                        .getRunningTasks(Integer.MAX_VALUE);
                boolean isActivityFound = false;

                if (services.get(0).topActivity.getPackageName().toString()
                        .equalsIgnoreCase(getApplicationContext().getPackageName().toString())) {
                    isActivityFound = true;
                }

                if (!isActivityFound) {
                    notificationManager.notify(notificationId, mBuilder.build());
                }

            }
        }

        private Intent getSenderMessagingActivity(final Context applicationContext, String senderId) {
            Log.d("GetSenderMessaging", senderId);
            final Intent[] intent = new Intent[1];
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", senderId);
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> user, com.parse.ParseException e) {
                    if (e == null) {
                        intent[0] = new Intent(applicationContext, MessagingActivity.class);
                        intent[0].putExtra("RECIPIENT_ID", user.get(0).getObjectId());
                    } else {
                        Toast.makeText(applicationContext,
                                "Error finding that user",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return intent[0];
        }


        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {

            final WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());

            //only add message to parse database if it doesn't already exist there
            ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessage");
            query.whereEqualTo("sinchId", message.getMessageId());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                    if (e == null) {
                        if (messageList.size() == 0) {
                            ParseObject parseMessage = new ParseObject("ParseMessage");
                            parseMessage.put("senderId", currentUserId);
                            parseMessage.put("recipientId", writableMessage.getRecipientIds().get(0));
                            parseMessage.put("messageText", writableMessage.getTextBody());
                            parseMessage.put("sinchId", writableMessage.getMessageId());
                            parseMessage.saveInBackground();

                            messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_OUTGOING);
                        }
                    }
                }
            });
        }

        @Override
        public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {}

        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }

    public static class VersionHelper
    {
        public static void refreshActionBarMenu(Activity activity)
        {
            activity.invalidateOptionsMenu();
        }
    }
}




