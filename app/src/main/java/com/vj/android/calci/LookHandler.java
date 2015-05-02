package com.vj.android.calci;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


/**
 * Created by I310588 on 4/5/2015.
 */
public class LookHandler {

    public static final int DEFAULT_THEME_LIGHT = 0;
    public static final int DEFAULT_THEME_DARK = 1;
    public static final int CRIMSON_RED = 2;
    public static final int CELADON_GREEN = 3;
    public static final int CERISE_PINK = 4;
    public static final int PALATINATE_PURPLE = 5;
    private static int display_color;
    private static int op_color;
    Activity activity;


    public LookHandler(Activity activity) {
        this.activity = activity;
    }

    public static void onActivityCreatedSetTheme(Activity activity, int theme) {
        if (theme == DEFAULT_THEME_DARK) {
            activity.setTheme(R.style.Theme_Calculator);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            activity.setTheme(R.style.Theme_Calculator_Light);
        } else if (theme == CRIMSON_RED) {
            activity.setTheme(R.style.Theme_Calculator_Cred);
        } else if (theme == CELADON_GREEN) {
            activity.setTheme(R.style.Theme_Calculator_Cgreen);
        } else if (theme == CERISE_PINK) {
            activity.setTheme(R.style.Theme_Calculator_Cpink);
        } else if (theme == PALATINATE_PURPLE) {
            activity.setTheme(R.style.Theme_Calculator_Ppurple);
        }
    }

    private static void giveDisplayColor(Activity activity) {
        int theme = Global.theme;
        if (isDarkTheme()) {
            op_color = activity.getResources().getColor(R.color.grey_theme_hex);
        } else {
            op_color = activity.getResources().getColor(R.color.default_light_op);
        }

        if (theme == DEFAULT_THEME_DARK) {
            display_color = activity.getResources().getColor(R.color.default_dark);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            display_color = activity.getResources().getColor(R.color.default_light);
        } else if (theme == CRIMSON_RED) {
            display_color = activity.getResources().getColor(R.color.crimson_red_theme);
        } else if (theme == CELADON_GREEN) {
            display_color = activity.getResources().getColor(R.color.celadon_green_theme);
        } else if (theme == CERISE_PINK) {
            display_color = activity.getResources().getColor(R.color.cerise_pink_theme);
        } else if (theme == PALATINATE_PURPLE) {
            display_color = activity.getResources().getColor(R.color.palatinate_purple_theme);
        }

    }

    public static void setTheme(Activity activity) {
        LinearLayout main_app = (LinearLayout) activity.findViewById(R.id.main_app);
        giveDisplayColor(activity);
        main_app.setBackgroundColor(display_color);
    }

    public static void setBtnImage(ImageButton clear_btn, ImageButton delete_btn) {
        int theme = Global.theme;
        if (theme == DEFAULT_THEME_DARK) {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        } else if (theme == DEFAULT_THEME_LIGHT) {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        } else if (theme == CRIMSON_RED) {
            clear_btn.setImageResource(R.drawable.clear_red);
            delete_btn.setImageResource(R.drawable.delete_red);
        } else if (theme == CELADON_GREEN) {
            clear_btn.setImageResource(R.drawable.clear_green);
            delete_btn.setImageResource(R.drawable.delete_green);
        } else if (theme == CERISE_PINK) {
            clear_btn.setImageResource(R.drawable.clear_pink);
            delete_btn.setImageResource(R.drawable.delete_pink);
        } else if (theme == PALATINATE_PURPLE) {
            clear_btn.setImageResource(R.drawable.clear_purple);
            delete_btn.setImageResource(R.drawable.delete_purple);
        }
    }

    public static void setOpColor(View[] v) {
        for (int i = 0; i < v.length; i++)
            v[i].setBackgroundColor(op_color);
    }

    public static int giveEnableModeColor(Activity activity) {
        int theme = Global.theme;
        int mode_color = 0;
        if (theme == LookHandler.DEFAULT_THEME_DARK) {
            mode_color = activity.getResources().getColor(R.color.default_dark_mode_enable);
        } else if (theme == LookHandler.DEFAULT_THEME_LIGHT) {
            mode_color = activity.getResources().getColor(R.color.default_light_mode_enable);
        } else if (theme == LookHandler.CRIMSON_RED) {
            mode_color = activity.getResources().getColor(R.color.crimson_red_mode_enable);
        } else if (theme == LookHandler.CELADON_GREEN) {
            mode_color = activity.getResources().getColor(R.color.celadon_green_mode_enable);
        } else if (theme == LookHandler.CERISE_PINK) {
            mode_color = activity.getResources().getColor(R.color.cerise_pink_mode_enable);
        } else if (theme == LookHandler.PALATINATE_PURPLE) {
            mode_color = activity.getResources().getColor(R.color.palatinate_purple_mode_enable);
        }
        return mode_color;
    }

