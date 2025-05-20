package com.company;

import java.util.*;

public class Kitchen {
    private static InstanceStorage storage=InstanceStorage.getInstance();
    private static Scanner sc=ScannerSingleton.getScanner();

    private static int currentDay=1;
    private static PriorityQueue<Order> submittedOrders=new PriorityQueue<>();
    private static PriorityQueue<Order> acceptedOrders=new PriorityQueue<>();
    private static LinkedList<Order> orderHistory=new LinkedList<>();


    public static PriorityQueue<Order> getSubmittedOrders() {
        return submittedOrders;
    }

    public static void setSubmittedOrders(PriorityQueue<Order> orders) {
        submittedOrders = orders;
    }

    public static PriorityQueue<Order> getAcceptedOrders() {
        return acceptedOrders;
    }

    public static void setAcceptedOrders(PriorityQueue<Order> orders) {
        acceptedOrders = orders;
    }

    public static LinkedList<Order> getOrderHistory() {
        return orderHistory;
    }

    public static void setOrderHistory(LinkedList<Order> history) {
        orderHistory = history;
    }






    public static void addSubmittedOrder(Order order) {
        submittedOrders.add(order);
    }

    public static void setCurrentDay(int currentDay1) {
        if(currentDay1!=currentDay) {
            currentDay=currentDay1;
            orderHistory.clear();
            Order.currentDay=currentDay;
        }

    }


    public  static void displayKitchenMenu(){
        System.out.println("Welcome to Kitchen Menu");
        System.out.println("-1.  Logout");
        System.out.println(" 0.  Main Menu");
        System.out.println(" 1.  View/Process submitted order");//only update price and availability
        System.out.println(" 2.  View/Process accepted orders");
        System.out.println(" 3.  See Sales report for the day");
        System.out.println(" 4.  Change day");



    }

