package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

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

    ImageView backIcon;
    TextView textName;
    ListView lv;
    WeatherAdapterNext7Days weatherAdapter;
    ArrayList<WeatherItemNext7Days> weatherArray;
    public static final String TAG = "MainActivityNext7Days";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_next7days);

//        imgId = (ImageView) findViewById(R.id.img_example);
        backIcon = (ImageView) findViewById(R.id.img_back);
        textName = (TextView) findViewById(R.id.textTenTP2);
        lv = (ListView) findViewById(R.id.list_view);

        addControls();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        String txtLat = intent.getStringExtra("lat");
        String txtLong = intent.getStringExtra("long");
        textName.setText(city);
        Get7DaysData(txtLat, txtLong);

    }

    private void addControls() {
        weatherArray = new ArrayList<>();
        weatherAdapter = new WeatherAdapterNext7Days(this, R.layout.custom_listview);
    }

    public void Get7DaysData(String latData, String longData) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivityNext7Days.this);
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat="
                + latData + "&lon=" + longData
                + "&units=metric&lang=vi&appid=80cb1c70e3a3eb816f34f5e4261df662";
        Log.i(TAG, "homnaytesturl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("daily");
                    for (int i = 0; i < 7; i++) {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt");
                        long l = Long.valueOf(ngay);
                        Date date1 = new Date(l * 1000L);
                        Date date2 = new Date(l * 1000L);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM");
                        String Day1 = simpleDateFormat1.format(date1);
                        String Day2 = simpleDateFormat2.format(date2);

                        JSONObject jsonObjectMain = jsonObjectList.getJSONObject("temp");
                        int temp = jsonObjectMain.getInt("day");
                        JSONArray jsonObjectWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectSubWeather = jsonObjectWeather.getJSONObject(0);
                        String status = jsonObjectSubWeather.getString("main");
                        int imgIdInt = 0;
                        if (status.equals("Clouds")) {
                            imgIdInt = R.drawable.ic_cloudy;
                        } else if (status.equals("Rain")) {
                            imgIdInt = R.drawable.ic_rainy;
                        } else if (status.equals("Snow")) {
                            imgIdInt = R.drawable.ic_snowy;
                        } else if (status.equals("Clear")) {
                            imgIdInt = R.drawable.ic_sunnycloudy;
                        } else {
                            imgIdInt = R.drawable.ic_thunder;
                        }
                        WeatherItemNext7Days weatherItemNext7Days
                                = new WeatherItemNext7Days(Day1, Day2, temp, imgIdInt);

                        weatherAdapter.add(weatherItemNext7Days);
                    }
                    lv.setAdapter(weatherAdapter);
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

