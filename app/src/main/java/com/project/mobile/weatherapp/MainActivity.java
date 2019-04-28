package com.project.mobile.weatherapp;



import android.content.Context;

import android.content.res.Configuration;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBarDrawerToggle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import com.project.mobile.weatherapp.utils.LocationUtil;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Context context;
//    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private SwitchCompat switchCompat;


    TabLayout tabLayout;
    ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_today_black_24dp,
            R.drawable.ic_hourly_black_24dp,
            R.drawable.ic_forecast_black_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String q = "London";
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        switchCompat = findViewById(R.id.switch_1);
        switchCompat = findViewById(R.id.switch_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LocationUtil locationUtil = new LocationUtil(MainActivity.this);

        //viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        // we add permissions we need to request location of the users

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}