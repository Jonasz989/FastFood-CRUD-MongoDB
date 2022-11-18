package Restaurant.System;

public class Menu {
    _id _idObject;
    private float menu_id;
    private String name;
    private float price;
    private String description;


    // Getter Methods

    public _id get_id() {
        return _idObject;
    }

    public float getMenu_id() {
        return menu_id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    // Setter Methods

    public void set_id(_id _idObject) {
        this._idObject = _idObject;
    }

    public void setMenu_id(float menu_id) {
        this.menu_id = menu_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
