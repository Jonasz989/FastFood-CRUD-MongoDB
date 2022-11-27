/**
 * A class for converting our order from database to application
 */
package Restaurant.System;

import java.util.ArrayList;

public class Order {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    _id _idObject;
    private float order_id;
    private float user_id;
    ArrayList < Object > positions = new ArrayList <>();
    private float price;
    Date DateObject;
    private String order_state;
    private String payment_method;
    private String payment_status;
    private String discount;
    private float chef_id;
    private float cashier_id;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public _id get_id() {
        return _idObject;
    }

    public float getOrder_id() {
        return order_id;
    }

    public float getUser_id() {
        return user_id;
    }

    public ArrayList<Object> getPositions() {
        return positions;
    }
    public float getPrice() {
        return price;
    }

    public Date getDate() {
        return DateObject;
    }

    public String getOrder_state() {
        return order_state;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getDiscount() {
        return discount;
    }

    public float getChef_id() {
        return chef_id;
    }

    public float getCashier_id() {
        return cashier_id;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SETTERS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void set_id(_id _idObject) {
        this._idObject = _idObject;
    }

    public void setOrder_id(float order_id) {
        this.order_id = order_id;
    }

    public void setUser_id(float user_id) {
        this.user_id = user_id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDate(Date dateObject) {
        this.DateObject = dateObject;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setChef_id(float chef_id) {
        this.chef_id = chef_id;
    }

    public void setCashier_id(float cashier_id) {
        this.cashier_id = cashier_id;
    }
    public void setPositions(ArrayList<Object> positions) {
        this.positions = positions;
    }
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
