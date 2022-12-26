package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityRegisterForm extends AppCompatActivity {

    EditText editUsernameRegister, editPasswordRegister, editPasswordConfirmRegister;
    TextView btnRegister;
    ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_form);

        backIcon = findViewById(R.id.img_back);
        editUsernameRegister = findViewById(R.id.editUsernameRegister);
        editPasswordRegister = findViewById(R.id.editPasswordRegister);
        editPasswordConfirmRegister = findViewById(R.id.editPasswordConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}