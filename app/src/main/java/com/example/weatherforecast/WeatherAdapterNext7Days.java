package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WeatherAdapterNext7Days extends ArrayAdapter<WeatherItemNext7Days> {
    Context context;
    private final int resource;

    public WeatherAdapterNext7Days(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(context).inflate(resource, null);

        // Ánh xạ thông qua customView (chưa có data)
        TextView nextDay = customView.findViewById(R.id.Next_Day);
        TextView Date = customView.findViewById(R.id.Date);
        TextView temp = customView.findViewById(R.id.temp);
        ImageView imgId = customView.findViewById(R.id.img_example);

        TextView hourly = customView.findViewById(R.id.time_horizontal_listview);
        TextView Date1 = customView.findViewById(R.id.null1);
        TextView temp1 = customView.findViewById(R.id.temp_example);
        ImageView imgId1 = customView.findViewById(R.id.anh_minh_hoa);

        TextView favoriteName = customView.findViewById(R.id.Them_Added);
        TextView favoriteDescription = customView.findViewById(R.id.Description_Added);
        TextView favoriteTemp = customView.findViewById(R.id.Do_C_Added);
        ImageView imgId2 = customView.findViewById(R.id.favoriteImg);

        WeatherItemNext7Days weatherItem = getItem(position);

        if (nextDay != null || Date != null) {
            nextDay.setText(weatherItem.getNextDay());
            Date.setText(weatherItem.getDate());
            temp.setText(weatherItem.getTemp() + "°");
            imgId.setImageResource(weatherItem.getImgId());
        }

        if (hourly != null || Date1 != null) {
            hourly.setText(weatherItem.getNextDay());
            Date1.setText(weatherItem.getDate());
            temp1.setText(weatherItem.getTemp() + "°");
            imgId1.setImageResource(weatherItem.getImgId());
        }

        // Dành cho favorite
        if (favoriteName != null) {
            favoriteName.setText(weatherItem.getNextDay());
            //favoriteDescription.setText(weatherItem.getDate());
            //favoriteTemp.setText(weatherItem.getTemp() + "°");
            //imgId2.setImageResource(weatherItem.getImgId());
        }

        return customView;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        WeatherItem weatherItem = getItem(position);
//        if(convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview, parent, false);
//        }
//
//        ImageView imageView = convertView.findViewById(R.id.img_example);
//        TextView Date = convertView.findViewById(R.id.Date);
//        TextView nextDay = convertView.findViewById(R.id.Next_Day);
//        TextView temp = convertView.findViewById(R.id.temp);
//
//        Date.setText(weatherItem.date);
//        nextDay.setText(weatherItem.nextDay);
//        temp.setText(weatherItem.temp+"°");
//
//
//        return super.getView(position, convertView, parent);
//    }
}
