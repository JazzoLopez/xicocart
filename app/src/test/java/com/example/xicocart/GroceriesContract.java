package com.example.xicocart;

import android.provider.BaseColumns;

public class GroceriesContract {
    private GroceriesContract(){} //No podemos heredar pero si instanciar
        public static final class Product implements BaseColumns{  //Interfaz
        //POR CADA ATRIBUTO DEBEMOS PONER UNA CONSTANTE
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_STOCK = "stock";


    }
}
