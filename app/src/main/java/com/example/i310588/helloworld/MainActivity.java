package com.example.i310588.helloworld;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    private int lastBtnHit = -1;
    TextView entry;
    Utility utility;
    ExpressionHandler exprhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BasicPad basicPad = new BasicPad();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_app, basicPad);
        transaction.commit();

        entry = (TextView) findViewById(R.id.entry);
        utility = new Utility(this);
        exprhandler = new ExpressionHandler();

//        TODO enable long click on delete button
//      Setting delete button to respond to long click
//        Button delbtn = (Button) findViewById(R.id.delbtn);
//        final TextView entry = (TextView) findViewById(R.id.entry);
//        delbtn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                performClear();
//                return true;
//            }
//        });
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

    // TODO remove these setEntry and getEntry text function before release.
    //    Method to facilitate testing not to be used in actual app
    public void setEntryText(String text) {
        entryText = text;
    }
    //    Method to facilitate testing not to be used in actual app
    public String getEntryText() {
        return entryText;
    }


    //  Method to perform calculation
    public void performCal() {

        String expr;
        expr = exprhandler.formatExpr(entryText);
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
                    utility.setDisplayText(Character.toString('\u221e'));
//                    entry.setText(Character.toString('\u221e'));
                }
                else
                {
                    entryText = "";
                    utility.setDisplayText("-" + Character.toString('\u221e'));
//                    entry.setText("-" + Character.toString('\u221e'));
                }
            }
            else if(Double.isNaN(res))
            {
                utility.setDisplayText(Double.toString(res));
//                entry.setText(Double.toString(res));
                entryText = "";
            }
            else if (utility.isDouble(res)) {
                entryText = Double.toString(res);
                utility.setDisplayText(entryText);
//                entry.setText(entryText);
            }
            else {
                entryText = Long.toString((long) res);
                utility.setDisplayText(entryText);
//                entry.setText(entryText);
            }
        } catch (EvaluationException ee) {
            entryText = "";
            utility.setDisplayText(entryText);
//            entry.setText(entryText);
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


    //  Method to perform short press of delete button
    public void performDelete() {
        int len = entryText.length();
        if (len > 0) {
            entryText = entryText.substring(0, len - 1);
        }
        utility.setDisplayText(entryText);
//        entry.setText(entryText);
    }

    //  Method to handle clear function
    public void performClear() {
        entryText = "";
        utility.setDisplayText(entryText);
//        entry.setText(entryText);
    }

    //  Method to handle click of operator button
    public void opClick(String op) {
        boolean hasNum = false;

//        checking whether entryText contains any number or not
        int len = entryText.length();

//        if operator is same as last char then return
        if (len > 0 && entryText.substring(len - 1, len).equals(op))
            return;

        for (int i = len - 1; i >= 0; i--) {
            if (!utility.isOperator(entryText.substring(i, i + 1))) {
                hasNum = true;
                break;
            }
        }

//        if no number is present
        if (!hasNum) {
            if (op.equals("x") || op.charAt(0) == '\u00f7')
                entryText = "";
            else if (op.equals("+") && entryText.equals(""))
                entryText = "";
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
                if (utility.isOperator(entryText.substring(len - 2, len - 1)))
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

        utility.setDisplayText(entryText);
//        entry.setText(entryText);
    }

    //  Method to handle decimal button Click
    public void decimalClick() {
//        if (lastBtnHit == R.id.decimalbtn)
//            entryText = "0";
        if (!utility.hasDecimal(entryText)) {
            entryText += ".";
            utility.setDisplayText(entryText);
//            entry.setText(entryText);
        }
    }

    //  Method to handle click of a digit
    public void digitClick(String dig) {
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
        }
        if (utility.hasDecimal(entryText)) {
            entryText += dig;
        } else {
            int idx = entryText.length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (utility.isOperator(lastchar)) {
                    break;
                }
                else {
                    idx--;
                }
            }
            if (idx >= 0) {
                String snumber = entryText.substring(idx + 1, entryText.length());
                if (snumber.length() == 1 && snumber.equals("0")) {
                    entryText = entryText.substring(0, entryText.length() - 1) + dig;
                } else {
                    entryText += dig;
                }
            } else {
                if (entryText.length() == 1 && entryText.equals("0"))
                    entryText = dig;
                else
                    entryText += dig;
            }
        }
        utility.setDisplayText(entryText);
//        entry.setText(entryText);
    }

    //  Method to handle click of zero button
    public void zeroClick() {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (utility.hasDecimal(entryText)) {
            entryText += "0";
            utility.setDisplayText(entryText);
//            entry.setText(entryText);
        } else {
            int idx = entryText.length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (utility.isOperator(lastchar))
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
                    utility.setDisplayText(entryText);
//                    entry.setText(entryText);
                }
            } else {
                if (entryText.length() == 1 && entryText.equals("0"))
                    return;
                entryText += "0";
                utility.setDisplayText(entryText);
//                entry.setText(entryText);
            }
        }
    }

    //  Method to handle click of left paranthesis
    public void leftParanClick() {
        entryText += "(";
//        entry.setText(entryText);
        utility.setDisplayText(entryText);
    }

    //  Method to handle click of right paranthesis
    public void rightParanClick() {
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
            utility.setDisplayText(entryText);
//            entry.setText(entryText);
        }
    }



    //  Method to handle clicks of button
    public void btnClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        int btnId = btn.getId();
        switch (btnId) {
            case R.id.clearbtn:
                performClear();
                break;
            case R.id.delbtn:
                performDelete();
                break;
            case R.id.plusbtn:
                opClick(btnText);
                break;
            case R.id.minusbtn:
                opClick(btnText);
                break;
            case R.id.multiplybtn:
                opClick(btnText);
                break;
            case R.id.dividebtn:
                opClick(btnText);
                break;
            case R.id.decimalbtn:
                decimalClick();
                break;
            case R.id.left_paran:
                leftParanClick();
                break;
            case R.id.right_paran:
                rightParanClick();
                break;
            case R.id.equalbtn:
                performCal();
                break;
            case R.id.zerobtn:
                zeroClick();
                break;
            default:
                digitClick(btnText);
        }
        lastBtnHit = btnId;
    }

}
