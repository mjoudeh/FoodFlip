package com.gt.foodflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * MainActivity displays the home screen, logo, search, and submit buttons.
 */
public class MainActivity extends ActionBarActivity {
    ImageButton main_screen_search;
    ImageButton main_screen_submit;
    static String userArray[] = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_screen_search = (ImageButton) findViewById(R.id.main_screen_search);
        main_screen_submit = (ImageButton) findViewById(R.id.main_screen_submit);
        main_screen_search.setOnClickListener(searchScreen);
        main_screen_submit.setOnClickListener(submitScreen);

        /*
         * The following lines of code are used to get a unique Id for each device. This will
         * be useful for creating and managing user accounts
         */
        final TelephonyManager tm = (TelephonyManager) getBaseContext().
                getSystemService(this.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) |
                tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        getUser(deviceId);

        if (userArray[0] == null)
            insertUser(deviceId);
        else
            System.out.println(userArray[0]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * This method takes us to the search screen when the search button is clicked.
     */
    View.OnClickListener searchScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent searchScreen = new Intent(getApplicationContext(), SearchScreenActivity.class);
            startActivity(searchScreen);
        }
    };

    /*
     * This method takes us to the submit screen when the submit button is clicked.
     */
    View.OnClickListener submitScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent submitScreen = new Intent(getApplicationContext(), SubmitScreenActivity.class);
            startActivity(submitScreen);
        }
    };

    public String[] getUser(String deviceId) {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();

        params.put("id", deviceId);
        // Make Http call to insertentry.php
        client.post("http://192.168.1.6/foodflip/getuser.php", params,
            new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    userArray[0] = obj.getString("id");
                    userArray[1] = obj.getString("karma");
                } catch (JSONException e) {
                    System.out.println("Error parsing getUser data: " + e.getMessage());
                    return;
                }
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

        return userArray;
    }

    public void insertUser(String deviceId) {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();

        params.put("id", deviceId);
        // Make Http call to insertentry.php
        client.post("http://192.168.1.6/foodflip/insertuser.php", params,
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
}
