package com.example.student.gestionarecheltuieli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.student.gestionarecheltuieli.R.layout.activity_cart;

public class Cart extends AppCompatActivity {
//    private ArrayList<Property> cartProducts = new ArrayList<>();
    private ArrayAdapter<Property> cartadapter;
    TextView textGrandTotal;
    public void sdada(){
        double sum=0;
        for (int i=0 ; i< cartadapter.getCount(); i++){
            Object obj = cartadapter.getItem(i);
            System.out.println(obj);
            Double price = ((Property) obj).getPrice();
            int quantity = ((Property) obj).getProductCount();
            sum += price *quantity;
        }
        textGrandTotal.setText(Double.toString(sum));
        //return sum;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_cart);

        cartadapter = new CartListAdapter(this, 0, MystaticVar.cartProd);
        //create property elements

        textGrandTotal = (TextView) findViewById(R.id.total);
        sdada();
        //textGrandTotal.setText(Double.toString(sdada()));
        //create our new array ada
        //Find list view and bind it with the custom adapter
        ListView cartList = (ListView) findViewById(R.id.cartList);
        cartList.setAdapter(cartadapter);
        Intent intent = getIntent();

//        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                sdada();
//               // textGrandTotal.setText(Double.toString(sdada()));
//            }
//        });

        }
}
