package com.vj.android.calci;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by I310588 on 4/24/2015.
 */
public class HistoryView extends BaseAdapter {

    protected ListView history;
    Context context;
    Utility utility;
    private View.OnClickListener mExprClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (int) v.getTag();
            addToEntryText(MainActivity.history.get(position).getExpr());
        }
    };
    private View.OnClickListener mResClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (int) v.getTag();
            addToEntryText(MainActivity.history.get(position).getResult());
        }
    };
    private View.OnClickListener mTrashClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (int) v.getTag();
            MainActivity.history.remove(position);
            notifyDataSetChanged();
            utility.historyWrite();
        }
    };

    HistoryView(Context c) {
        context = c;
        utility = new Utility((Activity) context);
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
        ImageView trash = (ImageView) row.findViewById(R.id.trash);

        HistoryRow temp = MainActivity.history.get(position);
        expr.setText(temp.getExpr());
        result.setText(temp.getResult());

        expr.setOnClickListener(mExprClickListener);
        result.setOnClickListener(mResClickListener);
        trash.setOnClickListener(mTrashClickListener);

        expr.setTag(position);
        result.setTag(position);
        trash.setTag(position);

        return row;
    }

    private void addToEntryText(String expr) {

        if (MainActivity.lastBtnHit == R.id.equalbtn)
            MainActivity.entryText = expr;
        else
            MainActivity.entryText += expr;

        ((Activity) context).finish();
    }
}
