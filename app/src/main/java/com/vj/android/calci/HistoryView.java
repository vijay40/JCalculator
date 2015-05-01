package com.vj.android.calci;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


/**
 * Created by I310588 on 4/24/2015.
 */
public class HistoryView extends BaseAdapter {

    protected ListView history;
    Context context;
    Utility utility;

    HistoryView(Context c) {
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
        View row = convertView;
        ViewHolder viewHolder = null;
        HistoryRow temp = MainActivity.history.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.history_entry, parent, false);
            viewHolder = new ViewHolder(row, context);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.expr.setText(temp.getExpr());
        viewHolder.result.setText(temp.getResult());

        viewHolder.expr.setTag(position);
        viewHolder.result.setTag(position);
        viewHolder.trash.setTag(position);

        return row;
    }



}
