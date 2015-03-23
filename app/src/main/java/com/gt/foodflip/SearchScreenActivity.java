package com.gt.foodflip;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
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
 * SearchScreenActivity populates the search screen using the tabitem xml to create a custom
 * layout for each entry and the activity_search xml for the overall layout.
 */
public class SearchScreenActivity extends Activity {
    ImageButton back_button_search_form;
    ImageButton account_button_search_form;
    ListView listView;
    ArrayList<FoodEntry> httpResponse;
    CustomAdapter customAdapter;
    public SearchScreenActivity customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        customListView = this;
        httpResponse = new ArrayList<>();

        listView = (ListView) findViewById(R.id.entries_list_view);
        back_button_search_form = (ImageButton) findViewById(R.id.back_button_search_form);
        account_button_search_form = (ImageButton) findViewById(R.id.account_button_search_form);

        back_button_search_form.setOnClickListener(mainScreen);
        getFoodEntries();

        Resources resources = getResources();
        customAdapter = new CustomAdapter(customListView, httpResponse, resources);
        listView.setAdapter(customAdapter);
    }

    /*
     * getFoodEntries is responsible for connecting to the database and making a call to
     * the php script getentries, which returns all food entries in the database.
     */
    public void getFoodEntries() {
        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();

        // Make Http call to insertentry.php
        client.post("http://192.168.1.6/foodflip/getentries.php", params,
                new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final FoodEntry entry = new FoodEntry();
                        JSONObject obj = jsonArray.getJSONObject(i);
                        entry.setBuilding(obj.getString("building"));
                        entry.setLocation(obj.getString("location"));
                        entry.setCategory(obj.getString("foodCategory"));
                        entry.setType(obj.getString("foodType"));
                        entry.setDescription(obj.getString("foodDescription"));
                        entry.setVotes(Integer.parseInt(obj.getString("votes")));
                        httpResponse.add(entry);
                    }
                } catch (JSONException e) {
                    System.out.println("Error parsing food entry data: " + e.getMessage());
                    return;
                }
            }

            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                System.out.println("failure.");
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found",
                            Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most " +
                            "common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    /*
     * This method takes us back to the main screen when the back button is clicked.
     */
    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };

    /*
    *  This method gets the specific food entry from the arrayList httpResponse,
    *  then takes us to that entries page.
    *
    *  @param mPosition position of the clicked food entry in the httpResponse arrayList.
    */
    public void onItemClick(int mPosition)
    {
        final FoodEntry entry = httpResponse.get(mPosition);

        Intent entryScreen = new Intent(getApplicationContext(), EntryScreenActivity.class);

        entryScreen.putExtra("building", entry.getBuilding());
        entryScreen.putExtra("location", entry.getLocation());
        entryScreen.putExtra("category", entry.getCategory());
        entryScreen.putExtra("type", entry.getType());
        entryScreen.putExtra("description", entry.getDescription());
        entryScreen.putExtra("votes", entry.getVotes());

        startActivity(entryScreen);
    }

}
