package com.example.student.gestionarecheltuieli;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CartListAdapter extends ArrayAdapter<Property> {

        private Context context;
        private List<Property> cartProducts;

        //constructor, call on creation
        public CartListAdapter(Context context, int resource, ArrayList<Property> objects) {
            super(context, resource, objects);

            this.context = context;
            this.cartProducts = objects;
        }

        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the property we are displaying
           final Property property = cartProducts.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.property_cart_layout, null);

            TextView name = (TextView) view.findViewById(R.id.product_name);
           final TextView description = (TextView) view.findViewById(R.id.product_description);
            TextView product_price = (TextView) view.findViewById(R.id.product_price);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            ImageView cart_minus = (ImageView) view.findViewById(R.id.cart_minus);
            ImageView cart_plus = (ImageView) view.findViewById(R.id.cart_plus);
           final TextView quantityOfProd = (TextView) view.findViewById(R.id.cart_product_quantity);

            //set product name
            String productName = property.getProductName();
            name.setText(productName);

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
            quantityOfProd.setText(String.valueOf(property.getProductCount()));
            //get the image associated with this property
            int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            if (imageID == 0){
                imageID = context.getResources().getIdentifier("no_image", "drawable", context.getPackageName());
            }
            image.setImageResource(imageID);

            cart_plus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int current = property.getProductCount()+1;
                    quantityOfProd.setText(String.valueOf(current));
                    property.setProductQuatity(current);
                    notifyDataSetChanged();
                    if(context instanceof Cart){
                        ((Cart)context).totalAmount();
                    }
                }
            });

            cart_minus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int current = property.getProductCount()-1;
                    if(current == 0) {
                        Integer index = (Integer) v.getTag();
                        cartProducts.remove(property);
                        notifyDataSetChanged();
                        if(context instanceof Cart) {
                            ((Cart) context).totalAmount();
                        }
                        MystaticVar.cartCount -= 1;
                        Toast.makeText(context, "Produsul a fost sters din cos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    quantityOfProd.setText(String.valueOf(current));
                    property.setProductQuatity(current);
                    notifyDataSetChanged();
                    if(context instanceof Cart){
                        ((Cart)context).totalAmount();
                    }

                }


            });
            return view;
        }

    }

