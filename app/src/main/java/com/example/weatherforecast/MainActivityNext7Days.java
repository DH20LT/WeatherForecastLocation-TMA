package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivityNext7Days extends AppCompatActivity {

    String tenthanhpho = "";
    ImageView backIcon, imgId;
    TextView textName, test;
    ListView lv;
    WeatherAdapterNext7Days weatherAdapter;
    ArrayList<WeatherItemNext7Days> weatherArray;
    Geocoder geocoder;
    public static final String TAG = "MainActivityNext7Days";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        imgId = (ImageView) findViewById(R.id.img_example);
        backIcon = (ImageView) findViewById(R.id.img_back);
        textName = (TextView) findViewById(R.id.textTenTP2);
        lv = (ListView) findViewById(R.id.list_view);
        weatherArray = new ArrayList<>();
        weatherAdapter = new WeatherAdapterNext7Days(this, weatherArray);
        lv.setAdapter(weatherAdapter);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if(city.equals("")){
            tenthanhpho = "Saigon";
            Get7DaysData(tenthanhpho);
        }
        else{
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }



    }

    public void Get7DaysData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivityNext7Days.this);
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&lang=vi&cnt=15&appid=7dc9f0e071501cb394623f1306fd2b0a";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String name =  jsonObjectCity.getString("name");
                    textName.setText(name);

                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for(int i = 0; i < jsonArrayList.length(); i++){
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt");
                        long l = Long.valueOf(ngay);
                        Date date1 = new Date(l*1000L);
                        Date date2 = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                        String Day1 = simpleDateFormat1.format(date1);
                        String Day2 = simpleDateFormat2.format(date2);

                        JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                        int temp = jsonObjectMain.getInt("temp");
                        JSONArray jsonObjectWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectSubWeather = jsonObjectWeather.getJSONObject(0);
                        String status = jsonObjectSubWeather.getString("main");
                        int imgIdInt = 0;
                        if(status.equals("Clouds"))
                        {
                            Log.i("MainAc", "them anh3 cloud");
                            imgIdInt = R.drawable.ic_cloudy;
                        }
                        else if(status.equals("Rain")){
                            imgIdInt = R.drawable.ic_rainy;
                        }
                        else if(status.equals("Snow")){
                            imgIdInt = R.drawable.ic_snowy;
                        }
                        else if(status.equals("Clear")){
                            imgIdInt = R.drawable.ic_sunnycloudy;
                        }
                        else{
                            imgIdInt = R.drawable.ic_thunder;
                        }
                        weatherArray.add(new WeatherItemNext7Days(Day1, Day2, temp, imgIdInt));
                    }
                    weatherAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}

