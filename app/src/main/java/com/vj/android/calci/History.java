package com.vj.android.calci;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;


/**
 * Created by I310588 on 4/17/2015.
 */
public class History extends Activity {

    ListView history_entries;

    public static void removeHistoryEntries(Context context) {
        int maxHistory = MainActivity.max_history;
        while (MainActivity.history.size() > maxHistory) {
            MainActivity.history.remove(MainActivity.history.size() - 1);
        }
        (new Utility((Activity) context)).historyWrite();
    }

    //   If the history entry is already present then remove it
    private static void checkPreviousHistory(String expr, String res) {
        for (int i = 0; i < MainActivity.history.size(); i++) {
            if (MainActivity.history.get(i).getExpr().equals(expr) && MainActivity.history.get(i).getResult().equals(res)) {
                MainActivity.history.remove(i);
                return;
            }
        }
    }

    public static void addHistoryEntry(String expr, Context context) {
        if (expr.equals(MainActivity.entryText))
            return;
        checkPreviousHistory(expr, MainActivity.entryText);
        MainActivity.history.add(0, new HistoryRow(expr, MainActivity.entryText));
        removeHistoryEntries(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history);

        history_entries = (ListView) findViewById(R.id.listView);
        history_entries.setAdapter(new HistoryView(this));
    }

}
