package com.example.pizzaandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A class to control the Store Orders Pizza activity that contains various
 * functions to handle user interaction.
 *
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class StoreOrdersActivity extends AppCompatActivity {

    private TextView orderTotalOutputLabel;
    private ListView orderList;
    private Spinner orderSpinner;

    private ArrayList<String> phoneNumList;
    private ArrayList<Pizza> orderPizzas;
    private StoreOrders storeOrders;
    ArrayAdapter pizzaAdapter;
    ArrayAdapter<String> orderAdapter;

    /**
     * onCreate method to initialize Store Orders activity with the necessary layout.
     * Also performs other setup such as connecting views with associated adapters.
     * @param savedInstanceState default bundle for dynamic data passing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        orderTotalOutputLabel = findViewById(R.id.orderTotalOutputLabel);
        orderSpinner = findViewById(R.id.orderSpinner);
        orderList = findViewById(R.id.orderList);
        storeOrders = MainActivity.getStoreOrders();

        try {
            phoneNumList = MainActivity.getStoreOrders().getOrderListPhoneNumbers();
        }
        catch(NullPointerException np) {
            return;
        }

        orderAdapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_spinner_dropdown_item, phoneNumList);
        orderSpinner.setAdapter(orderAdapter);
        setOrderSelectionListener();

        orderPizzas = new ArrayList<Pizza>();
        pizzaAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, orderPizzas);
        orderList.setAdapter(pizzaAdapter);

    }

    /**
     * Sets orderSpinner to listen to changes when a user selects a different order phone number.
     */
    public void setOrderSelectionListener() {
        orderSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        orderPizzas.clear();
                        String orderString = (String) orderSpinner.getSelectedItem();
                        Order currentOrder = storeOrders.findOrder(orderString);
                        ArrayList<Pizza> currentOrderPizzas = currentOrder.getPizzaList();
                        orderPizzas.addAll(currentOrderPizzas);
                        pizzaAdapter.notifyDataSetChanged();
                        String formattedTotal = formatPrice(currentOrder.getPrice());
                        orderTotalOutputLabel.setText(formattedTotal);
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });
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

    /**
     * Deletes the currently displayed order from storeOrders when the relevant button is clicked.
     * @param view instance of View to be used during method call
     */
    public void deleteOrder(View view) {
        if(orderAdapter.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.no_more_orders_alert, Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneNumber = (String) orderSpinner.getSelectedItem();
        Order orderToRemove = storeOrders.findOrder(phoneNumber);
        phoneNumList.remove(phoneNumber);
        orderAdapter.notifyDataSetChanged();
        orderPizzas.clear();
        pizzaAdapter.notifyDataSetChanged();
        String empty = "";
        orderTotalOutputLabel.setText(empty);
        MainActivity.removeOrder(orderToRemove);
        Toast.makeText(getApplicationContext(), R.string.order_removed_alert, Toast.LENGTH_SHORT).show();
    }
}