package com.example.student.gestionarecheltuieli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.ArrayList;

//public class MainActivity extends AppCompactActivity implements View.OnClickListener {
public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button scanBtn;
    private ImageButton cartButton;
    private ArrayList<Property> rentalProperties = new ArrayList<>();
    private ArrayAdapter<Property> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scan_button);
        cartButton = (ImageButton) findViewById(R.id.cartButton);

        //formatTxt = (TextView)findViewById(R.id.scan_format);
        //contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        adapter = new CustomListAdapter(this, 0, rentalProperties);
        //create property elements

        rentalProperties.add(
                new Property("Sunny Street", "Come and see this amazing studio ", 360.00,1, "property_image_4"));

        //create our new array adapter

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
//add event listener so we can handle clicks




    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            System.out.println("scannn");
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        if(v.getId()==R.id.cartButton){
            System.out.println("change to cart");
            //Intent myIntent = new Intent(this, NewActivity.class);

            Intent intent = new Intent(this,Cart.class);
            intent.putExtra("firstName", "Your First Name Here");
            intent.putExtra("lastName", "Your Last Name Here");
            startActivity(intent);
        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            //String scanFormat = scanningResult.getFormatName();
//

            String[] tokens = scanContent.split(";");
            int tokenCount = tokens.length;



            rentalProperties.add(
                    new Property( tokens[0], tokens[1],Double.parseDouble(tokens[3]),1, "property_image_4"));
            adapter.notifyDataSetChanged();


        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
