package com.example.claudevandort.lists.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.claudevandort.lists.adapters.MyAdapter;
import com.example.claudevandort.lists.R;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.list_view);

        names = new ArrayList<>();
        names.add("Claudio");
        names.add("Abel");
        names.add("Alvaro");
        names.add("Nico");
        names.add("Joe");
        names.add("Max");
        names.add("Roderick");

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_1, names);
        MyAdapter adapter = new MyAdapter(ListViewActivity.this, R.layout.list_item, names);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}