package com.example.pizzaandroidproject;

import java.text.DecimalFormat;

/**
 * A class to represent a Pepperoni pizza as a subclass of Pizza, with custom toppings
 * and methods to calculate the price.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class Pepperoni extends Pizza {

  private static final double BASE_PRICE = 8.99;
  private static final int BASE_TOPPINGS = 1;

  /**
   * A constructor that adds Pepperoni to the pizza.
   */
  public Pepperoni() {
    super();
    toppings.add(Topping.Pepperoni);
  }

  /**
   * A method to calculate the price of the pizza given the toppings, base price, and size of the pizza.
   * @return final price of the current Pepperoni pizza.
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
   * A toString method to represent the Pepperoni pizza as a String.
   * @return a string of the Pepperoni object's information.
   */
  @Override
  public String toString() {
    String moneyFormat = "###,##0.00";
    DecimalFormat decimalFormat = new DecimalFormat(moneyFormat);
    String formattedPrice = decimalFormat.format(this.price());

    return "Pepperoni " + super.toString() + ", $" + formattedPrice;
  }
}

