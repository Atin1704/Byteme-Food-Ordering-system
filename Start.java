package com.company;
import java.util.Scanner;

/* Start is the entry point to the system */

public  class  Start {

    private static void displayStartMenu(){
        System.out.println("Please Select Login type");
        System.out.println("-1. Exit");
        System.out.println(" 1. User Login");
        System.out.println(" 2. Admin Login");
        System.out.println("_______________\n");

    }

    public static void main(String[] args){
        Database.load();
        Scanner sc = ScannerSingleton.getScanner();
        String input="";
        System.out.println("Welcome to Login Page of Byteme!\n");

        while(true){
            displayStartMenu();
            input = sc.next();
            if(input.equals("-1")){
                break;
            }

            switch (input) {
                case "1":
                    Login userLogin = new UserLogin();
                    input = userLogin.login();
                    break;
                case "2":
                    Login adminLogin = new AdminLogin();
                    input = adminLogin.login();

                    break;
                default:
                    System.out.println("\nPlease Select Options Carefully!!!\n");
                    break;
            }



        }
        Database.store1();
        Database.store2();


        System.out.println("Thanks for using the System ");



    }

}
