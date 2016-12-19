package com.stephen.weather.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.stephen.weather.R;
import com.stephen.weather.databinding.ActivityMainBinding;
import com.stephen.weather.modelhandlers.MainModelHandler;
import com.stephen.weather.ui.adapter.PlaceAutocompleteAdapter;
import com.stephen.weather.ui.adapter.WeatherListAdapter;
import com.stephen.weather.util.AppUtils;
import com.stephen.weather.util.LocationUtil;
import com.stephen.weather.util.ViewUtils;
import com.stephen.weather.viewmodels.CurrentWeatherViewModel;
import com.stephen.weather.viewmodels.WeatherListViewModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int GET_LOCATION_REQUEST_CODE = 1;
    private static final int ADD_WEATHER_REQUEST_CODE = 2;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long GPS_TIMEOUT = 10000;

    private MainModelHandler mModelHandler;
    private LocationUtil mLocationUtil;
    private Location mLocation;
    private ActivityMainBinding binding;
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mGooglePlaceAdapter;

    private Runnable gpsTimeout = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "GPS Timeout");
                mLocationUtil.disconnect();
            } catch (SecurityException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                getWeatherData(null);
            }
        }
    };
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler(Looper.getMainLooper());
        mModelHandler = new MainModelHandler(this);
        mLocationUtil = new LocationUtil(this);

        setSupportActionBar(binding.toolbar);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        setUpAutocomplete();
        setUpListener();
    }

    private void setUpListener() {
        binding.fab.setOnClickListener(view -> binding.frameAutoComplete.setVisibility(View.VISIBLE));
        binding.swipeLayout.setOnRefreshListener(this::getCurrentLocation);
        binding.imageViewClose.setOnClickListener(view -> {
            binding.frameAutoComplete.setVisibility(View.GONE);
            binding.editTextSearch.setText("");
            ViewUtils.hideKeyboard(MainActivity.this);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
//        if(mLocation == null) {
        getCurrentLocation();
//        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        mLocationUtil.disconnect();
        handler.removeCallbacks(gpsTimeout);

        super.onStop();
    }

    private void setUpAutocomplete() {
        mGooglePlaceAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, null, null);
        binding.editTextSearch.setAdapter(mGooglePlaceAdapter);
        binding.editTextSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
                final AutocompletePrediction item = mGooglePlaceAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        ViewUtils.hideKeyboard(MainActivity.this);
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);

                        Log.i(TAG, "Place details received: " + place.getName());
                        Log.i(TAG, "address: " + place.getAddress());
                        Log.i(TAG, "phonenumber: " + place.getPhoneNumber());
                        Log.i(TAG, "id: " + place.getId());

                        boolean successInsert = mModelHandler.addLocation(place.getLatLng().latitude,
                                place.getLatLng().longitude,
                                place.getName().toString());
                        binding.editTextSearch.setText("");
                        if(!successInsert){
                            Toast.makeText(MainActivity.this, R.string.location_already_exists, Toast.LENGTH_SHORT).show();
                        } else {
                            getCurrentLocation();
                        }
                        places.release();
                    }
                });

                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            }
        });
    }

    @AfterPermissionGranted(GET_LOCATION_REQUEST_CODE)
    private void getCurrentLocation() {
        if (AppUtils.isAndroidM() && !EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            binding.swipeLayout.setRefreshing(false);
            EasyPermissions.requestPermissions(this,
                    getString(R.string.location_permission),
                    GET_LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            binding.swipeLayout.setRefreshing(true);
//            ProgressDialog progressDialog = ViewUtils.showProgressDialog(this, getString(R.string.get_current_location));
            mLocationUtil.setOnConnectionChangedListener(new LocationUtil.OnConnectionChangedListener() {
                @Override
                public void onConnected(Bundle bundle) {

                }

                @Override
                public void onDisconnected() {

                }

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Toast.makeText(MainActivity.this, R.string.fail_get_location, Toast.LENGTH_SHORT).show();
                    binding.swipeLayout.setRefreshing(false);
//                    progressDialog.dismiss();
                }
            });
            mLocationUtil.setOnLocationChangedListener(location -> {
//                progressDialog.dismiss();
                mLocationUtil.disconnect();

                mLocation = location;
                handler.removeCallbacks(gpsTimeout);
                getWeatherData(location);
            });
            mLocationUtil.connect();
            handler.postDelayed(gpsTimeout, GPS_TIMEOUT);
        }
    }

    private void getWeatherData(Location location) {
        if (location == null) {
            Toast.makeText(this, R.string.fail_get_location, Toast.LENGTH_SHORT).show();
            binding.swipeLayout.setRefreshing(false);
            return;
        }

        mCurrentLocation = location;

        mModelHandler.getWeatherList(location.getLatitude(), location.getLongitude())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(weatherViewModel -> {
                    setUpRowListener(weatherViewModel);
                    binding.setData(weatherViewModel);
                    getEachWeather(weatherViewModel);
                    binding.swipeLayout.setRefreshing(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    binding.swipeLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this,
                            R.string.fail_get_weather,
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void getEachWeather(WeatherListViewModel weatherViewModel) {
        for (CurrentWeatherViewModel viewModel : weatherViewModel.list) {//call weather api data one by one since there is no bulk api call available
            mModelHandler.getWeather(viewModel.getLatitude(), viewModel.getLongitude(), viewModel.getLocationName().get())
                    .compose(bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(currentWeatherViewModel -> {
                        WeatherListAdapter weatherListAdapter = (WeatherListAdapter) binding.recyclerView.getAdapter();
                        weatherListAdapter.setItems(currentWeatherViewModel);
                    }, throwable -> throwable.printStackTrace());
        }
    }

    private WeatherListViewModel setUpRowListener(WeatherListViewModel weatherViewModel) {
        int i = 0;
        for (CurrentWeatherViewModel viewModel : weatherViewModel.list) {
            int finalI = i;
            viewModel.setListener(view -> {
                Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
                intent.putExtra(WeatherDetailActivity.PARAM_POSITION, finalI);
                intent.putExtra(WeatherDetailActivity.PARAM_CURRENT_LOCATION,
                        new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                startActivity(intent);
            });
            i++;
        }
        return weatherViewModel;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.require_current_location, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView view, ArrayList<CurrentWeatherViewModel> list) {
        view.setAdapter(new WeatherListAdapter(list));
    }
}
