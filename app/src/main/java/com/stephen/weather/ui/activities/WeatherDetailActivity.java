package com.stephen.weather.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.stephen.weather.R;
import com.stephen.weather.modelhandlers.WeatherDetailModelHandler;
import com.stephen.weather.ui.fragments.ForecastFragment;
import com.stephen.weather.viewmodels.LocationViewModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import static com.stephen.weather.R.id.pager;

/**
 * Created by stephenadipradhana on 12/16/16.
 */

public class WeatherDetailActivity extends RxAppCompatActivity {

    public static final String PARAM_POSITION = "PARAM_POSITION";
    public static final String PARAM_CURRENT_LOCATION = "PARAM_CURRENT_LOCATION";

    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private PagerAdapter mPagerAdapter;
    private WeatherDetailModelHandler mModelHandler;
    private ArrayList<LocationViewModel> mWeatherList;
    private LatLng mCurrentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        int initialPosition = getIntent().getIntExtra(PARAM_POSITION, 0);
        mCurrentLocation = getIntent().getParcelableExtra(PARAM_CURRENT_LOCATION);
        mModelHandler = new WeatherDetailModelHandler(this);

        setUpView();
        mModelHandler.getLocationList()
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(latLngs -> {
                    mWeatherList = latLngs;
                    setUpViewState(initialPosition);
                });
    }

    private void setUpViewState(int initialPosition) {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);

        new Handler().post(() -> mPager.setCurrentItem(initialPosition));
    }

    private void setUpView() {
        mPager = (ViewPager) findViewById(pager);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ForecastFragment.newInstance(mCurrentLocation);
                default:
                    return ForecastFragment.newInstance(mWeatherList.get(position - 1));//-1 because there is current location in first
            }
        }

        @Override
        public int getCount() {
            return mWeatherList.size() + 1;
        }
    }

}
