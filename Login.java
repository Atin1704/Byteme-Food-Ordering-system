package com.company;

import java.util.Scanner;
//abstract class for login to be extended by admin and user
public abstract class Login {
    public Scanner sc= ScannerSingleton.getScanner();
    public InstanceStorage storage = InstanceStorage.getInstance();
    private String userId;
    public PasswordManager passwordManager= new PasswordManager();

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return this.userId;
    }

    public abstract String login();



    public abstract boolean verifyPassword();






}


