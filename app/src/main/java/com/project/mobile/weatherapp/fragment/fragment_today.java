package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mobile.weatherapp.PermissionAboveMarshmellow;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherIcon;
import com.project.mobile.weatherapp.utils.doComplete;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**

 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class fragment_today extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  Context context;
    private WeatherAsyncTask weatherAsyncTask;

    private double lat;
    private double lon;
    private OpenWeatherMap openWeatherMapToday;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        lat = args.getDouble("lat");
        lon = args.getDouble("lon");
        loadWeatherInfor();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("OnPause","OnPause is called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        dataCommunication = new DataCommunication(gpsTracker.getLatitude(),gpsTracker.getLongitude());
//        sendingData.sendData(dataCommunication);
    }

    private void loadWeatherInfor() {
        if (shouldAskPermissions() && !isFirstTimeLauncher()) {
            startActivity(new Intent(getActivity(), PermissionAboveMarshmellow.class));
            getWeather();
        }
        else getWeather();
    }
    private boolean isFirstTimeLauncher() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        boolean is = sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
        return  sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
    }
    private void getWeather() {

        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            weatherAsyncTask = new WeatherAsyncTask(lat,lon, new doComplete() {
                @Override
                public  void doComplete(OpenWeatherMap openWeatherMap) {
                    openWeatherMapToday = openWeatherMap;
                    NumberFormat format = new DecimalFormat("#0.0");
                    ImageView imgWeather = (ImageView) getActivity().findViewById(R.id.imgWeather);
                    TextView txtTemperature=(TextView) getActivity().findViewById(R.id.txtTemperature);
                    TextView txtCurrentAddressName=(TextView) getActivity().findViewById(R.id.txtCurrentAddressName);
                    TextView txtMaxTemp=(TextView) getActivity().findViewById(R.id.txtMaxTemp);
                    TextView txtMinTemp=(TextView) getActivity().findViewById(R.id.txtMinTemp);
                    TextView txtWind=(TextView) getActivity().findViewById(R.id.txtWind);
                    TextView txtCloudliness= (TextView) getActivity().findViewById(R.id.txtCloudliness);
                    TextView txtPressure= (TextView) getActivity().findViewById(R.id.txtPressure);
                    TextView txtHumidty= (TextView) getActivity().findViewById(R.id.txtHumidty);
                    TextView txtSunrise= (TextView) getActivity().findViewById(R.id.txtSunrise);
                    TextView txtSunset= (TextView) getActivity().findViewById(R.id.txtSunset);
                    imgWeather.setImageResource(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));
                    String temperature= format.format(openWeatherMap.getMain().getTemp()-273.15)+"°C";
                    String minTemp= format.format(openWeatherMap.getMain().getTemp_min()-273.15)+"°C";
                    String maxTemp= format.format(openWeatherMap.getMain().getTemp_max()-273.15)+"°C";
                    txtSunrise.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise()));
                    txtSunset.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunset()));
                    txtCurrentAddressName.setText(openWeatherMap.getName());
                    txtTemperature.setText(temperature);
                    txtMinTemp.setText(minTemp);
                    txtMaxTemp.setText(maxTemp);
                    String wind= openWeatherMap.getWind().getSpeed()+" m/s";
                    String mesg = openWeatherMap.getWeather().get(0).getMain();
                    String cloudiness= mesg;
                    String pressure= openWeatherMap.getMain().getPressure()+" hpa";
                    String humidity=openWeatherMap.getMain().getHumidity()+" %";
                    txtWind.setText(wind);
                    txtCloudliness.setText(cloudiness);
                    txtPressure.setText(pressure);
                    txtHumidty.setText(humidity);
                }
            });
            weatherAsyncTask.execute();
        }

    }
    private boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weatherAsyncTask != null) {
            weatherAsyncTask.cancel(true);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        try {
//            sendingData = (SendingData) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
//        }
//    }
}
