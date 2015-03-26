package com.gt.foodflip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * MainActivity displays the home screen, logo, search, and submit buttons.
 */
public class MainActivity extends ActionBarActivity {
    ImageButton main_screen_search;
    ImageButton main_screen_submit;
    static User currentUser = new User();
    ProgressDialog progress;


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
        final String deviceId = deviceUuid.toString();

        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        new MyTask(progress, deviceId).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void checkCurrentUser() {
        System.out.println("in check current user: " + currentUser.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public boolean getUser(String deviceId) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://128.61.117.147/foodflip/getuser.php");
        try {
            List nameValuePairs = new ArrayList();
            nameValuePairs.add(new BasicNameValuePair("id", deviceId));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject user = new JSONObject(result);
            currentUser.setId(user.getString("id"));
            currentUser.setKarma(user.getString("karma"));
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException in getUser: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException in getUser: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("JSONException in getUser: " + e.getMessage());
        }

        if (currentUser.getId() == null || currentUser.getId() == "" ||
                currentUser.getKarma() == null || currentUser.getKarma() == "")
            return false;

        return true;
    }

    public void insertUser(String deviceId) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://128.61.117.147/foodflip/insertuser.php");
        try {
            List nameValuePairs = new ArrayList();
            nameValuePairs.add(new BasicNameValuePair("id", deviceId));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            currentUser.setId(deviceId);
            currentUser.setKarma("0");
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException in getUser: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException in getUser: " + e.getMessage());
        }
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress;
        private String deviceId;

        public MyTask(ProgressDialog progress, String deviceId) {
            this.progress = progress;
            this.deviceId = deviceId;
        }

        public void onPreExecute() {
            progress.show();
        }

        public Void doInBackground(Void... unused) {
            if (!getUser(this.deviceId))
                insertUser(this.deviceId);
            return null;
        }

        public void onPostExecute(Void unused) {
            if (progress.isShowing())
                progress.dismiss();
            checkCurrentUser();
        }
    }
}
