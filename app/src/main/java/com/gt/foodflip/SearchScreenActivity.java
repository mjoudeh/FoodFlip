package com.gt.foodflip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by beer on 2/17/2015.
 */
public class SearchScreenActivity extends Activity {
    ImageButton back_button_search_form;
    ImageButton account_button_search_form;
    ListView listView;
    ArrayList<String> httpResponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Context context = this;
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.entries_list_view);
        back_button_search_form = (ImageButton) findViewById(R.id.back_button_search_form);
        account_button_search_form = (ImageButton) findViewById(R.id.account_button_search_form);

        back_button_search_form.setOnClickListener(mainScreen);

        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();

        // Make Http call to insertentry.php
        client.post("http://128.61.119.32/foodflip/getentries.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                // Defined Array values to show in ListView
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        httpResponse.add(obj.getString("foodType"));
                    }
                } catch (JSONException e) {
                    System.out.println("Error parsing food entry data: " + e.getMessage());
                    return;
                }

                // Define a new Adapter
                // First parameter - Context
                // Second parameter - Layout for the row
                // Third parameter - ID of the TextView to which the data is written
                // Forth - the Array of data
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, android.R.id.text1, httpResponse);


                // Assign adapter to ListView
                listView.setAdapter(adapter);

                // ListView Item Click Listener
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition = position;

                        // ListView Clicked item value
                        String itemValue = (String) listView.getItemAtPosition(position);

                        // Show Alert
                        Toast.makeText(getApplicationContext(),
                                "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                                .show();

                    }

                });

            }

            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                System.out.println("failure.");
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

    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };

}
