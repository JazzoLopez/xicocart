package com.example.xicocart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProductDAO {
    private Context context;
    private GroceriesDbHelper dbHelper;
    private SQLiteDatabase db;
    public ProductDAO(Context context){

        this.context=context; //CONSTRUCTOR PORQUE EL HELPER REQUIERE UN CONTEXTO
        dbHelper = new GroceriesDbHelper(context);
    }

    public boolean insertProduct(Product product){
        boolean result = false;
        db = dbHelper.getWritableDatabase();//acceso de escritura
        ContentValues values = new ContentValues(); //llave , valor
        values.put(GroceriesContract.Product.COLUMN_NAME_BARCODE, product.getBarcode());
        values.put(GroceriesContract.Product.COLUMN_NAME_DESCRIPTION,product.getDescription());
        values.put(GroceriesContract.Product.COLUMN_NAME_BRAND, product.getBrand());
        values.put(GroceriesContract.Product.COLUMN_NAME_COST, product.getCost());
        values.put(GroceriesContract.Product.COLUMN_NAME_PRICE, product.getPrice());
        values.put(GroceriesContract.Product.COLUMN_NAME_STOCK, product.getStock());


        long newRowId = db.insert(GroceriesContract.Product.TABLE_NAME,null,values);
             if(newRowId != -1)
                result = true;
             return result;
             
    }

    public ArrayList getAllBarcode(){
        ArrayList<String> result = new ArrayList<String>();
        db = dbHelper.getReadableDatabase();
        String[] projection = {
                GroceriesContract.Product.COLUMN_NAME_BARCODE //Filtro por codigo de barras
        };

        Cursor cursor = db.query(
                GroceriesContract.Product.TABLE_NAME,
                projection, //Solo el campo de
                null,
                null,
                null,
                null,
                null
        );

        while(cursor.moveToNext())
        {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

    public Product getProductByBarcode (String barcode){
        Product result = null;
        String selection = GroceriesContract.Product.COLUMN_NAME_BARCODE + " =?";//El signo se sustituye por el parametro recibido
        String [] selectionArgs = {barcode};//Este es el parametro que recibe
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                GroceriesContract.Product.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.moveToNext()){
            result = new Product();
            result.setBarcode(cursor.getString(0));
            result.setDescription(cursor.getString(1));
            result.setBrand(cursor.getString(2));
            result.setCost(cursor.getFloat(3));
            result.setPrice(cursor.getFloat(4));
            result.setStock(cursor.getInt(5));

        }

        cursor.close();
        return result;
    }

    public boolean updateProduct(Product product) {
        boolean result = false;
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GroceriesContract.Product.COLUMN_NAME_DESCRIPTION, product.getDescription());
        values.put(GroceriesContract.Product.COLUMN_NAME_BRAND, product.getBrand());
        values.put(GroceriesContract.Product.COLUMN_NAME_COST, product.getCost());
        values.put(GroceriesContract.Product.COLUMN_NAME_PRICE, product.getPrice());
        values.put(GroceriesContract.Product.COLUMN_NAME_STOCK, product.getStock());
        String selection = GroceriesContract.Product.COLUMN_NAME_BARCODE + " = ?";
        String[] selectionArgs = {product.getBarcode()};

        int count = db.update(GroceriesContract.Product.TABLE_NAME, values, selection, selectionArgs);

        if (count > 0) {
            result = true;
        }

        return result;
    }


    public boolean deleteProduct(Product product){
        boolean result = false;
        db = dbHelper.getWritableDatabase();
        String selection = GroceriesContract.Product.COLUMN_NAME_BARCODE + " = ?";
        String[] selectionArgs = {product.getBarcode()};
        int count = db.delete(GroceriesContract.Product.TABLE_NAME, selection, selectionArgs);

        if(count >0){
            result = true;
        }
        return result;
    }


}