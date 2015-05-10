package com.vj.android.calci;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HexPad extends Fragment implements View.OnClickListener {

    static View[] modes = new View[4];
    private final int BINARY_MODE = 0;
    private final int OCTAL_MODE = 1;
    private final int DECIMAL_MODE = 2;
    private final int HEX_MODE = 3;
    private Activity activity;
    private View[] buttons = new View[16];
    private ImageButton deletebtn;

    public static int ModeToIdx() {
        if (Global.mode == 2)
            return 0;
        else if (Global.mode == 8)
            return 1;
        else if (Global.mode == 10)
            return 2;
        else
            return 3;
    }

    private static void handleMode(Activity activity, View[] buttons) {
        LookHandler.DisableButtons(activity, buttons, "hex");
        LookHandler.EnableButtons(activity, buttons, "hex");
    }

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

//        deletebtn = (ImageButton) activity.findViewById(R.id.delbtnhex);
//        if(deletebtn != null)
//        {
//            deletebtn.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    ((MainActivity) getActivity()).performClear();
//                    return true;
//                }
//            });
//        }

        LookHandler.setThemeForHex(getActivity());

        modes[HEX_MODE] = activity.findViewById(R.id.hex_mode);
        modes[DECIMAL_MODE] = activity.findViewById(R.id.dec_mode);
        modes[OCTAL_MODE] = activity.findViewById(R.id.octal_mode);
        modes[BINARY_MODE] = activity.findViewById(R.id.bin_mode);

        for (int i = 0; i < 4; i++)
            modes[i].setOnClickListener(this);


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
        if (savedInstanceState != null) {
            Global.mode = savedInstanceState.getInt("mode");
        }
        handleMode(activity, buttons);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", Global.mode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Utility utility = new Utility(getActivity());
        if (id == R.id.hex_mode) {
            MainActivity.entryText = Utility.convertToRadix(MainActivity.entryText, Global.mode, 16);
            Global.mode = 16;
        } else if (id == R.id.dec_mode) {
            MainActivity.entryText = Utility.convertToRadix(MainActivity.entryText, Global.mode, 10);
            Global.mode = 10;
        } else if (id == R.id.octal_mode) {
            MainActivity.entryText = Utility.convertToRadix(MainActivity.entryText, Global.mode, 8);
            Global.mode = 8;
        } else if (id == R.id.bin_mode) {
            MainActivity.entryText = Utility.convertToRadix(MainActivity.entryText, Global.mode, 2);
            Global.mode = 2;
        }
        handleMode(activity, buttons);
        utility.setDisplayText(MainActivity.entryText, MainActivity.entryText.length());
    }
}