    public static void displaySalesReport() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders completed today.");
            return;
        }

        int totalRevenue = 0;
        HashMap<Items, Integer> itemPopularity = new HashMap<>();

        // Calculate total revenue and track item popularity
        for (Order order : orderHistory) {
            totalRevenue += order.getFinalcost();
            for (Map.Entry<Items, Integer> entry : order.getItemToQuantity().entrySet()) {
                Items item = entry.getKey();
                int quantity = entry.getValue();
                itemPopularity.put(item, itemPopularity.getOrDefault(item, 0) + quantity);
            }
        }

        // Sort items by popularity and then by price if counts are the same
        List<Map.Entry<Items, Integer>> sortedItems = new ArrayList<>(itemPopularity.entrySet());
        sortedItems.sort((a, b) -> {
            int countComparison = b.getValue().compareTo(a.getValue());
            return countComparison != 0 ? countComparison : Integer.compare(a.getKey().getPrice(), b.getKey().getPrice());
        });

        System.out.println("Sales Report for the Day:");
        System.out.println("Total Orders Completed: " + orderHistory.size());
        System.out.println("Total Revenue: $" + totalRevenue);

        // Display the top 3 popular items
        System.out.println("Top Items Sold Today:");
        for (int i = 0; i < Math.min(3, sortedItems.size()); i++) {
            Items item = sortedItems.get(i).getKey();
            int quantity = sortedItems.get(i).getValue();
            System.out.println(item.getName() + " - Sold: " + quantity + " times, Price: $" + item.getPrice());
        }
    }


    public  static String  kitchenMenu(){

        String input="";


        while(!input.equals("0")&&!input.equals("-1")){
            displayKitchenMenu();
            input=sc.next();
            if (input.equals("0") || input.equals("-1")) {
                break;
            } else if (input.equals("1")) {
                input=submittedMenu();

            } else if (input.equals("2")) {
                input=acceptedMenu();


            } else if (input.equals("3")) {
                displaySalesReport();
            } else if (input.equals("4")) {
                System.out.println("Current Day: " + currentDay);
                System.out.println("Enter new day");
                int day=sc.nextInt();
                setCurrentDay(day);

            }
            else {
                System.out.println("Invalid input");
            }
            System.out.println("\n");}

        return input;


    }

    public  static void displayAcceptedMenu(){
        System.out.println(" Accepted orders");

        System.out.println(" 0.  Kitchen Menu");
        System.out.println(" 1.  Change order status to delivered");//only update price and availability
        System.out.println(" 2.  Change order status to denied");



    }

    public  static void displaySubmittedMenu(){

        System.out.println(" Submitted orders");

        System.out.println(" 0.  Kitchen Menu");
        System.out.println(" 1.  Change order status to accepted");//only update price and availability
        System.out.println(" 2.  Change order status to denied");



    }


    private static String submittedMenu() {
        String input = "";

        while (!input.equals("0")) {
            if (submittedOrders.isEmpty()) {
                System.out.println("\nNo submitted orders available.");
                break;
            }

            Order order = submittedOrders.peek();  // Get the next order without removing it
            order.displayOrderDetails();  // Display the order details

            displaySubmittedMenu();
            input = sc.next();
            if (input.equals("0")) {
                return "5";

            }
            else if (input.equals("1")) {
                // Accept the order
                submittedOrders.poll(); // Remove order from submitted queue
                order.setStatus(OrderStatus.accepted);
                acceptedOrders.add(order); // Move to accepted orders queue
                System.out.println("Order accepted.");
            } else if (input.equals("2")) {
                // Deny the order and issue refund
                submittedOrders.poll(); // Remove order from submitted queue
                order.setStatus(OrderStatus.denied);
                order.setPaymentStatus(Payment.Refunded);

                // Move to user's order history and update storage
                User user = storage.getUserById(order.getUserId());
                if (user != null) {
                    user.deleteCurrentOrder(order);
                    user.addToOrderHistory(order);
                }

                System.out.println("Order denied and payment refunded.");
            } else if (!input.equals("0")) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return "5";
    }

    private static String acceptedMenu() {
        String input = "";

        while (!input.equals("0")) {
            if (acceptedOrders.isEmpty()) {
                System.out.println("No accepted orders available.");
                break;
            }

            Order order = acceptedOrders.peek();  // Get the next order without removing it
            order.displayOrderDetails();  // Display the order details

            displayAcceptedMenu();
            input = sc.next();

            if (input.equals("0")) {
                return "5";

            }

            if (input.equals("1")) {
                // Mark the order as delivered
                acceptedOrders.poll(); // Remove order from accepted queue
                order.setStatus(OrderStatus.delivered);
                orderHistory.add(order);

                // Move to user's order history and update storage
                User user = storage.getUserById(order.getUserId());
                if (user != null) {
                    user.deleteCurrentOrder(order);
                    user.addToOrderHistory(order);
                }

                System.out.println("Order marked as delivered.");
            } else if (input.equals("2")) {
                // Deny the order and issue refund
                acceptedOrders.poll(); // Remove order from accepted queue
                order.setStatus(OrderStatus.denied);
                order.setPaymentStatus(Payment.Refunded);

                // Move to user's order history and update storage
                User user = storage.getUserById(order.getUserId());
                if (user != null) {
                    user.deleteCurrentOrder(order);
                    user.addToOrderHistory(order);
                }

                System.out.println("Order denied and payment refunded.");
            } else if (!input.equals("0")) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return "5";
    }


    public static void deleteOrders(Items itemToDelete) {
        if (submittedOrders.isEmpty()) {
            System.out.println("No orders in the submitted queue.");
            return;
        }

        // Temporary queue to hold orders that do not contain the item to delete
        PriorityQueue<Order> tempQueue = new PriorityQueue<>();

        while (!submittedOrders.isEmpty()) {
            Order order = submittedOrders.poll();

            // Check if the order contains the item to delete
            if (order.itemInOrder(itemToDelete.getName())) {

                order.setStatus(OrderStatus.denied);
                order.setPaymentStatus(Payment.Refunded);

                // Update user's order history
                User user = storage.getUserById(order.getUserId());
                if (user != null) {
                    user.deleteCurrentOrder(order);
                    user.addToOrderHistory(order);
                }

                System.out.println("Order with id " + order.getId() + " has been denied and refunded.");
            } else {
                // If the order does not contain the item, add it to the temp queue
                tempQueue.add(order);
            }
        }

        // Restore the remaining orders back to the submittedOrders queue
        submittedOrders = tempQueue;
    }

    public static void deleteFromSubmitted(Order order) {
        submittedOrders.remove(order);

    }
}







