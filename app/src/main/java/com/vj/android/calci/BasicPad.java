package com.vj.android.calci;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * Created by I310588 on 3/16/2015.
 * Contains functionality of Basic pad fragment
 */
public class BasicPad extends Fragment {
    ImageButton deletebtn;
    private Activity activity;
    private View[] buttons = new View[10];

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.basic_pad, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LookHandler.setThemeForBasic(getActivity());

        buttons[0] = activity.findViewById(R.id.zerobtn);
        buttons[1] = activity.findViewById(R.id.onebtn);
        buttons[2] = activity.findViewById(R.id.twobtn);
        buttons[3] = activity.findViewById(R.id.threebtn);
        buttons[4] = activity.findViewById(R.id.fourbtn);
        buttons[5] = activity.findViewById(R.id.fivebtn);
        buttons[6] = activity.findViewById(R.id.sixbtn);
        buttons[7] = activity.findViewById(R.id.sevenbtn);
        buttons[8] = activity.findViewById(R.id.eightbtn);
        buttons[9] = activity.findViewById(R.id.ninebtn);

        LookHandler.DisableButtons(activity, buttons, "basic");
        LookHandler.EnableButtons(activity, buttons, "basic");

        deletebtn = (ImageButton) activity.findViewById(R.id.delbtn);
        deletebtn.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) getActivity()).performClear();
                return true;
            }
        });
    }
}
