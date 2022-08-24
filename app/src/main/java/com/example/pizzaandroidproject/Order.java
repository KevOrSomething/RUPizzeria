package com.example.pizzaandroidproject;

import java.util.ArrayList;

/**
 * A class to represent an order of pizzas at the restaurant. It contains a list of all pizzas
 * contained within the order.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class Order {
    private String phoneNum;
    private ArrayList<Pizza> pizzaList;
    private double price;
    private double salesTax;

    private final double TAX = 0.06625;

    /**
     * A constructor to instantiate a new order given a phone number.
     * @param phoneNum phone number to track the order.
     */
    public Order(String phoneNum) {
        this.phoneNum = phoneNum;
        this.price = 0;
        this.salesTax = 0;
        pizzaList = new ArrayList<Pizza>();
    }

    /**
     * Setter for the phone number variable.
     * @param phoneNum phone number to be set.
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Getter for the current phone number variable.
     * @return phone number string.
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Method to add a pizza to the order and update the total price.
     * @param pizza pizza to be added.
     */
    public void addPizza(Pizza pizza) {
        pizzaList.add(pizza);
        this.updatePrice();
    }

    /**
     * Method to remove a pizza from the order and update the total.
     * @param pizza pizza to be removed.
     */
    public void removePizza(Pizza pizza) {
        pizzaList.remove(pizza);
        this.updatePrice();
    }

    /**
     * Method to update the price of the pizza with sales tax and other checks to verify.
     */
    private void updatePrice() {
        if (pizzaList.isEmpty()) {
            price = 0;
            salesTax = 0;
        } else {
            double newPrice = 0;
            for (Pizza pizza : pizzaList) {
                newPrice += pizza.price();
            }
            salesTax = newPrice * TAX;
            price = newPrice + salesTax;
        }
    }

    /**
     * Method to get the price of the order.
     * @return price of order.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to get the sales tax for the order.
     * @return sales tax value of type double.
     */
    public double getSalesTax() {
        return salesTax;
    }


    /**
     * Method to return the list of pizzas in the order.
     * @return ArrayList of pizza objects.
     */
    public ArrayList<Pizza> getPizzaList() {
        return this.pizzaList;
    }

    /**
     * Method to return list of pizzas as a string.
     * @return Sting array of pizza names.
     */
    public ArrayList<String> getPizzaStringList(){
        ArrayList<String> pizzaStringList = new ArrayList<String>();
        for(Pizza p : pizzaList) {
            pizzaStringList.add(p.toString());
        }
        return pizzaStringList;
    }

}
