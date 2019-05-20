package com.project.mobile.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.Hourly;
import com.project.mobile.weatherapp.model.open_weather_map.Weather;

import java.util.List;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder>{
    private List<Hourly> mHourly;
    private Context mContext;

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
        holder.textprecipitation.setText(itemweather.getmTextPrecipitation());
        holder.texthumidity.setText(itemweather.getmTextHumidity());


    }

    @Override
    public int getItemCount() {
        return mHourly.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private View itemView;
        public TextView texttime;
        public TextView texttemp;
        public TextView textprecipitation;
        public TextView texthumidity;

        public ViewHolder (View itemView) {
            super(itemView);
            itemView = itemView;
            texttime = itemView.findViewById(R.id.text_time);
            texttemp = itemView.findViewById(R.id.text_temp);
            textprecipitation = itemView.findViewById(R.id.text_precipitation);
            texthumidity = itemView.findViewById(R.id.text_humidity);
        }

    }
}
