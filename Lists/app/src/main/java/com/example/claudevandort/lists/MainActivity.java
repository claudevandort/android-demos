package com.example.claudevandort.lists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        ArrayList<String> names = new ArrayList<>();
        names.add("Claudio");
        names.add("Abel");
        names.add("Alvaro");
        names.add("Nico");
        names.add("Joe");
        names.add("Max");
        names.add("Roderick");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);
    }
}
