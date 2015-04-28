package materialtest.theartistandtheengineer.co.materialtest.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.app.AppConfig;
import materialtest.theartistandtheengineer.co.materialtest.materialtest.ActivityUsingTabLibrary;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;


public class SingleBookActivity extends ActionBarActivity implements View.OnClickListener,DialogInterface.OnClickListener{

    private ProgressDialog pDialog;
    private ImageLoader mImageLoader;
    private ImageView mImageView;
    private VolleySingleton volleySingleton;

    private TextView tv_bookTitle;
    private TextView tv_bookAuthor;
    private TextView tv_isbn_13;

    private Spinner spinner;
    private Button button;
    private EditText sell_amount;

    private TextView t;

    private String bookTitle, bookAuthor, isbn_13, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        volleySingleton = VolleySingleton.getInstance();

        super.onCreate(savedInstanceState);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Get data from onClick inside listbooks view
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
            if (savedInstanceState == null) {
                bookTitle = bookAuthor = isbn_13 = url = null;
            } else {
                bookTitle = (String) savedInstanceState.getSerializable("bookTitle");
                bookAuthor = (String) savedInstanceState.getSerializable("bookAuthor");
                isbn_13 = (String) savedInstanceState.getSerializable("isbn_13");
                url = (String) savedInstanceState.getSerializable("url");
            }
        } else {
            bookTitle = (String) savedInstanceState.getSerializable("bookTitle");
            bookAuthor = (String) savedInstanceState.getSerializable("bookAuthor");
            isbn_13 = (String) savedInstanceState.getSerializable("isbn_13");
            url = (String) savedInstanceState.getSerializable("url");
        }

        // Used for debugging
        //Toast.makeText(this, bookTitle.toString() + "\n" + bookAuthor.toString() + "\n" + isbn_13.toString() + "\n" + url.toString(), Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_single_book);

        //addListenerOnButton();
        sell_amount = (EditText) findViewById(R.id.sell_amount);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        //addListenerOnSpinnerItemSelection();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        tv_bookTitle = (TextView) findViewById(R.id.bookTitle);
        tv_bookTitle.setText(bookTitle);
        tv_bookAuthor = (TextView) findViewById(R.id.bookAuthor);
        tv_bookAuthor.setText(bookAuthor);
        tv_isbn_13 = (TextView) findViewById(R.id.isbn_13);
        tv_isbn_13.setText(isbn_13);
        mImageView = (ImageView) findViewById(R.id.bookThumbnail);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(mImageView, R.drawable.ic_book215, R.drawable.ic_book219));



        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button.setOnClickListener(this);
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    // get the selected dropdown list value
    /*public void addListenerOnButton() {
        sell_amount = (EditText) findViewById(R.id.sell_amount);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SingleBookActivity.this,
                        "OnClickListener : " +
                                "\nSpinner: " + String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                if(sell_amount.length() > 0){
                    Log.d("asking price = ", sell_amount.getText().toString());
                    Toast.makeText(getApplicationContext(),
                            "Success", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter a selling price.", Toast.LENGTH_LONG)
                            .show();
                }
                //Log.d("condition = ", String.valueOf(spinner.getSelectedItem()));

            }
        });
    }*/

    /*public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_book, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Navigate up (back)
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (sell_amount.length() > 0){
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("Title: "+bookTitle+"\nAuthor: "+bookAuthor+"\nISBN: "+isbn_13+"\nPrice: $"+sell_amount.getText().toString()+"\nCondition: "+String.valueOf(spinner.getSelectedItem()))
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle("Confirm listing")
                    .setPositiveButton("Post", this)
                    .setNeutralButton("Cancel", this)
                    .setCancelable(false)
                    .create();

            ad.show();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "You must enter a price",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        switch(which){
            case DialogInterface.BUTTON_POSITIVE: // yes
                postSale(bookTitle, bookAuthor, isbn_13, sell_amount.getText().toString(), String.valueOf(spinner.getSelectedItem()));
                Toast.makeText(getApplicationContext(),
                        "Selected Sell",
                        Toast.LENGTH_LONG).show();
                break;

            case DialogInterface.BUTTON_NEUTRAL: // neutral

                Toast.makeText(getApplicationContext(),
                        "Selected Cancel",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                // nothing
                break;
        }
    }

    private void postSale(final String bookTitle, final String bookAuthor, final String isbn_13, final String sell_amount, final String condition) {
        pDialog.setMessage("Posting...");
        showDialog();
        hideDialog();
    }

    private class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show();*/

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}