package Restaurant.System;

import java.util.ArrayList;

public class Order {
    _id _idObject;
    Order_id Order_idObject;
    User_id User_idObject;
    ArrayList <Object> positions = new ArrayList<>();
    Price PriceObject;
    Date DateObject;
    private String order_state;
    private String payment_status;
    private String discount;
    Chef_id Chef_idObject;
    Cashier_id Cashier_idObject;
    private String payment_method;


    // Getter Methods

    public _id get_id() {
        return _idObject;
    }

    public Order_id getOrder_id() {
        return Order_idObject;
    }

    public User_id getUser_id() {
        return User_idObject;
    }

    public Price getPrice() {
        return PriceObject;
    }

    public Date getDate() {
        return DateObject;
    }

    public String getOrder_state() {
        return order_state;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getDiscount() {
        return discount;
    }

    public Chef_id getChef_id() {
        return Chef_idObject;
    }

    public Cashier_id getCashier_id() {
        return Cashier_idObject;
    }

    public String getPayment_method() {
        return payment_method;
    }

    // Setter Methods

    public void set_id(_id _idObject) {
        this._idObject = _idObject;
    }

    public void setOrder_id(Order_id order_idObject) {
        this.Order_idObject = order_idObject;
    }

    public void setUser_id(User_id user_idObject) {
        this.User_idObject = user_idObject;
    }

    public void setPrice(Price priceObject) {
        this.PriceObject = priceObject;
    }

    public void setDate(Date dateObject) {
        this.DateObject = dateObject;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setChef_id(Chef_id chef_idObject) {
        this.Chef_idObject = chef_idObject;
    }

    public void setCashier_id(Cashier_id cashier_idObject) {
        this.Cashier_idObject = cashier_idObject;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}




