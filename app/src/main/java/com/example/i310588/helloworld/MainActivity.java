package com.example.i310588.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;


public class MainActivity extends Activity {

    private String entryText = "";
    private int lastBtnHit;
    private double precision = 0.000000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Setting delete button to respond to long click
        Button delbtn = (Button) findViewById(R.id.delbtn);
        final TextView entry = (TextView) findViewById(R.id.entry);
        delbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performClear(entry);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("entryText", entryText);
        outState.putInt("lastBtn", lastBtnHit);
        Log.d("Viz", "entryText saved : " + entryText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        entryText = savedInstanceState.getString("entryText");
        lastBtnHit = savedInstanceState.getInt("lastBtn");
        TextView entry = (TextView) findViewById(R.id.entry);
        entry.setText(entryText);

        Log.d("Viz", "entryText restored : " + entryText);
    }

//    Method to facilitate testing not to be used in actual app
    public void setEntryText(String text) {
        entryText = text;
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

    //  Method to format expression for calculation
    public String formatExpr(String expr) {
        if (expr.isEmpty())
            return "";

        int len = expr.length();
        String current, last, next;
        String res = "";

//        handle trailing operators
        while (len > 0 && isOperator(expr.substring(len - 1, len)))
            len--;

//      handle invalid number of paranthesis
        int leftpara = 0;

        for (int i = 0; i < len; i++) {
            current = expr.substring(i, i + 1);
            if (current.equals("x"))
                res += "*";
            else if (current.charAt(0) == '\u00f7')
                res += "/";
            else if (current.equals("(")) {
                leftpara++;
                res += current;
            } else if (current.equals(")")) {
                leftpara--;
                res += current;
            } else
                res += current;
        }
        while (leftpara > 0) {
            res += ")";
            leftpara--;
        }

//        handles condition like N( to N*( and )N to )*N
        for (int i = 0; i < res.length(); i++) {
            current = res.substring(i, i + 1);
            if (current.equals("(") && i > 0) {
                last = res.substring(i - 1, i);
                if (!isOperator(last) && "1234567890.)".contains(last))
                    res = res.substring(0, i) + "*" + res.substring(i, res.length());
            } else if (current.equals(")") && i < res.length() - 1) {
                next = res.substring(i + 1, i + 2);
                if (!isOperator(next) && "1234567890.".contains(next))
                    res = res.substring(0, i + 1) + "*" + res.substring(i + 1, res.length());
            }
        }

//        handles conditions like . to 0.
        for(int i=0; i<res.length(); i++)
        {
            current = res.substring(i,i+1);
            if(i==0 && current.equals(".")) {
                res = "0" + res;
            }
            else if(current.equals(".") && !("0123456789".contains(res.substring(i-1,i)))) {
                res = res.substring(0,i) + "0" + res.substring(i,res.length());
            }
        }

        return res;
    }

    //  Method to perform calculation
    public void performCal(TextView entry) {

        String expr;
        expr = formatExpr(entryText);
//        handle empty expression text
        if(expr.isEmpty())
            return;
        Evaluator eval = new Evaluator();
        try {
            double res = eval.getNumberResult(expr);
            if(Double.isInfinite(res))
            {
                if(res > 0) {
                    entryText = "";
                    entry.setText(Character.toString('\u221e'));
                }
                else
                {
                    entryText = "";
                    entry.setText("-" + Character.toString('\u221e'));
                }
            }
            else if(Double.isNaN(res))
            {
                entry.setText(Double.toString(res));
                entryText = "";
            }
            else if (isDouble(res)) {
                entryText = Double.toString(res);
                entry.setText(entryText);
            }
            else {
                entryText = Long.toString((long) res);
                entry.setText(entryText);
            }
        } catch (EvaluationException ee) {
            entryText = "";
            entry.setText(entryText);
            Toast errorToast = new Toast(this);
            errorToast.setDuration(Toast.LENGTH_LONG);
            LayoutInflater lin = getLayoutInflater();
            View appear = lin.inflate(R.layout.error_toast, (ViewGroup)findViewById(R.id.error_toastroot));
            errorToast.setView(appear);
            errorToast.show();
        }
        Animation anim_fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        entry.startAnimation(anim_fadein);
    }

    //  Method to find whether current number contains a decimal point or not
    public boolean hasDecimal() {
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

    //  Method to perform short press of delete button
    public void performDelete(TextView entry) {
        int len = entryText.length();
        if (len > 0) {
            entryText = entryText.substring(0, len - 1);
        }
        entry.setText(entryText);
    }

    //  Method to handle clear function
    public void performClear(TextView entry) {
        entryText = "";
        entry.setText(entryText);
    }

    //  Method to handle click of operator button
    public void opClick(String op, TextView entry) {
        boolean hasNum = false;

//        checking whether entryText contains any number or not
        int len = entryText.length();

//        if operator is same as last char then return
        if (len > 0 && entryText.substring(len - 1, len).equals(op))
            return;

        for (int i = len - 1; i >= 0; i--) {
            if (!isOperator(entryText.substring(i, i + 1))) {
                hasNum = true;
                break;
            }
        }

//        if no number is present
        if (!hasNum) {
            if (op.equals("x") || op.charAt(0) == '\u00f7')
                return;
            else if (op.equals("+") && entryText.equals(""))
                return;
            else if (op.equals("+") && entryText.equals("-"))
                entryText = "";
            else
                entryText = "-";
        }
//        if number is present
        else {
            String lastOp = entryText.substring(len - 1, len);
            if (lastOp.equals("+")) {
                entryText = entryText.substring(0, len - 1) + op;
            } else if (lastOp.equals("-")) {
                if (isOperator(entryText.substring(len - 2, len - 1)))
                    entryText = entryText.substring(0, len - 2) + op;
                else
                    entryText = entryText.substring(0, len - 1) + op;
            } else if (lastOp.equals("x") || lastOp.charAt(0) == '\u00f7') {
                if (op.equals("-"))
                    entryText += op;
                else
                    entryText = entryText.substring(0, len - 1) + op;
            } else        // no operator after number
                entryText += op;
        }

        entry.setText(entryText);
    }

    //  Method to handle decimal button Click
    public void decimalClick(TextView entry) {
        if (lastBtnHit == R.id.decimalbtn)
            entryText = "0";
        if (!hasDecimal()) {
            entryText += ".";
            entry.setText(entryText);
        }
    }

    //  Method to handle click of a digit
    public void digitClick(String dig, TextView entry) {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (hasDecimal()) {
            entryText += dig;
        } else {
            int idx = entryText.length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (isOperator(lastchar))
                    break;
                else
                    idx--;
            }
            if (idx >= 0) {
                String snumber = entryText.substring(idx + 1, entryText.length());
                if (snumber.length() == 1 && snumber.equals("0")) {
                    entryText = entryText.substring(0, entryText.length() - 1) + dig;
                } else
                    entryText += dig;
            } else {
                if (entryText.length() == 1 && entryText.equals("0"))
                    entryText = dig;
                else
                    entryText += dig;
            }
        }
        entry.setText(entryText);
    }

    //  Method to handle click of zero button
    public void zeroClick(TextView entry) {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (hasDecimal()) {
            entryText += "0";
            entry.setText(entryText);
        } else {
            int idx = entryText.length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (isOperator(lastchar))
                    break;
                else
                    idx--;
            }
            if (idx >= 0) {
                String snumber = entryText.substring(idx + 1, entryText.length());
                if (snumber.length() == 1 && snumber.equals("0")) {
                    return;
                } else {
                    entryText += "0";
                    entry.setText(entryText);
                }
            } else {
                if (entryText.length() == 1 && entryText.equals("0"))
                    return;
                entryText += "0";
                entry.setText(entryText);
            }
        }
    }

