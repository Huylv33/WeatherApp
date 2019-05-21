package com.project.mobile.weatherapp;




import android.content.Context;


import android.content.Intent;

import android.content.res.Configuration;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import android.widget.Toast;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;


import com.project.mobile.weatherapp.adapter.MenuAdapter;
import com.project.mobile.weatherapp.fragment.DataCommunication;
import com.project.mobile.weatherapp.fragment_hourly;
import com.project.mobile.weatherapp.fragment.fragment_today;
import com.project.mobile.weatherapp.fragment.fragment_forecast;
import com.project.mobile.weatherapp.utils.GPSTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity  implements
        CompoundButton.OnCheckedChangeListener, DuoMenuView.OnMenuClickListener {
    private Context context;
//    private DrawerLayout drawerLayout;
    private GPSTracker gpsTracker;
    private SwitchCompat switchCompat;
//    private WeatherAsyncTask weatherAsyncTask;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> mTitles = new ArrayList<>();

    private DuoDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    // khoi tao icon tabLayout
    private int[] tabIcons = {
            R.drawable.ic_today_black_24dp,
            R.drawable.ic_hourly_black_24dp,
            R.drawable.ic_forecast_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*drawerLayout = (DuoDrawerLayout) findViewById(R.id.duo_navigation);
        drawerToggle = new DuoDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        */

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

// Phần comment này tạm thời không xóa đi nhé

        // Show main fragment in container
        //goToFragment(new fragment_today(), false);
        //mMenuAdapter.setViewSelected(0, true);
        //setTitle(mTitles.get(0));



        //switchCompat = findViewById(R.id.switch_1);
        //switchCompat = findViewById(R.id.switch_2);

        //switchCompat.setOnCheckedChangeListener(this);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        */
// Loại bỏ tiểu đề mặc định
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);


        //viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
//        loadWeatherInfor();
        gpsTracker = new GPSTracker(this);
    }

    //handle Toolbar and Menu
    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    //set up header, footer of duo navigation view
    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    //Mở màn hình quản lý vị trí
    public void showMenuClick(int position)
    {
        switch(position) {
            case 0: {
                Intent iLoc = new Intent(MainActivity.this, ManagerLocationActivity.class);
                startActivity(iLoc);
                break;
            }
            case 1: {
                Intent iNot = new Intent(MainActivity.this, ManagerNotificationActivity.class);
                startActivity(iNot);
                break;
            }
            case 3: {
                Intent iPre = new Intent(MainActivity.this, PrepareDayActivity.class);
                startActivity(iPre);
                break;
            }
            case 4: {
                Intent iUni = new Intent(MainActivity.this, UnitSettingActivity.class);
                startActivity(iUni);
                break;
            }
            case 5: {
                Intent iCha = new Intent(MainActivity.this, ChangeWallpaperActivity.class);
                startActivity(iCha);
                break;
            }
        }

    }

    //Mở màn hình quản lý thông báo

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.today, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0:
                showMenuClick(0);
                break;
            case 1:
                showMenuClick(1);
                break;
            case 3:
                showMenuClick(3);
                break;
            case 4:
                showMenuClick(4);
                break;
            case 5:
                showMenuClick(5);
                break;
            //case 0:
                //goToFragment(new fragment_hourly(), false);
            //case 1:
                //goToFragment(new fragment_forecast(), false);
            //default:
                //goToFragment(new fragment_today(), false);

        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

//    @Override
//    public void sendData(DataCommunication dataCommunication) {
//        fragment_forecast fragmentForecast  = new fragment_forecast();
//        Bundle args = new Bundle();
//        args.putSerializable("data",dataCommunication);
//        fragmentForecast.setArguments(args);
//        getSupportFragmentManager().beginTransaction().replace(R.id.today,fragmentForecast)
//        .addToBackStack(null).commit();
//    }


    //ViewHolder
    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.duo_navigation);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }

    // setup ViewPager and TabLayout icon
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_today(), "Hôm nay");
        adapter.addFragment(new fragment_hourly(), "Hằng giờ");
        adapter.addFragment(new fragment_forecast(), "Dự báo");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    // viewPager adapter

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putDouble("lat",gpsTracker.getLatitude());
            args.putDouble("lon",gpsTracker.getLongitude());

