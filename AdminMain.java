package com.company;

/*needs to be updated soon */

import java.util.Scanner;

public class AdminMain {





    Scanner sc=ScannerSingleton.getScanner();

    void displayMenuMessage(){
        System.out.println("Main menu");
        System.out.println("-1. To logout");
        System.out.println(" 1. Create/Update/Delete Items");
        System.out.println(" 2. OrdersPage");






    }


    public String mainMenu() {
        String input="";
        while(!input.equals("-1")){
            displayMenuMessage();
            input=sc.next();
            if(input.equals("-1")){
                break;
            }
            else if(input.equals("1")){
                input=ItemMenu.createUpdateItems();

            }
            else if(input.equals("2")){
                input=Kitchen.kitchenMenu();
            }
            else{
                System.out.println("Invalid input");

            }


        }
        return input;




    }


}