    //  Method to handle click of left paranthesis
    public void leftParanClick(TextView entry) {
        entryText += "(";
        entry.setText(entryText);
    }

    //  Method to handle click of right paranthesis
    public void rightParanClick(TextView entry) {
        int leftparan = 0, len;
        len = entryText.length();
        for (int i = 0; i < len; i++) {
            if (entryText.substring(i, i + 1).equals("("))
                leftparan++;
            else if (entryText.substring(i, i + 1).equals(")"))
                leftparan--;
        }

        if (leftparan > 0 && !entryText.substring(len - 1, len).equals("(")) {
            entryText += ")";
            entry.setText(entryText);
        }
    }

    //  Method to handle clicks of button
    public void btnClick(View view) {
        Button btn = (Button) view;
        TextView entry = (TextView) findViewById(R.id.entry);
        String btnText = btn.getText().toString();
        int btnId = btn.getId();

        switch (btnId) {
            case R.id.clearbtn:
                performClear(entry);
                break;
            case R.id.delbtn:
                performDelete(entry);
                break;
            case R.id.plusbtn:
                opClick(btnText, entry);
                break;
            case R.id.minusbtn:
                opClick(btnText, entry);
                break;
            case R.id.multiplybtn:
                opClick(btnText, entry);
                break;
            case R.id.dividebtn:
                opClick(btnText, entry);
                break;
            case R.id.decimalbtn:
                decimalClick(entry);
                break;
            case R.id.left_paran:
                leftParanClick(entry);
                break;
            case R.id.right_paran:
                rightParanClick(entry);
                break;
            case R.id.equalbtn:
                performCal(entry);
                break;
            case R.id.zerobtn:
                zeroClick(entry);
                break;
            default:
                digitClick(btnText, entry);
        }
        lastBtnHit = btnId;
    }

}
