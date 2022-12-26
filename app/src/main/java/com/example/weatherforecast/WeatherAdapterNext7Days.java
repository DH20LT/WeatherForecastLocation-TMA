package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapterNext7Days extends BaseAdapter {
    Context context;
    ArrayList<WeatherItemNext7Days> weatherItemArrayList;

    public WeatherAdapterNext7Days(Context context, ArrayList<WeatherItemNext7Days> weatherItemArrayList) {
        this.context = context;
        this.weatherItemArrayList = weatherItemArrayList;
    }



    @Override
    public int getCount() {
        return weatherItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_listview, null);

        WeatherItemNext7Days weatherItem = weatherItemArrayList.get(i);
        TextView nextDay = view.findViewById(R.id.Next_Day);
        TextView Date =  view.findViewById(R.id.Date);
        TextView temp = view.findViewById(R.id.temp);
        ImageView imgId = view.findViewById(R.id.img_example);


        nextDay.setText(weatherItem.nextDay);
        Date.setText(weatherItem.date);
        temp.setText(weatherItem.temp+"°");
        imgId.setImageResource(weatherItem.imgId);


        return view;
    }
//
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
