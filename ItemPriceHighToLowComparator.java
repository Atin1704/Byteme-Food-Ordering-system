package com.company;

import java.util.Comparator;

public class ItemPriceHighToLowComparator implements Comparator<Items> {
    @Override
    public int compare(Items item1, Items item2) {
        return Integer.compare(item2.getPrice(), item1.getPrice()); // Sort by price, high to low
    }
}



