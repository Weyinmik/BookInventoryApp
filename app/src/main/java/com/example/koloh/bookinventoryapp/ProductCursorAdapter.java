package com.example.koloh.bookinventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koloh.bookinventoryapp.data.ProductContract;

public class ProductCursorAdapter extends CursorAdapter {

    //Constructs a new ProductCursorAdapter
    public ProductCursorAdapter(Context context, Cursor c) {
        super ( context, c, 0 );
    }

    //Makes a new black list item view. No data is set to the views yet
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from ( context ).inflate ( R.layout.product_list_item, parent, false );
    }

    //This method binds the book data to the given list item layout
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //Find the individual views that we want to modify in the product_list_item layout
        TextView titleTextView = (TextView) view.findViewById ( R.id.title );
        TextView priceTextView = (TextView) view.findViewById ( R.id.price );
        TextView quantityTextView = (TextView) view.findViewById ( R.id.quantity );
        Button saleButton = (Button) view.findViewById ( R.id.sale_button );

        //Find the columns of book attributes we want to display
        int titleColumnIndex = cursor.getColumnIndex ( ProductContract.BookEntry.COLUMN_BOOK_TITLE );
        int priceColumnIndex = cursor.getColumnIndex ( ProductContract.BookEntry.COLUMN_BOOK_PRICE );
        int quantityColumnIndex = cursor.getColumnIndex ( ProductContract.BookEntry.COLUMN_BOOK_QUANTITY );

        //Read the book attributes from the cursor for the current book
        String bookTitle = cursor.getString ( titleColumnIndex );
        String productTitle = "Book Title : " + String.valueOf ( bookTitle );
        Float bookPrice = cursor.getFloat ( priceColumnIndex );
        String productPrice = "Price : " + String.valueOf ( bookPrice ) + " EUR";
        String bookQuantity = cursor.getString ( quantityColumnIndex );
        String quantityProduct = "Copies Available : " + String.valueOf ( bookQuantity );

        //Update the TextViews with the attributes for the current book
        titleTextView.setText ( productTitle );
        priceTextView.setText ( productPrice );
        quantityTextView.setText ( quantityProduct );

        final int quantity = Integer.valueOf ( bookQuantity );
        final int currentBookId = cursor.getInt ( cursor.getColumnIndex ( ProductContract.BookEntry._ID ) );

//Set up the sale button to decrement when the user clicks on it
        saleButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //Let's the user only decrement when the quantity of books is > 0
                if (quantity > 0) {
                    int decrementedQuantity = quantity - 1;

                    //Get the URI with the ID for the row
                    Uri quantityUri = ContentUris.withAppendedId ( ProductContract.BookEntry.CONTENT_URI, currentBookId );

                    //Update the database with the new quantity value
                    ContentValues values = new ContentValues ();
                    values.put ( ProductContract.BookEntry.COLUMN_BOOK_QUANTITY, decrementedQuantity );
                    context.getContentResolver ().update ( quantityUri, values, null, null );
                } else {
                    //Show an error when the quantity reaches 0
                    Toast.makeText ( context, R.string.out_of_stock_message, Toast.LENGTH_SHORT ).show ();
                }
            }
        } );
    }
}