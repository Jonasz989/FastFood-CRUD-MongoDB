/**
 * @author Damian Jabłoński
 */

package Restaurant.Users;

import Restaurant.Database.Connection;
import Restaurant.System.Menu;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;

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

        LocalDateTime obiekt = LocalDateTime.now();
        DateTimeFormatter formatowanie = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = obiekt.format(formatowanie);
        System.out.println(obiekt);
        System.out.println(format);

        /*Session client = new Session(Connection.countDocuments());
        createSession();
        Connection.readMenu(menuWithFood);
        System.out.println("Welcome to the BestBurgers");

        menu(client);*/








    }

    public static void createSession() {
        Connection.addSession();
    }

    public static void menu(Session client) {

        for (;;) {
            System.out.println("What do you want do to day");
            System.out.println("1. Check menu");
            System.out.println("2. Check your basket");
            System.out.println("3. Pay your order");
            System.out.println("4. Check your order status");
            System.out.println("0. Quit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    checkMenu(client);

                    break;
                case 2:
                    if (client.getBasket().size() == 0) {
                        System.out.println("No items in basket");
                    } else {
                        for (int i = 1; i < client.getBasket().size() + 1; i++) {
                            System.out.println("ID: " + i + ". " + client.getBasket().get(i - 1).getName() + " - price: " + client.getBasket().get(i - 1).getPrice());
                        }
                    }
                    break;
                case 3:
                    //submit order
                    break;
                case 4:
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
        for(;;) {
            for (Menu ess : menuWithFood) {
                System.out.println((int)ess.getMenu_id() + ". " + ess.getName() + " - price: " + ess.getPrice());
                foodIDs.add((int) ess.getMenu_id());
            }
            int choiceInChoosingFood = -1;
            System.out.println("0. Quit");
            System.out.print("Write the position you would like to add to your basket: ");
            choiceInChoosingFood = scan.nextInt();
            if (choiceInChoosingFood == 0) {
                return;
            }
            if (foodIDs.contains(choiceInChoosingFood)) {
                addToBasket(menuWithFood.get(choiceInChoosingFood - 1), client);

            } else {
                System.out.println("Sorry, wrong position");
            }
        }

    }

    public static void addToBasket(Menu IDOfPosition, Session client) {
        client.getBasket().add(IDOfPosition);
    }

    public static void submitOrder(Session client) {

    }

    public static void createOrder(Session client) {

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
