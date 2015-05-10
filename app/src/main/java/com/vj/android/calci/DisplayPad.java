package com.vj.android.calci;

import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Vijay on 5/10/2015.
 */
public class DisplayPad extends EditText {
    public DisplayPad(Context context) {
        super(context);
        setUp();
    }

    public DisplayPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DisplayPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public void setUp() {
//        remove underbar from edittext
        if (Build.VERSION.SDK_INT < 16)
            setBackgroundDrawable(null);
        else
            setBackground(null);

//        disable spell check
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        setCustomSelectionActionModeCallback(new NoTextSelection());
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
    }
}

class NoTextSelection implements ActionMode.Callback {

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}