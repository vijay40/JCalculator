package com.example.i310588.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by I310588 on 4/24/2015.
 */
public class HistoryView extends BaseAdapter {

    Context context;

    HistoryView(Context c)
    {
        context = c;
    }

    @Override
    public int getCount() {
        return MainActivity.history.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.history_entry, parent, false);
        TextView expr = (TextView) row.findViewById(R.id.expr);
        TextView result = (TextView) row.findViewById(R.id.result);
        HistoryRow temp = MainActivity.history.get(position);
        expr.setText(temp.expression);
        result.setText(temp.result);
        return row;
    }
}
