package com.project.mobile.weatherapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.DialogHourly;
import com.project.mobile.weatherapp.model.Hourly;
import com.project.mobile.weatherapp.utils.WeatherIcon;

import java.util.List;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<Hourly> mHourly;
    private Context mContext;
    //public Activity activity;

    public HourlyAdapter (List<Hourly> hourly, Context mContext) {
        this.mHourly = hourly;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout View Item
        View hourlyView = inflater.inflate(R.layout.item_hourly,parent, false);
        ViewHolder viewHolder = new ViewHolder(hourlyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hourly itemweather = mHourly.get(position);
        holder.texttime.setText(itemweather.getmTextTime());
        holder.texttemp.setText(itemweather.getmTextTemp());
        holder.textwind.setText(itemweather.getmTextWind());
        holder.texthumidity.setText(itemweather.getmTextHumidity());
        holder.weatherIcon.setImageResource(WeatherIcon.getIconId(itemweather.getWeatherIcon()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hourly hourly = mHourly.get(position);
                Bundle args = new Bundle();
                args.putString("textTime", hourly.getmTextTime());
                args.putString("textTemp", hourly.getmTextTemp());
                args.putInt("humidity", hourly.list.getMain().getHumidity());
                args.putString("status", hourly.list.getWeather().get(0).getDescription());
                args.putString("wind", hourly.getmTextWind());
                args.putInt("icon", WeatherIcon.getIconId(itemweather.getWeatherIcon()));
                args.putFloat("pressure", (float) hourly.list.getMain().getPressure());

                FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();

                DialogHourly dialog = new DialogHourly();
                dialog.setArguments(args);
                dialog.show(fm, "dialog detail");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHourly.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private View itemView;
        public TextView texttime;
        public TextView texttemp;
        public TextView textwind;
        public TextView texthumidity;
        public ImageView weatherIcon;
        public TextView textweather;
        public TextView textpressure;

        public ViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            texttime = itemView.findViewById(R.id.text_time);
            texttemp = itemView.findViewById(R.id.text_temp);
            textwind = itemView.findViewById(R.id.text_wind);
            texthumidity = itemView.findViewById(R.id.text_humidity);
            weatherIcon = itemView.findViewById(R.id.icon_weatherHour);
            //textweather = itemView.findViewById(R.id.text_weatherHour);
            //textpressure = itemView.findViewById(R.id.text_pressure);
        }

    }
}
