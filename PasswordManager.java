package com.company;

import java.util.HashMap;
/* handle user id's and passwords*/
public class PasswordManager {

    private  static HashMap<String,String> userIdToPasswords = new HashMap<>();
    private static String adminPassword="admin";


    public boolean checkExistence(String userId) {

        for(String Id : userIdToPasswords.keySet()){
            if(Id.equals(userId)){
                return true;
            }

        }
        return false;
    }

    public boolean checkUserPassword(String userId, String password) {
        String userPassword = userIdToPasswords.get(userId);
        return password.equals(userPassword);

    }

    public void setUserPassword(String userId, String password) {
        userIdToPasswords.put(userId, password);
    }

    public boolean checkAdminPassword( String password) {
            return adminPassword.equals(password);
    }

    public void setAdminPassword( String password) {
        adminPassword = password;

    }
    public static HashMap<String,String> getUserIdToPasswords() {
        return userIdToPasswords;
    }
    public static void setUserIdToPasswords(HashMap<String,String> userIdToPasswords) {
        PasswordManager.userIdToPasswords = userIdToPasswords;
    }



}
