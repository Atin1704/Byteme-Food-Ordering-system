package com.company;

import java.util.Scanner;

public class UserOrderManager {
    private static Scanner sc=ScannerSingleton.getScanner();

    public  static void displayOrderManagerMenu() {

        System.out.println("-1.  Logout");
        System.out.println(" 0.  Main Menu");
        System.out.println(" 1.  Cancel Order by Order ID");
        System.out.println(" 2.  Track orders by id");
        System.out.println(" 3.  View and re-order previous orders");


    }

    public static String orderManagerMenu(User user) {
        System.out.println("Welcome to the Order Management System");
        String input = "";

        while (!input.equals("0") && !input.equals("-1")) {
            displayOrderManagerMenu();
            input = sc.next();

            if (input.equals("0") || input.equals("-1")) {
                break;
            } else if (input.equals("1")) { // Cancel order by ID
                System.out.print("Enter the Order ID to cancel: ");
                try {
                    int orderId = Integer.parseInt(sc.next());
                    boolean orderFound = false;

                    for (Order order : user.getCurrentOrders()) {
                        if (order.getId() == orderId) {
                            orderFound = true;

                            if (order.getStatus() == OrderStatus.submitted) {
                                order.setStatus(OrderStatus.cancelled);
                                order.setPaymentStatus(Payment.Refunded);
                                user.deleteCurrentOrder(order);
                                user.addToOrderHistory(order);
                                Kitchen.deleteFromSubmitted(order);
                                System.out.println("Order " + orderId + " has been canceled and refunded.");
                            } else {
                                System.out.println("Order " + orderId + " cannot be canceled because it is not in the 'submitted' status.");
                            }
                            break;
                        }
                    }

                    if (!orderFound) {
                        System.out.println("Order with ID " + orderId + " not found in current orders.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid Order ID. Please enter a valid integer.");
                }

            } else if (input.equals("2")) { // Track orders by ID
                System.out.print("Enter the Order ID to track: ");
                try {
                    int orderId = Integer.parseInt(sc.next());
                    boolean orderFound = false;

                    for (Order order : user.getCurrentOrders()) {
                        if (order.getId() == orderId) {
                            orderFound = true;
                            System.out.println("Order ID: " + orderId + " | Status: " + order.getStatus() + " | Payment Status: " + order.getPaymentStatus());
                            break;
                        }
                    }

                    if (!orderFound) {
                        for (Order order : user.getOrderHistory()) {
                            if (order.getId() == orderId) {
                                orderFound = true;
                                System.out.println("Order ID: " + orderId + " | Status: " + order.getStatus() + " | Payment Status: " + order.getPaymentStatus());
                                break;
                            }
                        }
                    }

                    if (!orderFound) {
                        System.out.println("Order with ID " + orderId + " not found.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid Order ID. Please enter a valid integer.");
                }

            } else if (input.equals("3")) {
                if (user.getOrderHistory().isEmpty()) {
                    System.out.println("No previous orders found in your order history.");
                } else {
                    // Display previous orders
                    System.out.println("Previous Orders:");
                    for (Order order : user.getOrderHistory()) {
                        order.displayOrderDetails();
                        System.out.println(); // Add space between orders for readability
                    }

                    // Prompt the user to select an order to re-order by ID
                    System.out.print("Enter the Order ID of the order you wish to re-order: ");
                    try {
                        int orderId = Integer.parseInt(sc.next());
                        Order selectedOrder = null;

                        // Find the order with the given ID in the order history
                        for (Order order : user.getOrderHistory()) {
                            if (order.getId() == orderId) {
                                selectedOrder = order;
                                break;
                            }
                        }

                        if (selectedOrder != null) {
                            // Create a new order using the copy constructor
                            Order newOrder = new Order(selectedOrder);
                            user.setCart(newOrder); // Set the new order as the current cart
                            System.out.println("Order has been copied to your cart. You may proceed with modifications or checkout.");
                        } else {
                            System.out.println("Order with ID " + orderId + " not found in your order history.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Order ID. Please enter a valid integer.");
                    }
                }} else {
                System.out.println("Invalid input. Please try again.");
            }

            System.out.println();
        }

        return input;
    }
}
