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
import android.widget.TextView;
import android.widget.Toast;

import it.neokree.materialtabs.MaterialTabListener;
import materialtest.theartistandtheengineer.co.materialtest.R;


public class SingleBookActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
            intent.putExtra("bookTitle", bookDataArray[0]);
            intent.putExtra("bookAuthor", bookDataArray[1]);
            intent.putExtra("isbn_13", bookDataArray[2]);
            intent.putExtra("url", bookDataArray[3]);
         */
        //String newString;
        String bookTitle, bookAuthor, isbn_13, url;
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            savedInstanceState = getIntent().getExtras();
            if(savedInstanceState == null){
                //newString = null;
                bookTitle = bookAuthor = isbn_13 = url = null;

            }else{
                //newString = savedInstanceState.getString("bookData");
                bookTitle = savedInstanceState.getString("bookTitle");
                bookAuthor = savedInstanceState.getString("bookAuthor");
                isbn_13 = savedInstanceState.getString("isbn_13");
                url = savedInstanceState.getString("url");
            }}else{
               //newString = (String) savedInstanceState.getSerializable("bookData");
            bookTitle = (String) savedInstanceState.getSerializable("bookTitle");
            bookAuthor= (String) savedInstanceState.getSerializable("bookAuthor");
            isbn_13 = (String) savedInstanceState.getSerializable("isbn_13");
            url= (String) savedInstanceState.getSerializable("url");
            }

        Toast.makeText(this, bookTitle.toString()+"\n"+bookAuthor.toString()+"\n"+isbn_13.toString()+"\n"+url.toString(), Toast.LENGTH_SHORT).show();


        setContentView(R.layout.activity_single_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

}
