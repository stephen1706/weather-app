package com.stephen.weather.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherListViewModel extends BaseObservable {
    public ObservableArrayList<CurrentWeatherViewModel> list;
}
