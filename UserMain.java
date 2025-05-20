package com.company;

/*needs to be updated soon */

import java.util.Scanner;

public class UserMain {

    Scanner sc=ScannerSingleton.getScanner();
    User user;
    UserMain(User user){
        this.user = user;
    }

    void displayMenuMessage(){
        System.out.println("Main menu");
        System.out.println("-1. To logout");
        System.out.println(" 1. Check Vip status");
        System.out.println(" 2. Browse menu");
        System.out.println(" 3. View and Modify Cart");
        System.out.println(" 4. Place order");
        System.out.println(" 5. Manage/View orders");
        System.out.println(" 6. Items review");




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
               Finance.reviewVipStatus(user);
            }
            else if(input.equals("2")){
                input=ItemMenu.browseMenuItems();
            }
            else if(input.equals("3")){
                input=Cart.cartMenu(user.getCart());
            }
            else if(input.equals("4")){
                input=Finance.placeOrder(user);
            }
            else if(input.equals("5")){
                input=UserOrderManager.orderManagerMenu(user);
            }
            else if(input.equals("6")){
                input=Review.reviewMenu(user);

            }
            else{
                System.out.println("Invalid input");
            }


        }
        return input;




    }
}
