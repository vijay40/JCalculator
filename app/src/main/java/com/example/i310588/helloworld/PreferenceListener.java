package com.example.i310588.helloworld;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by I310588 on 4/4/2015.
 */
public class PreferenceListener extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("theme")) {
//                   TODO implement theme functionality
            Log.e("VIJAY", "theme changed");
        }

    }
}
