package com.example.i310588.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by I310588 on 4/5/2015.
 */
public class LookHandler {

    Activity activity;
    public static final int DEFAULT_THEME_LIGHT = 0;
    public static final int DEFAULT_THEME_DARK = 1;
    private static int display_color;
    private static int op_color;


    public LookHandler(Activity activity) {
        this.activity = activity;
    }


    public static void onActivityCreatedSetTheme(Activity activity, int theme) {
        if (theme == DEFAULT_THEME_DARK) {
            activity.setTheme(R.style.Theme_Calculator);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            activity.setTheme(R.style.Theme_Calculator_Light);
        }
    }

    private static void giveDisplayColor(Activity activity, int theme) {
        if (theme == DEFAULT_THEME_DARK) {
            display_color = activity.getResources().getColor(R.color.default_dark);
            op_color = activity.getResources().getColor(R.color.grey_theme_hex);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            display_color = activity.getResources().getColor(R.color.default_light);
            op_color = activity.getResources().getColor(R.color.default_light_op);
        }
    }

    public static void setTheme(Activity activity, int theme) {
        LinearLayout main_app = (LinearLayout) activity.findViewById(R.id.main_app);
        giveDisplayColor(activity, theme);
        main_app.setBackgroundColor(display_color);
    }

    public static void setThemeForBasic(Activity activity, int theme) {
        LinearLayout basic_pad = (LinearLayout) activity.findViewById(R.id.basic_pad);
        View plus_btn = activity.findViewById(R.id.plusbtn);
        View minus_btn = activity.findViewById(R.id.minusbtn);
        View multiply_btn = activity.findViewById(R.id.multiplybtn);
        View divide_btn = activity.findViewById(R.id.dividebtn);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtn);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtn);

        giveDisplayColor(activity, theme);

        basic_pad.setBackgroundColor(display_color);
        plus_btn.setBackgroundColor(op_color);
        minus_btn.setBackgroundColor(op_color);
        multiply_btn.setBackgroundColor(op_color);
        divide_btn.setBackgroundColor(op_color);
        clear_btn.setBackgroundColor(op_color);
        delete_btn.setBackgroundColor(op_color);

        if (theme == DEFAULT_THEME_DARK) {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }

    public static void setThemeForAdvance(Activity activity, int theme) {
        LinearLayout advance_pad = (LinearLayout) activity.findViewById(R.id.advance_pad);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtnadv);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtnadv);

        giveDisplayColor(activity, theme);

        advance_pad.setBackgroundColor(display_color);
        clear_btn.setBackgroundColor(op_color);
        delete_btn.setBackgroundColor(op_color);

        if (theme == DEFAULT_THEME_DARK) {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }

    public static void setThemeForHex(Activity activity, int theme) {
        LinearLayout hex_pad = (LinearLayout) activity.findViewById(R.id.hex_pad);
        View plus_btn = activity.findViewById(R.id.plusbtnhex);
        View minus_btn = activity.findViewById(R.id.minusbtnhex);
        View multiply_btn = activity.findViewById(R.id.multiplybtnhex);
        View divide_btn = activity.findViewById(R.id.dividebtnhex);
        View blank_space = activity.findViewById(R.id.blank_space);
        View A = activity.findViewById(R.id.hexA);
        View B = activity.findViewById(R.id.hexB);
        View C = activity.findViewById(R.id.hexC);
        View D = activity.findViewById(R.id.hexD);
        View E = activity.findViewById(R.id.hexE);
        View F = activity.findViewById(R.id.hexF);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtnhex);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtnhex);

        giveDisplayColor(activity, theme);

        hex_pad.setBackgroundColor(display_color);
        plus_btn.setBackgroundColor(op_color);
        minus_btn.setBackgroundColor(op_color);
        multiply_btn.setBackgroundColor(op_color);
        divide_btn.setBackgroundColor(op_color);
        clear_btn.setBackgroundColor(op_color);
        delete_btn.setBackgroundColor(op_color);
        blank_space.setBackgroundColor(op_color);
        A.setBackgroundColor(op_color);
        B.setBackgroundColor(op_color);
        C.setBackgroundColor(op_color);
        D.setBackgroundColor(op_color);
        E.setBackgroundColor(op_color);
        F.setBackgroundColor(op_color);

        if (theme == DEFAULT_THEME_DARK) {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }

    public static void EnableButtons(Activity activity, View[] buttons, String pad) {
        int theme = MainActivity.theme;
        int color = 0, hex_color = 0, mode_color = 0;

        if (theme == LookHandler.DEFAULT_THEME_DARK) {
            color = activity.getResources().getColor(R.color.grey_theme_normal);
            hex_color = activity.getResources().getColor(R.color.grey_theme_hex);
            mode_color = activity.getResources().getColor(R.color.default_dark_mode_enable);
        } else if (theme == LookHandler.DEFAULT_THEME_LIGHT) {
            color = activity.getResources().getColor(R.color.white);
            hex_color = activity.getResources().getColor(R.color.default_light_op);
            mode_color = activity.getResources().getColor(R.color.default_light_mode_enable);
        }

        for (int i = 0; i < Math.min(MainActivity.mode, 10); i++) {
            buttons[i].setClickable(true);
            buttons[i].setBackgroundColor(color);
        }

        if (pad.equals("hex")) {
            for (int i = 10; i < MainActivity.mode; i++) {
                buttons[i].setClickable(true);
                buttons[i].setBackgroundColor(hex_color);
            }
            HexPad.modes[HexPad.ModeToIdx()].setBackgroundColor(mode_color);
        }
    }

    public static void DisableButtons(Activity activity, View[] buttons, String pad) {
        int theme = MainActivity.theme;
        int color = 0, mode_color = 0;
        int endlimit;

        if (theme == LookHandler.DEFAULT_THEME_DARK) {
            color = activity.getResources().getColor(R.color.default_dark_disable);
            mode_color = activity.getResources().getColor(R.color.default_dark_mode_disable);
        } else if (theme == LookHandler.DEFAULT_THEME_LIGHT) {
            color = activity.getResources().getColor(R.color.default_light_disable);
            mode_color = activity.getResources().getColor(R.color.default_light_mode_disable);
        }

        if (pad.equals("hex")) {
            endlimit = 16;
            for (int i = 0; i < 4; i++) {
                int currentMode = HexPad.ModeToIdx();
                if (i == currentMode)
                    continue;
                else
                    HexPad.modes[i].setBackgroundColor(mode_color);
            }
        } else
            endlimit = 10;

        for (int i = MainActivity.mode; i < endlimit; i++) {
            buttons[i].setClickable(false);
            buttons[i].setBackgroundColor(color);
        }
    }

}
