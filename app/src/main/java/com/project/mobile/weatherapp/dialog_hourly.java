package com.project.mobile.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobile.weatherapp.model.Hourly;

import org.w3c.dom.Text;

public class dialog_hourly extends DialogFragment {
    public TextView mTextTemp;
    public TextView mTextPressure;
    public TextView texttime;
    public Context context;
    public ImageView mImage;

    public dialog_hourly() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_hourly_detail, container);
        Bundle args = getArguments();
        context = getActivity().getApplicationContext();
        texttime = (TextView) v.findViewById(R.id.textTimeHour);
        mTextTemp = (TextView) v.findViewById(R.id.textTempHour);
        mImage = (ImageView) v.findViewById(R.id.iconWeatherHour);
        String textTime = args.getString("textTime");
        String textTemp = args.getString("textTemp");
        Integer icon = args.getInt("icon");
        if(texttime == null) {
            Log.i("ddmmmmm", "caaaax");
        }
        texttime.setText(textTime);
        mTextTemp.setText(textTemp);
        mImage.setImageResource(icon);
        return v;
    }
}
