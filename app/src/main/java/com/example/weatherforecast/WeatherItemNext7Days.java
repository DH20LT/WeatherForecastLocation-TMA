package com.example.weatherforecast;

import android.widget.ImageView;

public class WeatherItemNext7Days {
    String nextDay, date, hour;
    int temp, imgId;



    public WeatherItemNext7Days(String nextDay, String date, int temp, int imgId) {
        this.nextDay = nextDay;
        this.date = date;
        this.temp = temp;
        this.imgId = imgId;
    }


}
