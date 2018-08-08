package com.example.koloh.bookinventoryapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.koloh.bookinventoryapp.data.ProductContract;
import com.example.koloh.bookinventoryapp.data.ProductDbHelper;


public class MainActivity extends AppCompatActivity {

    //This Database helper that provides access to the database
    private ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        //Setting up the Floating Action Bar to open the ProductEditorActivity
        FloatingActionButton fab = findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, ProductEditorActivity.class );
                startActivity ( intent );
            }
        } );

        /* To access the database, SQLiteOpenHelper subclass is instantiated
         and passed to context, which is the current activity.*/
        mDbHelper = new ProductDbHelper ( this );
    }

    @Override
    protected void onStart() {
        super.onStart ();
        displayDatabaseInfo ();
    }

    //Temporary helper method to check the amount of entries in the database
    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase ();

        //Projecting the specific columns that must be read from the database
        String[] projection = {
                ProductContract.BookEntry._ID,
                ProductContract.BookEntry.COLUMN_BOOK_TITLE,
                ProductContract.BookEntry.COLUMN_BOOK_AUTHOR,
                ProductContract.BookEntry.COLUMN_BOOK_PRICE,
                ProductContract.BookEntry.COLUMN_BOOK_QUANTITY,
                ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME,
                ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONENUMBER};

        //Perform a query on the books table
        Cursor cursor = db.query (
                ProductContract.BookEntry.TABLE_NAME, projection, null, null, null, null, null );

        TextView displayView = findViewById ( R.id.text_view_product );


        try {
            displayView.setText ( "The Book Table contains " + cursor.getCount () + " book(s).\n\n" );
            displayView.append ( ProductContract.BookEntry._ID + " - " + ProductContract.BookEntry.COLUMN_BOOK_TITLE + "\n" );

            //Sort out the index of the _ID column and the title column.
            int idColumnIndex = cursor.getColumnIndex ( ProductContract.BookEntry._ID );
            int titleColumnIndex = cursor.getColumnIndex ( ProductContract.BookEntry.COLUMN_BOOK_TITLE );

            //Iterate through the returned rows in the cursor.
            while (cursor.moveToNext ()) {
                int currentID = cursor.getInt ( idColumnIndex );
                String currentTitle = cursor.getString ( titleColumnIndex );
                //Display the values from the _ID and title column of the current row in the TextView
                displayView.append ( ("\n" + currentID + " - " + currentTitle) );
            }
        } finally {
            cursor.close ();
        }


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
