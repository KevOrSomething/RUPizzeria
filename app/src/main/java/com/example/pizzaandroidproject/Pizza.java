package com.example.pizzaandroidproject;

import java.util.ArrayList;

/**
 * Abstract class to represent a pizza object, extended by other subclasses in order to create
 * different types of pizzas.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public abstract class Pizza {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    protected static final double SIZE_PRICE = 2;
    protected static final double TOPPING_PRICE = 1.49;
    protected static final int MAX_TOPPINGS = 7;
    protected static final double ROUNDING_CORRECTION = 100.0;

    /**
     * Default pizza constructor that initializes it as a small pizza to be overloaded.
     */
    public Pizza() {
        size = Size.small;
    }
    /**
     * Helper method to be used with the size enum and return a value;
     * @return an integer value, 0 if small, 1 if medium, and 2 if large
     */
    public int sizeNum() {
        switch (size) {
            case small:
                return 0;
            case medium:
                return 1;
            case large:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Abstract method price to be implemented in subclasses to calculate the price of a
     * certain pizza.
     * @return double object of price.
     */
    public abstract double price();

    /**
     * Return the size of the pizza.
     * @return Size enum of pizza.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Set the size of the pizza.
     * @param size size enum to be set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Method to add topping to the pizza.
     * @param topping topping enum to be added.
     */
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    /**
     * Method to remove topping from pizza.
     * @param topping topping enum to be removed.
     */
    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    /**
     * toString method to represent the pizza.
     * @return String of the Pizza object's information.
     */
    public String toString() {
        String toppingsList = toppings.toString().replace("[", "").replace("]", "");
        return "pizza, " + toppingsList + ", " + size;
    }
}
