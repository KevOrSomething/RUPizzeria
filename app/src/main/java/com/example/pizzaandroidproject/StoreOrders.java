package com.example.pizzaandroidproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A class to represent an array of orders placed through the store via the GUI.
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class StoreOrders {

    private ArrayList<Order> orderList;
    private double price;
    private final static int NOT_FOUND = -1;

    /**
     * Constructor to instantiate the order list and set the total price to 0.
     */
    public StoreOrders() {
        price = 0;
        orderList = new ArrayList<Order>();
    }

    /**
     * Method to add an order to the order array and update the price.
     * @param order order to be added.
     */
    public void addOrder(Order order) {
        orderList.add(order);
        price += order.getPrice();
    }

    /**
     * Method to remove an order to the order array and update the price.
     * @param order order to be removed.
     */
    public void removeOrder(Order order) {
        orderList.remove(order);
        price -= order.getPrice();
    }

    /**
     * Method to get the total price of the order.
     * @return price value of type double.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to find the index of an order number within the array and return it.
     * @param newPhoneNum phone number to be found.
     * @return index if found, NOT_FOUND if not found.
     */
    public int findOrderIndex(String newPhoneNum) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getPhoneNum().equals(newPhoneNum)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method to find and return Order object from the array.
     * @param newPhoneNum order number to be found.
     * @return Order object from the array.
     */
    public Order findOrder(String newPhoneNum) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getPhoneNum().equals(newPhoneNum)) {
                return orderList.get(i);
            }
        }
        return null;
    }

    public boolean isEmpty() {
        if(orderList.isEmpty()) return true;
        return false;
    }

    /**
     * Method to get an order from the list based on index.
     * @param index index to be found.
     * @return Order based on index.
     */
    public Order getOrder(int index) {
        return orderList.get(index);
    }

    /**
     * Method to get all phone numbers from the order list.
     * @return ArrayList of phone numbers.
     */
    public ArrayList<String> getOrderListPhoneNumbers() {
        ArrayList<String> phoneNumberList = new ArrayList<String>();
        for(Order order : orderList) {
            phoneNumberList.add(order.getPhoneNum());
        }
        return phoneNumberList;
    }
}
