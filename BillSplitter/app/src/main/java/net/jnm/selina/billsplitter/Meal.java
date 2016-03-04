package net.jnm.selina.billsplitter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ryana on 3/3/2016.
 */
public class Meal {
    private String name;
    private int quantity;
    private double price;
    private int people;

    public Meal(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        people = 0;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public double getUnitPrice() {
        if( people == 0 ) {
            return 0;
        } else {
            return getTotalPrice() / people;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increase() {
        people++;
    }

    public void decrease() {
        people--;
    }
}
