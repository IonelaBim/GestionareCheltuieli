package com.example.student.gestionarecheltuieli;

//Base class to hold information about the property
public class Property {


    private String productName;
    private String description;
    private String image;
    private Double price;
    private int product_count;

    //constructor
    public Property(String productName, String description,  Double price,int product_count, String image){

        this.productName = productName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.product_count = product_count;

    }

    //getters
    public String getProductName() {return productName; }
    public String getDescription() {return description; }
    public Double getPrice() {return price; }
    public String getImage() { return image; }
    public int getProductCount(){return product_count;}


    //seters
    public void setProductQuatity(int count) {
        this.product_count = count;
    }


}