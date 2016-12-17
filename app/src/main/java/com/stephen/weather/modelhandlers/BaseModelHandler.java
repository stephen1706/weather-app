package com.stephen.weather.modelhandlers;

import android.content.Context;

import com.stephen.weather.WeatherApplication;
import com.stephen.weather.WeatherDataBridge;
import com.stephen.weather.api.ApiService;
import com.stephen.weather.provider.ApiProvider;
import com.stephen.weather.realm.WeatherLocation;
import com.stephen.weather.viewmodels.LocationViewModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by stephenadipradhana on 12/14/16.
 */


public class BaseModelHandler {
    protected final Context mContext;
    protected final ApiService mApiService;
    protected final Realm mRealm;

    public BaseModelHandler(Context context) {
        this.mContext = context;
        mApiService = ApiProvider.getApiService();
        mRealm = ((WeatherApplication) mContext.getApplicationContext()).getRealm();
    }

    public RealmResults<WeatherLocation> getLocation(){
        return mRealm.where(WeatherLocation.class).findAll();
    }

    public Observable<ArrayList<LocationViewModel>> getLocationList() {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<ArrayList<LocationViewModel>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<LocationViewModel>> e) throws Exception {
                e.onNext(WeatherDataBridge.getWeatherCityViewModel(getLocation()));
            }
        });
    }
}
