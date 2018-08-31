package com.example.claudio.sharedprefslogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences prefs;
    private EditText editTextEmail, editTextPassword;
    private Switch switchRemember;
    private Button buttonLogin;
    private String validEmail = "example@email.com", validPassword = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(validStoredCredentials()){
            editTextEmail.setText(prefs.getString("email", ""));
            editTextPassword.setText(prefs.getString("password", ""));
            switchRemember.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString(), password = editTextPassword.getText().toString();
                if(login(email, password)) {
                    goToMain();
                    savePreference(email, password);
                }
            }
        });
    }

    private void bindUI(){
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        switchRemember = findViewById(R.id.switch_remember);
        buttonLogin = findViewById(R.id.button_login);
    }

    private boolean login(String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isValidPassword(password)){
            Toast.makeText(this, "Password not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.equals(validEmail);
    }

    private boolean isValidPassword(String password){
        return password.length() >= 4 && password.equals(validPassword);
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void savePreference(String email, String password){
        if(switchRemember.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
        }
    }

    private boolean validStoredCredentials(){
        String storedEmail = prefs.getString("email", ""), storedPassword = prefs.getString("password", "");
        return storedEmail.equals(validEmail) && storedPassword.equals(validPassword);
    }
}
