package com.example.claudio.sharedprefslogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String validEmail = "example@email.com", validPassword = "password";
        String storedEmail = prefs.getString("email", ""), storedPassword = prefs.getString("password", "");
        Intent intent;

        if(storedEmail.equals(validEmail) && storedPassword.equals(validPassword)){
            intent = new Intent(this, MainActivity.class);
        }
        else{
            intent = new Intent(this, LoginActivity.class);
        }

        finish();
        startActivity(intent);
    }
}
