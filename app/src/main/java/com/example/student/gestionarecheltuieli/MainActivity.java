package com.example.student.gestionarecheltuieli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        scanBtn = (Button)findViewById(R.id.scan_button);
        cartButton = (ImageButton)findViewById(R.id.cartButton);

        //formatTxt = (TextView)findViewById(R.id.scan_format);
        //contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        adapter = new CustomListAdapter(this, 0, rentalProperties);
        //create property elements
        rentalProperties.add(
                new Property( "Smith Street", "A large 3 bedroom apa.", 450.00, "property_image_1"));

        rentalProperties.add(
                new Property( "King Street","A fully furnished studio apartment overl.", 320.00, "property_image_2"));

        rentalProperties.add(
                new Property( "Liverpool Road",   "A standard 3 bedroom house in ", 360.00, "property_image_3"));

        rentalProperties.add(
                new Property( "Sunny Street", "Come and see this amazing studio ", 360.00, "property_image_4"));

        //create our new array adapter

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, listItems);

//       // adapter = new ArrayAdapter<String>(this,android.R.layout.list_row, android.R.id.from_name, listItems);
//        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
//        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(adapter);


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
            Intent intent = new Intent(this,Cart.class);
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
//            itemname.add(scanContent);


            rentalProperties.add(
                    new Property( "Sunny Street", "Come and see this amazing studio ", 360.00, "property_image_4"));
            adapter.notifyDataSetChanged();


        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
