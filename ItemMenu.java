package com.company;

import java.util.*;
//manages items and provides methods to work with them
public class ItemMenu {
    private static Scanner sc=ScannerSingleton.getScanner();

    private static ArrayList<Items>  allItems= new ArrayList<>();
    private static LinkedList<Items> deletedItems= new LinkedList<>();

    public static ArrayList<Items> getAllItems() {
        return allItems;
    }

    public static void setAllItems(ArrayList<Items> items) {
        allItems = items;
    }

    public static LinkedList<Items> getDeletedItems() {
        return deletedItems;
    }

    public static void setDeletedItems(LinkedList<Items> items) {
        deletedItems = items;
    }


    private static EnumMap<FoodCategory, ArrayList<Items>> categorizedItems = new EnumMap<>(FoodCategory.class);
    public static EnumMap<FoodCategory, ArrayList<Items>> getCategorizedItems() {
        return categorizedItems;
    }

    public static void setCategorizedItems(EnumMap<FoodCategory, ArrayList<Items>> items) {
        categorizedItems = items;
    }

    public static void add(Items item) {
        allItems.add(item);

        categorizedItems
            .computeIfAbsent(item.getCategory(), k -> new ArrayList<>())
            .add(item);
    }


    // Method to find an item by name
    public static Items findItemByName(String itemName) {
        for (Items item : allItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item; // Return the found item
            }
        }
        return null; // Return null if no item is found
    }




    public static void  displayCreateUpdateItems(){
        System.out.println("Welcome to the Items Modification Menu");
        System.out.println("-1.  Logout");
        System.out.println(" 0.  Main Menu");
        System.out.println(" 1.  Update item");//only update price and availability
        System.out.println(" 2.  Add an item");
        System.out.println(" 3.  Delete an item");

    }



    public static void displayCategories(){
        System.out.println(" 1.  Beverages");
        System.out.println(" 2.  Sides");
        System.out.println(" 3.  Deserts");
        System.out.println(" 4.  Snacks");
        System.out.println(" 5.  Main course");

    }
    public static void deleteItem() {
        System.out.print("Enter the name of the item to delete: ");
        String itemName = sc.next();
        Items itemToDelete = findItemByName(itemName);

        if (itemToDelete != null) {
            // Check if the item is marked as 'not available'
            if (itemToDelete.getAvailability() == ItemAvailability.isNotAvailable) {
                allItems.remove(itemToDelete);

                // Remove from categorizedItems list
                ArrayList<Items> categoryList = categorizedItems.get(itemToDelete.getCategory());
                if (categoryList != null) {
                    categoryList.remove(itemToDelete);
                }

                // Add to deletedItems list
                deletedItems.add(itemToDelete);
                Kitchen.deleteOrders(itemToDelete);

                System.out.println("Item deleted successfully ");
            } else {
                System.out.println("Item cannot be deleted as it is currently available.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }


    public static String createUpdateItems() {
        String input = "";
        while (!input.equals("0") && !input.equals("-1")) {
            displayCreateUpdateItems();
            input = sc.next();

            if (input.equals("0") || input.equals("-1")) {
                break;
            } else if (input.equals("1")) {
                // Update item
                System.out.print("Enter the name of the item to update: ");
                String itemName = sc.next();
                Items itemToUpdate = null;

                for (Items item : allItems) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        itemToUpdate = item;
                        break;
                    }
                }

                if (itemToUpdate != null) {
                    boolean updating = true;

                    while (updating) {
                        System.out.println("Current Price: $" + itemToUpdate.getPrice());
                        System.out.println("Current Availability: " + itemToUpdate.getAvailability());
                        System.out.println("Choose an option:");
                        System.out.println("0. Done (Return to Menu)");
                        System.out.println("1. Update Price");
                        System.out.println("2. Update Availability");
                        System.out.print("Enter your choice: ");
                        int choice = sc.nextInt();

                        switch (choice) {
                            case 1:
                                // Update price
                                System.out.print("Enter new price: ");
                                int newPrice = sc.nextInt();
                                itemToUpdate.setPrice(newPrice);
                                System.out.println("Price updated successfully!");
                                break;

                            case 2:
                                // Update availability
                                System.out.print("Update availability (1 for Available, 2 for Not Available): ");
                                int availabilityInput = sc.nextInt();
                                if (availabilityInput == 1) {
                                    itemToUpdate.setAvailability(ItemAvailability.isAvailable);
                                } else if (availabilityInput == 2) {
                                    itemToUpdate.setAvailability(ItemAvailability.isNotAvailable);
                                } else {
                                    System.out.println("Invalid availability selection. No changes made.");
                                }
                                System.out.println("Availability updated successfully!");
                                break;

                            case 0:
                                updating = false; // Exit the update loop
                                break;

                            default:
                                System.out.println("Invalid option. Please choose again.");
                                break;
                        }
                    }
                } else {
                    System.out.println("Item not found.");
                }

            } else if (input.equals("2")) {
                // Add item
                System.out.print("Enter item name: ");
                String newItemName = sc.next();
                boolean exists = false;

                for (Items item : allItems) {
                    if (item.getName().equalsIgnoreCase(newItemName)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Item already exists. Cannot add a duplicate item.");
                } else {
                    System.out.print("Enter price: ");
                    int price = sc.nextInt();
                    displayCategories();
                    System.out.print("Select a category (1-5): ");
                    int categoryInput = sc.nextInt();
                    FoodCategory selectedCategory = null;

                    switch (categoryInput) {
                        case 1: selectedCategory = FoodCategory.Beverages; break;
                        case 2: selectedCategory = FoodCategory.Sides; break;
                        case 3: selectedCategory = FoodCategory.Deserts; break;
                        case 4: selectedCategory = FoodCategory.Snacks; break;
                        case 5: selectedCategory = FoodCategory.MainCourse; break;
                        default: System.out.println("Invalid category selection."); continue;
                    }

                    // Create and add new item
                    new Items(selectedCategory, newItemName, price);
                    System.out.println("Item added successfully!");
                }

            } else if (input.equals("3")) {
                deleteItem();
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("\n");
        }
        return input;
    }



    public static void displayItemsMenuMessage(){
        System.out.println("Welcome to the Items Menu");
        System.out.println("-1.  Logout");
        System.out.println(" 0.  Main Menu");
        System.out.println(" 1.  View All Items");
        System.out.println(" 2.  View all Items sorted from higher to lower price");
        System.out.println(" 3.  View all Items sorted from lower to higher price");
        System.out.println(" 4.  View items by category");
        System.out.println(" 5.  Search for item/items");

    }



    public static String browseMenuItems() {
        String input = "";
        while (!input.equals("0") && !input.equals("-1")) {
            displayItemsMenuMessage();
            input = sc.next();

            if (input.equals("0") || input.equals("-1")) {
                break;
            } else if (input.equals("1")) {
                // Show all items
                System.out.println("All Items:");
                for (Items item : allItems) {
                    System.out.println(item);
                }
            } else if (input.equals("2")) {
                // Show all items sorted from higher to lower price
                System.out.println("Items sorted from higher to lower price:");
                ArrayList<Items> sortedItemsHighToLow = new ArrayList<>(allItems);
                Collections.sort(sortedItemsHighToLow, new ItemPriceHighToLowComparator());
                for (Items item : sortedItemsHighToLow) {
                    System.out.println(item);
                }
            } else if (input.equals("3")) {
                // Show all items sorted from lower to higher price
                System.out.println("Items sorted from lower to higher price:");
                ArrayList<Items> sortedItemsLowToHigh = new ArrayList<>(allItems);
                Collections.sort(sortedItemsLowToHigh); // Uses the compareTo method
                for (Items item : sortedItemsLowToHigh) {
                    System.out.println(item);
                }
            } else if (input.equals("4")) {
                // Show items by a particular category
                displayCategories();
                System.out.print("Select a category (1-5): ");
                int categoryInput = sc.nextInt();
                FoodCategory selectedCategory = null;

                switch (categoryInput) {
                    case 1: selectedCategory = FoodCategory.Beverages; break;
                    case 2: selectedCategory = FoodCategory.Sides; break;
                    case 3: selectedCategory = FoodCategory.Deserts; break;
                    case 4: selectedCategory = FoodCategory.Snacks; break;
                    case 5: selectedCategory = FoodCategory.MainCourse; break;
                    default: System.out.println("Invalid category selection."); continue;
                }

                System.out.println("Items in category: " + selectedCategory+"\n");
                ArrayList<Items> itemsInCategory = categorizedItems.get(selectedCategory);
                if (itemsInCategory != null && !itemsInCategory.isEmpty()) {
                    for (Items item : itemsInCategory) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("No items available in this category.");
                }
            } else if (input.equals("5")) {
                // Allow users to search for items through a keyword or item name
                System.out.print("Enter the item name to search: ");
                String searchQuery = sc.next();
                boolean found = false;

                for (Items item : allItems) {
                    if (item.getName().equalsIgnoreCase(searchQuery)) {
                        System.out.println("Found Item: " + item);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No items found matching your search.");
                }
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("\n");
        }

        return input;
    }

}
