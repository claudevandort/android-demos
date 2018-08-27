package com.example.claudevandort.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    private List<Movie> movies;
    private int layout;
    private MoviesAdapter.OnItemClickListener itemClickListener;
    private Context context;

    public MoviesAdapter(List<Movie> movies, int layout, MoviesAdapter.OnItemClickListener itemClickListener){
        this.movies = movies;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        return new MoviesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        holder.bind(movies.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Movie movie, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageViewPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.text_view_movie_title);
            this.imageViewPoster = itemView.findViewById(R.id.image_view_movie_poster);
        }

        public void bind(final Movie movie, final MoviesAdapter.OnItemClickListener listener){
            this.textViewName.setText(movie.getName());
            //this.imageViewPoster.setImageResource(movie.getPoster());
            Picasso.get().load(movie.getPoster()).fit().into(this.imageViewPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(movie, getAdapterPosition());
                }
            });
        }
    }
}
