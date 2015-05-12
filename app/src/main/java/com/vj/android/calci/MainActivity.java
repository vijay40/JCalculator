package com.vj.android.calci;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.javia.arity.Symbols;

import java.util.ArrayList;

import adapter.TabView;


public class MainActivity extends FragmentActivity {

    public static String entryText = "";
    public static int lastBtnHit = -1;
    public static int max_history;
    public static ArrayList<HistoryRow> history;
    private final int REQUEST_EXIT = 1;
    private DisplayPad entry;
    private Utility utility;
    private SharedPreferences pref;
    private double prec = 1000000000.0;
    private ExpressionHandler exprhandler;
    private TabView tabViewAdapter;
    private ActionBar actionBar;
    private ViewPager viewPager;
    private String[] modes;
    private ImageButton deleteButton;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Global.theme = Integer.parseInt(pref.getString("theme", "0"));
        LookHandler.onActivityCreatedSetTheme(this, Global.theme);

        setContentView(R.layout.activity_main);

//        Initialization
        entry = (DisplayPad) findViewById(R.id.entry);
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

//        default calculation mode
        if (savedInstanceState != null)
            Global.mode = savedInstanceState.getInt("mode");
        else
            Global.mode = 10;

        history = new ArrayList<HistoryRow>();
        max_history = pref.getInt("max_history", 25);

        deleteButton = (ImageButton) findViewById(R.id.delbtn);
        if (deleteButton != null) {
            deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    performClear();
                    return true;
                }
            });
        }

//        setting font of display pad
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DejaVuSansMono-Bold.ttf");
        entry.setTypeface(tf);

//        register for context menu
        registerForContextMenu(entry);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LookHandler.setTheme(this);

