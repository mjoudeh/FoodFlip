package com.gt.foodflip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    ProgressDialog progress;

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

        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        new PopulateFoodEntries(progress).execute();
    }

    /*
     * getFoodEntries is responsible for connecting to the database and making a call to
     * the php script getentries, which returns all food entries in the database.
     */
    public void getFoodEntries() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://128.61.117.147/foodflip/getentries.php");
        try {
            HttpResponse response = httpclient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(result);
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
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException in getFoodEntries: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException in getFoodEntries: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("JSONException in getFoodEntries: " + e.getMessage());
        }
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

    public class PopulateFoodEntries extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress;

        public PopulateFoodEntries(ProgressDialog progress) {
            this.progress = progress;
        }

        public void onPreExecute() {
            progress.show();
        }

        public Void doInBackground(Void... unused) {
            getFoodEntries();

            Resources resources = getResources();
            customAdapter = new CustomAdapter(customListView, httpResponse, resources);
            listView.setAdapter(customAdapter);
            return null;
        }

        public void onPostExecute(Void unused) {
            if (progress.isShowing())
                progress.dismiss();
        }
    }
}
