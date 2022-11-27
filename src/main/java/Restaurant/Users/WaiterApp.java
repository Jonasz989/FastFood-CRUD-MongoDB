/**
 * @author Damian Jabłoński
 */

package Restaurant.Users;

import Restaurant.Database.Connection;
import Restaurant.System.Menu;
import Restaurant.System.Order;
import Restaurant.System.TimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class WaiterApp {

    private static ArrayList<Order> ORDERS = new ArrayList<>();
    private static ArrayList<Integer> ORDERSid = new ArrayList<>();
    private static ArrayList<Order> ORDERSnotPayed = new ArrayList<>();
    private static ArrayList<Order> ORDERSready = new ArrayList<>();
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
            System.out.println("Current orders: ");
            for (Order ord : ORDERS) {
                System.out.println(counterForOrders++ + ". Order ID: " + (int) ord.getOrder_id() + " Payment: " + ord.getPayment_status() + " Status: " + ord.getOrder_state());
            }
            System.out.println("Choose position you want to update: ");
            int choice;
            choice = scan.nextInt();


            if (choice == 0) {
                readOrders();
            } else if (ORDERSid.contains(choice - 1)) {

                if (ORDERS.get(choice - 1).getPayment_status().equals("NO_PAYMENT")) {
                    Connection.updateOrderPayment((int) ORDERS.get(choice - 1).getOrder_id());
                }
                if (ORDERS.get(choice - 1).getOrder_state().equals("READY")) {
                    Connection.updateOrderFinal((int) ORDERS.get(choice - 1).getOrder_id());
                    LocalDateTime data = LocalDateTime.parse(TimeParser.deleteLastCharAtString(ORDERS.get(choice - 1).getDate().get$date()));
                    LocalDateTime data2 = LocalDateTime.now();
                    LocalDateTime data3 = data2.minusMinutes(5);
                    if (data.compareTo(data3) == -1) {
                        Connection.updateOrderDiscount((int) ORDERS.get(choice - 1).getOrder_id());
                    }
                    System.out.println("ODDANE");
                }

                readOrders();
            } else {
                System.out.println("Sorry, wrong position");
            }


        }
    }


    private static void readOrders() {
        ORDERS.clear();
        ORDERSready.clear();
        ORDERSnotPayed.clear();
        ORDERSid.clear();
        int tempForOrdersID = 0;
        Connection.readOrdersThatAreOrderedOrReady(ORDERS);
        for (Order ord : ORDERS) {
            if (ord.getPayment_status().equals("NO_PAYMENT")) {
                ORDERSnotPayed.add(ord);
            }
            if (ord.getOrder_state().equals("READY")) {
                ORDERSready.add(ord);
            }
            ORDERSid.add(tempForOrdersID++);
        }
    }
}
