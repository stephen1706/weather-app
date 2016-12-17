package com.stephen.weather.viewmodels;

import java.util.ArrayList;

/**
 * Created by stephenadipradhana on 12/17/16.
 */

public class ForecastDetailViewModel {
    private ArrayList<EachDayForecastViewModel> forecasts;
    private CurrentWeatherViewModel currentWeather;

    public ArrayList<EachDayForecastViewModel> getForecasts() {
        return forecasts;
    }

    public void setForecasts(ArrayList<EachDayForecastViewModel> forecasts) {
        this.forecasts = forecasts;
    }

    public CurrentWeatherViewModel getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeatherViewModel currentWeather) {
        this.currentWeather = currentWeather;
    }
}
