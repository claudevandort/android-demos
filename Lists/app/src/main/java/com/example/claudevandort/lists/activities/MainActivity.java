package com.example.claudevandort.lists.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.claudevandort.lists.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mainListView;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainListView = findViewById(R.id.main_list_view);

        items = new ArrayList<>();
        items.add("ListView");
        items.add("GridView");
        items.add("RecyclerView");
        items.add("Movies (Recycler+CardView)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, items);

        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        goToActivity(ListViewActivity.class);
                        break;
                    case 1:
                        goToActivity(GridViewActivity.class);
                        break;
                    case 2:
                        goToActivity(RecyclerViewActivity.class);
                        break;
                    case 3:
                        goToActivity(MoviesActivity.class);
                        break;
                }
            }
        });
    }

    private void goToActivity(Class<?> target){
        startActivity(new Intent(MainActivity.this, target));
    }
}
