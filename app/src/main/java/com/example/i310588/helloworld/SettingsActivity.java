package com.example.i310588.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.prefs.PreferenceChangeListener;

/**
 * Created by I310588 on 4/4/2015.
 */
public class SettingsActivity extends Activity{

    static SharedPreferences pref;
    public static String theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = pref.getString("theme", "0");
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        private String[] themeNames;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            themeNames = getResources().getStringArray(R.array.themes);
            ListPreference lp = (ListPreference) findPreference("theme");
            lp.setTitle(themeNames[Integer.parseInt(theme)]);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            ListPreference lp = (ListPreference) findPreference("theme");
            theme = pref.getString("theme", "0");
            lp.setTitle(themeNames[Integer.parseInt(theme)]);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

}
