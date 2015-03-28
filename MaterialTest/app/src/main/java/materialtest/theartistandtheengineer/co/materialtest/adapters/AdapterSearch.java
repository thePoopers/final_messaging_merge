package materialtest.theartistandtheengineer.co.materialtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;
import materialtest.theartistandtheengineer.co.materialtest.pojo.Book;

/**
 * Created by mpcen-desktop on 3/27/15.
 */
public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolderBookSearch> {

    private ArrayList<Book> listBooks = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public AdapterSearch(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setBookList(ArrayList<Book> listBooks){
        this.listBooks = listBooks;
        notifyItemRangeChanged(0, listBooks.size());
    }

    @Override
    public ViewHolderBookSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_book, parent, false);
        ViewHolderBookSearch viewHolder = new ViewHolderBookSearch(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBookSearch holder, int position) {
        Book currentBook = listBooks.get(position);
        holder.bookTitle.setText(currentBook.getTitle());
        holder.bookAuthor.setText(currentBook.getAuthors());
        holder.isbn_13.setText(currentBook.getISBN_13());
        String urlThumbnail = currentBook.geturlThumbnail();
        if(urlThumbnail != null){
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener(){
                // if cant load image
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.bookThumbnail.setImageBitmap(response.getBitmap());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    static class ViewHolderBookSearch extends RecyclerView.ViewHolder {

        private ImageView bookThumbnail;
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView isbn_13;

        public ViewHolderBookSearch(View itemView) {
            super(itemView);
            bookThumbnail = (ImageView) itemView.findViewById(R.id.bookThumbnail);
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            bookAuthor = (TextView) itemView.findViewById(R.id.bookAuthor);
            isbn_13 = (TextView) itemView.findViewById(R.id.isbn_13);

        }
    }
}
