package com.example.i310588.helloworld;

import android.app.Activity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Created by I310588 on 3/22/2015.
 * contains various utility functions used by other classes
 */
public class Utility {
    private double precision = 0.000000001;
    Activity activity;

    // used for utility functions called by classes who didn't extend activity
    public Utility(){
        this.activity = null;
    }

    // used for setting display text, used by classes extending activity.
    public Utility(Activity activity) {
        this.activity = activity;
    }

    //    Method to determine wheather number is a double or not
    public boolean isDouble(double num) {
        long i = (long) num;

        if (Math.abs(num - (double) i) < precision)
            return false;
        return true;
    }

    //  Method to determine whether character is operator or not
    public boolean isOperator(String ch) {
        if ("+-x".contains(ch))
            return true;
        else if (ch.charAt(0) == '\u00f7')
            return true;
        return false;
    }

    public boolean isDigit(String ch) {
        if("0123456789".contains(ch))
            return true;
        return false;
    }

    //  Method to find whether current number contains a decimal point or not
    public boolean hasDecimal(String entryText) {
        boolean dec = false;
        int len = entryText.length();
        for (int i = len - 1; i >= 0; i--) {
            String lastchar = entryText.substring(i, i + 1);
            if (isOperator(lastchar))
                break;
            else if (entryText.substring(i, i + 1).equals(".")) {
                dec = true;
                break;
            }
        }
        return dec;
    }

    public void setDisplayText(String text) {
        final HorizontalScrollView display_pad = (HorizontalScrollView) this.activity.findViewById(R.id.display_pad);
        TextView entry = (TextView) this.activity.findViewById(R.id.entry);
        entry.setText(text);
        display_pad.post(new Runnable() {
            @Override
            public void run() {
                display_pad.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

}
