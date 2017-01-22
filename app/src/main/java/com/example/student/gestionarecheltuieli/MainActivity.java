package com.example.student.gestionarecheltuieli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


//public class MainActivity extends AppCompactActivity implements View.OnClickListener {
public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button scanBtn, closeBtn;
    private ImageButton cartButton;
    private TextView productCartCounter;
    private ArrayAdapter<Property> adapter;

    public void CountCartProducts(int count){
        productCartCounter.setText(Integer.toString(count));
    };

    //onCreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //any call you make to findViewById will lookup the id from content view (set with setContentView)
        closeBtn = (Button) findViewById(R.id.close_btn);
        scanBtn = (Button) findViewById(R.id.scan_button);
        cartButton = (ImageButton) findViewById(R.id.cartButton);
        productCartCounter = (TextView) findViewById(R.id.cartCount);

        //set buttons click listeners
        scanBtn.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        closeBtn.setOnClickListener(this);

        //set  products cart number
        CountCartProducts( MystaticVar.cartCount);

        //create a custom array adapter
        adapter = new CustomListAdapter(this, 0, MystaticVar.scanedProducts);

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //onClick Events
    @Override
    public void onClick(View v) {
       // implement the OnClickListener callback
        //Close button (close application)
        if(v.getId()==R.id.close_btn){
            finish();
            System.exit(0);
        }
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
            if(scanFormat.compareTo(BarcodeFormat.QR_CODE.toString())!= 0)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Nu ati scanat un cod QR!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            String[] tokens = scanContent.split(";");
            int tokenCount = tokens.length;
            //verify if scanned result has a valid format(length; data types)
            if (tokenCount < 4){
                Toast toast = Toast.makeText(getApplicationContext(),"Codul scanat nu este valid", Toast.LENGTH_LONG);
                toast.show();
                return;
            }else {
                String img = "no_image";
                if (!tokens[3].equals("-")) {
                    img = tokens[3];
                }
                try {
                    double price = Double.parseDouble(tokens[2]);
                    MystaticVar.scanedProducts.add(new Property(tokens[0], tokens[1], price, 1, img));
                    adapter.notifyDataSetChanged();

                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Codul scanat nu este valid", Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace(); //prints error
                    return;

                }

            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nu s-au primit date de la scaner", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
    }

}
