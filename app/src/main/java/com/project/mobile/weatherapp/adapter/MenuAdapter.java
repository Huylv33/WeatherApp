package com.project.mobile.weatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.mobile.weatherapp.R;

import java.util.ArrayList;
import java.util.ResourceBundle;

import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

/**
 * Created by PSD on 13-04-17.
 */

public class MenuAdapter extends BaseAdapter {
    private ArrayList<String> mOptions = new ArrayList<>();
    public Activity activity;
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();

    public MenuAdapter(ArrayList<String> options, Activity activity) {
        mOptions = options;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    public void setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option

        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }


    /*public void setmOptionsIcon (DuoOptionView v, View view){
        for (int i=0; i < mOptionViews.size(); i++) {
            switch (i){
                case 0:
                    getView();

            }
        }
    }*/



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);
        Drawable drawable0 = activity.getResources().getDrawable(R.drawable.ic_location_on_black_24dp);
        Drawable drawable1 = activity.getResources().getDrawable(R.drawable.ic_notifications_black_24dp);
        Drawable drawable2 = activity.getResources().getDrawable(R.drawable.ic_event_note_black_24dp);
        Drawable drawable3 = activity.getResources().getDrawable(R.drawable.ic_swap_horiz_black_24dp);
        Drawable drawable4 = activity.getResources().getDrawable(R.drawable.ic_wallpaper_black_24dp);

        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        if (convertView == null) {
            optionView = new DuoOptionView(parent.getContext());
        } else {
            optionView = (DuoOptionView) convertView;
        }



        // Using the DuoOptionView's default selectors
        optionView.bind(option, null, null);
            switch (position){
                case 0:
                    optionView.bind(option,drawable0);
                    break;
                case 1:
                    optionView.bind(option,drawable1);
                    break;
                case 2:
                    optionView.bind(option,drawable2);
                    break;
                case 3:
                    optionView.bind(option,drawable3);
                    break;
                case 4:
                    optionView.bind(option,drawable4);
                    break;

        }

        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView);

        return optionView;
    }
}
