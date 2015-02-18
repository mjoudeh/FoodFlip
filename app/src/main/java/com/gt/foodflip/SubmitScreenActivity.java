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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by beer on 2/10/2015.
 */
public class SubmitScreenActivity extends Activity {
    ImageButton back_button_submit_form;
    ImageButton submit_button_submit_form;
    ImageButton account_button_submit_form;
    EditText text_description;
    EditText text_type;
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
        back_button_submit_form = (ImageButton) findViewById(R.id.back_button_submit_form);
        submit_button_submit_form = (ImageButton) findViewById(R.id.submit_button_submit_form);
        account_button_submit_form = (ImageButton) findViewById(R.id.account_button_submit_form);
        text_description = (EditText) findViewById(R.id.text_description);
        text_type = (EditText) findViewById(R.id.text_type);
        back_button_submit_form.setOnClickListener(mainScreen);
        submit_button_submit_form.setOnClickListener(submitFood);

        buildingsList.setAdapter(adapter);
    }

    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };

    View.OnClickListener submitFood = new View.OnClickListener() {
        public void onClick(View v) {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();
        params.put("FoodType", text_type.getText().toString());
        params.put("FoodDescription", text_description.getText().toString());
        // Make Http call to insertentry.php
        client.post("http://128.61.126.152/foodflip/insertentry.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
            }
            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        }
    };
}
