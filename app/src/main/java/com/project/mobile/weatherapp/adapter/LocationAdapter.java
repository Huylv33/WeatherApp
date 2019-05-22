package com.project.mobile.weatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.project.mobile.weatherapp.AddLocationActivity;
import com.project.mobile.weatherapp.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<AutocompletePrediction> placeList;
    public LocationAdapter(List<AutocompletePrediction> places){
        placeList = places;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text_location;

        public ViewHolder(View itemView) {
            super(itemView);

            text_location = (TextView) itemView.findViewById(R.id.text_location);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            AutocompletePrediction prediction = placeList.get(position);
            Toast.makeText(v.getContext(), prediction.getPrimaryText(null).toString(), Toast.LENGTH_LONG).show();
            Log.d("HUENT", "onClick: clicked" + prediction.getPrimaryText(null).toString());
            ((Activity) v.getContext()).finish();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_location_in_search, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AutocompletePrediction place = placeList.get(i);
        viewHolder.text_location.setText(place.getPrimaryText(null).toString());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
