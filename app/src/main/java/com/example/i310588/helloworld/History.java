package com.example.i310588.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by I310588 on 4/17/2015.
 */
public class History extends Activity{

    ListView history_entries;
    Utility utility;

    public static void removeHistoryEntries() {
        int maxHistory = MainActivity.max_history;
        while (MainActivity.history.size() >= maxHistory) {
            MainActivity.history.remove(MainActivity.history.size() - 1);
        }
    }

    public static void addHistoryEntry(String expr)
    {
        removeHistoryEntries();
        MainActivity.history.add(0, new HistoryRow(expr, MainActivity.entryText));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history);

        utility = new Utility(this);

        history_entries = (ListView) findViewById(R.id.listView);
        history_entries.setAdapter(new HistoryView(this));
    }

//    public void dataChanged()
//    {
//       utility.historyWrite();
//    }
}
