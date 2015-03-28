package com.gt.foodflip;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * CustomAdapter class for the purpose of giving all food entries in the search screen
 * custom layouts.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    FoodEntry tempValues = null;
    int i = 0;
    ViewHolder holder;

    /**
     * CustomAdapter Constructor.
     *
     * @param a the current Activity.
     * @param d the arrayList being passed in (contains all food entries).
     */
    public CustomAdapter(Activity a, ArrayList d, Resources resLocal, Context c) {
        activity = a;
        data = d;
        res = resLocal;
        context = c;

        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /* Calculates the size of the passed in arrayList */
    public int getCount() {
        return data.size() <= 0 ? 1 : data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /* Create a holder Class to contain inflated tabitem xml file elements */
    public static class ViewHolder {
        public TextView building;
        public TextView location;
        public TextView foodCategory;
        public TextView foodType;
        public TextView votes;
        public TextView foodDescription;
        public ImageButton downvote;
        public ImageButton upvote;
    }

    /* Depends upon data size called for each row , Create each ListView row */
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        //ViewHolder holder;

        if (convertView == null) {
            /* Inflate tabitem.xml file for each row ( Defined below ) */
            view = inflater.inflate(R.layout.tabitem, null);

            /* View Holder Object to contain tabitem.xml file elements */
            holder = new ViewHolder();
            holder.building = (TextView) view.findViewById(R.id.building);
            holder.location = (TextView) view.findViewById(R.id.location);
            holder.foodCategory = (TextView) view.findViewById(R.id.food_category);
            holder.foodType = (TextView) view.findViewById(R.id.food_type);
            holder.votes = (TextView) view.findViewById(R.id.votes);
            //holder.foodDescription = (TextView) vi.findViewById(R.id.food_description);

            /*  Set holder with LayoutInflater */
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        if (data.size() <= 0)
            holder.building.setText("No Data");
        else {
            /* Get each entry object from Arraylist */
            tempValues = null;
            tempValues = (FoodEntry) data.get(position);

            /*  Set entry values in holder elements */
            holder.building.setText(tempValues.getBuilding());
            holder.location.setText(tempValues.getLocation());
            holder.foodCategory.setText(tempValues.getCategory());
            holder.foodType.setText(tempValues.getType());
            holder.votes.setText(Integer.toString(tempValues.getVotes()));
            holder.downvote = (ImageButton) view.findViewById(R.id.downvote);
            holder.upvote = (ImageButton) view.findViewById(R.id.upvote);
            //holder.foodDescription.setText(tempValues.getDescription());

            /* Set Item Click Listener for LayoutInflater for each row */
            view.setOnClickListener(new OnItemClickListener(position));
            /* view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
                @Override
                public void onSwipeRight() {
                    Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSwipeLeft() {
                    Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
                }
            });*/

            /* Set onClickListeners for downvote and upvote buttons */
            holder.downvote.setOnClickListener(new OnDownvoteClickListener(holder.votes));
            holder.upvote.setOnClickListener(new OnUpvoteClickListener(holder.votes));
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /* This handles upvotes and setting the vote counter */
    private class OnUpvoteClickListener implements View.OnClickListener {
        private TextView votes;

        OnUpvoteClickListener(TextView votes) {
            this.votes = votes;
        }

        @Override
        public void onClick(View arg0) {
            int currentVotes = Integer.parseInt(this.votes.getText().toString());
            this.votes.setText(Integer.toString(++currentVotes));
        }
    }

    /* This handles downvotes and setting the vote counter */
    private class OnDownvoteClickListener implements View.OnClickListener {
        private TextView votes;

        OnDownvoteClickListener(TextView votes) {
            this.votes = votes;
        }

        @Override
        public void onClick(View arg0) {
            int currentVotes = Integer.parseInt(this.votes.getText().toString());
            this.votes.setText(Integer.toString(--currentVotes));
        }
    }

    /**
     * Called when entry clicked in ListView. Sets each entries onClick to be onItemClick in
     * SearchScreenActivity. onItemClick, in turn, sets the view to be entry_view, which displays
     * all the data for the entry clicked by the user.
     */
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            SearchScreenActivity searchScreenActivity = (SearchScreenActivity) activity;
            searchScreenActivity.onItemClick(mPosition);
        }
    }
}