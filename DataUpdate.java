package com.data;

import com.company.Items;
import com.company.Order;
import com.company.User;

import java.io.*;
import java.util.*;

public class DataUpdate {
    private static final String FILE_PATH = "data7.ser";


    private static ArrayList<Items> allItems;
    private static PriorityQueue<Order> submittedOrders;
    private static PriorityQueue<Order> acceptedOrders;

    // Getter methods for the data structures
    public static ArrayList<Items> getAllItems() {
        return allItems;
    }

    public static PriorityQueue<Order> getSubmittedOrders() {
        return submittedOrders;
    }

    public static PriorityQueue<Order> getAcceptedOrders() {
        return acceptedOrders;
    }


    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)))) {

            // Initialize the collections if they are null
            if (allItems == null) {
                allItems = new ArrayList<>();
            }

            allItems = (ArrayList<Items>) ois.readObject();


            if (submittedOrders == null) {
                submittedOrders = new PriorityQueue<>();
            }

            submittedOrders = (PriorityQueue<Order>) ois.readObject();


            if (acceptedOrders == null) {
                acceptedOrders = new PriorityQueue<>();
            }

            acceptedOrders = (PriorityQueue<Order>) ois.readObject();

            System.out.println("Data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Initializing with empty data.");
            initializeEmptyData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void initializeEmptyData() {
        allItems = new ArrayList<>();
        submittedOrders = new PriorityQueue<>();
        acceptedOrders = new PriorityQueue<>();
    }
}





















