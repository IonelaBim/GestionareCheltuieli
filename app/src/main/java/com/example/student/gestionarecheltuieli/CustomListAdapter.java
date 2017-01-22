package com.example.student.gestionarecheltuieli;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//custom ArrayAdapter
class CustomListAdapter extends ArrayAdapter<Property> {

    private Context context;
    private List<Property> scanedProducts;

    //constructor, call on creation
    public CustomListAdapter(Context context, int resource, ArrayList<Property> objects) {
        super(context, resource, objects);

        this.context = context;
        this.scanedProducts = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
       final Property property = scanedProducts.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.property_layout, null);

        //
        TextView name = (TextView) view.findViewById(R.id.product_name);
       final  TextView description = (TextView) view.findViewById(R.id.product_description);
        TextView product_price = (TextView) view.findViewById(R.id.product_price);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        Button addtoCart = (Button) view.findViewById(R.id.addtocart);

        //set product name
        String productName = property.getProductName();
        name.setText(productName);

        //display trimmed excerpt for product description
        //display trimmed excerpt for description
        int descriptionLength = property.getDescription().length();
        if(descriptionLength >= 50){
            String descriptionTrim = property.getDescription().substring(0, 50) + "(...)";
            description.setText(descriptionTrim);
        }else{
            description.setText(property.getDescription());
        }

        description.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                description.setText(property.getDescription());

            }
        });
        //set product price
        product_price.setText(String.valueOf(property.getPrice()) + "lei" );

        //get the image associated with this property
        int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
        if (imageID == 0){
            imageID = context.getResources().getIdentifier("no_image", "drawable", context.getPackageName());
        }
        image.setImageResource(imageID);

        // ADD button click event
        addtoCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MystaticVar.cartProd.add(new Property( property.getProductName(), property.getDescription(),property.getPrice(),property.getProductCount(),property.getImage()));

                MystaticVar.cartCount +=1;
                ((MainActivity)context).CountCartProduct(MystaticVar.cartCount);
                Toast.makeText(context,"Produsul a fost adaugat in cos" , Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}