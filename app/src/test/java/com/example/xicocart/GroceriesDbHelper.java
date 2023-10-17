package com.example.xicocart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;

public class GroceriesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="groceries.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_PRODUCTS ="CREATE TABLE " + GroceriesContract.Product.TABLE_NAME + " ( "+
            GroceriesContract.Product.COLUMN_NAME_BARCODE + " TEXT PRIMARY KEY, "+
            GroceriesContract.Product.COLUMN_NAME_DESCRIPTION + " TEXT, "+
            GroceriesContract.Product.COLUMN_NAME_BRAND + " TEXT, "+
            GroceriesContract.Product.COLUMN_NAME_COST + "FLOAT, "+
            GroceriesContract.Product.COLUMN_NAME_PRICE + "FLOAT, "+
            GroceriesContract.Product.COLUMN_NAME_STOCK + "INT )";

    public static final String SQL_DELETE_PRODUCTS = "DROP TABLE IF EXISTS";

    public GroceriesDbHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }
}
