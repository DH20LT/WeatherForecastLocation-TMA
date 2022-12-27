package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityRegisterForm extends AppCompatActivity {

    EditText editUsernameRegister, editPasswordRegister, editPasswordConfirmRegister, editEmailRegister;
    TextView btnRegister, btnDangNhap;
    ImageView backIcon;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_form);

        backIcon = findViewById(R.id.img_back);
        editUsernameRegister = findViewById(R.id.editUsernameRegister);
        editPasswordRegister = findViewById(R.id.editPasswordRegister);
        editPasswordConfirmRegister = findViewById(R.id.editPasswordConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);
        editEmailRegister = findViewById(R.id.editEmailRegister);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityRegisterForm.this, MainActivityLoginForm.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String inputEmail = editEmailRegister.getText().toString();
        String Pass = editPasswordRegister.getText().toString();
        String ConPass = editPasswordConfirmRegister.getText().toString();

        if(!inputEmail.matches(emailPattern)){
            editEmailRegister.setError("Enter Connext Email");
        }else if(Pass.isEmpty() || Pass.length()<6){
            editPasswordRegister.setError("Enter Proper Password");
        }else if(!Pass.equals(ConPass)){
            editPasswordConfirmRegister.setError("Both Password not match!");
        }else{
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(inputEmail,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivityRegisterForm.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivityRegisterForm.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivityRegisterForm.this,Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}