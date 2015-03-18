package com.gt.foodflip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.UUID;


public class MainActivity extends ActionBarActivity {
    ImageButton main_screen_search;
    ImageButton main_screen_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_screen_search = (ImageButton) findViewById(R.id.main_screen_search);
        main_screen_submit = (ImageButton) findViewById(R.id.main_screen_submit);
        main_screen_search.setOnClickListener(searchScreen);
        main_screen_submit.setOnClickListener(submitScreen);

        /* The following lines of code are used to get a unique Id for each device */
        final TelephonyManager tm = (TelephonyManager) getBaseContext().
                getSystemService(this.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        /* System.out.println("tmDevice: " + tmDevice);
        System.out.println("tmSerial: " + tmSerial);
        System.out.println("androidId: " + androidId); */

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) |
                tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        /* System.out.println("deviceId: " + deviceId); */
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

    View.OnClickListener searchScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent searchScreen = new Intent(getApplicationContext(), SearchScreenActivity.class);
            startActivity(searchScreen);
        }
    };

    View.OnClickListener submitScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent submitScreen = new Intent(getApplicationContext(), SubmitScreenActivity.class);
            startActivity(submitScreen);
        }
    };
}
