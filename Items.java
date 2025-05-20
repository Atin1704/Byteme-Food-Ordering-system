package com.company;

import javax.xml.transform.Source;
import java.io.Serializable;
import java.util.ArrayList;
/* 5 Categories-Beverages,Snacks,Main course,Sides,Desert */
public class Items implements Comparable<Items>, Serializable {

    private FoodCategory category;
    private String name;
    private int price;
    private ItemAvailability availability;


    Items(FoodCategory category, String name, int price) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.availability=ItemAvailability.isAvailable;
        ItemMenu.add(this);


    }


    @Override
    public String toString() {
        return "Item: " + name + " | Price: $" + price + "\n" +
            "Category: " + category + " | Availability: " + availability+"\n";
    }

    public FoodCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ItemAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(ItemAvailability availability) {
        this.availability = availability;

    }

    public void setCategory(FoodCategory category) {
        this.category = category;
        //remember to add a method here that will cancel all the orders, won't be able to do it now tho..
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public int compareTo(Items other) {
        return Integer.compare(this.price, other.price); // Natural order: low to high
    }

    public static void a(){

    }




}
