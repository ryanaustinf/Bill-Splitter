package net.jnm.selina.billsplitter;

import java.util.ArrayList;

/**
 * Created by ryana on 3/3/2016.
 */
public class Person {
    private String name;
    private ArrayList<Meal> meals;
    private double cashTendered;

    public Person(String name) {
        this.name = name;
        cashTendered = 0;
        meals = new ArrayList<Meal>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPayment() {
        double payment = 0;
        for(Meal m: meals) {
            payment += m.getUnitPrice();
        }
        return payment;
    }

    public boolean hasMeal(String meal) {
        for(Meal m: meals) {
            if(m.getName().equals(meal)) {
                return true;
            }
        }

        return false;
    }

    public void reset() {
        for(int i = 0; i < meals.size();) {
            Meal m = meals.get(i);
            m.decrease();
            meals.remove(i);
        }
    }

    public void addPayment(Meal m) {
        for(int i = 0; i < meals.size(); i++ ) {
            if( meals.get(i).getName().equals(m.getName())) {
                return;
            }
        }
        meals.add(m);
        m.increase();
    }

    public void removePayment(Meal m) {
        for(int i = 0; i < meals.size(); i++ ) {
            if( meals.get(i).getName().equals(m.getName())) {
                meals.remove(m);
                m.decrease();
                break;
            }
        }
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(double cashTendered) {
        this.cashTendered = cashTendered;
    }

    public double getChange() {
        return cashTendered - getPayment();
    }
}
