package com.gt.foodflip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * EntryScreenActivity populates the entry_view xml with data passed in from SearchScreenActivity.
 */
public class EntryScreenActivity extends Activity {
    private int entryId;

    Button entry_view_submit_button;
    Button entry_view_cancel_button;
    EditText comment_edit_text;
    ImageButton back_button_entry_view;
    ImageButton account_button_entry_view;
    ListView comments;
    ProgressDialog pDialog;
    ArrayAdapter arrayAdapter;
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

        entry_view_submit_button = (Button) findViewById(R.id.entry_view_submit_button);
        entry_view_cancel_button = (Button) findViewById(R.id.entry_view_cancel_button);
        comment_edit_text = (EditText) findViewById(R.id.comment_edit_text);
        back_button_entry_view = (ImageButton) findViewById(R.id.back_button_entry_view);
        account_button_entry_view = (ImageButton) findViewById(R.id.account_button_entry_view);
        comments = (ListView) findViewById(R.id.comments);
        building = (TextView) findViewById(R.id.building);
        location = (TextView) findViewById(R.id.location);
        category = (TextView) findViewById(R.id.category);
        type = (TextView) findViewById(R.id.type);
        description = (TextView) findViewById(R.id.description);
        votes = (TextView) findViewById(R.id.votes);

        back_button_entry_view.setOnClickListener(searchScreen);
        entry_view_submit_button.setOnClickListener(submitComment);

        setEntryViewValues();
        new PopulateEntryComments().execute();
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
            entryId = entry.getInt("id");
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
    View.OnClickListener submitComment = new View.OnClickListener() {
        public void onClick(View v) {
            new AddAComment().execute();
        }
    };

    View.OnClickListener searchScreen = new View.OnClickListener() {
        public void onClick(View v) {
            Intent searchScreen = new Intent(getApplicationContext(), SearchScreenActivity.class);
            startActivity(searchScreen);
        }
    };

    /**
     * This gets all comments given a food entries id, stores them in an ArrayList, then returns
     * the ArrayList.
     *
     * @return comments - the ArrayList of comments.
     */
    public ArrayList<String> getEntryComments() {
        ArrayList<String> comments = new ArrayList<>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.6/foodflip/getentrycomments.php");

        try {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id", Integer.toString(entryId)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(builder.toString());
            JSONArray commentsArray = new JSONArray(tokener);
            for (int i = 0; i < commentsArray.length(); i++)
                comments.add(commentsArray.getJSONObject(i).getString("comment"));
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException in getEntryComments: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException in getEntryComments: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("JSONException in getEntryComments: " + e.getMessage());
        }

        return comments;
    }

    public void addAComment() {
        if (comment_edit_text.getText().toString() == null ||
                comment_edit_text.getText().toString().equals("") ||
                comment_edit_text.getText().toString().equals("Add a comment."))
            return;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.6/foodflip/addentrycomment.php");

        try {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id", Integer.toString(entryId)));
            nameValuePairs.add(new BasicNameValuePair("comment",
                    comment_edit_text.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException in getUser: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException in getUser: " + e.getMessage());
        }
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(EntryScreenActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }


    /**
     * Shows a loading progress dialog while populating the food entries.
     */
    public class PopulateEntryComments extends AsyncTask<Void, Void, Void> {
        public PopulateEntryComments() {
        }

        public void onPreExecute() {
            showProgressDialog();
        }

        public Void doInBackground(Void... unused) {
            final ArrayList<String> commentsList = getEntryComments();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayAdapter = new ArrayAdapter(getApplicationContext(),
                            R.layout.basic_text_view, commentsList);
                    comments.setAdapter(arrayAdapter);
                }
            });
            return null;
        }

        public void onPostExecute(Void unused) {
            if (EntryScreenActivity.this.isDestroyed())
                return;

            dismissProgressDialog();
        }
    }

    public class AddAComment extends AsyncTask<Void, Void, Void> {
        public AddAComment() {
        }

        public void onPreExecute() {
            showProgressDialog();
        }

        public Void doInBackground(Void... unused) {
            addAComment();
            return null;
        }

        public void onPostExecute(Void unused) {
            if (EntryScreenActivity.this.isDestroyed())
                return;

            dismissProgressDialog();
        }
    }
}
