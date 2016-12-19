package com.stephen.weather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.stephen.weather.datamodels.CurrentWeatherDataModel;
import com.stephen.weather.datamodels.ForecastDataModel;
import com.stephen.weather.realm.WeatherLocation;
import com.stephen.weather.viewmodels.CurrentWeatherViewModel;
import com.stephen.weather.viewmodels.EachDayForecastViewModel;
import com.stephen.weather.viewmodels.ForecastDetailViewModel;
import com.stephen.weather.viewmodels.LocationViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmResults;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherDataBridge {
    public static CurrentWeatherViewModel getWeatherViewModel(CurrentWeatherDataModel dataModel, double latitude, double longitude){
        CurrentWeatherViewModel viewModel = new CurrentWeatherViewModel();
        viewModel.setLocationName(dataModel.getName());
        viewModel.setDescription(dataModel.getWeather().get(0).getDescription());
        viewModel.setTemperature((int) dataModel.getMain().getTemp());
        viewModel.setLatitude(latitude);
        viewModel.setLongitude(longitude);
        return viewModel;
    }

    public static ForecastDetailViewModel getForecastViewModel(Context context, ForecastDataModel forecastDataModel,
                                                               CurrentWeatherDataModel currentWeatherDataModel,
                                                               String locationName, double latitude, double longitude){
        ForecastDetailViewModel viewModel = new ForecastDetailViewModel();
        CurrentWeatherViewModel currentWeatherViewModel = getWeatherViewModel(currentWeatherDataModel, latitude, longitude);
        if(!TextUtils.isEmpty(locationName)){//change location name from db, sometimes the return from api weather is strange
            currentWeatherViewModel.setLocationName(locationName);
        }
        viewModel.setCurrentWeather(currentWeatherViewModel);

        ArrayList<EachDayForecastViewModel> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMM");
        for(ForecastDataModel.ListBean bean : forecastDataModel.getList()){
            EachDayForecastViewModel eachDayForecastViewModel = new EachDayForecastViewModel();
            eachDayForecastViewModel.setHigh((int) bean.getTemp().getMax());
            eachDayForecastViewModel.setLow((int) bean.getTemp().getMin());
            eachDayForecastViewModel.setWeatherImage(getImage(context, bean.getWeather().get(0).getIcon()));
            eachDayForecastViewModel.setWeatherStatus(bean.getWeather().get(0).getMain());
            eachDayForecastViewModel.setDate(simpleDateFormat.format(new Date(bean.getDt()*1000L)));
            list.add(eachDayForecastViewModel);
        }
        viewModel.setForecasts(list);

        return viewModel;
    }

    private static Drawable getImage(Context context, String icon) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("ic_" + icon, "drawable", context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    public static ArrayList<LocationViewModel> getWeatherCityViewModel(RealmResults<WeatherLocation> location) {
        ArrayList<LocationViewModel> list = new ArrayList<>();
        for(WeatherLocation each : location){
            LocationViewModel locationViewModel = new LocationViewModel();
            locationViewModel.setName(each.getName());
            locationViewModel.setLatitude(each.getLatitude());
            locationViewModel.setLongitude(each.getLongitude());
            list.add(locationViewModel);
        }
        return list;
    }
}
