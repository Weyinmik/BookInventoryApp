package com.example.koloh.bookinventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koloh.inventoryapp.data.ProductContract;
import com.example.koloh.inventoryapp.data.ProductDbHelper;

import static com.example.koloh.inventoryapp.data.ProductContract.BookEntry.TABLE_NAME;


public class ProductEditorActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;

    // EditText field to enter the title of the book product
    private EditText mTitleEditText;

    //EditText field to enter the author of the book product
    private EditText mAuthorEditText;

    //EditText field to enter the price of the book product
    private EditText mPriceEditText;

    //EditText field to enter the quantity of this type of book book product
    private EditText mQuantityEditText;

    //EditText field to enter the name of the supplier of the book book product
    private EditText mSupplierNameEditText;

    //EditText field to enter the phonenumber of the supplier of the book product
    private EditText mSupplierPhonenumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_editor );

        //Find the relevant views that will be needed for the user to use the editor
        mTitleEditText = findViewById ( R.id.edit_book_title );
        mAuthorEditText = findViewById ( R.id.edit_book_author );
        mPriceEditText = findViewById ( R.id.edit_book_price );
        mQuantityEditText = findViewById ( R.id.edit_book_quantity );
        mSupplierNameEditText = findViewById ( R.id.edit_book_supplier_name );
        mSupplierPhonenumberEditText = findViewById ( R.id.edit_book_supplier_phonenumber );
    }

    //Get user input from the editor and save a new book into the database
    private void insertProductName() {
        /*Read from input fields and
        use trim to eliminate leading or trailing with white space*/

        String titleString = mTitleEditText.getText ().toString ().trim ();
        String authorString = mAuthorEditText.getText ().toString ().trim ();
        String priceString = mPriceEditText.getText ().toString ().trim ();
        String quantityString = mQuantityEditText.getText ().toString ().trim ();
        int quantity = Integer.parseInt ( quantityString );
        String supplierNameString = mSupplierNameEditText.getText ().toString ().trim ();
        String supplierPhonenumberString = mSupplierPhonenumberEditText.getText ().toString ().trim ();

        //Create database helper
        mDbHelper = new ProductDbHelper ( this );


        //Make the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase ();

        //Create a ContentValues object where column names are the keys, and the book attributes from the editor the values.
        ContentValues values = new ContentValues ();
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_TITLE, titleString );
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_AUTHOR, authorString );
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_PRICE, priceString );
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_QUANTITY, quantity );
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierNameString );
        values.put ( ProductContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONENUMBER, supplierPhonenumberString );

        //Insert a new row for the book in the database, returning the ID of that new row.
        long newRowId = db.insert ( TABLE_NAME, null, values );

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with serting row.
            Toast.makeText ( this, R.string.error_toast_message, Toast.LENGTH_SHORT ).show ();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText ( this, (int) (R.string.save_toast_message + newRowId), Toast.LENGTH_SHORT ).show ();
        }
    }

    /**
     * Method to delete all book products data from the database.
     * To be properly implemented on Stage 2 of the App
     */
    private void deleteAllEntries() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase ();
        db.delete ( TABLE_NAME, null, null );
        db.execSQL ( "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'" );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar of the product_editor.xml.
        getMenuInflater ().inflate ( R.menu.product_editor, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId ()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertProductName ();
                // Exit activity
                finish ();
                return true;

            /* Respond to a click on the "Delete all entries" menu option
             *  This to be properly implemented on Stage2 of the App*/
            case R.id.action_delete_all_entries:
                deleteAllEntries ();

                return true;
        }
        return super.onOptionsItemSelected ( item );
    }
}
