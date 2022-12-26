package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityFavorite extends AppCompatActivity {

    ImageView backIcon, btnAdd, btnEdit, userIcon, homeIcon, chartIcon, favoriteIcon;
    TextView nameTP;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favorite);

        backIcon = findViewById(R.id.img_back);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFavorite.this,MainActivityHome.class));
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityFavorite.this,MainActivityLoginByWhat.class));
            }
        });
    }
}