package com.vj.android.calci;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


/**
 * Created by I310588 on 4/4/2015.
 */
public class SettingsActivity extends Activity {

    public static String theme;
    static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = pref.getString("theme", "0");
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private String[] themeNames;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            themeNames = getResources().getStringArray(R.array.themes);
            ListPreference lp = (ListPreference) findPreference("theme");
            lp.setTitle(themeNames[Integer.parseInt(theme)]);

            handleHistoryChange();
        }

        private void handleHistoryChange() {
            NumberPickerPreference npp = (NumberPickerPreference) findPreference("max_history");
            int max_history = pref.getInt("max_history", 1);
            MainActivity.max_history = max_history;
            npp.setSummary("Store upto " + max_history + " history entries");
            History.removeHistoryEntries(this.getActivity());
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals("theme")) {
                ListPreference lp = (ListPreference) findPreference("theme");
                theme = pref.getString("theme", "0");
                lp.setTitle(themeNames[Integer.parseInt(theme)]);
            } else if (key.equals("max_history")) {
                handleHistoryChange();
            }


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
