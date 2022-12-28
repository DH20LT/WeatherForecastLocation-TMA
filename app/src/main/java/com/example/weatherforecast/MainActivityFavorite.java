package com.example.weatherforecast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivityFavorite extends AppCompatActivity {

    public static final String TAG = "MainActivityFavorite";

    ImageView backIcon, btnAdd, btnEdit, userIcon, homeIcon, chartIcon, favoriteIcon, findIcon;
    TextView nameTP;
    ListView lv;

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    WeatherAdapterNext7Days weatherAdapter;
    ArrayList<WeatherItemNext7Days> weatherArray;

    WeatherAdapterNext7Days placeList;

    // init Firebase Realtime Database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favorite);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.API_KEY_MAPS, Locale.US);
        }

        backIcon = findViewById(R.id.img_back);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);
        findIcon = (ImageView) findViewById(R.id.find_icon);
        btnAdd = findViewById(R.id.img_plus);
        lv = findViewById(R.id.listview_favorite);
        placeList = new WeatherAdapterNext7Days(this, R.layout.custom_listview_favorite);

        addControls();

        // get data from Firebase
        DatabaseReference myRef = database.getReference("favorites");
        myRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = (DataSnapshot) task.getResult();
                if (dataSnapshot.exists()) {
                    Log.i(TAG, "onCreate: " + dataSnapshot.getValue());
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String lat = snapshot.child("lat").getValue(String.class);
                        String lon = snapshot.child("lon").getValue(String.class);

                        // add data to listview
                        placeList.add(new WeatherItemNext7Days(name));
                    }
                    lv.setAdapter(placeList);
                }
            }
        });

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

//    public void Get7DaysData(String latData, String longData) {
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivityFavorite.this);
//        String url = "https://api.openweathermap.org/data/3.0/onecall?lat="
//                + latData + "&lon=" + longData
//                + "&units=metric&lang=vi&appid=80cb1c70e3a3eb816f34f5e4261df662";
//        Log.i(TAG, "homnaytesturl" + url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jsonObjectCurrent = jsonObject.getJSONObject("current");
//                    int temp = jsonObjectCurrent.getInt("temp");
//                    JSONArray jsonArrayList = jsonObjectCurrent.getJSONArray("weather");
//                    JSONObject jsonObjectList = jsonArrayList.getJSONObject(0);
//                    String description = jsonObjectList.getString("description");
//
//                    WeatherItemNext7Days weatherItemNext7Days
//                            = new WeatherItemNext7Days(description, "", temp, 0);
//
//                    weatherAdapter.add(weatherItemNext7Days);
//
//                    lv.setAdapter(weatherAdapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringRequest);
//    }

    public void onSearchCalled() {
        Log.i(TAG, "onSearchCalled");
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("VN")
                .setTypeFilter(TypeFilter.CITIES)
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
                DatabaseReference favorite = database.getReference().child("favorites");
                // save place to firebase
                com.example.weatherforecast.Place place1 = new com.example.weatherforecast.Place(place.getName(),
                        String.valueOf(place.getLatLng().latitude),
                        String.valueOf(place.getLatLng().longitude)
                );
                favorite.push().setValue(place1);
                placeList.add(new WeatherItemNext7Days(place.getName()));
                lv.setAdapter(placeList);
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