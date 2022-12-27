package com.example.weatherforecast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

public class MainActivityFavorite extends AppCompatActivity {

    ImageView backIcon, btnAdd, btnEdit, userIcon, homeIcon, chartIcon, favoriteIcon, findIcon;
    TextView nameTP;
    ListView lv;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    WeatherAdapterNext7Days weatherAdapter;
    ArrayList<WeatherItemNext7Days> weatherArray;
    public static final String TAG = "MainActivityFavorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favorite);

        backIcon = findViewById(R.id.img_back);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);
        findIcon = (ImageView) findViewById(R.id.find_icon);
        btnAdd = findViewById(R.id.img_plus);
        lv = (ListView) findViewById(R.id.listview_favorite);

        addControls();

        findIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFavorite.this, MainActivityFind.class));
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFavorite.this, MainActivityHome.class));
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFavorite.this, MainActivityLoginByWhat.class));
            }
        });

        btnAdd.setOnClickListener(view -> onSearchCalled());

    }

    private void addControls() {
        weatherArray = new ArrayList<>();
        weatherAdapter = new WeatherAdapterNext7Days(this, R.layout.custom_listview_favorite);
    }

    public void Get7DaysData(String latData, String longData) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivityFavorite.this);
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat="
                + latData + "&lon=" + longData
                + "&units=metric&lang=vi&appid=80cb1c70e3a3eb816f34f5e4261df662";
        Log.i(TAG, "homnaytesturl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCurrent = jsonObject.getJSONObject("current");
                    int temp = jsonObjectCurrent.getInt("temp");
                    JSONArray jsonArrayList = jsonObjectCurrent.getJSONArray("weather");
                    JSONObject jsonObjectList = jsonArrayList.getJSONObject(0);
                    String description = jsonObjectList.getString("description");

                    WeatherItemNext7Days weatherItemNext7Days
                            = new WeatherItemNext7Days(description, "", temp, 0);

                    weatherAdapter.add(weatherItemNext7Days);

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

    public void onSearchCalled() {
        Log.i(TAG, "onSearchCalled");
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("VN")
                .build(this);
        Log.i(TAG, "startActivityForResult");
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "onActivityResult RESULT_OK");
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                Toast.makeText(MainActivityFavorite.this, "ID: " + place.getId() + "address:" + place.getAddress() +
                        "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                String address = place.getAddress();
                // do query with address

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(MainActivityFavorite.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}