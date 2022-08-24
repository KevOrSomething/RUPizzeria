package com.example.pizzaandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A class to control the Current Order activity that contains various
 * functions to handle user interaction.
 *
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class CurrentOrderActivity extends AppCompatActivity {

    private TextView phoneNumberOutputLabel, subtotalOutputLabel,
            salesTaxOutputLabel, orderTotalOutputLabel;

    private ListView orderListView;
    ArrayList<Pizza> orderPizzas;
    ArrayAdapter orderAdapter;
    Order currentOrder;

    /**
     * onCreate method to initialize Current Order activity with the necessary layout.
     * Also performs other setup such as connecting views with associated adapters.
     * @param savedInstanceState default bundle for dynamic data passing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        currentOrder = MainActivity.getCurrentOrder();

        phoneNumberOutputLabel = findViewById(R.id.phoneNumberOutputLabel);
        subtotalOutputLabel = findViewById(R.id.subtotalOutputLabel);
        salesTaxOutputLabel = findViewById(R.id.salesTaxOutputLabel);
        orderTotalOutputLabel = findViewById(R.id.orderTotalOutputLabel);
        orderListView = findViewById(R.id.orderListView);

        phoneNumberOutputLabel.setText(currentOrder.getPhoneNum());
        double subtotal = currentOrder.getPrice() - currentOrder.getSalesTax();
        subtotalOutputLabel.setText(formatPrice(subtotal));
        salesTaxOutputLabel.setText(formatPrice(currentOrder.getSalesTax()));
        orderTotalOutputLabel.setText(formatPrice(currentOrder.getPrice()));

        orderPizzas = currentOrder.getPizzaList();
        orderAdapter = new ArrayAdapter(this,
                R.layout.list_item_multiple_choice_wrapped, orderPizzas);
        orderListView.setAdapter(orderAdapter);
        orderListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    /**
     * A method to remove a pizza from the current order.
     * @param view instance of View to be used during method call
     */
    public void removePizzas(View view){
        SparseBooleanArray checked = orderListView.getCheckedItemPositions();
        for(int i = 0; i < orderAdapter.getCount(); i++) {
            if (checked.get(i)) {
                Pizza p = orderPizzas.get(i);
                orderPizzas.remove(p);
                currentOrder.removePizza(p);
                orderAdapter.notifyDataSetChanged();
            }
        }
        phoneNumberOutputLabel.setText(currentOrder.getPhoneNum());
        double subtotal = currentOrder.getPrice() - currentOrder.getSalesTax();
        subtotalOutputLabel.setText(formatPrice(subtotal));
        salesTaxOutputLabel.setText(formatPrice(currentOrder.getSalesTax()));
        orderTotalOutputLabel.setText(formatPrice(currentOrder.getPrice()));
        Toast.makeText(getApplicationContext(), R.string.selected_pizzas_removed_alert, Toast.LENGTH_SHORT).show();
    }

    /**
     * A method to handle placing the order, and returning the app back to MainActivity.
     * @param view instance of View to be used during method call
     */
    public void placeOrder(View view){
        StoreOrders mainStoreOrders = MainActivity.getStoreOrders();
        if(currentOrder.getPizzaList().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.empty_orders_alert, Toast.LENGTH_SHORT).show();
            return;
        }
        else if(mainStoreOrders.findOrder(currentOrder.getPhoneNum()) != null){
            Toast.makeText(getApplicationContext(), R.string.order_already_exists_alert, Toast.LENGTH_SHORT).show();
            return;
        }
        mainStoreOrders.addOrder(currentOrder);
        orderAdapter.notifyDataSetChanged();
        MainActivity.setCurrentOrder(null);
        finish();
        Toast.makeText(getApplicationContext(), R.string.order_placed_alert, Toast.LENGTH_SHORT).show();
        return;
    }

    /**
     * Method to convert an amount of money in double type to the regular USD decimal format.
     * @param price amount of money using type double
     * @return string of the price in typical USD format
     */
    private String formatPrice(double price) {
        String moneyFormat = "###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(moneyFormat);
        String formattedPrice = decimalFormat.format(price);
        return formattedPrice;
    }
}