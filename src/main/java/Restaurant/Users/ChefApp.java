/**
 * @author Damian Jabłoński
 */

package Restaurant.Users;

import Restaurant.Database.Connection;
import Restaurant.System.Menu;
import Restaurant.System.Order;
import java.util.ArrayList;
import java.util.Scanner;

public class ChefApp {

    private static ArrayList<Order> ORDERS = new ArrayList<>();
    private static ArrayList<Integer> ORDERSid = new ArrayList<>();
    private static ArrayList<Menu> menuWithFood = new ArrayList<>();
    private static ArrayList<Integer> foodIDs = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {

        Connection.readMenu(menuWithFood);
        for (Menu menu : menuWithFood) {
            foodIDs.add((int) menu.getMenu_id());
        }

        for (;;) {
            int counterForOrders = 1;
            System.out.println("0. Refresh orders");
            System.out.println("Orders to do: ");
            for (Order ord : ORDERS) {
                System.out.println(counterForOrders++ + ". Order ID: " + (int) ord.getOrder_id());
                for(Object z : ord.getPositions()) {
                    System.out.println(menuWithFood.get((int) z).getName());
                }
            }
            System.out.println("What do you want do: ");
            int choice;
            choice = scan.nextInt();

            if (choice == 0) {
                readOrders();
            } else if (ORDERSid.contains(choice - 1)) {
                Connection.updateOrderChef((int) ORDERS.get(choice - 1).getOrder_id());
                readOrders();

            } else {
                System.out.println("Sorry, wrong position");
            }

        }
    }

    private static void readOrders() {
        ORDERS.clear();
        int tempForOrdersID = 0;
        Connection.readOrdersThatAreInProgress(ORDERS);
        for (Order ord : ORDERS) {
            ORDERSid.add(tempForOrdersID++);
        }
    }
}
