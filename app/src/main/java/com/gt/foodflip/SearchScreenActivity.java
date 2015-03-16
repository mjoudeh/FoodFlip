package com.gt.foodflip;

import android.app.Activity;
import android.content.Context;
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
 * Created by beer on 2/17/2015.
 */
public class SearchScreenActivity extends Activity {
    ImageButton back_button_search_form;
    ImageButton account_button_search_form;
    ListView listView;
    ArrayList<ListModel> httpResponse = new ArrayList<>();
    CustomAdapter customAdapter;
    public SearchScreenActivity customListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Context context = this;
        customListView = this;

        listView = (ListView) findViewById(R.id.entries_list_view);
        back_button_search_form = (ImageButton) findViewById(R.id.back_button_search_form);
        account_button_search_form = (ImageButton) findViewById(R.id.account_button_search_form);

        back_button_search_form.setOnClickListener(mainScreen);

        getFoodEntries(context);

        Resources resources = getResources();
        customAdapter = new CustomAdapter(customListView, httpResponse, resources);
        listView.setAdapter(customAdapter);
    }

    public void getFoodEntries(final Context context) {
        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();

        // Make Http call to insertentry.php
        client.post("http://192.168.1.6/foodflip/getentries.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final ListModel entry = new ListModel();
                        JSONObject obj = jsonArray.getJSONObject(i);
                        entry.setBuilding(obj.getString("building"));
                        entry.setLocation(obj.getString("location"));
                        entry.setCategory(obj.getString("foodCategory"));
                        entry.setType(obj.getString("foodType"));
                        entry.setDescription(obj.getString("foodDescription"));
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

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel tempValues = (ListModel) httpResponse.get(mPosition);


        // SHOW ALERT

        /*Toast.makeText(CustomListView, ""+tempValues.getCompanyName()
                        +"
                Image:"+tempValues.getImage()
            +"
        Url:"+tempValues.getUrl(),
        Toast.LENGTH_LONG)
        .show();*/
    }

}
