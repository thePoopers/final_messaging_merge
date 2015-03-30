package materialtest.theartistandtheengineer.co.materialtest.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import it.neokree.materialtabs.MaterialTabListener;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        volleySingleton = VolleySingleton.getInstance();

        /*
            intent.putExtra("bookTitle", bookDataArray[0]);
            intent.putExtra("bookAuthor", bookDataArray[1]);
            intent.putExtra("isbn_13", bookDataArray[2]);
            intent.putExtra("url", bookDataArray[3]);
         */
        //String newString;
        String bookTitle, bookAuthor, isbn_13, url;
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
            if (savedInstanceState == null) {
                //newString = null;
                bookTitle = bookAuthor = isbn_13 = url = null;

            } else {
                //newString = savedInstanceState.getString("bookData");
                bookTitle = (String) savedInstanceState.getSerializable("bookTitle");
                bookAuthor = (String) savedInstanceState.getSerializable("bookAuthor");
                isbn_13 = (String) savedInstanceState.getSerializable("isbn_13");
                url = (String) savedInstanceState.getSerializable("url");
            }
        } else {
            //newString = (String) savedInstanceState.getSerializable("bookData");
            bookTitle = (String) savedInstanceState.getSerializable("bookTitle");
            bookAuthor = (String) savedInstanceState.getSerializable("bookAuthor");
            isbn_13 = (String) savedInstanceState.getSerializable("isbn_13");
            url = (String) savedInstanceState.getSerializable("url");
        }

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
        mImageLoader = volleySingleton.getInstance().getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(mImageView, R.drawable.ic_book215, R.drawable.ic_book219));

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SingleBookActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

        }});
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

    /*
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtAuthor;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            txtAuthor = (TextView) itemView.findViewById(R.id.bookAuthor);
        }
    }*/


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