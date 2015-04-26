package materialtest.theartistandtheengineer.co.materialtest.activities;

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

import com.android.volley.toolbox.ImageLoader;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;


public class SingleBookActivity extends ActionBarActivity {

    private ImageLoader mImageLoader;
    private ImageView mImageView;
    private VolleySingleton volleySingleton;

    private TextView tv_bookTitle;
    private TextView tv_bookAuthor;
    private TextView tv_isbn_13;

    private Spinner spinner;
    private Button button;
    private EditText sell_amount;

    private String bookTitle, bookAuthor, isbn_13, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        volleySingleton = VolleySingleton.getInstance();

        super.onCreate(savedInstanceState);

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

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();


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
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
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

                Log.d("condition = ", String.valueOf(spinner.getSelectedItem()));
                Log.d("asking price = ", sell_amount.getText().toString());
            }
        });
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

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

    private class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}