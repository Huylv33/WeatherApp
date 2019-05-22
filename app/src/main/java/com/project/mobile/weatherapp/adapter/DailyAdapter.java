package com.project.mobile.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.utils.WeatherIcon;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder>{
    private List<Daily> mDaily;
    private Context mContext;

    public DailyAdapter (List<Daily> daily, Context mContext) {
        this.mDaily = daily;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout View Item
        View dailyView = inflater.inflate(R.layout.item_daily,parent, true);
        ViewHolder viewHolder = new ViewHolder(dailyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Daily itemweather = mDaily.get(position);

        holder.textdate.setText(itemweather.getmTextDate());
        holder.textweather.setText(itemweather.getmTextWeather());
        holder.tempmax.setText(itemweather.getmTempMax());
        holder.tempmin.setText(itemweather.getmTempMin());
        holder.icon.setImageResource(WeatherIcon.getIconId(itemweather.getmIconId()));


    }

    @Override
    public int getItemCount() {
        return mDaily.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private View itemView;
        public TextView textdate;
        public TextView textweather;
        public TextView tempmax;
        public TextView tempmin;
        public ImageView icon;

        public ViewHolder (View itemView) {
            super(itemView);
            itemView = itemView;
            textdate = itemView.findViewById(R.id.text_date);
            textweather = itemView.findViewById(R.id.text_weather);
            tempmax = itemView.findViewById(R.id.text_temp_max);
            tempmin = itemView.findViewById(R.id.text_temp_min);
            icon = itemView.findViewById(R.id.icon_weather);
        }

    }
}
