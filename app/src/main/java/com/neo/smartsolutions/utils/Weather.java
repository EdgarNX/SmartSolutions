package com.neo.smartsolutions.utils;

public class Weather {
    public static String WEATHER_DESCRIPTION = "error";
    public static int WEATHER_CELSIUS = 0;
    public static int WEATHER_WIND_SPEED = 0;

    public static String getWeatherDescription() {
        return WEATHER_DESCRIPTION;
    }

    public static int getWeatherCelsius() {
        return WEATHER_CELSIUS;
    }

    public static int getWeatherWindSpeed() {
        return WEATHER_WIND_SPEED;
    }

    public static void setWeatherDescription(String weatherDescription) {
        WEATHER_DESCRIPTION = weatherDescription;
    }

    public static void setWeatherCelsius(int weatherCelsius) {
        WEATHER_CELSIUS = weatherCelsius;
    }

    public static void setWeatherWindSpeed(int weatherWindSpeed) {
        WEATHER_WIND_SPEED = weatherWindSpeed;
    }
}
