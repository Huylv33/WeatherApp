package com.project.mobile.weatherapp;




import android.content.BroadcastReceiver;
import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.res.Configuration;

import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.LinearLayout;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import com.project.mobile.weatherapp.broadcast.BroadcastNoti;
import com.project.mobile.weatherapp.setting.BackgroundSetting;
import com.project.mobile.weatherapp.setting.LocationSetting;
import com.project.mobile.weatherapp.setting.NotificationSetting;
import com.project.mobile.weatherapp.setting.PrepareDaySetting;
import com.project.mobile.weatherapp.adapter.MenuAdapter;
import com.project.mobile.weatherapp.fragment.FragmentHourly;
import com.project.mobile.weatherapp.fragment.FragmentToday;
import com.project.mobile.weatherapp.fragment.FragmentForecast;
import com.project.mobile.weatherapp.utils.AlarmUtils;
import com.project.mobile.weatherapp.utils.GPSTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements
        CompoundButton.OnCheckedChangeListener, DuoMenuView.OnMenuClickListener {
    private Context context;
    private GPSTracker gpsTracker;
    private SwitchCompat switchCompat;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LocationSetting locationSetting = new LocationSetting(this);
    private ArrayList<String> mTitles = new ArrayList<>();
    private DuoDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private Button mButton;
    // khoi tao icon tabLayout
    private int[] tabIcons = {
            R.drawable.ic_today_black_24dp,
            R.drawable.ic_hourly_black_24dp,
            R.drawable.ic_forecast_black_24dp
    };
    public String city = "Hanoi";
    public String country = "Vietnam";
    public Boolean usingLocation;
    public LinearLayout linearLayout;
    public BackgroundSetting backgroundSetting;
    public NotificationSetting notificationSetting;
    public AlarmUtils alarmUtils;
    public BroadcastReceiver broadcastNoti;
    public BroadcastReceiver broadcastWallpaper;
    public PrepareDaySetting prepareDaySetting;
    private BroadcastReceiver broadcastLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("OnCreate MainActivity","called");

        linearLayout = (LinearLayout) findViewById(R.id.linear_main_activity);
        backgroundSetting = new BackgroundSetting(getApplicationContext());
        backgroundSetting.loadBackgroundSetting();
        linearLayout.setBackgroundResource(backgroundSetting.backgroundId);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        // Initialize the views
        mViewHolder = new ViewHolder();
        // Handle toolbar actions
        handleToolbar();
        // Handle menu actions
        handleMenu();
        // Handle drawer actions
        handleDrawer();
        // Loại bỏ tiểu đề mặc định
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        locationSetting.loadLocationSetting();
        usingLocation = locationSetting.usingLocation;
        gpsTracker = new GPSTracker(this);
        this.city = locationSetting.city;
        this.country = locationSetting.country;
        // Lay intent tu Search truyen lai
        Intent locationIntent = getIntent();
        Bundle locationBundle = locationIntent.getBundleExtra("Place");
        if(locationBundle != null){
            usingLocation = false;
            this.city = locationBundle.getString("City");
            this.country = locationBundle.getString("Country");
        }
        //Lay intent tu Vi tri mac dinh
        Intent currentIntent = getIntent();
        Bundle currentBundle = currentIntent.getBundleExtra("CurrentLocation");
        if(currentBundle != null) {
            usingLocation = true;
        }

        locationSetting.city = this.city;
        locationSetting.country = this.country;
        locationSetting.usingLocation = usingLocation;
        locationSetting.saveLocationSetting();

        notificationSetting = new NotificationSetting(this);
        notificationSetting.loadNotificationSetting();
        alarmUtils = new AlarmUtils(this);

        if(notificationSetting.notification){
            Log.i("notification ", ":1");
            alarmUtils.startRepeat();
        }

        broadcastNoti = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notificationSetting.loadNotificationSetting();
                if(notificationSetting.notification){
                    Log.i("noti ", notificationSetting.notification + "");
                    alarmUtils.startRepeat();
                }
                else{
                    alarmUtils.stopRe();
                }
            }
        };

        IntentFilter filter = new IntentFilter("notiSetting");
        registerReceiver(broadcastNoti, filter);
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
        mMenuAdapter = new MenuAdapter(mTitles,this);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        mViewHolder.mDuoMenuView.setBackground(backgroundSetting.backgroundId);
    }

    //set up header, footer of duo navigation view
    @Override
    public void onFooterClicked() {
//        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
//        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    //Mở màn hình quản lý vị trí, click vao cai nao thi chuyen den activity do
    public void showMenuClick(int position)
    {
        switch(position) {
            case 0: {
                Intent iLoc = new Intent(MainActivity.this, ManageLocationActivity.class);
                startActivity(iLoc);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_out);
                break;
            }
            case 1: {
                Intent iNot = new Intent(MainActivity.this, ManageNotificationActivity.class);
                startActivity(iNot);
                break;
            }
            case 2: {
                Intent iPre = new Intent(MainActivity.this, PrepareDayActivity.class);
                startActivity(iPre);
                break;
            }
            case 3: {
                Intent iUni = new Intent(MainActivity.this, UnitSettingActivity.class);
                startActivity(iUni);
                break;
            }
            case 4: {
                Intent iFiv = new Intent(MainActivity.this, ChangeWallpaperActivity.class);
                startActivity(iFiv);
                break;
            }
        }

    }

    //Dang khong dung

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.today, fragment).commit();
    }

    // Chay cai showmenuClick
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
            case 2:
                showMenuClick(2);
                break;
            case 3:
                showMenuClick(3);
                break;
            case 4:
                showMenuClick(4);
                break;

        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }


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

    // setup ViewPager and TabLayout icon, add  trang thai cua 3 man hinh vao
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentToday(), "Hôm nay");
        adapter.addFragment(new FragmentHourly(), "Hằng giờ");
        adapter.addFragment(new FragmentForecast(), "Dự báo");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    // Set up icon cho 3 tab
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
            args.putDouble("lon", gpsTracker.getLongitude());
            args.putString("city", city);
            args.putString("country", country);
            args.putBoolean("usingLocation", usingLocation);
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
        // Ấn vào nó ra cái Activity Location
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent iLoc = new Intent(MainActivity.this, ManageLocationActivity.class);
                startActivity(iLoc);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    private void showNetworkAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        Context mContext = getApplicationContext();
        //Setting Dialog Title
        alertDialog.setTitle("Thông báo");

        //Setting Dialog Message
        alertDialog.setMessage("Thiết bị chưa được bật dịch vụ mạng");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        //On pressing cancel button
        alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmUtils.stop();
        alarmUtils.stopRe();
        unregisterReceiver(broadcastNoti);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart Activity", "called");
    }
}

