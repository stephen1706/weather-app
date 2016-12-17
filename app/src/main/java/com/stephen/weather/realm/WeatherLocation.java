package com.stephen.weather.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherLocation extends RealmObject {
    private double latitude;
    private double longitude;
    @PrimaryKey
    private String name;
    private String lastDescription;
    private int lastTemperature;

    public String getLastDescription() {
        return lastDescription;
    }

    public void setLastDescription(String lastDescription) {
        this.lastDescription = lastDescription;
    }

    public int getLastTemperature() {
        return lastTemperature;
    }

    public void setLastTemperature(int lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
