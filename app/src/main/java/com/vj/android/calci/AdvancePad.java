package com.vj.android.calci;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AdvancePad extends Fragment {
    ImageButton deletebtn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.advance_pad, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LookHandler.setThemeForAdvance(getActivity());
//        deletebtn = (ImageButton) getActivity().findViewById(R.id.delbtnadv);
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
    }
}
