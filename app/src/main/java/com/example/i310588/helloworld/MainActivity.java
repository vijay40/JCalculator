package com.example.i310588.helloworld;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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

import org.javia.arity.Symbols;

import java.util.ArrayList;

import adapter.TabView;


public class MainActivity extends FragmentActivity {

    public static String entryText = "";
    public static int lastBtnHit = -1;
    public static int max_history;
    private double prec = 1000000000.0;
    TextView entry;
    Utility utility;
    private ExpressionHandler exprhandler;
    private TabView tabViewAdapter;
    private ActionBar actionBar;
    private ViewPager viewPager;
    private String[] modes;
    private final int REQUEST_EXIT = 1;
    public static int theme;
    public static int mode;
    public static ArrayList<HistoryRow> history;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = Integer.parseInt(pref.getString("theme", "0"));
        LookHandler.onActivityCreatedSetTheme(this, theme);

        setContentView(R.layout.activity_main);

//        Initialization
        entry = (TextView) findViewById(R.id.entry);
        utility = new Utility(this);
        exprhandler = new ExpressionHandler();
        tabViewAdapter = new TabView(getSupportFragmentManager());
        actionBar = getActionBar();
        viewPager = (ViewPager) findViewById(R.id.pad);
        modes = getResources().getStringArray(R.array.modes);

        viewPager.setAdapter(tabViewAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String mode : modes) {
            actionBar.addTab(actionBar.newTab().setText(mode).setTabListener(new ScrollTabListener(this)));
        }

        viewPager.setOnPageChangeListener(new ViewPageListener(this));

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

//        default calculation mode
        mode = 10;

        history = new ArrayList<HistoryRow>();
        max_history = pref.getInt("max_history", 25);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LookHandler.setTheme(this, theme);

//     reading result from file
        utility.historyRead();

