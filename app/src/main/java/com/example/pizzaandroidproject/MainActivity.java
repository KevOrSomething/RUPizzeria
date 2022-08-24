package com.example.pizzaandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A class to control the Main activity that contains various
 * functions to handle user interaction.
 *
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextInputEditText phoneNumberInput;
    private Spinner pizzaSpinner;
    private Button addPizzaBtn, storeOrdersBtn, currentOrderBtn;
    private static Order currentOrder;
    private static StoreOrders storeOrders;

    /**
     * onCreate method to initialize Main activity with the necessary layout.
     * Also performs other setup such as connecting views with associated adapters.
     * @param savedInstanceState default bundle for dynamic data passing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzaSpinner = findViewById(R.id.pizzaSpinner);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        storeOrders = new StoreOrders();
    }

    /**
     * Opens Customize Pizza activity when the relevant button is clicked.
     * @param view instance of View to be used during method call
     */
    public void addPizza(View view) {
        if(newOrderCheck()) {
            String pizzaType = (String) pizzaSpinner.getSelectedItem();
            Intent intent = new Intent(this, CustomizeActivity.class);
            intent.putExtra("TYPE", pizzaType);
            startActivity(intent);
        }
    }

    /**
     * Opens the Current Order activity when the relevant button is clicked.
     * @param view instance of View to be used during method call
     */
    public void currentOrder(View view){
        if(newOrderCheck()) {
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Checks if a user validly attempts to create a new order.
     * @return true if the user can create a new order, false if otherwise
     */
    private boolean newOrderCheck(){
        String phoneNumber = phoneNumberInput.getText().toString();

        boolean checked = checkPhoneNumber(phoneNumber);
        if(!checked) {
            Toast.makeText(getApplicationContext(), R.string.invalid_phone_alert, Toast.LENGTH_SHORT).show();
            return false;
        }

        int currentOrderIndex = storeOrders.findOrderIndex(phoneNumber);
        if(currentOrderIndex != -1){
            Toast.makeText(getApplicationContext(), R.string.order_already_exists_alert, Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if(currentOrder == null) {
                currentOrder = new Order(phoneNumber);
                Toast.makeText(getApplicationContext(), R.string.starting_new_order_alert, Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(!phoneNumber.equals(currentOrder.getPhoneNum())) {
                Toast.makeText(getApplicationContext(), R.string.mid_order_alert, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    /**
     * Opens the Store Orders activity when the relevant button is clicked.
     * @param view instance of View to be used during method call
     */
    public void openStoreOrders(View view) {
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        startActivity(intent);
    }

    /**
     * Checks if a phone number is in valid 10-digit format (no excess characters).
     * @param phoneNumber string of the given phone number to be checked
     * @return true if the phone number is valid, false otherwise
     */
    private boolean checkPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 10) return false;
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Getter method for current order.
     * @return the current order
     */
    public static Order getCurrentOrder(){
        return currentOrder;
    }

    /**
     * Setter method for the current order.
     * @param o the new order to set currentOrder to
     */
    public static void setCurrentOrder(Order o) {
        currentOrder = o;
    }

    /**
     * Getter method for this app's store orders instance.
     * @return this app's store orders instance
     */
    public static StoreOrders getStoreOrders(){
        return storeOrders;
    }

    /**
     * Method to remove the order from this app's store orders instance.
     * @param o the order to remove
     */
    public static void removeOrder(Order o){
        storeOrders.removeOrder(o);
    }
}