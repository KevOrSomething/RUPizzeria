package com.example.pizzaandroidproject;

import java.text.DecimalFormat;

/**
 * A class to represent a Deluxe pizza as a subclass of Pizza, with custom toppings
 * and methods to calculate the price.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class Deluxe extends Pizza {

    private static final double BASE_PRICE = 12.99;
    private static final int BASE_TOPPINGS = 5;

    /**
     * A constructor that adds Pepperoni, Sausage, GreenPeppers, Onions, Mushrooms to the
     * Deluxe pizza.
     */
    public Deluxe() {
        super();
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.Sausage);
        toppings.add(Topping.GreenPeppers);
        toppings.add(Topping.Onions);
        toppings.add(Topping.Mushrooms);
    }

    /**
     * A method to calculate the price of the pizza given the toppings, base price, and size of the pizza.
     * @return final price of the current deluxe pizza.
     */
    public double price() {
        int addedToppings = toppings.size() - BASE_TOPPINGS;
        if(toppings.size() > MAX_TOPPINGS) {
            addedToppings = MAX_TOPPINGS - BASE_TOPPINGS;
        }
        if (addedToppings < 0) addedToppings = 0;
        double finalPrice = BASE_PRICE + (sizeNum() * SIZE_PRICE) + (addedToppings * TOPPING_PRICE);
        return Math.round(finalPrice*ROUNDING_CORRECTION)/ROUNDING_CORRECTION;
    };

    /**
     * A toString method to represent the Deluxe pizza as a String.
     * @return a string of the Deluxe object's information.
     */
    @Override
    public String toString() {

        String moneyFormat = "###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(moneyFormat);
        String formattedPrice = decimalFormat.format(this.price());

        return "Deluxe " + super.toString() + ", $" + formattedPrice;
    }
}
