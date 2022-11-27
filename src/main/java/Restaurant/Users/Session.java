package Restaurant.Users;

import Restaurant.System.Menu;
import Restaurant.System.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Session {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int userID;
    private double currentPrice = 0;
    private ArrayList<Menu> basket = new ArrayList<>();
    private ArrayList<Integer> idsOfBasket = new ArrayList<>();
    private String paymentType = "NONE";
    private ArrayList<Order> orders = new ArrayList<>();



    Session(int userID) {
        this.userID = userID;
    }



    public int getUserID() {
        return userID;
    }
    public ArrayList<Menu> getBasket() {
        return basket;
    }
    public void setBasket(ArrayList<Menu> basket) {
        this.basket = basket;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
    public ArrayList<Integer> getIdsOfBasket() {
        return idsOfBasket;
    }
    public void setIdsOfBasket(ArrayList<Integer> idsOfBasket) {
        this.idsOfBasket = idsOfBasket;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

}