        utility.setDisplayText(entryText);
    }

    @Override
    protected void onStop() {
        super.onStop();

//        write result to file
        utility.historyWrite();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_EXIT);
            return true;
        }
        else if (id == R.id.history)
        {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            this.finish();
            this.startActivity(new Intent(this, this.getClass()));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("entryText", entryText);
        outState.putInt("lastBtn", lastBtnHit);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        entryText = savedInstanceState.getString("entryText");
        lastBtnHit = savedInstanceState.getInt("lastBtn");
        TextView entry = (TextView) findViewById(R.id.entry);
        entry.setText(entryText);

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
    //  expression is always in decimal here
    public double evaluate(String expression) {

        String expr;
        expr = exprhandler.formatExpr(expression);
//        handle empty expression text
        if (expr.isEmpty())
            return 0;
        Symbols symbol = new Symbols();
        try {
            double res = symbol.eval(expr);
            return res;
        } catch (Exception ee) {
            entryText = "";
            utility.setDisplayText(entryText);
            Toast errorToast = new Toast(this);
            errorToast.setDuration(Toast.LENGTH_SHORT);
            LayoutInflater lin = getLayoutInflater();
            View appear = lin.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toastroot));
            errorToast.setView(appear);
            errorToast.show();
            return 0;
        }
    }

    //    method to display the result in the current mode of calculator
    public void DisplayResult(String expression) {
        double res;
        if (mode == 16) {
            expression = Utility.convertToRadix(expression, 16, 10);
        } else if (mode == 8) {
            expression = Utility.convertToRadix(expression, 8, 10);
        } else if (mode == 2) {
            expression = Utility.convertToRadix(expression, 2, 10);
        }

        res = evaluate(expression);

        if (Double.isInfinite(res)) {
            if (res > 0) {
                entryText = "";
                utility.setDisplayText(Character.toString('\u221e'));
                return;
            } else {
                entryText = "";
                utility.setDisplayText("-" + Character.toString('\u221e'));
                return;
            }
        } else if (Double.isNaN(res)) {
            utility.setDisplayText(Double.toString(res));
            entryText = "";
            return;
        } else if (mode == 10 && utility.isDouble(res)) {
            res = Math.round(res * prec) / prec;
            entryText = Double.toString(res);

            History.addHistoryEntry(expression);
        } else if(mode == 10){
            entryText = Long.toString((long) res);

            History.addHistoryEntry(expression);
        } else if(mode == 16) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 16);

            History.addHistoryEntry(expression);
        } else if(mode == 8) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 8);

            History.addHistoryEntry(expression);
        } else if(mode == 2) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 2);

            History.addHistoryEntry(expression);
        }

        utility.setDisplayText(entryText);
        Animation anim_fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        entry.startAnimation(anim_fadein);
    }

    //  Method to perform short press of delete button
    public void performDelete() {
        int len = entryText.length();
        if (len > 0) {
            if (len <= 2)
                entryText = entryText.substring(0, len - 1);
            else if (len == 3) {
                entryText = entryText.substring(0, len - utility.numCharDelete(entryText));
            } else {
                int delchar = utility.numCharDelete(entryText.substring(len - 4));
                entryText = entryText.substring(0, len - delchar);
            }
        }
        utility.setDisplayText(entryText);
    }

    //  Method to handle clear function
    public void performClear() {
        entryText = "";
        utility.setDisplayText(entryText);
    }

    //  Method to handle click of operator button
    public void opClick(String op) {
        if (op.equals("-")) {
            if (utility.isLastOperator(entryText)) {
                if (op.equals(entryText.substring(entryText.length() - 1)))
                    return;
                else if ("+".equals(entryText.substring(entryText.length() - 1)))
                    entryText = entryText.substring(0, entryText.length() - 1) + op;
                else
                    entryText += op;
            } else {
                entryText += op;
            }
        } else if (op.equals("+")) {
            if (utility.isLastOperator(entryText)) {
                if (op.equals(entryText.substring(entryText.length() - 1)))
                    return;
                else if ("-".equals(entryText.substring(entryText.length() - 1))) {
                    entryText = entryText.substring(0, entryText.length() - 1);
                } else
                    entryText = entryText.substring(0, entryText.length() - 1) + op;
            } else if (utility.isLastOperand(entryText)) {
                entryText += op;
            }

        } else    // it should be 'x' or '÷'
        {
            if (utility.isLastOperator(entryText)) {
                if (op.equals(entryText.substring(entryText.length() - 1)))
                    return;
                else {
                    if (entryText.length() > 1 && utility.isLastOperand(entryText.substring(0, entryText.length() - 1)))
                        entryText = entryText.substring(0, entryText.length() - 1) + op;
                    else
                        return;
                }
            } else {
                if (utility.isLastOperand(entryText))
                    entryText += op;
                else
                    return;
            }
        }
        utility.setDisplayText(entryText);
    }

    //  Method to handle decimal button Click
    public void decimalClick() {
        if (!utility.hasDecimal(entryText)) {
            entryText += ".";
            utility.setDisplayText(entryText);
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
                } else {
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
    }

    //  Method to handle click of zero button
    public void zeroClick() {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (utility.hasDecimal(entryText)) {
            entryText += "0";
            utility.setDisplayText(entryText);
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
                }
            } else {
                if (entryText.length() == 1 && entryText.equals("0"))
                    return;
                entryText += "0";
                utility.setDisplayText(entryText);
            }
        }
    }

    //  Method to handle click of left paranthesis
    public void leftParanClick() {
        entryText += "(";
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
        }
    }

    public void trignoClick(String function) {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (utility.isLastExpo(entryText))
            return;
        entryText += function + "(";
        utility.setDisplayText(entryText);
    }

    private void expoClick() {
        if (utility.isLastOperand(entryText)) {
            entryText += "e";
            utility.setDisplayText(entryText);
        }
    }

    public void logClick(String log) {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        if (utility.isLastExpo(entryText))
            return;
        entryText += log + "(";
        utility.setDisplayText(entryText);
    }

    public void PIclick() {
        if (utility.isLastExpo(entryText))
            return;
        entryText += "π";
        utility.setDisplayText(entryText);
    }

    public void percentClick() {
        if (entryText.length() == 0 || utility.isLastExpo(entryText))
            return;
        else if (utility.isOperator(entryText.substring(entryText.length() - 1)))
            return;
        entryText += "%";
        utility.setDisplayText(entryText);
    }

    public void factorialClick() {
        if (entryText.length() == 0 || utility.isLastExpo(entryText))
            return;
        else if (utility.isOperator(entryText.substring(entryText.length() - 1)))
            return;
        entryText += "!";
        utility.setDisplayText(entryText);
    }

    public void sqrtClick() {
        entryText += "√";
        utility.setDisplayText(entryText);
    }

    public void powerClick() {
        if (utility.isLastOperand(entryText)) {
            entryText += "^";
            utility.setDisplayText(entryText);
        }
    }

    public void hexClick(String btn) {
        if (lastBtnHit == R.id.equalbtn)
            entryText = "";
        entryText += btn;
        utility.setDisplayText(entryText);
    }

    public boolean onAdvancePanel() {
        return this.getActionBar().getSelectedNavigationIndex() == 1;
    }

    //  Method to handle clicks of button
    public void btnClick(View view) {
        int btnId = view.getId();

        String btnText;

        if (btnId == R.id.clearbtn || btnId == R.id.delbtn || btnId == R.id.clearbtnadv || btnId == R.id.delbtnadv || btnId == R.id.delbtnhex || btnId == R.id.clearbtnhex) {
            btnText = null;
        } else {
            Button btn = (Button) view;
            btnText = btn.getText().toString();
        }

        switch (btnId) {
            case R.id.clearbtn:
                performClear();
                break;
            case R.id.clearbtnadv:
                performClear();
                break;
            case R.id.clearbtnhex:
                performClear();
                break;
            case R.id.delbtn:
                performDelete();
                break;
            case R.id.delbtnadv:
                performDelete();
                break;
            case R.id.delbtnhex:
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
            case R.id.plusbtnhex:
                opClick(btnText);
                break;
            case R.id.minusbtnhex:
                opClick(btnText);
                break;
            case R.id.multiplybtnhex:
                opClick(btnText);
                break;
            case R.id.dividebtnhex:
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
                DisplayResult(entryText);
                break;
            case R.id.zerobtn:
                zeroClick();
                break;
            case R.id.sin:
                trignoClick("sin");
                break;
            case R.id.cos:
                trignoClick("cos");
                break;
            case R.id.tan:
                trignoClick("tan");
                break;
            case R.id.exponential:
                expoClick();
                break;
            case R.id.logn:
                logClick("ln");
                break;
            case R.id.log:
                logClick("log");
                break;
            case R.id.pi:
                PIclick();
                break;
            case R.id.percentage:
                percentClick();
                break;
            case R.id.factorial:
                factorialClick();
                break;
            case R.id.sqrt:
                sqrtClick();
                break;
            case R.id.power:
                powerClick();
                break;
            case R.id.hexA:
                hexClick("A");
                break;
            case R.id.hexB:
                hexClick("B");
                break;
            case R.id.hexC:
                hexClick("C");
                break;
            case R.id.hexD:
                hexClick("D");
                break;
            case R.id.hexE:
                hexClick("E");
                break;
            case R.id.hexF:
                hexClick("F");
                break;
            case R.id.hexequalbtn:
                DisplayResult(entryText);
                btnId = R.id.equalbtn;
                break;
            default:
                digitClick(btnText);
        }
        lastBtnHit = btnId;

        utility.vibrate();
        if (onAdvancePanel() && utility.returnToBasic()) {
            new ScrollTabListener(this).changeToBasic();
        }
    }

}
