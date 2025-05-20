package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
/* to handle reviews and feedback */
public class Review {
    private static Scanner sc = ScannerSingleton.getScanner();
    private String review;
    private static HashMap<String, List<Review>> itemToReviews = new HashMap<>();

    public Review(String review, String itemName) {
        this.review = review;
        itemToReviews.computeIfAbsent(itemName, k -> new ArrayList<>()).add(this);
    }

    public static void displayReviewMenu() {
        System.out.println("-1. Logout");
        System.out.println(" 0. Main Menu");
        System.out.println(" 1. Provide review");
        System.out.println(" 2. View review");
    }

    public static String reviewMenu(User user) {
        String input = "";

        while (!input.equals("0") && !input.equals("-1")) {
            displayReviewMenu();
            input = sc.next();
            if (input.equals("0") || input.equals("-1")) {
                break;
            } else if (input.equals("1")) {
                System.out.print("Enter the item name to review: ");
                String itemName = sc.next();

                // Check if the user has ordered the item before
                if (hasOrderedItem(user, itemName)) {
                    System.out.print("Enter your review: ");
                    String reviewText = sc.next();
                    new Review(reviewText, itemName); // Add review to item
                    System.out.println("Review added for " + itemName + ".");
                } else {
                    System.out.println("You cannot review this item as you have not ordered it.");
                }
            } else if (input.equals("2")) {
                System.out.print("Enter the item name to view reviews: ");
                String itemName = sc.next();

                // Display reviews for the item
                if (itemToReviews.containsKey(itemName)) {
                    System.out.println("Reviews for " + itemName + ":");
                    for (Review r : itemToReviews.get(itemName)) {
                        System.out.println("- " + r.review);
                    }
                } else {
                    System.out.println("No reviews available for " + itemName + ".");
                }
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("\n");
        }

        return input;
    }

    // Helper method to check if the user has ordered the item before
    private static boolean hasOrderedItem(User user, String itemName) {
        for (Order order : user.getOrderHistory()) {
            if (order.itemInOrder(itemName)) {
                return true;
            }
        }
        return false;
    }
}