//     reading result from file
        utility.historyRead();

        utility.setDisplayText(entryText, entryText.length());
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
        } else if (id == R.id.history) {
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
            this.recreate();
//            this.finish();
//            this.startActivity(new Intent(this, this.getClass()));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        position = entry.getSelectionStart();
        outState.putString("entryText", entryText);
        outState.putInt("lastBtn", lastBtnHit);
        outState.putInt("mode", Global.mode);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Global.mode = savedInstanceState.getInt("mode");

        position = savedInstanceState.getInt("position");
        entryText = savedInstanceState.getString("entryText");
        lastBtnHit = savedInstanceState.getInt("lastBtn");
        DisplayPad entry = (DisplayPad) findViewById(R.id.entry);
        entry.setText(entryText);
        entry.setSelection(position);
    }

    //    Method to facilitate testing not to be used in actual app
    public String getEntryText() {
        return entryText;
    }

    // TODO remove these setEntry and getEntry text function before release.
    //    Method to facilitate testing not to be used in actual app
    public void setEntryText(String text) {
        entryText = text;
    }

    //  Method to perform calculation
    //  expression is always in decimal here
    private double evaluate(String expression) {

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
            position = 0;
            utility.setDisplayText(entryText, 0);
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
    private void DisplayResult(String expression) {
        double res;
        if (Global.mode == 16) {
            expression = Utility.convertToRadix(expression, 16, 10);
        } else if (Global.mode == 8) {
            expression = Utility.convertToRadix(expression, 8, 10);
        } else if (Global.mode == 2) {
            expression = Utility.convertToRadix(expression, 2, 10);
        }

        res = evaluate(expression);

        if (Double.isInfinite(res)) {
            if (res > 0) {
                entryText = "";
                position = 0;
                utility.setDisplayText(Character.toString('\u221e'), 1);
                return;
            } else {
                entryText = "";
                position = 0;
                utility.setDisplayText("-" + Character.toString('\u221e'), 2);
                return;
            }
        } else if (Double.isNaN(res)) {
            utility.setDisplayText(Double.toString(res), 3);
            entryText = "";
            position = 0;
            return;
        } else if (Global.mode == 10 && utility.isDouble(res)) {
            res = Math.round(res * prec) / prec;
            entryText = Double.toString(res);
            position = entryText.length();
            History.addHistoryEntry(expression, this);
        } else if (Global.mode == 10) {
            entryText = Long.toString((long) res);
            position = entryText.length();
            History.addHistoryEntry(expression, this);
        } else if (Global.mode == 16) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 16);
            position = entryText.length();
            History.addHistoryEntry(expression, this);
        } else if (Global.mode == 8) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 8);
            position = entryText.length();
            History.addHistoryEntry(expression, this);
        } else if (Global.mode == 2) {
            entryText = Utility.convertToRadix(Double.toString(res), 10, 2);
            position = entryText.length();
            History.addHistoryEntry(expression, this);
        }

        utility.setDisplayText(entryText, entryText.length());
        Animation anim_fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        entry.startAnimation(anim_fadein);
    }

    //  Method to perform short press of delete button
    private void performDelete() {
        int len = position;
        int newposition = 0;
        if (len > 0) {
            if (len <= 2) {
                entryText = entryText.substring(0, len - 1) + entryText.substring(position);
                newposition = len - 1;
            }
            else if (len == 3) {
                newposition = len - utility.numCharDelete(entryText.substring(0, position));
                entryText = entryText.substring(0, newposition) + entryText.substring(position);
            } else {
                int delchar = utility.numCharDelete(entryText.substring(len - 4, position));
                newposition = len - delchar;
                entryText = entryText.substring(0, newposition) + entryText.substring(position);
            }
        }
        utility.setDisplayText(entryText, newposition);
    }

    //  Method to handle clear function
    public void performClear() {
        entryText = "";
        position = 0;
        utility.setDisplayText(entryText, 0);
    }

    //  Method to handle click of operator button
    public void opClick(String op) {
        String tempEntryText = entryText.substring(0, position);
        int newposition = position;

        if (op.equals("-")) {
            if (utility.isLastOperator(tempEntryText)) {
                if (op.equals(tempEntryText.substring(tempEntryText.length() - 1)))
                    return;
                else if ("+".equals(tempEntryText.substring(tempEntryText.length() - 1))) {
                    tempEntryText = tempEntryText.substring(0, tempEntryText.length() - 1) + op;
                }
                else {
                    tempEntryText += op;
                    newposition++;
                }
            } else {
                tempEntryText += op;
                newposition++;
            }
        } else if (op.equals("+")) {
            if (utility.isLastOperator(tempEntryText)) {
                if (op.equals(tempEntryText.substring(tempEntryText.length() - 1)))
                    return;
                else if ("-".equals(tempEntryText.substring(tempEntryText.length() - 1))) {
                    tempEntryText = tempEntryText.substring(0, tempEntryText.length() - 1);
                    newposition--;
                } else
                    tempEntryText = tempEntryText.substring(0, tempEntryText.length() - 1) + op;
            } else if (Utility.isLastOperand(tempEntryText)) {
                tempEntryText += op;
                newposition++;
            }

        } else    // it should be 'x' or '÷'
        {
            if (utility.isLastOperator(tempEntryText)) {
                if (op.equals(tempEntryText.substring(tempEntryText.length() - 1)))
                    return;
                else {
                    if (tempEntryText.length() > 1 && Utility.isLastOperand(tempEntryText.substring(0, tempEntryText.length() - 1)))
                        tempEntryText = tempEntryText.substring(0, tempEntryText.length() - 1) + op;
                    else
                        return;
                }
            } else {
                if (Utility.isLastOperand(tempEntryText)) {
                    tempEntryText += op;
                    newposition++;
                }
                else
                    return;
            }
        }
        entryText = tempEntryText + entryText.substring(position);
        utility.setDisplayText(entryText, newposition);
    }

    //  Method to handle decimal button Click
    private void decimalClick() {
        if (!utility.hasDecimal(entryText, position)) {
            Utility.EditEntryText(".", position);
            utility.setDisplayText(entryText, position + 1);
        }
    }

    //  Method to handle click of a digit
    private void digitClick(String dig) {
        int newposition = 0;
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
            position = 0;
        }
        if (utility.hasDecimal(entryText, position)) {
//            entryText += dig;
            Utility.EditEntryText(dig, position);
            newposition = position + 1;
        } else {
            int idx = entryText.substring(0, position).length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (utility.isOperator(lastchar)) {
                    break;
                } else {
                    idx--;
                }
            }
            if (idx >= 0) {
//                String snumber = entryText.substring(idx + 1, entryText.length());
                String snumber = entryText.substring(idx + 1, position);
                if (snumber.length() == 1 && snumber.equals("0")) {
                    entryText = entryText.substring(0, position - 1) + dig + entryText.substring(position);
                    newposition = position;
                } else {
//                    entryText += dig;
                    Utility.EditEntryText(dig, position);
                    newposition = position + 1;
                }
            } else {
                if (entryText.substring(0, position).length() == 1 && entryText.substring(0, position).equals("0")) {
                    entryText = dig + entryText.substring(position);
                    newposition = position;
                } else {
//                    entryText += dig;
                    Utility.EditEntryText(dig, position);
                    newposition = position + 1;
                }
            }
        }
        utility.setDisplayText(entryText, newposition);
    }

    //  Method to handle click of zero button
    private void zeroClick() {
        int position = entry.getSelectionStart();
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
            position = 0;
        }
        if (utility.hasDecimal(entryText, position)) {
//            entryText += "0";
            Utility.EditEntryText("0", position);
            utility.setDisplayText(entryText, position + 1);
        } else {
            int idx = entryText.substring(0, position).length() - 1;
            while (idx >= 0) {
                String lastchar = entryText.substring(idx, idx + 1);
                if (utility.isOperator(lastchar))
                    break;
                else
                    idx--;
            }
            if (idx >= 0) {
                String snumber = entryText.substring(idx + 1, position);
                if (snumber.length() == 1 && snumber.equals("0")) {
                    return;
                } else {
//                    entryText += "0";
                    Utility.EditEntryText("0", position);
                    utility.setDisplayText(entryText, position + 1);
                }
            } else {
                if (entryText.substring(0, position).length() == 1 && entryText.substring(0, position).equals("0"))
                    return;
//                entryText += "0";
                Utility.EditEntryText("0", position);
                utility.setDisplayText(entryText, position + 1);
            }
        }
    }

    //  Method to handle click of left parenthesis
    private void leftParanClick() {
//        entryText += "(";
        Utility.EditEntryText("(", position);
        utility.setDisplayText(entryText, position + 1);
    }

    //  Method to handle click of right parenthesis
    private void rightParanClick() {
        int leftparan = 0, len;
        len = position;
        for (int i = 0; i < len; i++) {
            if (entryText.substring(i, i + 1).equals("("))
                leftparan++;
            else if (entryText.substring(i, i + 1).equals(")"))
                leftparan--;
        }

        if (leftparan > 0 && !entryText.substring(len - 1, len).equals("(")) {
//            entryText += ")";
            Utility.EditEntryText(")", position);
            utility.setDisplayText(entryText, position + 1);
        }
    }

    private void trignoClick(String function) {
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
            position = 0;
        }
