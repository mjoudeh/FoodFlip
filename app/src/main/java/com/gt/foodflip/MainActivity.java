package com.gt.foodflip;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            setContentView(R.layout.activity_search);
        }
    };

    View.OnClickListener submitScreen = new View.OnClickListener() {
        public void onClick(View v) {
            setContentView(R.layout.activity_submit);
        }
    };
}
