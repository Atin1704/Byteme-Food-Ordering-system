package com.data;

import com.company.Items;
import com.company.Order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Startpage {

    public static void main(String[] args) {

        DataUpdate.load();


        JFrame frame = new JFrame("Start Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());


        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));


        JButton displayItemsButton = new JButton("Display All Items");
        JButton displaySubmittedOrdersButton = new JButton("Display Submitted Orders");
        JButton displayAcceptedOrdersButton = new JButton("Display Accepted Orders");
        JButton quitButton = new JButton("Quit");


        optionsPanel.add(displayItemsButton);
        optionsPanel.add(displaySubmittedOrdersButton);
        optionsPanel.add(displayAcceptedOrdersButton);
        optionsPanel.add(quitButton);

        frame.add(optionsPanel, BorderLayout.CENTER);


        displayItemsButton.addActionListener(e -> {
            displayAllItems();
        });

        displaySubmittedOrdersButton.addActionListener(e -> {
            displaySubmittedOrders();
        });

        displayAcceptedOrdersButton.addActionListener(e -> {
            displayAcceptedOrders();
        });

        quitButton.addActionListener(e -> {
            System.exit(0);
        });


        frame.setVisible(true);
    }


    private static void displayAllItems() {
        JFrame itemsFrame = new JFrame("All Items");
        itemsFrame.setSize(600, 400);
        itemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        itemsFrame.add(scrollPane);


        ArrayList<Items> items = DataUpdate.getAllItems();
        if (items.isEmpty()) {
            textArea.append("No items available.\n");
        } else {
            for (Items item : items) {
                textArea.append("Item: " + item.getName() + " | Price: $" + item.getPrice() +
                    " | Availability: " + item.getAvailability() + "\n");
            }
        }


        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            itemsFrame.dispose();

            main(new String[0]);
        });

        itemsFrame.add(goBackButton, BorderLayout.SOUTH);
        itemsFrame.setVisible(true);
    }


    private static void displaySubmittedOrders() {
        JFrame ordersFrame = new JFrame("Submitted Orders");
        ordersFrame.setSize(600, 400);
        ordersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        ordersFrame.add(scrollPane);


        ArrayList<Order> orders = new ArrayList<>(DataUpdate.getSubmittedOrders());
        if (orders.isEmpty()) {
            textArea.append("No submitted orders.\n");
        } else {
            for (Order order : orders) {
                textArea.append("Order ID: " + order.getId() + " | Status: " + order.getStatus() +
                    " | Cost: $" + order.getCost() + "\n");

                // Show items in the order
                for (Map.Entry<Items, Integer> entry : order.getItemToQuantity().entrySet()) {
                    textArea.append("- " + entry.getKey().getName() + " | Quantity: " + entry.getValue() + "\n");
                }
                textArea.append("\n");
            }
        }


        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            ordersFrame.dispose();

            main(new String[0]);
        });

        ordersFrame.add(goBackButton, BorderLayout.SOUTH);
        ordersFrame.setVisible(true);
    }


    private static void displayAcceptedOrders() {
        JFrame ordersFrame = new JFrame("Accepted Orders");
        ordersFrame.setSize(600, 400);
        ordersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        ordersFrame.add(scrollPane);


        ArrayList<Order> orders = new ArrayList<>(DataUpdate.getAcceptedOrders());
        if (orders.isEmpty()) {
            textArea.append("No accepted orders.\n");
        } else {
            for (Order order : orders) {
                textArea.append("Order ID: " + order.getId() + " | Status: " + order.getStatus() +
                    " | Cost: $" + order.getCost() + "\n");

                // Show items in the order
                for (Map.Entry<Items, Integer> entry : order.getItemToQuantity().entrySet()) {
                    textArea.append("- " + entry.getKey().getName() + " | Quantity: " + entry.getValue() + "\n");
                }
                textArea.append("\n");
            }
        }


        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            ordersFrame.dispose();

            main(new String[0]);
        });

        ordersFrame.add(goBackButton, BorderLayout.SOUTH);
        ordersFrame.setVisible(true);
    }
}