//        entryText += function + "(";
        Utility.EditEntryText(function + "(", position);
        utility.setDisplayText(entryText, position + function.length() + 1);
    }

    private void power() {
        if (Utility.isLastOperand(entryText.substring(0, position))) {
//            entryText += "^";
            Utility.EditEntryText("^", position);
            utility.setDisplayText(entryText, position + 1);
        }
    }

    private void exponential() {
//        entryText += "e";
        Utility.EditEntryText("e", position);
        utility.setDisplayText(entryText, position + 1);
    }

    private void logClick(String log) {
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
            position = 0;
        }
//        entryText += log + "(";
        Utility.EditEntryText(log + "(", position);
        utility.setDisplayText(entryText, position + log.length() + 1);
    }


    private void functionClick(String func) {
        if (entryText.substring(0, position).length() == 0)
            return;
        else if (utility.isOperator(entryText.substring(position - 1, position)))
            return;
//        entryText += func;
        Utility.EditEntryText(func, position);
        utility.setDisplayText(entryText, position + func.length());
    }

    private void sqrtClick() {
//        entryText += "√";
        Utility.EditEntryText("√", position);
        utility.setDisplayText(entryText, position + 1);
    }

    private void cubeRootClick() {
//        entryText += "∛(";
        Utility.EditEntryText("∛(", position);
        utility.setDisplayText(entryText, position + 2);
    }

    private void PIclick() {
//        entryText += "π";
        Utility.EditEntryText("π", position);
        utility.setDisplayText(entryText, position + 1);
    }

    private void hexClick(String btn) {
        if (lastBtnHit == R.id.equalbtn) {
            entryText = "";
            position = 0;
        }
//        entryText += btn;
        Utility.EditEntryText(btn, position);
        utility.setDisplayText(entryText, position + 1);
    }

    private boolean onAdvancePanel() {
        return this.getActionBar().getSelectedNavigationIndex() == 1;
    }

    //  Method to handle clicks of button
    public void btnClick(View view) {
        position = entry.getSelectionStart();
        int btnId = view.getId();

        String btnText;

        if (btnId == R.id.delbtn) {
            btnText = null;
        } else {
            Button btn = (Button) view;
            btnText = btn.getText().toString();
        }

//        if (btnId == R.id.clearbtnhex)
//            performClear();
        if (btnId == R.id.delbtn)
            performDelete();
        else if (btnId == R.id.plusbtn || btnId == R.id.plusbtnhex || btnId == R.id.minusbtn || btnId == R.id.minusbtnhex || btnId == R.id.multiplybtn || btnId == R.id.multiplybtnhex || btnId == R.id.dividebtn || btnId == R.id.dividebtnhex)
            opClick(btnText);
        else if (btnId == R.id.sin || btnId == R.id.cos || btnId == R.id.tan)
            trignoClick(btnText);
        else if (btnId == R.id.hexA || btnId == R.id.hexB || btnId == R.id.hexC || btnId == R.id.hexD || btnId == R.id.hexE || btnId == R.id.hexF)
            hexClick(btnText);
        else if (btnId == R.id.log || btnId == R.id.logn)
            logClick(btnText);
        else if (btnId == R.id.decimalbtn)
            decimalClick();
        else if (btnId == R.id.left_paran)
            leftParanClick();
        else if (btnId == R.id.right_paran)
            rightParanClick();
        else if (btnId == R.id.zerobtn)
            zeroClick();
        else if (btnId == R.id.pi)
            PIclick();
        else if (btnId == R.id.factorial || btnId == R.id.percentage)
            functionClick(btnText);
        else if (btnId == R.id.exponential)
            exponential();
        else if (btnId == R.id.power)
            power();
        else if (btnId == R.id.sqrt)
            sqrtClick();
        else if (btnId == R.id.cube_root)
            cubeRootClick();
        else if (btnId == R.id.equalbtn || btnId == R.id.hexequalbtn) {
            DisplayResult(entryText);
            btnId = R.id.equalbtn;
        } else
            digitClick(btnText);


        lastBtnHit = btnId;

        utility.vibrate();
        if (onAdvancePanel() && utility.returnToBasic()) {
            new ScrollTabListener(this).changeToBasic();
        }
    }

}
