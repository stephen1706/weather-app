package com.stephen.weather.modelhandlers;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.text.TextUtils;

import com.stephen.weather.BuildConfig;
import com.stephen.weather.Constants;
import com.stephen.weather.WeatherDataBridge;
import com.stephen.weather.realm.WeatherLocation;
import com.stephen.weather.viewmodels.CurrentWeatherViewModel;
import com.stephen.weather.viewmodels.LocationViewModel;
import com.stephen.weather.viewmodels.WeatherListViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class MainModelHandler extends BaseModelHandler {
    public MainModelHandler(Context context) {
        super(context);
    }

    public Observable<CurrentWeatherViewModel> getWeather(double latitude, double longitude, String locationName){
        return mApiService.getWeather(latitude, longitude, BuildConfig.WEATHER_API_KEY, Constants.CELCIUS)
                .subscribeOn(Schedulers.io())
                .doOnNext(currentWeatherDataModel -> {//update cache
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    WeatherLocation realmResults = realm.where(WeatherLocation.class)
                            .equalTo("name", currentWeatherDataModel.getName())
                            .findFirst();
                    if(realmResults != null) {
                        realmResults.setLastTemperature((int) currentWeatherDataModel.getMain().getTemp());
                        realmResults.setLastDescription(currentWeatherDataModel.getWeather().get(0).getDescription());
                    }
                    realm.commitTransaction();
                })
                .map((dataModel) -> {
                    CurrentWeatherViewModel viewModel = WeatherDataBridge.getWeatherViewModel(dataModel, latitude, longitude);
                    if(!TextUtils.isEmpty(locationName)){
                        viewModel.setLocationName(locationName);
                    }
                    return viewModel;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean addLocation(double latitude, double longitude, String name) {
        try {
            mRealm.beginTransaction();
            WeatherLocation user = mRealm.createObject(WeatherLocation.class, name);
            user.setLatitude(latitude);
            user.setLongitude(longitude);
            mRealm.commitTransaction();
            return true;
        } catch (RealmPrimaryKey    ConstraintException e) {
            mRealm.cancelTransaction();
            return false;
        }
    }

    public Observable<WeatherListViewModel> getWeatherList(double latitude, double longitude) {
        return Observable.zip(getLocationList(), getWeather(latitude, longitude, null), (locationViewModels, currentLocationWeather) -> {
            WeatherListViewModel viewModel = new WeatherListViewModel();
            viewModel.list = new ObservableArrayList<CurrentWeatherViewModel>();
            viewModel.list.add(currentLocationWeather);//current location comes with complete data
            for(LocationViewModel locationViewModel : locationViewModels){
                CurrentWeatherViewModel currentWeatherViewModel = new CurrentWeatherViewModel();
                currentWeatherViewModel.setLocationName(locationViewModel.getName());
                currentWeatherViewModel.setLatitude(locationViewModel.getLatitude());
                currentWeatherViewModel.setLongitude(locationViewModel.getLongitude());
                //use cache from db
                Realm realm = Realm.getDefaultInstance();
                WeatherLocation weatherLocation = realm.where(WeatherLocation.class)
                        .equalTo("latitude", locationViewModel.getLatitude())
                        .equalTo("longitude", locationViewModel.getLongitude())
                        .findFirst();
                if(weatherLocation != null){
                    currentWeatherViewModel.setTemperature(weatherLocation.getLastTemperature());
                    currentWeatherViewModel.setDescription(weatherLocation.getLastDescription());
                }
                viewModel.list.add(currentWeatherViewModel);
            }
            return viewModel;
        });
//                .map(locationViewModels -> {
//                    WeatherListViewModel viewModel = new WeatherListViewModel();
//                    viewModel.list = new ObservableArrayList<CurrentWeatherViewModel>();
//                    for(LocationViewModel locationViewModel : locationViewModels){
//                        CurrentWeatherViewModel currentWeatherViewModel = new CurrentWeatherViewModel();
//                        currentWeatherViewModel.setLocationName(locationViewModel.getName());
//                        currentWeatherViewModel.setLatitude(locationViewModel.getLatitude());
//                        currentWeatherViewModel.setLongitude(locationViewModel.getLongitude());
//                        viewModel.list.add(currentWeatherViewModel);
//                    }
//                    return viewModel;
//                });
    }
}
