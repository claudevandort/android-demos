package com.example.claudevandort.lists.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.claudevandort.lists.models.Movie;
import com.example.claudevandort.lists.adapters.MoviesAdapter;
import com.example.claudevandort.lists.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private List<Movie> movies;
    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movies = new ArrayList<Movie>(){{
            add(new Movie("Beautiful Mind", R.drawable.beautiful_mind));
            add(new Movie("Corpse Bride", R.drawable.corpse_bride));
            add(new Movie("Ready Player One", R.drawable.ready_player_one));
            add(new Movie("Spiderman", R.drawable.spiderman));
            add(new Movie("Tron Legacy", R.drawable.tron));
        }};

        mRecyclerview = findViewById(R.id.movies_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MoviesAdapter(movies, R.layout.movie_recycler_view_item, new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie, int position) {
                Toast.makeText(MoviesActivity.this, "Deleted " + movie.getName(), Toast.LENGTH_SHORT).show();
                deleteMovie(position);
            }
        });

        mRecyclerview.setHasFixedSize(true);
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
                this.addMovie(0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void addMovie(int position){
        movies.add(position, new Movie("New movie " + (movies.size()+1), R.drawable.no_image));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }

    private void deleteMovie(int position){
        movies.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
