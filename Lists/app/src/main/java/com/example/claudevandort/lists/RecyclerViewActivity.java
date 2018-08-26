package com.example.claudevandort.lists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private List<String> names;
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        names = new ArrayList<String>(){{
            add("Claudio");
            add("Abel");
            add("Alvaro");
            add("Nico");
            add("Joe");
            add("Max");
            add("Roderick");
        }};

        mRecyclerview = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerAdapter(names, R.layout.recycler_view_item, new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(RecyclerViewActivity.this, "Clicked: " + name, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
    }
}
