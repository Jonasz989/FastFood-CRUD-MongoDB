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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final ArrayList<Order> ORDERS = new ArrayList<>();
    private static final ArrayList<Integer> ORDERSid = new ArrayList<>();
    private static final ArrayList<Menu> menuWithFood = new ArrayList<>();
    private static final ArrayList<Integer> foodIDs = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        readMenu(); //reading menu to memory


        for (;;) {
            int counterForOrders = 1;
            System.out.println("0. Refresh orders");
            System.out.println("Orders to do: ");
            for (Order ord : ORDERS) {
                System.out.println(counterForOrders++ + ". Order ID: " + (int) ord.getOrder_id());
                for (Object z : ord.getPositions()) {
                    System.out.println(menuWithFood.get((int) z - 1).getName());
                }
            }
            System.out.println("What do you want do: ");
            int choice;
            choice = scan.nextInt();

            if (choice == 0) {
                readOrders(); //refreshing orders
            } else if (ORDERSid.contains(choice - 1)) {
                Connection.updateOrderChef((int) ORDERS.get(choice - 1).getOrder_id());
                readOrders();
            } else {
                System.out.println("Sorry, wrong position");
            }

        }
    }


    private static void readMenu() {
        Connection.readMenu(menuWithFood); //connecting to db to get menu into menuWithFood
        for (Menu menu : menuWithFood) {
            foodIDs.add((int) menu.getMenu_id()); //adding IDs of position to menu
        }
    }

    private static void readOrders() {
        ORDERS.clear(); //clearing current orders in memory
        int tempForOrdersID = 0;
        Connection.readOrdersThatAreInProgress(ORDERS);
        for (Order ord : ORDERS) {
            ORDERSid.add(tempForOrdersID++); //calculating how many IDs from order are in the ORDERS
        }
    }
}
