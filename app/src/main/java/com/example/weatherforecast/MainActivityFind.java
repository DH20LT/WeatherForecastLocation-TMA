package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivityFind extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    ImageView favoriteIcon, chartIcon, userIcon, homeIcon;
    String City = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_find);

        editSearch = (EditText) findViewById(R.id.editCity);
        btnSearch = (Button) findViewById(R.id.btnOK);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFind.this,MainActivityFavorite.class));
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFind.this,MainActivityLoginByWhat.class));
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFind.this,MainActivityHome.class));
            }
        });

//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String city = editSearch.getText().toString();
//                if(city.equals("")){
//                    City = "Saigon";
//                    GetCurrentWeatherData(City);
//                }
//                else{
//                    City = city;
//                    GetCurrentWeatherData(City);
//                }
//            }
//        });
    }

}