package com.example.claudevandort.lists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
                Toast.makeText(RecyclerViewActivity.this, "Deleted " + name, Toast.LENGTH_SHORT).show();
                deleteName(position);
            }
        });

        mRecyclerview.hasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item_menu_option:
                this.addName(0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void addName(int position){
        names.add(position, "New item " + (names.size()+1));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }

    private void deleteName(int position){
        names.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
