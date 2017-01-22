package com.example.student.gestionarecheltuieli;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.student.gestionarecheltuieli.R.layout.activity_cart;

public class Cart extends AppCompatActivity {
    private ArrayAdapter<Property> cartAdapter;
    TextView textGrandTotal;
// calculate cart total amount
    public void totalAmount(){
        double sum=0;
        for (int i=0 ; i< cartAdapter.getCount(); i++){
            Object obj = cartAdapter.getItem(i);
            Double price = ((Property) obj).getPrice();
            int quantity = ((Property) obj).getProductCount();
            sum += price *quantity;
        }
        textGrandTotal.setText(Double.toString(sum) +"  Lei");
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_cart);

        cartAdapter = new CartListAdapter(this, 0, MystaticVar.cartProd);

        textGrandTotal = (TextView) findViewById(R.id.total);
        totalAmount();

        //Find list view and bind it with the custom adapter
        ListView cartList = (ListView) findViewById(R.id.cartList);
        cartList.setAdapter(cartAdapter);
        //Intent intent = getIntent();
        }
}
