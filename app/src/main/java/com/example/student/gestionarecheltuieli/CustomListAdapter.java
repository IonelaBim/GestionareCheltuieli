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

        //find view elements by id
        TextView name = (TextView) view.findViewById(R.id.product_name);
        final  TextView description = (TextView) view.findViewById(R.id.product_description);
        TextView product_price = (TextView) view.findViewById(R.id.product_price);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        Button addtoCart = (Button) view.findViewById(R.id.addtocart);

        //set product name
        String productName = property.getProductName();
        name.setText(productName);

        //display trimmed excerpt for product description
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

        //get the image associated with this property(if image is not find in the drawable folder, we will show default image called  "no_image" )
        int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
        if (imageID == 0){
            imageID = context.getResources().getIdentifier("no_image", "drawable", context.getPackageName());
        }
        image.setImageResource(imageID);

        // ADD button click event
        addtoCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean addProductToCart = true;
                if(!MystaticVar.cartProd.isEmpty()){ //if cart is NOT empty(it has products)
                    for(Property product : MystaticVar.cartProd) {
                       //verify if product is already in the cart
                        if (product.getProductName().equals(property.getProductName()) &&
                                product.getDescription().equals(property.getDescription()) &&
                                product.getPrice().equals(property.getPrice()) &&
                                product.getImage().equals(property.getImage()))
                        {
                            //if product exists, then increase quantity value for this product
                            addProductToCart = false;
                            product.setProductQuatity(product.getProductCount() + 1);
                            MystaticVar.cartCount +=1;
                            //update products cart number
                            ((MainActivity)context).CountCartProducts(MystaticVar.cartCount);
                            Toast.makeText(context, "Produsul exista deja in cos. Acum cosul dumneavoastra contine mai multe produse de acest tip.", Toast.LENGTH_SHORT).show();

                        }
                    }
                   //if product is'n find in the cart, then  it will be added to cart
                    if(addProductToCart == true){
                        MystaticVar.cartProd.add(new Property( property.getProductName(), property.getDescription(),property.getPrice(),property.getProductCount(),property.getImage()));
                        MystaticVar.cartCount +=1;
                        ((MainActivity)context).CountCartProducts(MystaticVar.cartCount);
                        Toast.makeText(context,"Produsul a fost adaugat in cos" , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // if cart is empty then add the first product
                    MystaticVar.cartProd.add(new Property( property.getProductName(), property.getDescription(),property.getPrice(),property.getProductCount(),property.getImage()));
                    MystaticVar.cartCount +=1;
                    ((MainActivity)context).CountCartProducts(MystaticVar.cartCount);
                    Toast.makeText(context,"Primul produs a fost adaugat in cos." , Toast.LENGTH_SHORT).show();

                }

            }
        });

        return view;
    }
}