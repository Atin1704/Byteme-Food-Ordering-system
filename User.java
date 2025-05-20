package com.company;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class User implements Serializable {
    private String userId;
    private Order cart;
    private ArrayList<Order> currentOrders;
    private LinkedList<Order> orderHistory;
    transient InstanceStorage storage=  InstanceStorage.getInstance();
    private VipStatus vipStatus;


    public User(String userId) {
        storage.addUser(userId,this);
        this.userId=userId;
        vipStatus= VipStatus.notVip;
        orderHistory = new LinkedList<>();
        currentOrders = new ArrayList<>();
        cart = new Order();
        cart.setUserId(this.userId);

    }


    public void addCurrentOrder(Order order) {
        currentOrders.add(order);
    }


    public void deleteCurrentOrder(Order order) {
        currentOrders.remove(order);
    }


    public ArrayList<Order> getCurrentOrders() {
        return currentOrders;
    }
    public void setCurrentOrders(ArrayList<Order> currentOrders) {
        this.currentOrders = currentOrders;
    }


    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }


    public LinkedList<Order> getOrderHistory() {
        return orderHistory;
    }
    public void setOrderHistory(LinkedList<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }


    public VipStatus getVipStatus() {
        return vipStatus;
    }
    public void setVipStatus(VipStatus vipStatus) {
        this.vipStatus = vipStatus;
    }
    public Order getCart(){
        return cart;
    }
    public void createCart(){
        this.cart = new Order();
        cart.setUserId(this.userId);
    }
    public void setCart(Order cart) {
        this.cart = cart;
    }

    public String getUserId() {
        return userId;
    }

    public void submitOrder(String address, String specialRequest) {
        cart.orderSubmitted(address, vipStatus, specialRequest);
        currentOrders.add(cart);
        System.out.println("Creating a new cart...");
        createCart(); // Call the method to reset cart
        System.out.println("New cart created with Order ID: " + cart.getId());
    }


    public static void a(){

    }






}
