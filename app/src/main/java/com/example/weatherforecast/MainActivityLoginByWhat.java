package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivityLoginByWhat extends AppCompatActivity {

    RelativeLayout btnFacebookLogin, btnGoogleLogin, btnAccountLogin;
    TextView btnDangKy;
    ImageView chartIcon, homeIcon, favoriteIcon, userIcon, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_by_what);

        backIcon = findViewById(R.id.img_back);
        btnAccountLogin = (RelativeLayout) findViewById(R.id.btnAccountLogin);
        btnFacebookLogin = (RelativeLayout) findViewById(R.id.btnFacebookLogin);
        btnGoogleLogin = (RelativeLayout) findViewById(R.id.btnGoogleButton);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);
        chartIcon = (ImageView) findViewById(R.id.chart_icon);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        homeIcon = (ImageView) findViewById(R.id.home_icon);
        btnDangKy = findViewById(R.id.btnDangKy);
        backIcon = findViewById(R.id.img_back);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityLoginByWhat.this,MainActivityHome.class));
            }
        });

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityLoginByWhat.this,MainActivityFavorite.class));
            }
        });

    }
}