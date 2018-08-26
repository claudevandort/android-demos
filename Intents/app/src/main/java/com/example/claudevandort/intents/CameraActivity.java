package com.example.claudevandort.intents;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity {
    private Button buttonCameraShoot;
    private static final int CAMERA_PHOTO_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonCameraShoot = findViewById(R.id.button_camera_shoot);
        buttonCameraShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PHOTO_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case CAMERA_PHOTO_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(CameraActivity.this, "Result: " + data.toUri(0), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(CameraActivity.this, "Couldn't get the picture :(", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
