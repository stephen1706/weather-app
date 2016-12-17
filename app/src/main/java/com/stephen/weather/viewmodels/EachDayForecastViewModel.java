package com.stephen.weather.viewmodels;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

/**
 * Created by stephenadipradhana on 12/16/16.
 */

public class EachDayForecastViewModel {
    private ObservableField<String> date;
    private ObservableField<Integer> high;
    private ObservableField<Integer> low;
    private Drawable weatherImage;
    private ObservableField<String> weatherStatus;

    public ObservableField<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = new ObservableField<String>(date);
    }

    public ObservableField<Integer> getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = new ObservableField<Integer>(high);
    }

    public ObservableField<Integer> getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = new ObservableField<Integer>(low);
    }

    public Drawable getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(Drawable weatherImage) {
        this.weatherImage = weatherImage;
    }

    public ObservableField<String> getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = new ObservableField<String>(weatherStatus);
    }
}
