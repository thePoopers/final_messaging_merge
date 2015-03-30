package com.example.mysqltest;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListBooks extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON IDS:
    private static final String TAG_RESULT_COUNT = "result_count";
    private static final String TAG_TITLE = "title";
    private static final String TAG_BEGIN = "begin";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_INDEX_SEARCHED = "index_searched";
    private static final String TAG_PAGE_COUNT = "page_count";
    private static final String TAG_DATA = "data";
    private static final String TAG_ISBN13 = "isbn13";
    private static final String TAG_AUTHOR_DATA = "author_data";
    private static final String TAG_AUTHOR_NAME = "name";
    private static final String TAG_AUTHOR_ID = "id";

    // An array of each book post
    private JSONArray booksArray = null;
    // Manages all of the results in a list
    private ArrayList<HashMap<String, String>> booksList;
    String newString;

    // Needed to pass data from one intent to another
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_results);

        // Get the data from last intent
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("results");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("results");
        }
        Log.d("newString", newString);
    }

    @Override
    protected void onResume(){
        super.onResume();
        new LoadComments().execute();
    }

    // Retrieves book results from search
    public void updateJSONdata() {

        booksList = new ArrayList<HashMap<String, String>>();

        try{
            JSONObject json = new JSONObject(newString);
            booksArray = json.getJSONArray(TAG_DATA);

            for(int i = 0; i < booksArray.length(); i++){
                JSONObject c = booksArray.getJSONObject(i);

                String title = c.getString(TAG_TITLE);
                String isbn13 = c.getString(TAG_ISBN13);
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_TITLE, title);
                map.put(TAG_ISBN13, isbn13);

                booksList.add(map);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void updateList(){

        ListAdapter adapter = new SimpleAdapter(this, booksList,
                R.layout.single_book, new String[] {TAG_TITLE, TAG_ISBN13},
                new int[] {R.id.title, R.id.isbn13});

        setListAdapter(adapter);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txtview = (TextView)view.findViewById(R.id.isbn13);
                String test = txtview.getText().toString();
                Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();

                // This method is triggered if an item is click within our
                // list. For our example we won't be using this, but
                // it is useful to know in real life applications.

            }
        });
    }

    public class LoadComments extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(ListBooks.this);
            pDialog.setMessage("Loading Books...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {

                updateJSONdata();

            return null;
        }

        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
    }

}


