package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserLoginTest {

    private UserLogin userLogin;
    private PasswordManager passwordManager;
    private InstanceStorage storage;
    private ByteArrayOutputStream outputStream; // To capture system output

    @BeforeEach
    void setUp() {
        userLogin = new UserLogin();
        passwordManager = new PasswordManager();
        storage = InstanceStorage.getInstance();


        userLogin.passwordManager = passwordManager;
        userLogin.storage = storage;


        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testExistingUserIncorrectPasswordTwice() {
        // Add an existing user to the system
        String userId = "Riya";
        passwordManager.setUserPassword(userId, "correctPassword");


        String simulatedInput = userId + "\nwrongPassword1\nwrongPassword2\n";
        setSystemInput(simulatedInput);


        String result = userLogin.login();


        assertEquals("-1", result, "Login should return to the start after two incorrect password attempts.");


        String output = outputStream.toString();
        assertTrue(output.contains("Enter password:"), "Should prompt for password.");
        assertTrue(output.contains("Incorrect password. Please try again."), "Should display incorrect password message.");
        assertTrue(output.contains("Returning to the start"), "Should display returning to start message.");
    }

    @Test
    void
    testNewUserPasswordMismatchTwice() {

        String newUserId = "NewUser";
        String simulatedInput = newUserId + "\npassword1\npassword2\npassword3\npassword4\n";
        setSystemInput(simulatedInput);


        String result = userLogin.login();


        assertNull(storage.getUserById(newUserId), "No user should be created if passwords do not match.");
        assertEquals("-1", result, "Login should return to the start after two mismatched password attempts.");


        String output = outputStream.toString();
        assertTrue(output.contains("Create password:"), "Should prompt to create password.");
        assertTrue(output.contains("Passwords do not match. Please try again."), "Should display passwords do not match message.");
        assertTrue(output.contains("Returning to the start"), "Should display returning to start message.");
    }

    /**
     * Helper method to replace System.in with simulated input.
     *
     * @param input The input to simulate.
     */
    private void setSystemInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userLogin.sc = new Scanner(System.in);
    }
}

