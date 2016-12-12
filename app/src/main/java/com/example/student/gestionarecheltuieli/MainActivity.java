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
    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();
    String[] itemname = {
            "Safari",
            "Camera",
            "Global",

    };
    Integer[] imgid={
            R.drawable.ic_plus,
            R.drawable.cart,
            R.drawable.ic_minus,

    };
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

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, listItems);

       // adapter = new ArrayAdapter<String>(this,android.R.layout.list_row, android.R.id.from_name, listItems);
        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
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
           // itemname.add(scanContent);
//            adapter.notifyDataSetChanged();


        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
