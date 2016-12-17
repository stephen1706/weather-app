package com.stephen.weather.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class CurrentWeatherDataModel {

    /**
     * coord : {"lon":145.77,"lat":-16.92}
     * weather : [{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}]
     * base : cmc stations
     * main : {"temp":293.25,"pressure":1019,"humidity":83,"tempMin":289.82,"tempMax":295.37}
     * wind : {"speed":5.1,"deg":150}
     * clouds : {"all":75}
     * rain : {"3h":3}
     * dt : 1435658272
     * sys : {"type":1,"id":8166,"message":0.0166,"country":"AU","sunrise":1435610796,"sunset":1435650870}
     * id : 2172797
     * name : Cairns
     * cod : 200
     */

    private CoordBean coord;
    private String base;
    private MainBean main;
    private WindBean wind;
    private CloudsBean clouds;
    private RainBean rain;
    private long dt;
    private SysBean sys;
    private int id;
    private String name;
    private int cod;
    private List<WeatherBean> weather;

    public CoordBean getCoord() {
        return coord;
    }

    public void setCoord(CoordBean coord) {
        this.coord = coord;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainBean getMain() {
        return main;
    }

    public void setMain(MainBean main) {
        this.main = main;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public CloudsBean getClouds() {
        return clouds;
    }

    public void setClouds(CloudsBean clouds) {
        this.clouds = clouds;
    }

    public RainBean getRain() {
        return rain;
    }

    public void setRain(RainBean rain) {
        this.rain = rain;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public SysBean getSys() {
        return sys;
    }

    public void setSys(SysBean sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public static class MainBean {
        /**
         * temp : 293.25
         * pressure : 1019
         * humidity : 83
         * tempMin : 289.82
         * tempMax : 295.37
         */

        private double temp;
        private double pressure;
        private double humidity;
        @SerializedName("temp_min")
        private double tempMin;
        @SerializedName("temp_max")
        private double tempMax;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }
    }

    public static class WindBean {
        /**
         * speed : 5.1
         * deg : 150
         */

        private double speed;
        private double deg;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public static class CloudsBean {
        /**
         * all : 75
         */

        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class RainBean {
        /**
         * 3h : 3
         */

        @SerializedName("3h")
        private double value3h;

        public double getValue3h() {
            return value3h;
        }

        public void setValue3h(int value3h) {
            this.value3h = value3h;
        }
    }

    public static class SysBean {
        /**
         * type : 1
         * id : 8166
         * message : 0.0166
         * country : AU
         * sunrise : 1435610796
         * sunset : 1435650870
         */

        private int type;
        private int id;
        private double message;
        private String country;
        private int sunrise;
        private int sunset;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }
    }

}
