package com.gt.foodflip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

/**
 * Created by beer on 2/10/2015.
 */
public class SubmitScreenActivity extends Activity {
    ImageButton back_button_submit_form;
    ImageButton submit_button_submit_form;
    ImageButton account_button_submit_form;
    EditText text_description;
    EditText text_type;
    HashMap<String, String> entriesList;
    //FFDBController ffdbController = new FFDBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        back_button_submit_form = (ImageButton) findViewById(R.id.back_button_submit_form);
        submit_button_submit_form = (ImageButton) findViewById(R.id.submit_button_submit_form);
        account_button_submit_form = (ImageButton) findViewById(R.id.account_button_submit_form);
        text_description = (EditText) findViewById(R.id.text_description);
        text_type = (EditText) findViewById(R.id.text_type);
        back_button_submit_form.setOnClickListener(mainScreen);
        submit_button_submit_form.setOnClickListener(submitFood);
    }

    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };

    View.OnClickListener submitFood = new View.OnClickListener() {
        public void onClick(View v) {
            entriesList = new HashMap<String, String>();
            entriesList.put("FoodType", text_type.getText().toString());
            entriesList.put("FoodDescription", text_description.getText().toString());
            String foodT = text_type.getText().toString();
            String foodD = text_description.getText().toString();
            //ffdbController.insertEntry(entriesList);
            // Create AsycHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            // Http Request Params Object
            RequestParams params = new RequestParams();
            params.put("FoodType", foodT);
            params.put("FoodDescription", foodD);
            // Show ProgressBar
            //prgDialog.show();
            // Make Http call to getusers.php
            client.post("http://128.61.126.152/foodflip/insertentry.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    // Hide ProgressBar
                    //prgDialog.hide();
                    // Update SQLite DB with response sent by getusers.php
                    //updateSQLite(response);
                }
                // When error occured
                @Override
                public void onFailure(int statusCode, Throwable error, String content) {
                    // TODO Auto-generated method stub
                    // Hide ProgressBar
                    //prgDialog.hide();
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
