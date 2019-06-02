package com.project.mobile.weatherapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.adapter.LocationAdapter;

import java.util.LinkedList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity {
    private static final String TAG = "HUENT";
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private PlacesClient placesClient;
    private LocationAdapter locationAdapter;
    private RecyclerView rvLocation;
    private SearchView svLocation;
    private List<AutocompletePrediction> locations = new LinkedList<>();
    public RelativeLayout relativeLayout;
    public BackgroundSetting backgroundSetting;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        String apiKey = "AIzaSyBi0njf8h1fbSAs34PG5Jc1_POHRdws7H4";

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_add_location);
        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);


        placesClient = Places.createClient(this);
        initSearchBar();
        rvLocation = (RecyclerView) findViewById(R.id.rv_location);
        locationAdapter = new LocationAdapter(locations, this);
        rvLocation.setAdapter(locationAdapter);
        rvLocation.setLayoutManager(new LinearLayoutManager(this));

    }

    private void searchPlaceAutoComplete(String query){
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setSessionToken(token)
                .setTypeFilter(TypeFilter.CITIES)
                .build();
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            locations.clear();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i(TAG, prediction.getPlaceId());
                Log.i(TAG, prediction.getPrimaryText(null).toString());
                locations.add(prediction);
            }
            locationAdapter.notifyDataSetChanged();
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
            }
        });
    }

    public void initSearchBar(){
        svLocation = ((SearchView) findViewById(R.id.sv_location));
        svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPlaceAutoComplete(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPlaceAutoComplete(newText);
                return true;
            }
        });
        svLocation.setFocusable(true);
        // svLocation.setIconifiedByDefault(false);
        svLocation.requestFocusFromTouch();
    }
    public void back(View v){
        finish();
    }
}
