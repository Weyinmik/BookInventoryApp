package com.example.koloh.bookinventoryapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProductDbHelper extends SQLiteOpenHelper {

    //Name of the database file
    private static final String DATABASE_NAME = "bookstore.db";

    //Database version. If the database schema is changed, the version must be incremented.
    private static final int DATABASE_VERSION = 1;

    //Constructs a new instance of ProductDbHelper
    public ProductDbHelper(Context context) {
        super ( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a String that contains the SQL statement to create the books table
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + ProductContract.BookEntry.TABLE_NAME + " ("
                + ProductContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductContract.BookEntry.COLUMN_BOOK_TITLE + " TEXT NOT NULL, "
                + ProductContract.BookEntry.COLUMN_BOOK_AUTHOR + " TEXT NOT NULL, "
                + ProductContract.BookEntry.COLUMN_BOOK_PRICE + " REAL NOT NULL, "
                + ProductContract.BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME + " TEXT NOT NULL, "
                + ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONENUMBER + " TEXT NOT NULL);";

        //Execute the SQL statement
        db.execSQL ( SQL_CREATE_BOOKS_TABLE );

    }

    //This is called when the database needs to be updated.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.

    }
}
