package com.example.claudevandort.intents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class WebActivity extends AppCompatActivity {
    private EditText editTextWebAddress;
    private ImageButton buttonWebAddress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        editTextWebAddress = findViewById(R.id.edit_text_web_address);
        buttonWebAddress = findViewById(R.id.button_web_address);
        buttonWebAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editTextWebAddress.getText().toString();
                if(url != ""){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://"+url));
                    startActivity(intent);
                }
            }
        });
    }
}
