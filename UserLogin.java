package com.company;

public class UserLogin extends Login {

    boolean userIdExists;
    boolean passwordCorrect;

    @Override
    public String login() {
        System.out.println("Login page\nPlease enter your userId");
        String userId = sc.next();
        this.setUserId(userId);
        this.userIdExists = passwordManager.checkExistence(this.getUserId());

        if (!userIdExists) {
            System.out.println("UserId not in system data\nNew User being created....\n");
        }

        int tries = 0;
        while (tries < 2) {
            boolean flag = verifyPassword(); // Attempt to verify password
            if (flag) {
                break; // Exit loop if password verification succeeds
            }
            tries++;
        }

        if (tries >= 2) { // Return to the start after 2 failed attempts
            System.out.println("Returning to the start");
            return "-1";
        }

        User newUser = this.returnInstance();
        UserMain main = new UserMain(newUser);
        return main.mainMenu();
    }

    @Override
    public boolean verifyPassword() {
        String password;
        String passwordConfirm;

        if (userIdExists) {
            System.out.println("Enter password:");
            password = sc.next();
            passwordCorrect = passwordManager.checkUserPassword(this.getUserId(), password);

            if (!passwordCorrect) {
                System.out.println("Incorrect password. Please try again.");
            }

            return passwordCorrect; // Return result directly
        } else {
            System.out.println("Create password:");
            password = sc.next();
            System.out.println("Re-enter password:");
            passwordConfirm = sc.next();

            if (passwordConfirm.equals(password)) {
                passwordManager.setUserPassword(this.getUserId(), password);
                System.out.println("Password successfully created.");
                return true;
            } else {
                System.out.println("Passwords do not match. Please try again.");
                return false;
            }
        }
    }

    // Helper method to return an instance of User
    private User returnInstance() {
        if (userIdExists) {
            return storage.getUserById(this.getUserId());
        } else {
            User newUser = new User(this.getUserId());
            storage.addUser(this.getUserId(), newUser); // Add new user to storage
            return newUser;
        }
    }
}
