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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.history);
        history_entries.setAdapter(adapter);
        history_entries.setOnItemClickListener(this);
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
}
