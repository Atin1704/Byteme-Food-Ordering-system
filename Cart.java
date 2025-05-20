package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
//to handle the user's cart
public class Cart {
    private static Scanner sc = ScannerSingleton.getScanner();

    public  static void displayCartMenu() {
        System.out.println("Welcome to the Cart Menu");
        System.out.println("-1.  Logout");
        System.out.println(" 0.  Main Menu");
        System.out.println(" 1.  Modify cart");
        System.out.println(" 2.  Add items");
        System.out.println(" 3.  Delete items");
        System.out.println(" 4.  Total Cost");
    }

    public static String cartMenu(Order cart) {
        String input = "";
        while (!input.equals("0") && !input.equals("-1")) {
            cart.showOrder();
            displayCartMenu();
            input = sc.next();

            if (input.equals("0") || input.equals("-1")) {
                break;

            } else if (input.equals("1")) { // Modify cart
                System.out.println("Enter item name to modify quantity:");
                String itemName = sc.next();
                if (cart.itemInOrder(itemName)) {
                    System.out.println("Enter new quantity:");
                    int quantity = sc.nextInt();
                    cart.modifyQuantity(itemName, quantity);
                } else {
                    System.out.println("Item not found in cart.");
                }

            } else if (input.equals("2")) { // Add items
                System.out.println("Enter item name to add:");
                String itemToAddName = sc.next();
                Items itemToAdd = ItemMenu.findItemByName(itemToAddName);
                if (itemToAdd != null) {
                    System.out.println("Enter quantity:");
                    int addQuantity = sc.nextInt();
                    cart.addItem(itemToAdd, addQuantity);

                } else {
                    System.out.println("Item not found.");
                }

            } else if (input.equals("3")) { // Delete items
                System.out.println("Enter item name to delete:");
                String itemToDeleteName = sc.next();
                Items itemToDelete = ItemMenu.findItemByName(itemToDeleteName);
                if (itemToDelete != null && cart.itemInOrder(itemToDeleteName)) {
                    cart.deleteItem(itemToDelete);
                    System.out.println(itemToDelete.getName() + " removed from cart.");
                } else {
                    System.out.println("Item not found in cart.");
                }

            } else if (input.equals("4")) { // Total Cost
                int totalCost = cart.getCost();
                System.out.println("Total Cost: $" + totalCost);

            } else {
                System.out.println("Invalid input. Please try again.");
            }

            System.out.println();
        }

        return input;
    }
}




