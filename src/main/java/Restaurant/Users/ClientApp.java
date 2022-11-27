/**
 * @author Damian Jabłoński
 */

package Restaurant.Users;

import Restaurant.Database.Connection;
import Restaurant.System.Menu;
import Restaurant.System.MyException;
import Restaurant.System.Order;
import Restaurant.System.TimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientApp {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<Menu> menuWithFood = new ArrayList<>();
    private static ArrayList<Integer> foodIDs = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) {



        Session client = new Session(Connection.countDocumentsSession()); //creating new user session
        createSession(); //adding session in db
        Connection.readMenu(menuWithFood); //reading menu from db
        System.out.println("Welcome to the BestBurgers");
        menu(client); //starting menu








    }

    public static void createSession() { // adding session in database
        Connection.addSession();
    }
    public static void menu(Session client) {
        for (;;) {
            System.out.println();
            System.out.println("What do you want to do today:");
            System.out.println("1. Check menu");
            System.out.println("2. Check your basket");
            System.out.println("3. Pay your order");
            System.out.println("4. Check your order status");
            System.out.println("0. Quit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    checkMenu(client); //checking menu
                    break;
                case 2:
                    checkBasket(client); //checking basket
                    break;
                case 3:
                    finalizeOrder(client); //finalizing order
                    break;
                case 4:
                    Connection.readOrdersFromUser(client); //reading order of client from db
                    checkOrders(client); //showing his orders
                    break;
                case 0:
                    System.exit(100);
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }
    }
    public static void checkMenu(Session client) { //showing menu
        for(;;) {
            for (Menu menu : menuWithFood) { //showing possible options
                System.out.println((int)menu.getMenu_id() + ". " + menu.getName() + " - price: " + menu.getPrice());
                foodIDs.add((int) menu.getMenu_id());
            }
            int choiceInChoosingFood;
            System.out.println("0. Quit");
            System.out.print("Write the position you would like to add to your basket: ");
            choiceInChoosingFood = scan.nextInt(); // getting position id we want to add to basket
            if (choiceInChoosingFood == 0) {
                return;
            }
            if (foodIDs.contains(choiceInChoosingFood)) {
                addToBasket(menuWithFood.get(choiceInChoosingFood - 1), client); //adding to basket

            } else {
                System.out.println("Sorry, wrong position");
            }
        }

    }
    public static void checkBasket(Session client) { //checking what we have in basket
        if (client.getBasket().size() == 0) {
            System.out.println("No items in basket");
        } else {
            for (int i = 1; i < client.getBasket().size() + 1; i++) {
                System.out.println(i + ". " + client.getBasket().get(i - 1).getName() + " - price: " + client.getBasket().get(i - 1).getPrice());
            }
            System.out.println("Total price: " + client.getCurrentPrice());
        }
    }
    public static void addToBasket(Menu IDOfPosition, Session client) {
        try {
            if (client.getBasket().size() > 5) {
                throw new MyException();
            }
            client.getBasket().add(IDOfPosition);
            client.getIdsOfBasket().add((int) IDOfPosition.getMenu_id());
            client.setCurrentPrice(IDOfPosition.getPrice() + client.getCurrentPrice()); //updating price

        } catch (MyException e) {

        }

    }
    public static void finalizeOrder(Session client) {
        System.out.println("Do you want to finalize your order?");
        System.out.println("1. Yes - type '1'");
        System.out.println("2. No - type '2'");
        int choice = 0;
        choice = scan.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Choose payment method");
                System.out.println("1. Card");
                System.out.println("2. Cash");
                System.out.println("0. Quit");
                int choiceInPaymentType;
                choiceInPaymentType = scan.nextInt();
                switch (choiceInPaymentType) {
                    case 1:
                        System.out.println("Paying with card");
                        client.setPaymentType("CARD");
                        submitOrder(client);
                        System.out.println("Your order ID is: " + Connection.countDocumentsOrders());
                        resetClientForNewOrder(client);
                        break;
                    case 2:
                        System.out.println("Paying with cash");
                        client.setPaymentType("CASH");
                        submitOrder(client);
                        System.out.println("Your order ID is: " + Connection.countDocumentsOrders());
                        resetClientForNewOrder(client);
                        break;
                    case 0:
                        System.out.println("Leaving");
                        break;
                    default:
                        System.out.println("Wrong input");
                        break;
                }
                break;
            case 2:
                System.out.println("Going back");
                return;
            default:
                System.out.println("Wrong input");
                break;
        }
    }
    public static void resetClientForNewOrder(Session client) {
        client.setCurrentPrice(0);
        client.getBasket().clear();
        client.getIdsOfBasket().clear();
        client.setPaymentType("NONE");
    }
    public static void submitOrder(Session client) { //adding order to db
        Connection.addOrder(client);
    }
    public static void checkOrders(Session client) { //checking our orders from db
        System.out.println("Your orders: ");
        for (Order currentOrder : client.getOrders()) {




        }

            if (client.getOrders().isEmpty()) {
                System.out.println("No orders");
            } else {
                for (int i = 1; i < client.getOrders().size() + 1; i++) {
                    System.out.println(i + ". " + "ID: " + (int) client.getOrders().get(i - 1).getOrder_id() + " - STATUS: " + client.getOrders().get(i - 1).getOrder_state() + " - PAYED: " + client.getOrders().get(i - 1).getPayment_status() + " DISCOUNT: " + client.getOrders().get(i - 1).getDiscount());
                }
            }





    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
