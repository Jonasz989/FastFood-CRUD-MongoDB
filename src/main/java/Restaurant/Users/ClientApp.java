package Restaurant.Users;

import Restaurant.Database.Connection;
import Restaurant.System.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientApp {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<Menu> menuWithFood = new ArrayList<>();
    private static ArrayList<Integer> foodIDs = new ArrayList<>();
    private static ArrayList<Integer> basket = new ArrayList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) {


        Session client = new Session(Connection.countDocuments());
        createSession();
        Connection.readMenu(menuWithFood);
        System.out.println("Welcome to the BestBurgers");

        menu(client);








    }

    public static void createSession() {
        Connection.addSession();
    }

    public static void menu(Session client) {
        Scanner scan = new Scanner(System.in);
        for (;;) {
            System.out.println("What do you want do to day");
            System.out.println("1. Check menu");
            System.out.println("2. Check your basket");
            System.out.println("3. Check your order status");
            System.out.println("0. Quit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    for (;;) {
                        int choiceInChoosingFood = -1;
                        checkMenu(client);
                        System.out.println("0. Quit");
                        System.out.print("Write the position you would like to add to your basket: ");
                        choiceInChoosingFood = scan.nextInt();
                        if (choiceInChoosingFood == 0) {
                            break;
                        }
                        if (foodIDs.contains(choiceInChoosingFood)) {
                            addToBasket(choiceInChoosingFood);
                        } else {
                            System.out.println("Sorry, wrong position");
                        }
                    }
                    break;
                case 2:
                    //checking basket
                case 3:
                    //checking orders
                    break;
                case 0:
                    System.exit(100);
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }
    }

    public static void checkMenu(Session client) {
        for (Menu ess : menuWithFood) {
            System.out.println((int)ess.getMenu_id() + ". " + ess.getName() + " - price: " + ess.getPrice());
            foodIDs.add((int) ess.getMenu_id());
        }
    }

    public static void addToBasket(int IDOfPosition) {
        basket.add(IDOfPosition);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
