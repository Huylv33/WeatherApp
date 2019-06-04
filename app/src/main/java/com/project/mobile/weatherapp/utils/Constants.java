package com.project.mobile.weatherapp.utils;

public class Constants {
    public static final String OPEN_WEATHER_MAP_API_KEY = "82bc2fb6b5cd963286d1275326454889";
    public static final String AIR_VISUAL_API_KEY = "ByQRFAM4BrnhnRAKe";
    public static final String AQICN_API_KEY = "9b73c3c85c8e5e408d5e1675b18a27b0149a367f";
    public static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String AIR_VISUAL_URL = "https://api.airvisual.com/v2/";
    public static final int LOCATION_REQUEST = 1000;
    public static final String ACCUWEATHER_API_KEY = "lbjilagzgOmeHP63lZd8uK0ERxCRonoA";
    public static final String KEY_TYPE = "type";
    public static final class WeatherDatabaseTable {

        public static final String DB_NAME = "weather";
        public static final String TABLE_NAME = "weathertable";
        public static final int DB_VERSION = +1;

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String GET_PRODUCTS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String KEY_ID = "keyid";
        public static final String MAIN = "main";
        public static final String DESCRIPTION = "description";
        public static final String ICON = "icon";
        public static final String TEMP = "temp";
        public static final String HUMIDITY = "humidity";
        public static final String TEMP_MIN = "temp_min";
        public static final String TEMP_MAX = "temp_max";
        public static final String SPEED = "speed";
        public static final String COUNTRY = "country";
        public static final String SUNRISE = "sunrise";
        public static final String SUNSET = "sunset";
        public static final String NAME = "name";

        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + MAIN + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + ICON + " TEXT,"
                + TEMP + " TEXT,"
                + HUMIDITY + " TEXT,"
                + TEMP_MIN + " TEXT,"
                + TEMP_MAX + " TEXT,"
                + SPEED + " TEXT,"
                + COUNTRY + " TEXT,"
                + SUNRISE + " TEXT,"
                + SUNSET + " TEXT,"
                + NAME + " TEXT" + ")";
    }
}
