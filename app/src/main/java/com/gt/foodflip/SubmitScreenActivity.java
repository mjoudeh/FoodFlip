package com.gt.foodflip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Submit Screen Activity creates a layout using the activity_search xml and allows the user
 * to submit food into the foodflip database.
 */
public class SubmitScreenActivity extends Activity {
    ImageButton back_button_submit_form;
    ImageButton submit_button_submit_form;
    ImageButton account_button_submit_form;
    ToggleButton food_truck_toggle_button;
    ToggleButton delivery_toggle_button;
    ToggleButton other_toggle_button;
    ToggleButton pizza_toggle_button;
    ToggleButton wings_toggle_button;
    ToggleButton baked_goods_toggle_button;
    ToggleButton sandwiches_toggle_button;
    ToggleButton drinks_toggle_button;
    ToggleButton other_type_toggle_button;
    EditText text_description;
    EditText text_location;
    private AutoCompleteTextView buildingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        String[] buildings = getResources().getStringArray(R.array.list_of_buildings);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                buildings);

        buildingsList = (AutoCompleteTextView) findViewById(R.id.autocompletetext_building);
        text_location = (EditText) findViewById(R.id.text_location);
        back_button_submit_form = (ImageButton) findViewById(R.id.back_button_submit_form);
        submit_button_submit_form = (ImageButton) findViewById(R.id.submit_button_submit_form);
        account_button_submit_form = (ImageButton) findViewById(R.id.account_button_submit_form);
        food_truck_toggle_button = (ToggleButton) findViewById(R.id.food_truck_toggle_button);
        delivery_toggle_button = (ToggleButton) findViewById(R.id.delivery_toggle_button);
        other_toggle_button = (ToggleButton) findViewById(R.id.other_toggle_button);
        pizza_toggle_button = (ToggleButton) findViewById(R.id.pizza_toggle_button);
        wings_toggle_button = (ToggleButton) findViewById(R.id.wings_toggle_button);
        baked_goods_toggle_button = (ToggleButton) findViewById(R.id.baked_goods_toggle_button);
        sandwiches_toggle_button = (ToggleButton) findViewById(R.id.sandwiches_toggle_button);
        drinks_toggle_button = (ToggleButton) findViewById(R.id.drinks_toggle_button);
        other_type_toggle_button = (ToggleButton) findViewById(R.id.other_type_toggle_button);
        text_description = (EditText) findViewById(R.id.text_description);

        back_button_submit_form.setOnClickListener(mainScreen);
        submit_button_submit_form.setOnClickListener(submitFood);
        food_truck_toggle_button.setOnClickListener(toggleCategory);
        delivery_toggle_button.setOnClickListener(toggleCategory);
        other_toggle_button.setOnClickListener(toggleCategory);

        buildingsList.setAdapter(adapter);
    }

    /**
     * the mainScreen onClickListener takes us back to the main screen when the back button is
     * clicked.
     */
    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };

    /**
     * the toggleCategory onClickListener is called whenever a category is clicked by the user.
     * It ensures that only one category is selected at a time.
     */
    View.OnClickListener toggleCategory = new View.OnClickListener() {
        public void onClick(View v) {
            ToggleButton t = (ToggleButton) v;

            if (!t.isChecked())
                return;

            switch(t.getText().toString()) {
                case "Food Truck":
                    delivery_toggle_button.setChecked(false);
                    other_toggle_button.setChecked(false);
                    break;
                case "Delivery":
                    food_truck_toggle_button.setChecked(false);
                    other_toggle_button.setChecked(false);
                    break;
                case "Other":
                    food_truck_toggle_button.setChecked(false);
                    delivery_toggle_button.setChecked(false);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * the submitFood onClickListener is called when the submit button is clicked. It is
     * responsible for actually inputting the food entry into the database by making an
     * http request to insertentry.php.
     */
    View.OnClickListener submitFood = new View.OnClickListener() {
        public void onClick(View v) {
            if (!validateInput())
                return;

            String category = getCategory();
            String types = getTypes();

            // Create AsycHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();

            // Http Request Params Object
            RequestParams params = new RequestParams();

            params.put("Building", buildingsList.getText().toString());
            params.put("Location", text_location.getText().toString());
            params.put("FoodCategory", category);
            params.put("FoodType", types);
            params.put("FoodDescription", text_description.getText().toString());
            // Make Http call to insertentry.php
            client.post("http://192.168.1.6/foodflip/insertentry.php", params,
                    new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    System.out.println(response);
                }
                // When error occured
                @Override
                public void onFailure(int statusCode, Throwable error, String content) {
                    if (statusCode == 404)
                        Toast.makeText(getApplicationContext(), "Requested resource not found",
                                Toast.LENGTH_LONG).show();
                    else if (statusCode == 500)
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured!" +
                                        " [Most common Error: Device might not be connected" +
                                        " to Internet]",
                                Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    /**
     * returns the current category that is selected.
     *
     * @return category - "Food Truck", "Delivery", "Other" - depending which is selected.
     */
    public String getCategory() {
        if (food_truck_toggle_button.isChecked())
            return "Food Truck";
        else if (delivery_toggle_button.isChecked())
            return "Delivery";
        else
            return "Other";
    }

    /**
     * Gets all types of food that are checked. Uses a StringBuilder to concatenate all food types
     * that are checked, then returns the string.
     *
     * @return stringBuilder.toString() the string of food types checked.
     */
    public String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();

        if (pizza_toggle_button.isChecked()) stringBuilder.append("Pizza ");
        if (wings_toggle_button.isChecked()) stringBuilder.append("Wings ");
        if (baked_goods_toggle_button.isChecked()) stringBuilder.append("Baked Goods ");
        if (sandwiches_toggle_button.isChecked()) stringBuilder.append("Sandwiches ");
        if (drinks_toggle_button.isChecked()) stringBuilder.append("Drinks ");
        if (other_type_toggle_button.isChecked()) stringBuilder.append("Other");

        return stringBuilder.toString();
    }

    /*
     * This method makes sure that all inputs are not empty.
     * TODO: display a message on false
     *
     * @return true if a building is selected, a location is specifies, one food category is chosen,
     * and at least one food type is chosen. False otherwise.
     */
    public boolean validateInput() {
        if (buildingsList.getText().toString() == "Building" ||
                buildingsList.getText().toString() == null ||
                buildingsList.getText().toString() == "")
            return false;

        if (text_location.getText().toString() == "Location (room #, area, etc.)" ||
                text_location.getText().toString() == null ||
                text_location.getText().toString() == "")
            return false;

        if (!food_truck_toggle_button.isChecked() &&
                !delivery_toggle_button.isChecked() &&
                !other_toggle_button.isChecked())
            return false;

        if (!pizza_toggle_button.isChecked() &&
                !wings_toggle_button.isChecked() &&
                !baked_goods_toggle_button.isChecked() &&
                !sandwiches_toggle_button.isChecked() &&
                !drinks_toggle_button.isChecked() &&
                !other_toggle_button.isChecked())
            return false;

        return true;
    }
}
