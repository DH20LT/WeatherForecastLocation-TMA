package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivityHome extends AppCompatActivity {

    TextView textCityName1, textTime, textTempurature, textStatus, textHpa, textCloud, textWind, Next7Days;
    ImageView imgAnh1, findIcon, favoriteIcon, chartIcon, userIcon, homeIcon;
    String City = "";
    private static final String TAG = "MainActivityHome";
    // FusedLocationProviderClient - Main class for receiving location updates.
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Location currentLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        Next7Days = (TextView) findViewById(R.id.Next7DaysUnderline);
        textCityName1 = (TextView) findViewById(R.id.cityName1);
        textTime = (TextView) findViewById(R.id.timeSet);
        textTempurature = (TextView) findViewById(R.id.temperature);
        textStatus = (TextView) findViewById(R.id.status);
        textHpa = (TextView) findViewById(R.id.textHpa);
        textCloud = (TextView) findViewById(R.id.textMay);
        textWind = (TextView) findViewById(R.id.textGio);
        imgAnh1 = (ImageView) findViewById(R.id.anh1);
        findIcon = (ImageView) findViewById(R.id.find_icon);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);
        requestLocation();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest.Builder(TimeUnit.SECONDS.toMillis(60))
                .build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() != null) {
                    //currentLocation = locationResult.getLocations()[0];
                    double latitude = currentLocation.getLatitude();
                    double longitude = currentLocation.getLongitude();
                    Log.i(TAG, "onLocationResult: " + latitude + " " + longitude);
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.i(TAG, "onSuccess getLastLocation: " + location.getLatitude() + " " + location.getLongitude());
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String cityName = addresses.get(0).getLocality();
                            textCityName1.setText(cityName);
                            String stateName = addresses.get(0).getAddressLine(0);
                            String countryName = addresses.get(0).getCountryName();

                            Log.i(TAG, "onSuccess: " + cityName + ", " + stateName + ", " + countryName);
                        } else
                        {
                            Log.i(TAG, "onSuccess getLastLocation: null");
                        }
                    }
                });


        findIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityHome.this, MainActivityFind.class));
            }
        });

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityHome.this, MainActivityFavorite.class));
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityHome.this, MainActivityLoginByWhat.class));
            }
        });

        Next7Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = textCityName1.getText().toString();
                Log.i(TAG, "Next7Days onClick: city name to send through Intent " + city);
                Intent intent = new Intent(MainActivityHome.this, MainActivityNext7Days.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });

        if (textCityName1.getText().toString() != null) {
            GetCurrentWeatherData(textCityName1.getText().toString());
        }
    }

    private void requestLocation() {
        Log.i(TAG, "requestLocation GPS");
        // LocationRequest Builder
        com.google.android.gms.location.LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY)
                .setIntervalMillis(1000)
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // Lấy setting từ máy, để biết GPS đã bật chưa
        SettingsClient client = LocationServices.getSettingsClient(this);

        // Task dùng để mở luồng (thread) mới để kiểm tra GPS
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        Log.i(TAG, "requestLocation GPS - prepare to add Listener");
        // Nếu GPS chưa bật thì mở cửa sổ yêu cầu bật GPS
        // thêm listener để bắt sự kiện khi người dùng chưa bật GPS (vì Task fail)
        task.addOnFailureListener(this, e -> {
            Log.i(TAG, "onFailure");
            if (e instanceof ResolvableApiException) {
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().

                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(this,
                            51);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    public void GetCurrentWeatherData(String data) {
        Log.i(TAG, "GetCurrentWeatherData");
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivityHome.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + data + "&units=metric&lang=vi&appid=80cb1c70e3a3eb816f34f5e4261df662";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            textCityName1.setText(name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                            String Day = simpleDateFormat.format(date);
                            textTime.setText(Day);

                            JSONArray jsonObjectWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectSubWeather = jsonObjectWeather.getJSONObject(0);
                            String status = jsonObjectSubWeather.getString("main");

                            textStatus.setText(status);
                            if (status.equals("Clouds")) {
                                imgAnh1.setImageResource(R.drawable.ic_cloudy);
                            } else if (status.equals("Rain")) {
                                imgAnh1.setImageResource(R.drawable.ic_rainy);
                            } else if (status.equals("Snow")) {
                                imgAnh1.setImageResource(R.drawable.ic_snowy);
                            } else if (status.equals("Clear")) {
                                imgAnh1.setImageResource(R.drawable.ic_sunnycloudy);
                            } else {
                                imgAnh1.setImageResource(R.drawable.ic_thunder);
                            }

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            int temp = jsonObjectMain.getInt("temp");
                            int pressure = jsonObjectMain.getInt("pressure");
                            textTempurature.setText(temp + "°");
                            textHpa.setText(pressure + "hpa");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            textWind.setText(wind + "m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String cloud = jsonObjectCloud.getString("all");
                            textCloud.setText(cloud + "%");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}