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
    private static final String DEFAULT_THEME_LIGHT = "0";
    private static final String DEFAULT_THEME_DARK = "1";
    private static int display_color;
    private static int op_color;


    public LookHandler(Activity activity) {
        this.activity = activity;
    }


    public static void onActivityCreatedSetTheme(Activity activity, String name)
    {
        if(name.equals(DEFAULT_THEME_DARK)) {
            activity.setTheme(R.style.Theme_Calculator);
        }
        else if(name.equals(DEFAULT_THEME_LIGHT)) {
            activity.setTheme(R.style.Theme_Calculator_Light);
        }
    }

    private static void giveDisplayColor(Activity activity, String theme)
    {
        if(theme.equals(DEFAULT_THEME_DARK)) {
            display_color = activity.getResources().getColor(R.color.default_dark);
            op_color = activity.getResources().getColor(R.color.grey_theme_hex);
        }
        else if(theme.equals(DEFAULT_THEME_LIGHT)) {
            display_color = activity.getResources().getColor(R.color.default_light);
            op_color = activity.getResources().getColor(R.color.default_light_op);
        }
    }

    public static void setTheme(Activity activity, String name)
    {
        LinearLayout main_app = (LinearLayout) activity.findViewById(R.id.main_app);
        giveDisplayColor(activity, name);
        main_app.setBackgroundColor(display_color);
    }

    public static void setThemeForBasic(Activity activity, String name)
    {
        LinearLayout basic_pad = (LinearLayout) activity.findViewById(R.id.basic_pad);
        View plus_btn = activity.findViewById(R.id.plusbtn);
        View minus_btn = activity.findViewById(R.id.minusbtn);
        View multiply_btn = activity.findViewById(R.id.multiplybtn);
        View divide_btn = activity.findViewById(R.id.dividebtn);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtn);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtn);

        giveDisplayColor(activity, name);

        basic_pad.setBackgroundColor(display_color);
        plus_btn.setBackgroundColor(op_color);
        minus_btn.setBackgroundColor(op_color);
        multiply_btn.setBackgroundColor(op_color);
        divide_btn.setBackgroundColor(op_color);
        clear_btn.setBackgroundColor(op_color);
        delete_btn.setBackgroundColor(op_color);

        if(name.equals(DEFAULT_THEME_DARK))
        {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        }
        else if(name.equals(DEFAULT_THEME_LIGHT))
        {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }

    public static void setThemeForAdvance(Activity activity, String theme)
    {
        LinearLayout advance_pad = (LinearLayout) activity.findViewById(R.id.advance_pad);
        ImageButton clear_btn = (ImageButton) activity.findViewById(R.id.clearbtnadv);
        ImageButton delete_btn = (ImageButton) activity.findViewById(R.id.delbtnadv);

        giveDisplayColor(activity, theme);

        advance_pad.setBackgroundColor(display_color);
        clear_btn.setBackgroundColor(op_color);
        delete_btn.setBackgroundColor(op_color);

        if(theme.equals(DEFAULT_THEME_DARK))
        {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        }
        else if(theme.equals(DEFAULT_THEME_LIGHT))
        {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }

    public static void setThemeForHex(Activity activity, String theme)
    {
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

        if(theme.equals(DEFAULT_THEME_DARK))
        {
            clear_btn.setImageResource(R.drawable.clear_white);
            delete_btn.setImageResource(R.drawable.delete_white);
        }
        else if(theme.equals(DEFAULT_THEME_LIGHT))
        {
            clear_btn.setImageResource(R.drawable.clear_blue);
            delete_btn.setImageResource(R.drawable.delete_blue);
        }
    }
}
