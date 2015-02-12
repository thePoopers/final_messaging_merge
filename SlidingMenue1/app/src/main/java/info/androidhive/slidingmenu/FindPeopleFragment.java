package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindPeopleFragment extends Fragment implements OnClickListener {

    //Layout elements
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


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_find_people, container, false);

        bookData = (EditText) rootView.findViewById(R.id.bookData);
        bSearch = (Button) rootView.findViewById(R.id.bSearch);
        bSearch.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        new PostBookSearch().execute();
    }

    class PostBookSearch extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
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
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_LONG).show();
            }
            else if(results == null){
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Please enter a title, author, or ISBN!", Toast.LENGTH_LONG).show();
            }
            else{

                Toast.makeText(getActivity(), "Search successful!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);
                i.putExtra("results", results);
                getActivity().startActivity(i);
            }
        }
    }
}
