package com.example.pizzaandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * A class to control the Customize Pizza activity that contains various
 * functions to handle user interaction.
 *
 * @author Arnav Lakkavajjala, Kevin Perez
 *
 */
public class CustomizeActivity extends AppCompatActivity {

    private Pizza newPizza;
    private TextView pizzaNameView, priceOutputLabel;
    private ListView toppingsList;
    private Spinner sizeSpinner;
    private ImageView pizzaImageView;

    Topping[] toppingEnumList = Topping.values();
    private int baseNumToppings;
    private int numToppings;
    private static final int MAX_TOPPINGS = 7;

    /**
     * onCreate method to initialize Customize Pizza activity with the necessary layout.
     * Also performs other setup such as connecting views with associated adapters.
     * @param savedInstanceState default bundle for dynamic data passing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        Intent intent = getIntent();
        String pizzaType = intent.getStringExtra("TYPE");

        newPizza = PizzaMaker.createPizza(pizzaType);

        pizzaImageView = findViewById(R.id.pizzaImageView);
        setPizzaImage(pizzaType);

        pizzaNameView = findViewById(R.id.pizzaNameView);
        String pizzaNameText = pizzaType + " Pizza";
        pizzaNameView.setText(pizzaNameText);

        sizeSpinner = findViewById(R.id.sizeSpinner);
        setSizeSelectionListener();

        toppingsList = findViewById(R.id.toppingsList);
        toppingsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] toppingStringList = toppingStringList();
        ArrayAdapter toppingAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, toppingStringList);
        toppingsList.setAdapter(toppingAdapter);
        setPreselected(pizzaType);
        priceOutputLabel = findViewById(R.id.priceOutputLabel);
        setToppingClickListener();
    }

    /**
     * A method to add the current personalized pizza to the current order.
     * @param view instance of View to be used during method call
     */
    public void addOrder(View view) {
        Order mainOrder = MainActivity.getCurrentOrder();
        mainOrder.addPizza(newPizza);
        finish();
        Toast.makeText(getApplicationContext(), R.string.pizza_added_alert, Toast.LENGTH_SHORT).show();
        return;
    }

    /**
     * Sets toppingsList to listen to the changes when a user selects/deselects toppings.
     */
    public void setToppingClickListener(){
        toppingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                int checked = getChecked();
                if(checkToppings(checked, position)){
                    numToppings = checked;
                    String selectedTopping = (String) toppingsList.getItemAtPosition(position);
                    Topping t = checkToppingName(selectedTopping);
                    if(toppingsList.isItemChecked(position)){
                        newPizza.addTopping(t);
                    }
                    else newPizza.removeTopping(t);
                    recalculatePriceField();
                }
            }
        });
    }

    /**
     * Sets sizeSpinner to listen to the user's selection of the size.
     */
    public void setSizeSelectionListener(){
        sizeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String sizeString = (String) sizeSpinner.getSelectedItem();
                        Size size = checkSize(sizeString);
                        if(size == null) return;
                        newPizza.setSize(size);
                        recalculatePriceField();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });

    }

    /**
     * Designates what pizza image to display given the type of pizza.
     * @param pizzaType name of a given pizza type
     */
    private void setPizzaImage(String pizzaType){
        if(pizzaType.equals("Pepperoni")){
            pizzaImageView.setImageResource(R.mipmap.pepperoni_pizza_round);
        }
        else if(pizzaType.equals("Deluxe")){
            pizzaImageView.setImageResource(R.mipmap.deluxe_pizza_round);
        }
        else if(pizzaType.equals("Hawaiian")){
            pizzaImageView.setImageResource(R.mipmap.hawaiian_pizza_round);
        }
    }

    /**
     * Method to recalculate the price field, format the price, and display it.
     */
    private void recalculatePriceField() {
        String moneyFormat = "###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(moneyFormat);
        String formattedPrice = decimalFormat.format(newPizza.price());
        priceOutputLabel.setText(formattedPrice);
    }

    /**
     * Prohibits a pizza from having more than the max amount of toppings.
     * Also signals if a pizza is going under the base number of toppings.
     * In both cases, an alert is displayed.
     * @param checked the number of toppings
     * @param position index of topping in toppings list to reset checkbox after invalid input
     * @return
     */
    private boolean checkToppings(int checked, int position){
        if(checked > MAX_TOPPINGS){
            Toast.makeText(getApplicationContext(), R.string.too_many_toppings_alert, Toast.LENGTH_SHORT).show();
            toppingsList.setItemChecked(position, false);
            return false;
        }
        else if(checked < baseNumToppings){
            Toast.makeText(getApplicationContext(), R.string.default_toppings_alert, Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }

    /**
     * Gets the Topping object for a given topping name.
     * @param toppingName string of topping name
     * @return the Topping object
     */
    private Topping checkToppingName(String toppingName){
        Topping topping = null;
        for(Topping t : Topping.values()){
            if(toppingName.equalsIgnoreCase(t.name())) {
                topping = t;
            }
        }
        return topping;
    }

    /**
     * Gets the Size object for a given size name.
     * @param sizeString string of the size name
     * @return the Size object
     */
    private Size checkSize(String sizeString){
        Size size = null;
        for(Size s : Size.values()){
            if(sizeString.equalsIgnoreCase(s.name())) {
                size = s;
            }
        }
        return size;
    }

    /**
     * Finds the number of toppings currently checked in toppingsList.
     * @return the number of selected toppings
     */
    private int getChecked(){
        int numChecked = 0;
        SparseBooleanArray checked = toppingsList.getCheckedItemPositions();
        for (int i = 0; i < toppingsList.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                numChecked++;
            }
        }
        return numChecked;
    }

    /**
     * Preselects the toppings for a given pizza type.
     * @param pizzaType name of the pizza type
     */
    private void setPreselected(String pizzaType) {
        if(pizzaType.equals("Pepperoni")){
            toppingsList.setItemChecked(0, true);
            baseNumToppings = 1;
        }
        else if(pizzaType.equals("Deluxe")){
            toppingsList.setItemChecked(0, true);
            toppingsList.setItemChecked(3, true);
            toppingsList.setItemChecked(4, true);
            toppingsList.setItemChecked(5, true);
            toppingsList.setItemChecked(6, true);
            baseNumToppings = 5;
        }
        else if(pizzaType.equals("Hawaiian")){
            toppingsList.setItemChecked(1, true);
            toppingsList.setItemChecked(2, true);
            baseNumToppings = 2;
        }
    }

    /**
     * Gets the names of all toppings currently selected in toppingsList.
     * @return a string array of the selected toppings
     */
    private String[] toppingStringList() {
        String[] result = new String[toppingEnumList.length];
        for(int i = 0; i < toppingEnumList.length; i++){
            result[i] = toppingEnumList[i].name();
        }
        return result;
    }
}