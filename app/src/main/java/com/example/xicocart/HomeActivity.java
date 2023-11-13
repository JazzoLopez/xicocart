package com.example.xicocart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private EditText txtBarcode;
    private EditText txtDescription;
    private EditText txtBrand;
    private EditText txtCost;
    private EditText txtPrice;
    private EditText txtStock;
    private Product product;
    private ProductDAO productDAO;

    private Button buttonUpdate;
    private Button buttonDelete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonUpdate = findViewById(R.id.btn_update);
        buttonDelete = findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);

                if (productDAO.deleteProduct(product)) {
                    Toast.makeText(HomeActivity.this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            };

        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los datos de los campos de texto
                String newDescription = txtDescription.getText().toString();
                String newBrand = txtBrand.getText().toString();
                float newCost = Float.parseFloat(txtCost.getText().toString());
                float newPrice = Float.parseFloat(txtPrice.getText().toString());
                int newStock = Integer.parseInt(txtStock.getText().toString());

                // Actualiza los datos del producto
                if (product != null) {
                    product.setDescription(newDescription);
                    product.setBrand(newBrand);
                    product.setCost(newCost);
                    product.setPrice(newPrice);
                    product.setStock(newStock);

                    // Llama al método de actualización en el DAO
                    productDAO.updateProduct(product);

                    // Notifica al usuario que la actualización se realizó correctamente
                    Toast.makeText(HomeActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtBarcode=findViewById(R.id.txt_barcode);
        txtDescription=findViewById(R.id.txt_description);
        txtBrand=findViewById(R.id.txt_brand);
        txtCost=findViewById(R.id.txt_cost);
        txtPrice=findViewById(R.id.txt_price);
        txtStock=findViewById(R.id.txt_stock);
        buttonDelete=findViewById(R.id.btn_delete);
        String barcode=getIntent().getExtras().getString("barcode");
        productDAO=new ProductDAO(this);
        product=productDAO.getProductByBarcode(barcode);

        if(product!=null){
            txtBarcode.setText(product.getBarcode());
            txtDescription.setText(product.getDescription());
            txtBrand.setText(product.getBrand());
            txtCost.setText(String.valueOf(product.getCost()));
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtStock.setText(String.valueOf(product.getStock()));


        }


    }
}