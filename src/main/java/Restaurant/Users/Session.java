package Restaurant.Users;

import Restaurant.System.Menu;

import java.util.ArrayList;

public class Session {


    private int userID;
    private double currentPrice = 0;
    private ArrayList<Menu> basket = new ArrayList<>();



    Session(int userID) {
        this.userID = userID;
    }



    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
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

}
