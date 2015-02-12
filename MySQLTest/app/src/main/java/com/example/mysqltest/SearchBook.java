package com.example.mysqltest;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchBook extends Activity implements OnClickListener{

    // Layout elements
    private EditText bookData;
    private Button bSearch;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON Parser Class
    JSONParser jsonParser = new JSONParser();

    // Search page
    private static final String SEARCH_BOOK_URL = "http://theartistandtheengineer.co/webservice/findbook.php";

    // ID's that get pulled from the JSON output
    private static final String TAG_RESULT_COUNT = "result_count";
    private static final String TAG_TITLE = "title";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BEGIN = "begin";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_INDEX_SEARCHED = "index_searched";
    private static final String TAG_PAGE_COUNT = "page_count";
    private static final String TAG_DATA = "data";
    private static final String TAG_ISBN13 = "isbn13";
    private static final String TAG_AUTHOR_DATA = "author_data";
    private static final String TAG_AUTHOR_NAME = "name";
    private static final String TAG_AUTHOR_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book);

        bookData = (EditText) findViewById(R.id.bookData);
        bSearch = (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new PostBookSearch().execute();
    }

    class PostBookSearch extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchBook.this);
            pDialog.setMessage("Searching For Book...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            int result_count;

            String book_data = bookData.getText().toString();

            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("contents", book_data));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        SEARCH_BOOK_URL, "POST", params);

                Log.d("Search book attempt", json.toString());

                result_count = json.getInt(TAG_RESULT_COUNT);

                if(result_count == 0){
                    return "0";
                }
                else if(result_count > 0){
                    return json.toString();
                }
                else{
                    return null;
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String results){

            pDialog.dismiss();

            if(results == "0") {
                pDialog.dismiss();
                Toast.makeText(SearchBook.this, "No results found", Toast.LENGTH_LONG).show();
            }
            else if(results == null){
                pDialog.dismiss();
                Toast.makeText(SearchBook.this, "Please enter a title, author, or ISBN!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(SearchBook.this, "Successfully Searched!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SearchBook.this, ListBooks.class);
                i.putExtra("results", results);
                startActivity(i);
            }
        }
    }
}


