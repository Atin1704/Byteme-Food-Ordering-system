package com.company;
//just for static data
public class DataInitializer {
    public static void initData() {

    }

    static{
        User user1=new User("Harry");

        PasswordManager passwordManager=new PasswordManager();

        passwordManager.setUserPassword("Harry","Harry");

        Items item1 = new Items(FoodCategory.Beverages, "Coke", 40);
        Items item2 = new Items(FoodCategory.Beverages, "Pepsi", 40);
        Items item3 = new Items(FoodCategory.Snacks, "FrenchFries", 60);
        Items item4 = new Items(FoodCategory.Snacks, "Nachos", 80);
        Items item5 = new Items(FoodCategory.Sides, "GarlicBread", 50);
        Items item6 = new Items(FoodCategory.Sides, "OnionRings", 45);
        Items item7 = new Items(FoodCategory.Deserts, "IceCream", 70);
        Items item8 = new Items(FoodCategory.Deserts, "Brownie", 90);
        Items item9 = new Items(FoodCategory.MainCourse, "Burger", 120);
        Items item10 = new Items(FoodCategory.MainCourse, "Pizza", 200);
        Items item11 = new Items(FoodCategory.Beverages, "Lemonade", 30);
        Items item12 = new Items(FoodCategory.Snacks, "Popcorn", 35);
        Items item13 = new Items(FoodCategory.Sides, "Coleslaw", 25);
        Items item14 = new Items(FoodCategory.Deserts, "Cupcake", 60);
        Items item15 = new Items(FoodCategory.MainCourse, "Pasta", 150);
        item1.setAvailability(ItemAvailability.isNotAvailable);
        item10.setAvailability(ItemAvailability.isNotAvailable);
















    }




}
