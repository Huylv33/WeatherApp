package com.project.mobile.weatherapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.project.mobile.weatherapp.fragment.fragment_today;
import com.project.mobile.weatherapp.utils.Constants;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, CompoundButton.OnCheckedChangeListener, DuoMenuView.OnMenuClickListener {

    private Location location;
    private DuoDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles = new ArrayList<>();
    TextView textView;
    SwitchCompat switchCompat;
    TabLayout tabLayout;
    ViewPager viewPager;

    // khoi tao icon tabLayout
    private int[] tabIcons = {
            R.drawable.ic_today_black_24dp,
            R.drawable.ic_hourly_black_24dp,
            R.drawable.ic_forecast_black_24dp
    };

    @Override
    public void onProviderDisabled(String s) {

    }

    private GoogleApiClient googleApiClient;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest locationRequest;

    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds

    // lists for permissions

    private ArrayList<String> permissionsToRequest;

    private ArrayList<String> permissionsRejected = new ArrayList<>();

    private ArrayList<String> permissions = new ArrayList<>();

    // integer for permissions results request

    private static final int ALL_PERMISSIONS_RESULT = 1011;


    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String q = "London";
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

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
        // we add permissions we need to request location of the users

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0) {

                requestPermissions(permissionsToRequest.toArray(

                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);

            }

        }

        // we build google api client

        /*googleApiClient = new GoogleApiClient.Builder(this).

                addApi(LocationServices.API).

                addConnectionCallbacks(this).

                addOnConnectionFailedListener(this).build();
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(q, doComplete);
        weatherAsyncTask.execute();
        */
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
        adapter.addFragment(new com.project.mobile.weatherapp.fragment_hourly(), "Hằng giờ");
        adapter.addFragment(new com.project.mobile.weatherapp.fragment_forecast(), "Dự báo");
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
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


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

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {

        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {

            if (!hasPermission(perm)) {

                result.add(perm);

            }

        }

        return result;

    }

    private boolean hasPermission(String permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;

        }

        return true;

    }



    @Override

    protected void onStart() {

        super.onStart();



        if (googleApiClient != null) {

            googleApiClient.connect();

        }

    }



    @Override

    protected void onResume() {

        super.onResume();



        if (!checkPlayServices()) {


        }

    }

    @Override

    protected void onPause() {

        super.onPause();



        // stop location updates

//        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
//
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//
//            googleApiClient.disconnect();
//
//        }

    }



    private boolean checkPlayServices() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (apiAvailability.isUserResolvableError(resultCode)) {

                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);

            } else {

                finish();

            }



            return false;

        }



        return true;

    }



    @Override

    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                &&  ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;

        }



        // Permissions ok, we get last location

//        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);



        if (location != null) {

        }



        startLocationUpdates();

    }
    private void startLocationUpdates() {

        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationRequest.setInterval(UPDATE_INTERVAL);

        locationRequest.setFastestInterval(FASTEST_INTERVAL);



        if (ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                &&  ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();

        }


//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }



    @Override

    public void onConnectionSuspended(int i) {

    }



    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override

    public void onLocationChanged(Location location) {

        if (location != null) {

        }

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode) {

            case ALL_PERMISSIONS_RESULT:

                for (String perm : permissionsToRequest) {

                    if (!hasPermission(perm)) {

                        permissionsRejected.add(perm);

                    }

                }



                if (permissionsRejected.size() > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {

                            new AlertDialog.Builder(MainActivity.this).

                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").

                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                requestPermissions(permissionsRejected.

                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);

                                            }

                                        }

                                    }).setNegativeButton("Cancel", null).create().show();



                            return;

                        }

                    }

                } else {

                    if (googleApiClient != null) {

                        googleApiClient.connect();

                    }

                }



                break;

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


}

