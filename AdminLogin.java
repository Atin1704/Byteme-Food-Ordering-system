package com.company;

public class AdminLogin extends Login {

    @Override
    public String login() {
        System.out.println("Welcome to Admin Login Page");

        int tries = 0;
        while (tries < 2) {
            boolean flag = verifyPassword();
            if (flag) {
                break;
            }
            System.out.println("Incorrect password. Please try again.");
            tries++;
        }

        if (tries >= 2) {
            System.out.println("Returning to the start");
            return "-1";
        }

        Admin admin = this.returnInstance();
        AdminMain main = new AdminMain();
        return main.mainMenu();
    }

    @Override
    public boolean verifyPassword() {
        System.out.println("Enter Password:");
        String password = sc.next();
        return passwordManager.checkAdminPassword(password);
    }

    // Helper method to return the single instance of Admin
    private Admin returnInstance() {
        return storage.getAdmin();
    }
}

