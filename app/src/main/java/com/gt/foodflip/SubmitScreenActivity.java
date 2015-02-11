package com.gt.foodflip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by beer on 2/10/2015.
 */
public class SubmitScreenActivity extends Activity {
    ImageButton back_button_submit_form;
    ImageButton submit_button_submit_form;
    ImageButton account_button_submit_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        back_button_submit_form = (ImageButton) findViewById(R.id.back_button_submit_form);
        submit_button_submit_form = (ImageButton) findViewById(R.id.submit_button_submit_form);
        account_button_submit_form = (ImageButton) findViewById(R.id.account_button_submit_form);
        back_button_submit_form.setOnClickListener(mainScreen);
    }

    View.OnClickListener mainScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
        }
    };
}