    public static int giveDisableModeColor(Activity activity) {
        int theme = Global.theme;
        int mode_color = 0;
        if (theme == LookHandler.DEFAULT_THEME_DARK) {
            mode_color = activity.getResources().getColor(R.color.default_dark_mode_disable);
        } else if (theme == LookHandler.DEFAULT_THEME_LIGHT) {
            mode_color = activity.getResources().getColor(R.color.default_light_mode_disable);
        } else if (theme == LookHandler.CRIMSON_RED) {
            mode_color = activity.getResources().getColor(R.color.crimson_red_mode_disable);
        } else if (theme == LookHandler.CELADON_GREEN) {
            mode_color = activity.getResources().getColor(R.color.celadon_green_mode_disable);
        } else if (theme == LookHandler.CERISE_PINK) {
            mode_color = activity.getResources().getColor(R.color.cerise_pink_mode_disable);
        } else if (theme == LookHandler.PALATINATE_PURPLE) {
            mode_color = activity.getResources().getColor(R.color.palatinate_purple_mode_disable);
        }
        return mode_color;
    }

    public static void setThemeForBasic(Activity activity) {
        View plus_btn = activity.findViewById(R.id.plusbtn);
        View minus_btn = activity.findViewById(R.id.minusbtn);
        View multiply_btn = activity.findViewById(R.id.multiplybtn);
        View divide_btn = activity.findViewById(R.id.dividebtn);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtn);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtn);

        View[] v = {plus_btn, minus_btn, multiply_btn, divide_btn, clear_btn, delete_btn};

        giveDisplayColor(activity);
        setOpColor(v);
        setBtnImage(clear_btn, delete_btn);
    }

    public static void setThemeForAdvance(Activity activity) {
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtnadv);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtnadv);

        View[] v = {clear_btn, delete_btn};

        giveDisplayColor(activity);
        setOpColor(v);
        setBtnImage(clear_btn, delete_btn);
    }

    public static void setThemeForHex(Activity activity) {
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

        View[] v = {plus_btn, minus_btn, multiply_btn, divide_btn, blank_space, A, B, C, D, E, F, clear_btn, delete_btn};

        giveDisplayColor(activity);
        setOpColor(v);
        setBtnImage(clear_btn, delete_btn);
    }

    private static boolean isDarkTheme() {
        int theme = Global.theme;
        return theme == LookHandler.DEFAULT_THEME_DARK;
    }

    public static void EnableButtons(Activity activity, View[] buttons, String pad) {
        int theme = Global.theme;
        int color = 0, hex_color = 0, mode_color = 0;

        if (isDarkTheme()) {
            color = activity.getResources().getColor(R.color.grey_theme_normal);
            hex_color = activity.getResources().getColor(R.color.grey_theme_hex);
        } else {
            color = activity.getResources().getColor(R.color.white);
            hex_color = activity.getResources().getColor(R.color.default_light_op);
        }

        mode_color = giveEnableModeColor(activity);

        for (int i = 0; i < Math.min(Global.mode, 10); i++) {
            buttons[i].setClickable(true);
            buttons[i].setBackgroundColor(color);
        }

        if (pad.equals("hex")) {
            for (int i = 10; i < Global.mode; i++) {
                buttons[i].setClickable(true);
                buttons[i].setBackgroundColor(hex_color);
            }
            HexPad.modes[HexPad.ModeToIdx()].setBackgroundColor(mode_color);
        }
    }

    public static void DisableButtons(Activity activity, View[] buttons, String pad) {
        int theme = Global.theme;
        int color = 0, mode_color = 0;
        int endlimit;

        if (isDarkTheme()) {
            color = activity.getResources().getColor(R.color.default_dark_disable);
        } else {
            color = activity.getResources().getColor(R.color.default_light_disable);
        }

        mode_color = giveDisableModeColor(activity);

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

        for (int i = Global.mode; i < endlimit; i++) {
            buttons[i].setClickable(false);
            buttons[i].setBackgroundColor(color);
        }
    }

}
