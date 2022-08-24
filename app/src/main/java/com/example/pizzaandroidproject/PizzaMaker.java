package com.example.pizzaandroidproject;

/**
 * Public PizzaMaker class to instantiate a pizza of a certain type.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class PizzaMaker {
  /**
   * Method to create and return a pizza given a certain flavor.
   * @param flavor flavor to be created.
   * @return pizza object of flavor, null if invalid flavor.
   */
  public static Pizza createPizza(String flavor) {

    Pizza pizza;

    if(flavor.equals("Pepperoni")) {
      pizza = new Pepperoni();
    } else if (flavor.equals("Hawaiian")) {
      pizza = new Hawaiian();
    } else if (flavor.equals("Deluxe")) {
      pizza = new Deluxe();
    } else {
      pizza = null;
    }

    return pizza;
  }
}
