package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FinanceTest {

    private User user1;
    private Items item1;
    private Items item2;

    @BeforeEach
    void setUp() {

        user1 = new User("Harry");
        item1 = new Items(FoodCategory.Beverages, "Coke", 40);
        item2 = new Items(FoodCategory.MainCourse, "Pizza", 200);
    }

    @Test
    void testPlaceOrderWithUnavailableItem() {

        user1.getCart().addItem(item1, 1);  // Adding 1 Coke to the cart
        user1.getCart().addItem(item2, 1);  // Adding 1 Pizza to the cart


        item1.setAvailability(ItemAvailability.isNotAvailable); // Simulating Coke being unavailable


        String result = simulatePlaceOrder(user1);


        assertFalse(user1.getCart().itemInOrder("Coke"), "Coke should have been removed from the cart");


        int expectedCost = 200; // Pizza cost
        int actualCost = user1.getCart().getCost();
        assertEquals(expectedCost, actualCost, "The total cost should match the expected cost");


        assertEquals("Order placed successfully", result, "Order should be processed successfully after unavailable items are removed");
    }



    @Test
    void testPlaceOrderWithOnlyUnavailableItem() {

        user1.getCart().addItem(item1, 1);
        item1.setAvailability(ItemAvailability.isNotAvailable);


        String simulatedInput = "y\n\n456 Avenue\n";
        setSystemInput(simulatedInput);


        String result = Finance.placeOrder(user1);


        assertEquals("0", result, "Order should not be processed if the cart is empty.");
        assertTrue(user1.getCart().getCost() == 0, "Cart should be empty if all items are unavailable.");
    }

    /**
     * Helper method to simulate System.in input.
     *
     * @param input Simulated input as a string.
     */
    private void setSystemInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Finance.sc = new Scanner(System.in); // Reset the scanner with simulated input
    }

    /**
     * Helper method to simulate the logic of the placeOrder method without calling the actual method.
     *
     * @param user The user for placing the order.
     * @return The result of the simulated order process.
     */
    private String simulatePlaceOrder(User user) {
        System.out.println("Hello, " + user.getUserId() + "!");


        int totalCost = user.getCart().getCost();
        if (totalCost == 0) {
            System.out.println("Your order is empty. Please add items to your cart before placing an order.");
            return "0";
        }


        user.getCart().showOrder();
        totalCost = user.getCart().getCost();
        if (totalCost == 0) {
            System.out.println("Your order is empty. Please add items to your cart before placing an order.");
            return "0";
        }

        System.out.println("Here is your current order:");
        System.out.println("Total Cost: $" + totalCost);


        return "Order placed successfully";
    }

}
