package com.example.claudevandort.lists.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.claudevandort.lists.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MyRecyclerAdapter(List<String> names, int layout, OnItemClickListener itemClickListener){
        this.names = names;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(names.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public interface OnItemClickListener{
        void onItemClick(String name, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.recycler_view_item_text_view);
        }

        public void bind(final String name, final OnItemClickListener listener){
            this.textViewName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }
}
