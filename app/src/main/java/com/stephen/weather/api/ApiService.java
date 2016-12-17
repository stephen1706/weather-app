package com.stephen.weather.api;

import com.stephen.weather.datamodels.ForecastDataModel;
import com.stephen.weather.datamodels.CurrentWeatherDataModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public interface ApiService {
    @GET(ApiRoutes.GET_WEATHER_ROUTES)
    Observable<CurrentWeatherDataModel> getWeather(@Query("q") String query,
                                                   @Query("appid") String apiKey,
                                                   @Query("units") String unit);

    @GET(ApiRoutes.GET_WEATHER_ROUTES)
    Observable<CurrentWeatherDataModel> getWeather(@Query("lat") double latitude,
                                                   @Query("lon") double longitude,
                                                   @Query("appid") String apiKey,
                                                   @Query("units") String unit);

    @GET(ApiRoutes.FORECAST_ROUTES)
    Observable<ForecastDataModel> getForecast(@Query("lat") double latitude,
                                              @Query("lon") double longitude,
                                              @Query("appid") String apiKey,
                                              @Query("units") String unit);
}
