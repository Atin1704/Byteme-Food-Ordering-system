package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//order stores in itself array of items and works as cart and order in different stages of its lifetime
public class Order implements Comparable<Order>, Serializable {
    private int id;
    private String userId;
    private OrderStatus status;
    private int orderDay;
    static int currentDay=1;
    private String address;
    private int finalcost;
    private Payment paymentStatus;
    private VipStatus vipStatus;
    private static int  orderNo=1;
    private String specialRequest;





    private HashMap<Items, Integer> itemToQuantity;

    public Order() {
        this.status = OrderStatus.pending;
        this.itemToQuantity = new HashMap<>();


    }
    public Order(Order order) {
        this.itemToQuantity = new HashMap<>(order.itemToQuantity);
        this.status = OrderStatus.pending;
    }




    public int getFinalcost() {
        return finalcost;
    }
    public void setFinalcost(int finalcost) {
        this.finalcost = finalcost;
    }
    public VipStatus getVipStatus() {
        return vipStatus;
    }
    public void setVipStatus(VipStatus vipStatus) {
        this.vipStatus = vipStatus;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getId() {
        return id;
    }


    public Payment getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(Payment paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public int getOrderDay() {
        return orderDay;
    }
    public void setOrderDay() {
        this.orderDay = currentDay;
    }


    public void setOrderId(int orderNo) {
        this.id = orderNo++;
    }

    public HashMap<Items, Integer> getItemToQuantity() {
        return itemToQuantity;
    }

    public static void setOrderNo(int orderNo) {
        Order.orderNo = orderNo;
    }
    public static int getOrderNo() {
        return orderNo;
    }

    @Override
    public int compareTo(Order other) {

        int vipComparison = this.vipStatus.compareTo(other.vipStatus);
        if (vipComparison != 0) {
            return vipComparison;
        }

        // If VIP status is the same, compare by order ID (smaller IDs come first)
        return Integer.compare(this.id, other.id);
    }



    //send it to kitchen.addSubmittedOrder(order)


    public void orderSubmitted(String address, VipStatus vipStatus,String specialRequest ){
        this.status = OrderStatus.submitted;
        this.finalcost = getCost();
        this.orderDay = currentDay;
        this.paymentStatus = Payment.Paid;
        this.address = address;
        this.vipStatus = vipStatus;
        this.id = orderNo++;
        this.specialRequest = specialRequest;
        Kitchen.addSubmittedOrder(this);
        System.out.println("Note order id  "+this.id);
    }





    public void displayOrderDetails() {
        System.out.println("Order ID: " + id);

        // Display items and quantities in the order
        if (itemToQuantity.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            System.out.println("Items in the Order:");
            for (Map.Entry<Items, Integer> entry : itemToQuantity.entrySet()) {
                Items item = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("- " + item.getName() + " | Quantity: " + quantity );
            }
        }

        // Handle null values for address and special request
        String displayAddress = (address != null && !address.isEmpty()) ? address : "No address provided";
        String displayRequest = (specialRequest != null && !specialRequest.isEmpty()) ? specialRequest : "No special request";
        System.out.println("Payment Status: "+this.paymentStatus);
        System.out.println("Delivery Address: " + displayAddress);
        System.out.println("Special Request: " + displayRequest);
    }















    public int getCost() {


        int totalCost = 0;
        for (HashMap.Entry<Items, Integer> entry : itemToQuantity.entrySet()) {
            Items item = entry.getKey();
            int quantity = entry.getValue();
            totalCost += item.getPrice() * quantity; // Assuming Items has a getPrice() method
        }
        return totalCost;
    }


    // Method to show items in the order with their quantities
    public void showOrder() {
        System.out.println("Order ID: " + id);
        if (itemToQuantity.isEmpty()) {
            System.out.println("The order is empty.");
        } else {
            System.out.println("Items in Order:");
            Iterator<HashMap.Entry<Items, Integer>> iterator = itemToQuantity.entrySet().iterator();
            while (iterator.hasNext()) {
                HashMap.Entry<Items, Integer> entry = iterator.next();
                Items item = entry.getKey();
                int quantity = entry.getValue();

                // Check item availability
                if (item.getAvailability() == ItemAvailability.isNotAvailable) {
                    iterator.remove();  // Remove item if not available
                    System.out.println(item.getName() + " is no longer available and has been removed from the order.");
                } else {
                    System.out.println(item.getName() + " - Quantity: " + quantity + ", Price: $" + item.getPrice());
                }
            }

        }
    }

    // Method to add items to the order or update quantities if already added
    public void addItem(Items item, int quantity) {
        if (item.getAvailability() == ItemAvailability.isNotAvailable) {
            System.out.println("The item " + item.getName() + " is not available and cannot be added to the order.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        if (itemToQuantity.containsKey(item)) {
            itemToQuantity.put(item, itemToQuantity.get(item) + quantity);
        } else {
            itemToQuantity.put(item, quantity);
        }
    }

    // Method to check whether an item is in the order
    public boolean itemInOrder(String itemName) {
        for (Items item : itemToQuantity.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    // Method to modify the quantity of an item
    public void modifyQuantity(String itemName, int quantity) {
        for (Items item : itemToQuantity.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (quantity <= 0) {
                    deleteItem(item);
                } else {
                    itemToQuantity.put(item, quantity);
                }
                return;
            }
        }
        System.out.println("Item not found in order.");
    }



    // Method to delete an item from the order
    public void deleteItem(Items item) {
        if (itemToQuantity.containsKey(item)) {
            itemToQuantity.remove(item);
            System.out.println(item.getName() + " has been removed from the order.");
        } else {
            System.out.println("Item not found in order.");
        }
    }
    public static void a(){

    }
}
