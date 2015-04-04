package com.example.i310588.helloworld;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by I310588 on 3/22/2015.
 * contains various utility functions used by other classes
 */
public class Utility {
    private double precision = 0.000000001;
    private int PRECISION = 9;
    Activity activity;

    // used for utility functions called by classes who didn't extend activity
    public Utility() {
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

    public boolean returnToBasic() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        return pref.getBoolean("return_basic", false);
    }

    //    determine how many characters should be deleted with the help of delete button
    public int numCharDelete(String f) {
        if ("sin(".equals(f) || "cos(".equals(f) || "tan(".equals(f) || "log(".equals(f))
            return 4;
        else {
            f = f.substring(f.length() - 3);
            if ("ln(".equals(f))
                return 3;
            else
                return 1;
        }
    }

    //    determine whether last operations is exponential or not
    public boolean isLastExpo(String expr) {
        if (expr.length() > 0 && "e".contains(expr.substring(expr.length() - 1)))
            return true;
        return false;
    }

    //  Method to determine whether character is operator or not
    public boolean isOperator(String ch) {
        if ("+-x÷".contains(ch))
            return true;
        return false;
    }

    //    determine whether the last character is a number or pi or '%' or '!'
    //    this helps in determining that operators which require two operands are applicable or not.
    public boolean isLastOperand(String expr) {
        if (expr.length() > 0) {
            String lastchar = expr.substring(expr.length() - 1);
            if ("0123456789.%!πABCDEF)".contains(lastchar))
                return true;
        }
        return false;
    }

    //    determine whether last char is operator or not
    public boolean isLastOperator(String expr) {
        if (expr.length() > 0 && isOperator(expr.substring(expr.length() - 1)))
            return true;
        return false;
    }

    public boolean isDigit(String ch) {
        if ("0123456789".contains(ch))
            return true;
        return false;
    }

//  Method to change the base of a number
    public String changeBase(String originalNumber, int originalBase, int base) throws SyntaxException {
        Symbols symbol = new Symbols();
        String[] parts = originalNumber.split(Pattern.quote("."));
        if(parts.length == 0) {
            parts = new String[1];
            parts[0] = "0";
        }
        if(parts[0].isEmpty()) {
            parts[0] = "0";
        }
        if(originalBase != 10) {
            parts[0] = Long.toString(Long.parseLong(parts[0], originalBase));
        }


        String wholeNumber = "";
        switch(base) {
            case 2:
                wholeNumber = Long.toBinaryString(Long.parseLong(parts[0]));
                break;
            case 8:
                wholeNumber = Long.toOctalString(Long.parseLong(parts[0]));
                break;
            case 10:
                wholeNumber = parts[0];
                break;
            case 16:
                wholeNumber = Long.toHexString(Long.parseLong(parts[0]));
                break;
        }
        if(parts.length == 1) return wholeNumber.toUpperCase(Locale.US);

        // Catch overflow (it's a decimal, it can be (slightly) rounded
        if(parts[1].length() > 13) {
            parts[1] = parts[1].substring(0, 13);
        }

        double decimal = 0;
        if(originalBase != 10)
        {
            String decimalFraction = Long.toString(Long.parseLong(parts[1], originalBase)) + "/" + originalBase + "^" + parts[1].length();
            decimal = symbol.eval(decimalFraction);
        }
        else
            decimal = Double.parseDouble("0." + parts[1]);

        if(decimal == 0) return wholeNumber.toUpperCase(Locale.US);

        String decimalNumber = "";
        for(int i = 0, id = 0; decimal != 0 && i <= PRECISION; i++) {
            decimal *= base;
            id = (int) Math.floor(decimal);
            decimal -= id;
            decimalNumber += Integer.toHexString(id);
        }
        return (wholeNumber + "." + decimalNumber).toUpperCase(Locale.US);
    }

    public String convertToRadix(String expr, int originalRadix, int radix) {
        String res = "", num = "", ch;
        long n;
        for(int i=0; i<expr.length(); i++){
            ch = expr.substring(i,i+1);
            if("0123456789.ABCDEF".contains(ch)) {
                num += ch;
            }
            else {
                if(num.length() > 0){
                    try {
                        res += changeBase(num, originalRadix, radix);
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
                res += ch;
                num = "";
            }
        }
        if(num.length() > 0) {
            try {
                res += changeBase(num, originalRadix, radix);
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return res;
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
