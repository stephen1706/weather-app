package com.stephen.weather.modelhandlers;

import android.content.Context;

import com.stephen.weather.BuildConfig;
import com.stephen.weather.Constants;
import com.stephen.weather.WeatherDataBridge;
import com.stephen.weather.realm.WeatherLocation;
import com.stephen.weather.viewmodels.ForecastDetailViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by stephenadipradhana on 12/16/16.
 */

public class ForecastModelHandler extends BaseModelHandler {
    public ForecastModelHandler(Context context) {
        super(context);
    }

    public Observable<ForecastDetailViewModel> getForecast(double latitude, double longitude, String locationName) {
        return Observable.zip(mApiService.getForecast(latitude,
                longitude,
                BuildConfig.WEATHER_API_KEY,
                Constants.CELCIUS),
                mApiService.getWeather(latitude,
                        longitude,
                        BuildConfig.WEATHER_API_KEY,
                        Constants.CELCIUS),
                (forecastDataModel, currentWeatherDataModel) -> {
                    Realm realm = Realm.getDefaultInstance();
                    WeatherLocation realmResults = realm
                            .where(WeatherLocation.class).equalTo("name", locationName)
                            .findFirst();
                    if(realmResults != null) {
                        realm.beginTransaction();
                        realmResults.setLastTemperature((int) currentWeatherDataModel.getMain().getTemp());
                        realmResults.setLastDescription(currentWeatherDataModel.getWeather().get(0).getDescription());
                        realm.commitTransaction();
                    }
                    return WeatherDataBridge.getForecastViewModel(mContext, forecastDataModel,
                            currentWeatherDataModel, locationName, latitude, longitude);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
