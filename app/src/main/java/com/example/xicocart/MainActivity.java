package com.example.xicocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtBarcode;
    private EditText txtDescription;
    private EditText txtBrand;
    private EditText txtCost;
    private EditText txtPrice;
    private EditText txtStock;
    private ListView lvProducts;
    private Button btnSave;
    private Product product;
    private ProductDAO productDAO;
    private ArrayList<String> dataOrigin;
    private ArrayAdapter<String> adapter;
GroceriesDbHelper DbHelper = new GroceriesDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBarcode = findViewById(R.id.txt_barcode);
        txtDescription = findViewById(R.id.txt_description);
        txtBrand = findViewById(R.id.txt_brand);
        txtCost = findViewById(R.id.txt_cost);
        txtPrice = findViewById(R.id.txt_price);
        txtStock = findViewById(R.id.txt_stock);
        lvProducts = findViewById(R.id.lv_products);
        productDAO = new ProductDAO(this);
        btnSave = findViewById(R.id.btn_save);
        updateList();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product = new Product(); //Objeto producto
                product.setBarcode(txtBarcode.getText().toString());
                product.setDescription(txtDescription.getText().toString());
                product.setBrand(txtBrand.getText().toString());
                product.setCost(Float.parseFloat(txtCost.getText().toString()));
                product.setPrice(Float.parseFloat(txtPrice.getText().toString()));
                product.setStock(Integer.parseInt(txtStock.getText().toString()));

                try {
                    if (productDAO.insertProduct(product)==true) {
                        Toast.makeText(MainActivity.this, "Producto almacenado con éxito", Toast.LENGTH_SHORT).show();
                        updateList();
                        clearFields();

                    } else {
                        Toast.makeText(MainActivity.this, "Servidor no disponible", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al insertar el producto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String barcode = (String)lvProducts.getItemAtPosition(position); //AL PRESIONAR DE LA LISTA NOS DARA UN EMERGENTE PRA COMPROBAR QUE SI ESTAREMOS CON UN BARCODE
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("barcode",barcode);
                startActivity(intent);

            }
        });

    }

    protected void updateList(){
        dataOrigin = productDAO.getAllBarcode();
        //contexto, diseño y datos
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataOrigin);
        lvProducts.setAdapter(adapter);//Le asignamos el adaptador ya que lo hizo interpretable


    }

    protected void clearFields(){
        txtBarcode.setText("");
        txtDescription.setText("");
        txtBrand.setText("");
        txtPrice.setText("");
        txtCost.setText("");
        txtStock.setText("");
        txtBarcode.requestFocus();
    }
}