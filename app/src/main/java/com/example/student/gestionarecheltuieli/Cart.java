package com.example.student.gestionarecheltuieli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;

import static android.R.attr.value;
import static com.example.student.gestionarecheltuieli.R.layout.activity_cart;

public class Cart extends AppCompatActivity {
//    private ArrayList<Property> cartProducts = new ArrayList<>();
    private ArrayAdapter<Property> cartadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_cart);

        cartadapter = new CartListAdapter(this, 0, MystaticVar.cartProd);
        //create property elements


        //create our new array adapter

        //Find list view and bind it with the custom adapter
        ListView cartList = (ListView) findViewById(R.id.cartList);
        cartList.setAdapter(cartadapter);
        Intent intent = getIntent();





    }


}
