package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityLoginForm extends AppCompatActivity {

    ImageView backIcon, avatarImg;
    TextView btnLogin;
    EditText editUsernameLogin, editPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_form);

        backIcon = findViewById(R.id.img_back);
        avatarImg = findViewById(R.id.avatar_login);
        btnLogin = findViewById(R.id.btnLogin);
        editUsernameLogin = findViewById(R.id.editUsername);
        editPasswordLogin = findViewById(R.id.editPassword);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}