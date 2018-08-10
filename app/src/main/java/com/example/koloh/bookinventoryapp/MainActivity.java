package com.example.koloh.bookinventoryapp;


import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.koloh.bookinventoryapp.data.ProductContract;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Identifier for the book data loader
    private static final int PRODUCT_LOADER = 0;

    //Adapter for the ListView
    ProductCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        //Set up Floating Action Button to open the EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, ProductEditorActivity.class );
                startActivity ( intent );
            }
        } );

        //Find the ListView which will be populated with the book data
        ListView bookListView = (ListView) findViewById ( R.id.list );

        //Find and set empty view on the ListView, so that it only shows when the list has no items
        View emptyView = findViewById ( R.id.empty_view );
        bookListView.setEmptyView ( emptyView );

        //Set up an Adapter to create a list item for each row of book data in the Cursor.
        mCursorAdapter = new ProductCursorAdapter ( this, null );
        bookListView.setAdapter ( mCursorAdapter );

        //Set up the item click listener
        bookListView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to EditorActivity
                Intent intent = new Intent ( MainActivity.this, ProductEditorActivity.class );
                //Form the content URI that represents the specific book that was clicked on
                Uri currentBookUri = ContentUris.withAppendedId ( ProductContract.BookEntry.CONTENT_URI, id );
                //Set the URI of the data field of the intent
                intent.setData ( currentBookUri );
                //Launch the EditorActivity
                startActivity ( intent );
            }
        } );

        //Initialize the loader
        getLoaderManager ().initLoader ( PRODUCT_LOADER, null, this );

    }

    //Method to delete all books in the database
    private void deleteAllBooks() {
        int rowsDeleted = getContentResolver ().delete ( ProductContract.BookEntry.CONTENT_URI, null, null );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu options and add menu items to the app bar
        getMenuInflater ().inflate ( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //User clicked on a menu option (switch statement used for if any other options get added later)
        switch (item.getItemId ()) {
            case R.id.action_delete_all_books:
                deleteAllBooks ();
                return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //Define a projection that specifies the columns from the table that should be shown
        String[] projection = {
                ProductContract.BookEntry._ID,
                ProductContract.BookEntry.COLUMN_BOOK_TITLE,
                ProductContract.BookEntry.COLUMN_BOOK_PRICE,
                ProductContract.BookEntry.COLUMN_BOOK_QUANTITY};


        //This Loader will execute the ContentProvider's query method on the background thread
        return new CursorLoader ( this,
                ProductContract.BookEntry.CONTENT_URI,
                projection,
                null,
                null,
                null );
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
//Update ProductCursorAdapter with this new cursor containing updated book data
        mCursorAdapter.swapCursor ( data );
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor ( null );
    }
}


//References:

/*  Each of the Udacity lessons and work through has helped me greatly
 in understanding this project.
 https://github.com/udacity/ud845-Pets/tree/d9d1e99c77c1771cccdbb5dd3b2ca332d78dbfa0/app/src/main*/

/*<div>Icons made by <a href="https://www.flaticon.com/authors/picol" title="Picol">Picol</a> from
  <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="
  http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
*/
