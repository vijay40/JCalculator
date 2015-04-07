package com.example.i310588.helloworld;

import android.app.Activity;
import android.widget.LinearLayout;

/**
 * Created by I310588 on 4/5/2015.
 */
public class LookHandler {

    Activity activity;
    private final String DEFAULT_THEME = "1";
    private final String RED_THEME = "2";
    private final String BLUE_THEME = "3";


    public LookHandler(Activity activity) {
        this.activity = activity;
    }

    public void changeTheme(String name) {
        LinearLayout main_app = (LinearLayout) activity.findViewById(R.id.main_app);
        int display_color = 0;
        if(name.equals(DEFAULT_THEME)) {
            display_color = activity.getResources().getColor(R.color.default_theme);

        }
        else if(name.equals(RED_THEME)) {
            display_color = activity.getResources().getColor(R.color.red_theme);
        }
        main_app.setBackgroundColor(display_color);
    }
}
