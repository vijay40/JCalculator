package com.example.i310588.helloworld.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.MediumTest;

import com.example.i310588.helloworld.MainActivity;

/**
 * Created by I310588 on 4/19/2015.
 */
public class MainActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public MainActivityInstrumentationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @MediumTest
    @UiThreadTest
    public void testOpClick() {
        String message;
        String actualOutput;
        String expectedOutput;

        String [][] testEntry = {
            // Message, Initial Entry test, Op click, Actual O/p
            {"Testing + click on empty entryText", "", "+", ""},
            {"Testing - click on empty entryText", "", "-", "-"},
            {"Testing x click on empty entryText", "", "x", ""},
            {"Testing ÷ click on empty entryText", "", "÷", ""},
            {"Testing ÷ click on - entryText", "-", "÷", "-"},
            {"Testing + click on - entryText", "-", "+", ""},
            {"Testing x click on - entryText", "-", "x", "-"},
            {"Testing - click on - entryText", "-", "-", "-"},
            {"Testing - click after (", "sin(", "-", "sin(-"},
            {"Testing x click after (", "23sin(", "x", "23sin("},
            {"Testing ÷ click after -", "34cos(-", "÷", "34cos(-"},
            {"Testing - click after function", "24e", "-", "24e-"},
            {"Testing ÷ click after function", "35e", "÷", "35e"},
            {"Testing + click after x", "37+sin(23x", "+", "37+sin(23+"},
            {"Testing + click after -", "48-", "+", "48"},
            {"Testing + click after )", "(3+5)", "+", "(3+5)+"}
        };

        for(int testno=0; testno<testEntry.length; testno++)
        {
            message = testEntry[testno][0];
            activity.setEntryText(testEntry[testno][1]);
            activity.opClick(testEntry[testno][2]);
            actualOutput = activity.getEntryText();
            expectedOutput = testEntry[testno][3];
            MainActivityTest.runAssertEqual(message,actualOutput,expectedOutput);
        }
    }

}
