package com.example.i310588.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by I310588 on 3/22/2015.
 */
public class HexPad extends Fragment implements View.OnClickListener{

    private final int BINARY_MODE = 0;
    private final int OCTAL_MODE = 1;
    private final int DECIMAL_MODE = 2;
    private final int HEX_MODE = 3;

    Activity activity;
    View[] buttons = new View[16];
    static View[] modes = new View[4];

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hex_pad, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        modes[HEX_MODE] = activity.findViewById(R.id.hex_mode);
        modes[DECIMAL_MODE] = activity.findViewById(R.id.dec_mode);
        modes[OCTAL_MODE] = activity.findViewById(R.id.octal_mode);
        modes[BINARY_MODE] = activity.findViewById(R.id.bin_mode);

        for(int i=0; i<4; i++)
            modes[i].setOnClickListener(this);

        LookHandler.setThemeForHex(getActivity(), MainActivity.theme);

        buttons[0] = activity.findViewById(R.id.hexzerobtn);
        buttons[1] = activity.findViewById(R.id.hexonebtn);
        buttons[2] = activity.findViewById(R.id.hextwobtn);
        buttons[3] = activity.findViewById(R.id.hexthreebtn);
        buttons[4] = activity.findViewById(R.id.hexfourbtn);
        buttons[5] = activity.findViewById(R.id.hexfivebtn);
        buttons[6] = activity.findViewById(R.id.hexsixbtn);
        buttons[7] = activity.findViewById(R.id.hexsevenbtn);
        buttons[8] = activity.findViewById(R.id.hexeightbtn);
        buttons[9] = activity.findViewById(R.id.hexninebtn);
        buttons[10] = activity.findViewById(R.id.hexA);
        buttons[11] = activity.findViewById(R.id.hexB);
        buttons[12] = activity.findViewById(R.id.hexC);
        buttons[13] = activity.findViewById(R.id.hexD);
        buttons[14] = activity.findViewById(R.id.hexE);
        buttons[15] = activity.findViewById(R.id.hexF);

//      setting default mode to decimal
        handleMode();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.hex_mode){
            MainActivity.mode = 16;
            handleMode();
        }
        else if(id == R.id.dec_mode) {
            MainActivity.mode = 10;
            handleMode();
        }
        else if(id == R.id.octal_mode) {
            MainActivity.mode = 8;
            handleMode();
        }
        else if(id == R.id.bin_mode) {
            MainActivity.mode = 2;
            handleMode();
        }
    }

    public static int ModeToIdx()
    {
        if(MainActivity.mode == 2)
            return 0;
        else if(MainActivity.mode == 8)
            return 1;
        else if(MainActivity.mode == 10)
            return 2;
        else
            return 3;
    }

//    private void EnableButtons()
//    {
//        int theme = MainActivity.theme;
//        int color = 0, hex_color = 0, mode_color = 0;
//
//        if(theme == LookHandler.DEFAULT_THEME_DARK)
//        {
//            color = activity.getResources().getColor(R.color.grey_theme_normal);
//            hex_color = activity.getResources().getColor(R.color.grey_theme_hex);
//            mode_color = activity.getResources().getColor(R.color.default_dark_mode_enable);
//        }
//        else if(theme == LookHandler.DEFAULT_THEME_LIGHT)
//        {
//            color = activity.getResources().getColor(R.color.white);
//            hex_color = activity.getResources().getColor(R.color.default_light_op);
//            mode_color = activity.getResources().getColor(R.color.default_light_mode_enable);
//        }
//
//        for(int i=0; i<Math.min(MainActivity.mode, 10); i++)
//        {
//            buttons[i].setClickable(true);
//            buttons[i].setBackgroundColor(color);
//        }
//
//        for(int i=10; i<MainActivity.mode; i++)
//        {
//            buttons[i].setClickable(true);
//            buttons[i].setBackgroundColor(hex_color);
//        }
//        HexPad.modes[HexPad.ModeToIdx()].setBackgroundColor(mode_color);
//    }


//    private void DisableButtons() {
//        int theme = MainActivity.theme;
//        int color = 0, mode_color = 0;
//
//        if(theme == LookHandler.DEFAULT_THEME_DARK)
//        {
//            color = activity.getResources().getColor(R.color.default_dark_disable);
//            mode_color = activity.getResources().getColor(R.color.default_dark_mode_disable);
//        }
//        else if(theme == LookHandler.DEFAULT_THEME_LIGHT)
//        {
//            color = activity.getResources().getColor(R.color.default_light_disable);
//            mode_color = activity.getResources().getColor(R.color.default_light_mode_disable);
//        }
//
//        for(int i=0; i<4; i++)
//        {
//            int currentMode = HexPad.ModeToIdx();
//            if(i == currentMode)
//                continue;
//            else
//                HexPad.modes[i].setBackgroundColor(mode_color);
//        }
//
//        for(int i=MainActivity.mode; i<16; i++)
//        {
//            buttons[i].setClickable(false);
//            buttons[i].setBackgroundColor(color);
//        }
//    }

    public void handleMode()
    {
        LookHandler.DisableButtons(activity, buttons, "hex");
        LookHandler.EnableButtons(activity, buttons, "hex");
    }
}
