package com.stephen.weather.datamodels;

import java.util.List;

/**
 * Created by stephenadipradhana on 12/16/16.
 */

public class ForecastDataModel {

    /**
     * city : {"id":524901,"name":"Moscow","coord":{"lon":37.615555,"lat":55.75222},"country":"RU","population":0}
     * cod : 200
     * message : 0.0266
     * cnt : 7
     * list : [{"dt":1481878800,"temp":{"day":256.03,"min":252.82,"max":261.21,"night":261.21,"eve":258.44,"morn":256.2},"pressure":1023.42,"humidity":74,"weather":[{"id":600,"main":"Snow","description":"小雪","icon":"13d"}],"speed":1.45,"deg":183,"clouds":12,"snow":0.2},{"dt":1481965200,"temp":{"day":267.27,"min":263.65,"max":268.98,"night":268.98,"eve":267.62,"morn":263.65},"pressure":1015.25,"humidity":95,"weather":[{"id":600,"main":"Snow","description":"小雪","icon":"13d"}],"speed":3.61,"deg":282,"clouds":76,"snow":0.82},{"dt":1482051600,"temp":{"day":269.86,"min":269,"max":271.06,"night":269.85,"eve":269.52,"morn":270.65},"pressure":1004.08,"humidity":94,"weather":[{"id":601,"main":"Snow","description":"雪","icon":"13d"}],"speed":4.82,"deg":286,"clouds":76,"snow":1.67},{"dt":1482138000,"temp":{"day":259.48,"min":253.21,"max":260.4,"night":253.21,"eve":255.6,"morn":260.4},"pressure":1013.83,"humidity":0,"weather":[{"id":600,"main":"Snow","description":"小雪","icon":"13d"}],"speed":6.92,"deg":39,"clouds":13,"snow":0.03},{"dt":1482224400,"temp":{"day":251.01,"min":243.03,"max":251.01,"night":243.03,"eve":244.65,"morn":251.01},"pressure":1030.63,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"晴","icon":"01d"}],"speed":3.57,"deg":29,"clouds":0},{"dt":1482310800,"temp":{"day":254.05,"min":247.36,"max":261.05,"night":261.05,"eve":256.93,"morn":247.36},"pressure":1029.64,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"晴","icon":"01d"}],"speed":7.07,"deg":256,"clouds":28},{"dt":1482397200,"temp":{"day":270.74,"min":265.34,"max":277.41,"night":273.87,"eve":277.41,"morn":265.34},"pressure":1010.68,"humidity":0,"weather":[{"id":600,"main":"Snow","description":"小雪","icon":"13d"}],"speed":12.98,"deg":274,"clouds":80,"rain":0.44,"snow":0.36}]
     */

    private CityBean city;
    private String cod;
    private double message;
    private int cnt;
    private List<ListBean> list;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CityBean {
        /**
         * id : 524901
         * name : Moscow
         * coord : {"lon":37.615555,"lat":55.75222}
         * country : RU
         * population : 0
         */

        private int id;
        private String name;
        private CoordBean coord;
        private String country;
        private int population;

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

        public CoordBean getCoord() {
            return coord;
        }

        public void setCoord(CoordBean coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }
    }

    public static class ListBean {
        /**
         * dt : 1481878800
         * temp : {"day":256.03,"min":252.82,"max":261.21,"night":261.21,"eve":258.44,"morn":256.2}
         * pressure : 1023.42
         * humidity : 74
         * weather : [{"id":600,"main":"Snow","description":"小雪","icon":"13d"}]
         * speed : 1.45
         * deg : 183
         * clouds : 12
         * snow : 0.2
         * rain : 0.44
         */

        private long dt;
        private TempBean temp;
        private double pressure;
        private int humidity;
        private double speed;
        private int deg;
        private int clouds;
        private double snow;
        private double rain;
        private List<WeatherBean> weather;

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public TempBean getTemp() {
            return temp;
        }

        public void setTemp(TempBean temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public double getSnow() {
            return snow;
        }

        public void setSnow(double snow) {
            this.snow = snow;
        }

        public double getRain() {
            return rain;
        }

        public void setRain(double rain) {
            this.rain = rain;
        }

        public List<WeatherBean> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherBean> weather) {
            this.weather = weather;
        }

        public static class TempBean {
            /**
             * day : 256.03
             * min : 252.82
             * max : 261.21
             * night : 261.21
             * eve : 258.44
             * morn : 256.2
             */

            private double day;
            private double min;
            private double max;
            private double night;
            private double eve;
            private double morn;

            public double getDay() {
                return day;
            }

            public void setDay(double day) {
                this.day = day;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getNight() {
                return night;
            }

            public void setNight(double night) {
                this.night = night;
            }

            public double getEve() {
                return eve;
            }

            public void setEve(double eve) {
                this.eve = eve;
            }

            public double getMorn() {
                return morn;
            }

            public void setMorn(double morn) {
                this.morn = morn;
            }
        }
    }
}
