package com.example.claudio.roomfragmentnotes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.claudio.roomfragmentnotes.R;
import com.example.claudio.roomfragmentnotes.entities.Board;

import java.util.List;

public class BoardsAdapter extends RecyclerView.Adapter<BoardsAdapter.BoardViewHolder>{
    private final LayoutInflater mInflater;
    private List<Board> mBoards;

    public BoardsAdapter(Context context){ mInflater = LayoutInflater.from(context); }

    class BoardViewHolder extends RecyclerView.ViewHolder{
        private final TextView boardNameTextView;

        public BoardViewHolder(View itemView) {
            super(itemView);
            boardNameTextView = itemView.findViewById(R.id.boards_item_name_text_view);
        }
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_boards_item, parent, false);
        return new BoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        if(mBoards != null){
            Board board = mBoards.get(position);
            holder.boardNameTextView.setText(board.getName());
        }
        else{
            holder.boardNameTextView.setText(" -- ");
        }
    }

    @Override
    public int getItemCount() {
        if(mBoards != null)
            return mBoards.size();
        else return 0;
    }

    public void setBoards(List<Board> boards){
        mBoards = boards;
        notifyDataSetChanged();
    }
}
