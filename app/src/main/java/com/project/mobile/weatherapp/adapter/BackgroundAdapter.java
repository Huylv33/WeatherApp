package com.project.mobile.weatherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.model.Daily;

import java.util.List;

public class BackgroundAdapter extends  RecyclerView.Adapter<BackgroundAdapter.ViewHolder>{
    private List<Integer> mId;
    private Context mContext;
    public int sellectId;


    public BackgroundAdapter(List<Integer> id, Context context) {
        this.mId = id;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout View Item
        View backgroundView = inflater.inflate(R.layout.item_wallpaper,parent, false);
        BackgroundAdapter.ViewHolder viewHolder = new BackgroundAdapter.ViewHolder(backgroundView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Integer id = this.mId.get(position);
        viewHolder.backgroundImg.setImageResource(id);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellectId = position;
                notifyDataSetChanged();
            }
        });
        if(position == sellectId) {
            viewHolder.icon.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.icon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mId.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private View itemView;
        public ImageView backgroundImg;
        public ImageView icon;


        public ViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            backgroundImg = itemView.findViewById(R.id.item_wallpaper_image);
            icon = itemView.findViewById(R.id.iv_check);
            icon.setVisibility(View.INVISIBLE);
//            backgroundImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    icon.setVisibility(View.VISIBLE);
//                }
//            });

            this.setIsRecyclable(false);

        }

    }
}
