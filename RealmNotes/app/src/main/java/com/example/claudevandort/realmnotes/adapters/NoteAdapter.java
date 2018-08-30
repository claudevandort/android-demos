package com.example.claudevandort.realmnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.claudevandort.realmnotes.R;
import com.example.claudevandort.realmnotes.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;
    private int layout;
    public NoteAdapter(Context context, List<Note> notes, int layout) {
        this.context = context;
        this.notes = notes;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notes.get(position);
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
            vh.description = convertView.findViewById(R.id.text_view_note_description);
            vh.createdAt = convertView.findViewById(R.id.text_view_note_date);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        Note note = notes.get(position);

        vh.description.setText(note.getDescription());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        vh.createdAt.setText(df.format(note.getCreatedAt()));
        return convertView;
    }

    public class ViewHolder{
        TextView description, createdAt;
    }
}
