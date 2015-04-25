package com.example.i310588.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by I310588 on 4/17/2015.
 */
public class History extends Activity{

    ListView history_entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history);

        history_entries = (ListView) findViewById(R.id.listView);
        history_entries.setAdapter(new HistoryView(this));
    }

    public static void removeHistoryEntries()
    {
        int maxHistory = MainActivity.max_history;
        while(MainActivity.history.size() >= maxHistory)
        {
            MainActivity.history.remove(MainActivity.history.size()-1);
        }
    }

    public static void addHistoryEntry(String expr)
    {
        removeHistoryEntries();
        MainActivity.history.add(0, new HistoryRow(expr, MainActivity.entryText));
    }
}
