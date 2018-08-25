package com.example.claudevandort.intents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneCallActivity extends AppCompatActivity {
    private EditText editTextPhoneNumber;
    private ImageButton buttonPhoneNuber;
    private String phoneNumber;
    private static final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        editTextPhoneNumber = findViewById(R.id.edit_text_phone_number);
        buttonPhoneNuber = findViewById(R.id.button_phone_number);
        buttonPhoneNuber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = editTextPhoneNumber.getText().toString();
                if (!phoneNumber.equals("")) {
                    // Check call permission
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        // For old android versions
                        oldCallRequestPermissions();
                    } else {
                        // For devices using android Marshmallow or newer
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    }
                }
                else{
                    Toast.makeText(PhoneCallActivity.this, "Input a phone number first", Toast.LENGTH_LONG).show();
                }
            }

            public void oldCallRequestPermissions() {
                if (checkPermissions(Manifest.permission.CALL_PHONE)) {
                    callIntent();
                } else {
                    Toast.makeText(PhoneCallActivity.this, "Phone access declined", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:
                if (permissions[0].equals(Manifest.permission.CALL_PHONE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        callIntent();
                    }
                    else{
                        Toast.makeText(PhoneCallActivity.this, "Phone access declined", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public void callIntent(){
        if (ActivityCompat.checkSelfPermission(PhoneCallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private boolean checkPermissions(String permission){
        return this.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
