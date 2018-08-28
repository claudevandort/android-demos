package com.example.claudevandort.lists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.claudevandort.lists.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<String> names;

    public MyAdapter(Context context, int layout, List<String> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }
    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View holder pattern
        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(this.layout, null);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.list_item_text_view);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTextView.setText(names.get(position));
        return convertView;
    }

    class ViewHolder{
        private TextView nameTextView;
    }
}
