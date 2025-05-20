package com.company;


import java.util.Scanner;
//to handle transactions
public class Finance {
    static Scanner sc=ScannerSingleton.getScanner();
    private final static int vipCost=80;
    public static void reviewVipStatus(User user) {
        if(user.getVipStatus()==VipStatus.isVip){
            System.out.println("You are a VIP customer !!!");
        }
        else{
            System.out.println("You are not a VIP customer ");
            System.out.println("Please pay    "+vipCost);
            System.out.println("Press 'y' for yes and 'n' for no");
            String choice=sc.next();
            if(choice.equalsIgnoreCase("y")){
                pay(user,vipCost);
                System.out.println("Congrats you are a VIP customer !!!");
                user.setVipStatus(VipStatus.isVip);
                return;
            }
            else if(choice.equalsIgnoreCase("n")){
                System.out.println("Cancelling payment");
                return;
            }
            else{
                System.out.println("Invalid choice,returning to main menu");
                return;
            }





        }
    }

    public static void pay(User user,int amount) {
        System.out.println("You need to pay "+amount+"\n");
        System.out.println("Add UPI address for payment of "+amount);
        String input=sc.next();
        System.out.println("Processing from bank");
        System.out.println(" Your amount paid thru upi id "+input +amount);
        System.out.println("Received payment,thanks   " + user.getUserId());

    }







    public static String placeOrder(User user) {
        System.out.println("Hello, " + user.getUserId() + "!");

        // Check if the order is empty
        int totalCost = user.getCart().getCost();
        if (totalCost == 0) {
            System.out.println("Your order is empty. Please add items to your cart before placing an order.");
            return "0"; // Return immediately if the cart is empty
        }

        // Show the current order details
        user.getCart().showOrder();
        totalCost = user.getCart().getCost();
        if (totalCost == 0) {
            System.out.println("Your order is empty. Please add items to your cart before placing an order.");
            return "0"; // Return immediately if the cart is empty
        }

        System.out.println("Here is your current order:");
        System.out.println("Total Cost: $" + totalCost);

        // Confirm if the user wants to proceed with payment
        System.out.println("Do you want to confirm the payment? (y/n)");
        String confirmPayment = sc.next();
        sc.nextLine();  // Consume the newline

        if (!confirmPayment.equalsIgnoreCase("y")) {
            System.out.println("Order placement cancelled.");
            return "0";
        }

        // Ask for any special request (ensure we capture the full input line)
        System.out.println("Do you have any special request? (Leave blank if none)");
        String specialRequest = sc.nextLine();  // Use nextLine to capture the full line

        // Ask for the address for delivery
        System.out.println("Please enter your delivery address:");
        String address = sc.nextLine();

        // Process payment
        pay(user, totalCost);

        // Finalize and submit the order with address and special request
        user.submitOrder(address, specialRequest);
        System.out.println("Order submitted successfully!");

        return "1"; // Indicating success
    }









}

