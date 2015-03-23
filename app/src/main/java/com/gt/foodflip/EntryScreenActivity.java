package com.gt.foodflip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * EntryScreenActivity populates the entry_view xml with data passed in from SearchScreenActivity.
 */
public class EntryScreenActivity extends Activity {
    ImageButton back_button_entry_view;
    ImageButton account_button_entry_view;
    TextView building;
    TextView location;
    TextView category;
    TextView type;
    TextView description;
    TextView votes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_view);

        back_button_entry_view = (ImageButton) findViewById(R.id.back_button_entry_view);
        account_button_entry_view = (ImageButton) findViewById(R.id.account_button_entry_view);
        building = (TextView) findViewById(R.id.building);
        location = (TextView) findViewById(R.id.location);
        category = (TextView) findViewById(R.id.category);
        type = (TextView) findViewById(R.id.type);
        description = (TextView) findViewById(R.id.description);
        votes = (TextView) findViewById(R.id.votes);

        back_button_entry_view.setOnClickListener(searchScreen);

        setEntryViewValues();
    }

    /**
     * Sets each respective textView in the entry_view xml.
     */
    public void setEntryViewValues() {
        String building_;
        String location_;
        String category_;
        String type_;
        String description_;
        int votes_;

        Bundle entry = getIntent().getExtras();
        if (entry != null) {
            building_ = entry.getString("building");
            location_ = entry.getString("location");
            category_ = entry.getString("category");
            type_ = entry.getString("type");
            description_ = entry.getString("description");
            votes_ = entry.getInt("votes");
        } else
            return;

        building.setText(building_);
        location.setText(location_);
        category.setText(category_);
        type.setText(type_);
        description.setText(description_);
        votes.setText(Integer.toString(votes_));
    }

    /**
     * This method takes us back to the search screen when the back button is clicked.
     */
    View.OnClickListener searchScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent searchScreen = new Intent(getApplicationContext(), SearchScreenActivity.class);
            startActivity(searchScreen);
        }
    };

}
