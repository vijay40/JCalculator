package com.vj.android.calci;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class ViewHolder {
    TextView expr;
    TextView result;
    ImageView trash;
    private Utility utility;
    private Context context;
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
            History.historyView.notifyDataSetChanged();
            utility.historyWrite();
        }
    };

    ViewHolder(View v, Context context) {
        expr = (TextView) v.findViewById(R.id.expr);
        result = (TextView) v.findViewById(R.id.result);
        trash = (ImageView) v.findViewById(R.id.trash);

        expr.setOnClickListener(mExprClickListener);
        result.setOnClickListener(mResClickListener);
        trash.setOnClickListener(mTrashClickListener);

        utility = new Utility((Activity) context);
        this.context = context;
    }

    private void addToEntryText(String expr) {

        if (MainActivity.lastBtnHit == R.id.equalbtn)
            MainActivity.entryText = expr;
        else
            MainActivity.entryText += expr;

        MainActivity.lastBtnHit = R.id.list_item;
        ((Activity) context).finish();
    }

}
