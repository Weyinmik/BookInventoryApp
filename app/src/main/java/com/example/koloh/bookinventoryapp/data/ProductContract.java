package com.example.koloh.bookinventoryapp.data;


import android.provider.BaseColumns;

//API contract for the Inventory App of Book Products
public final class ProductContract {

    //Empty constructor which prevents accidental instantiation of the contract class
    private ProductContract() {
    }

    /*
     * Inner class that defines constant values for the database table of the Inventory App
     * Each entry in the table represents a book product
     */
    public static final class BookEntry implements BaseColumns {

        //Name of the database table for books
        public final static String TABLE_NAME = "books";

        //Unique ID number for the book (only for use in the database table
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        /*Title of the book (Product Name)
        Type: TEXT*/
        public final static String COLUMN_BOOK_TITLE = "title";

        /*Author of the book
        Type: TEXT*/
        public final static String COLUMN_BOOK_AUTHOR = "author";

        /*Price of the book
        Type: TEXT*/
        public final static String COLUMN_BOOK_PRICE = "price";

        /*Quantity of books
        Type: INTEGER*/
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /*Name of the supplier of the book product
        Type: TEXT*/
        public final static String COLUMN_BOOK_SUPPLIER_NAME = "supplier_name";

        /*Phonenumber of the supplier of the book product
        Type: TEXT*/
        public final static String COLUMN_BOOK_SUPPLIER_PHONENUMBER = "supplier_phonenumber";

    }
}


