package com.example.claudevandort.realmnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.claudevandort.realmnotes.R;
import com.example.claudevandort.realmnotes.models.Board;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BoardAdapter extends BaseAdapter {
    private Context context;
    private List<Board> boards;
    private int layout;

    public BoardAdapter(Context context, List<Board> boards, int layout) {
        this.context = context;
        this.boards = boards;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Object getItem(int position) {
        return boards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.title = convertView.findViewById(R.id.text_view_board_item_title);
            vh.notes = convertView.findViewById(R.id.text_view_board_item_notes);
            vh.createdAt = convertView.findViewById(R.id.text_view_board_item_date);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }

        Board board = boards.get(position);
        vh.title.setText(board.getTitle());
        int notesCount = board.getNotes().size();
        String notesLabel = notesCount == 1 ? "Note" : "Notes";
        vh.notes.setText(notesCount + " " + notesLabel);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        vh.createdAt.setText(df.format(board.getCreatedAt()));
        return convertView;
    }

    public class ViewHolder{
        TextView title, notes, createdAt;
    }
}
