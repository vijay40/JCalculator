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
public class History extends Activity implements AdapterView.OnItemClickListener{

    ListView history_entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history);

        history_entries = (ListView) findViewById(R.id.listView);
        history_entries.setAdapter(new HistoryView(this));
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.history_entry, R.id.result, MainActivity.result);
//        history_entries.setAdapter(adapter);
//        history_entries.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view;

        if(MainActivity.lastBtnHit == R.id.equalbtn)
            MainActivity.entryText = (String) tv.getText();
        else
            MainActivity.entryText += tv.getText();

        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static void removeHistoryEntries()
    {
        int maxHistory = MainActivity.max_history;
        while(MainActivity.history.size() >= maxHistory)
        {
//            MainActivity.result.remove(MainActivity.result.size()-1);
//            MainActivity.expression.remove(MainActivity.expression.size()-1);
            MainActivity.history.remove(MainActivity.history.size()-1);
        }
    }

    public static void addHistoryEntry(String expr)
    {
        removeHistoryEntries();
//        MainActivity.result.add(0, MainActivity.entryText);
//        MainActivity.expression.add(0, expr);
        MainActivity.history.add(0, new HistoryRow(expr, MainActivity.entryText));
    }
}