//            args.putString("lon",gpsTracker.getLongitude() + "");
            mFragmentList.get(position).setArguments(args);
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override

    public void onCheckedChanged(CompoundButton compoundButton,
                                 boolean b) {
        switchCompat.isChecked();
    }

    /*@Override
>>>>>>> erik
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
    */


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent iLoc = new Intent(MainActivity.this, ManagerLocationActivity.class);
                startActivity(iLoc);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
<<<<<<< HEAD
//    private void loadWeatherInfor() {
//        if (shouldAskPermissions() && !isFirstTimeLauncher()) {
//            startActivity(new Intent(this,PermissionAboveMarshmellow.class));
//            getWeather();
//        }
//        else getWeather();
=======
    */

//    private void getCurrentLocation() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            Log.d("Location", "location here");
//                        }
//                        else {
//                            Log.d("Location", "location here");
//                        }
//                    }
//                });

//    }
//    private boolean isFirstTimeLauncher() {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
//        boolean is = sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
//        return  sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
//    }
//    private void getWeather() {
//        if (NetworkChecking.isNetworkAvailable(getApplicationContext())) {
//            GPSTracker gpsTracker = new GPSTracker(this);
//            gpsTracker.getLocation();
//            weatherAsyncTask = new WeatherAsyncTask(gpsTracker.getLatitude(), gpsTracker.getLongitude(), new doComplete() {
//                @Override
//                public void doCompele(OpenWeatherMap openWeatherMap) {
//                    NumberFormat format = new DecimalFormat("#0.0");
//                    ImageView imgWeather = (ImageView) findViewById(R.id.imgWeather);
//                    TextView txtTemperature=(TextView) findViewById(R.id.txtTemperature);
//                    TextView txtCurrentAddressName=(TextView) findViewById(R.id.txtCurrentAddressName);
//                    TextView txtMaxTemp=(TextView) findViewById(R.id.txtMaxTemp);
//                    TextView txtMinTemp=(TextView) findViewById(R.id.txtMinTemp);
//                    TextView txtWind=(TextView) findViewById(R.id.txtWind);
//                    TextView txtCloudliness= (TextView) findViewById(R.id.txtCloudliness);
//                    TextView txtPressure= (TextView) findViewById(R.id.txtPressure);
//                    TextView txtHumidty= (TextView) findViewById(R.id.txtHumidty);
//                    TextView txtSunrise= (TextView) findViewById(R.id.txtSunrise);
//                    TextView txtSunset= (TextView) findViewById(R.id.txtSunset);
//                    imgWeather.setImageResource(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));
//                    String temperature= format.format(openWeatherMap.getMain().getTemp()-273.15)+"°C";
//                    String minTemp= format.format(openWeatherMap.getMain().getTemp_min()-273.15)+"°C";
//                    String maxTemp= format.format(openWeatherMap.getMain().getTemp_max()-273.15)+"°C";
//                    txtSunrise.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise()));
//                    txtSunset.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunset()));
//                    txtCurrentAddressName.setText(openWeatherMap.getName());
//                    txtTemperature.setText(temperature);
//                    txtMinTemp.setText(minTemp);
//                    txtMaxTemp.setText(maxTemp);
//                    String wind= openWeatherMap.getWind().getSpeed()+" m/s";
//                    String mesg = openWeatherMap.getWeather().get(0).getMain();
//                    String cloudiness= mesg;
//                    String pressure= openWeatherMap.getMain().getPressure()+" hpa";
//                    String humidity=openWeatherMap.getMain().getHumidity()+" %";
//                    txtWind.setText(wind);
//                    txtCloudliness.setText(cloudiness);
//                    txtPressure.setText(pressure);
//                    txtHumidty.setText(humidity);
//                }
//            });
//            weatherAsyncTask.execute();
//        }
//    }
//    private boolean shouldAskPermissions() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (weatherAsyncTask != null) {
//            weatherAsyncTask.cancel(true);
//        }
//    }



}

