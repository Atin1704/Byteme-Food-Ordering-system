package com.company;
import java.io.*;
import java.util.*;


public class Database {
    private static final String FILE_PATH = "data6.ser";

    public static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)))) {




            PasswordManager.setUserIdToPasswords((HashMap<String, String>) ois.readObject());


            InstanceStorage.getInstance().setUserId_userInstance_map((HashMap<String, User>) ois.readObject());


            Order.setOrderNo(ois.readInt());


            ItemMenu.setAllItems((ArrayList<Items>) ois.readObject());


            ItemMenu.setDeletedItems((LinkedList<Items>) ois.readObject());


            ItemMenu.setCategorizedItems((EnumMap<FoodCategory, ArrayList<Items>>) ois.readObject());


            if (ItemMenu.getAllItems().isEmpty() && ItemMenu.getDeletedItems().isEmpty()) {
                DataInitializer.initData();
            }


            Kitchen.setSubmittedOrders((PriorityQueue<Order>) ois.readObject());


            Kitchen.setAcceptedOrders((PriorityQueue<Order>) ois.readObject());


            Kitchen.setOrderHistory((LinkedList<Order>) ois.readObject());

            System.out.println("Data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Initializing with empty data.");
            DataInitializer.initData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void store1() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_PATH)))) {

            oos.writeObject(PasswordManager.getUserIdToPasswords());


            oos.writeObject(InstanceStorage.getInstance().getUserId_userInstance_map());


            oos.writeInt(Order.getOrderNo());


            oos.writeObject(ItemMenu.getAllItems());



            oos.writeObject(ItemMenu.getDeletedItems());


            oos.writeObject(ItemMenu.getCategorizedItems());


            oos.writeObject(Kitchen.getSubmittedOrders());



            oos.writeObject(Kitchen.getAcceptedOrders());



            oos.writeObject(Kitchen.getOrderHistory());

            System.out.println("Data stored successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store2() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data7.ser")))) {


            oos.writeObject(ItemMenu.getAllItems());


            oos.writeObject(Kitchen.getSubmittedOrders());


            oos.writeObject(Kitchen.getAcceptedOrders());

            System.out.println("Data stored successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
