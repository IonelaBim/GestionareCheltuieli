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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.ArrayList;

import static com.google.zxing.BarcodeFormat.QR_CODE;

//public class MainActivity extends AppCompactActivity implements View.OnClickListener {
public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button scanBtn;
    private ImageButton cartButton;
    private TextView cartCountView;
    private ArrayAdapter<Property> adapter;

    public void CountCartProduct(int count){
        cartCountView.setText(Integer.toString(count));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button) findViewById(R.id.scan_button);
        cartButton = (ImageButton) findViewById(R.id.cartButton);
        cartCountView = (TextView) findViewById(R.id.cartCount);

        CountCartProduct( MystaticVar.cartCount);
        scanBtn.setOnClickListener(this);
        cartButton.setOnClickListener(this);

        //create a new array adapter
        adapter = new CustomListAdapter(this, 0, MystaticVar.scanedProducts);

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //onClick Events
    @Override
    public void onClick(View v) {
        //Scan button (Open camera to scan)
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        //Cart button (change to Cart activity)
        if(v.getId()==R.id.cartButton){
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
            String scanFormat = scanningResult.getFormatName();
          //verify if scaned result is a QR_CODE
            if(scanningResult.getFormatName().compareTo(BarcodeFormat.QR_CODE.toString())!= 0)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Nu ati scanat un cod QR!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            String[] tokens = scanContent.split(";");
            int tokenCount = tokens.length;

            String img="no_image";
            if ((tokenCount >= 5) && (!tokens[4].equals("-"))) {
                img = tokens[4];
            }
            MystaticVar.scanedProducts.add(
                    new Property( tokens[0], tokens[1],Double.parseDouble(tokens[3]),1,img));
            adapter.notifyDataSetChanged();


        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
