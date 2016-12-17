package com.stephen.weather.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.stephen.weather.BuildConfig;

/**
 * Created by stephenadipradhana on 12/14/16.
 */


public class LocationUtil implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = LocationUtil.class.getSimpleName();
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    private OnLocationChangedListener mOnLocationChangedListener;
    private OnConnectionChangedListener mOnConnectionChangedListener;
    private LocationRequest mLocationRequest;
    private LocationManager mLocationManager;

    private Location mLastLocation;

    public LocationUtil(Context context) {
        mContext = context;
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    //================================================================================
    // Setter / Getter
    //================================================================================

    public boolean isLocationEnabled() {
        boolean isLocationEnabled = false;
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isLocationEnabled = true;
        }

        return isLocationEnabled;
    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    //================================================================================
    // Interface Implementation for Location Service Callback
    //================================================================================

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "location acquired : " + latitude + "," + longitude);
        }

        mLastLocation = location;

        if (mOnLocationChangedListener != null) {
            mOnLocationChangedListener.onLocationChanged(location);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (AppUtils.isAndroidM() &&
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (mOnConnectionChangedListener != null) {
            mOnConnectionChangedListener.onConnected(bundle);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mOnConnectionChangedListener != null) {
            mOnConnectionChangedListener.onDisconnected();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mOnConnectionChangedListener != null) {
            mOnConnectionChangedListener.onConnectionFailed(connectionResult);
        }
    }

    //================================================================================
    // Callback Interface
    //================================================================================

    public void setOnLocationChangedListener(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
    }

    public void setOnConnectionChangedListener(OnConnectionChangedListener onConnectionChangedListener) {
        mOnConnectionChangedListener = onConnectionChangedListener;
    }

    public interface OnLocationChangedListener {
        void onLocationChanged(Location location);
    }

    public interface OnConnectionChangedListener {
        void onConnected(Bundle bundle);

        void onDisconnected();

        void onConnectionFailed(ConnectionResult connectionResult);
    }

    public Boolean isGPSEnabled() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || getLocationMode() > 0;
    }

    public int getLocationMode() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

       /* 0 = LOCATION_MODE_OFF
        1 = LOCATION_MODE_SENSORS_ONLY
        2 = LOCATION_MODE_BATTERY_SAVING
        3 = LOCATION_MODE_HIGH_ACCURACY
        */
    }
}

