package com.stephen.weather.viewmodels;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class CurrentWeatherViewModel {
    private ObservableField<String> locationName;
    private ObservableField<Integer> temperature;
    private ObservableField<String> description;
    private double latitude;
    private double longitude;
    private View.OnClickListener listener;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = new ObservableField<String>(description);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public ObservableField<String> getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = new ObservableField<String>(locationName);
    }

    public ObservableField<Integer> getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = new ObservableField<Integer>(temperature);
    }
}
