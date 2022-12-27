package com.example.weatherforecast;

import android.widget.ImageView;

public class WeatherItemNext7Days {
    String nextDay, date;
    int temp, imgId;



    public WeatherItemNext7Days(String nextDay, String date, int temp, int imgId) {
        this.nextDay = nextDay;
        this.date = date;
        this.temp = temp;
        this.imgId = imgId;
    }

    public String getNextDay() {
        return nextDay;
    }

    public String getDate() {
        return date;
    }

    public int getTemp() {
        return temp;
    }

    public int getImgId() {
        return imgId;
    }

    public void setNextDay(String nextDay) {
        this.nextDay = nextDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String toString() {
        return nextDay + " " + date + " " + temp + " " + imgId;
    }
}
