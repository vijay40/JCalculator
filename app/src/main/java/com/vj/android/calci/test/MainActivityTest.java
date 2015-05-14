package com.vj.android.calci.test;

import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.vj.android.calci.ExpressionHandler;
import com.vj.android.calci.MainActivity;
import com.vj.android.calci.Utility;

import junit.framework.TestCase;

public class MainActivityTest extends TestCase {

    public static void runAssertEqual(String message, String actualOutput, String expectedOutput) {
        message += "\n" + "Expected : " + expectedOutput + "\t";
        message += "Actual : " + actualOutput + "\t\t";
        assertEquals(message, actualOutput, expectedOutput);
    }

    @MediumTest
    public void testFormatExpr() {
        ExpressionHandler m = new ExpressionHandler();
        String message;
        String actualOutput;
        String expectedOutput;

        message = "testing completion of parenthesis";
        actualOutput = m.formatExpr("2+(1");
        expectedOutput = "2+(1)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing change of x & ÷ and completion of parenthesis";
        actualOutput = m.formatExpr("7x(9-4÷3÷");
        expectedOutput = "7*(9-4/3)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing number( changes to number*( and completion of parenthesis";
        actualOutput = m.formatExpr("4+3(5");
        expectedOutput = "4+3*(5)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing trailing open brackets";
        actualOutput = m.formatExpr("4+3(5(-");
        expectedOutput = "4+3*(5)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing N( changes to N*( and )N to )*N";
        actualOutput = m.formatExpr("45(23)34");
        expectedOutput = "45*(23)*34";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing .( changes to .*(";
        actualOutput = m.formatExpr("2.(32.).1");
        expectedOutput = "2.*(32.)*0.1";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "converting . to 0 part 1";
        actualOutput = m.formatExpr(".");
        expectedOutput = "0.";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "converting . to 0 part 2";
        actualOutput = m.formatExpr("23x.");
        expectedOutput = "23*0.";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing sin formatting";
        actualOutput = m.formatExpr("2x3sin(24)32sin(21)+32sin(1)");
        expectedOutput = "2*3*sin(24)*32*sin(21)+32*sin(1)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing sin formatting with multiplication";
        actualOutput = m.formatExpr("89xcos(5)");
        expectedOutput = "89*cos(5)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing basic sin formatting";
        actualOutput = m.formatExpr("sin(3)");
        expectedOutput = "sin(3)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing sin & cos formatting";
        actualOutput = m.formatExpr("2x3sin(24)32cos(21)+32sin(1)");
        expectedOutput = "2*3*sin(24)*32*cos(21)+32*sin(1)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing sin, cos & tan formatting";
        actualOutput = m.formatExpr("2x3sin(24)32cos(21)+32sin(1)-54tan(1)+tan(9)");
        expectedOutput = "2*3*sin(24)*32*cos(21)+32*sin(1)-54*tan(1)+tan(9)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing basic cos formatting";
        actualOutput = m.formatExpr("cos(3)");
        expectedOutput = "cos(3)";
        runAssertEqual(message, actualOutput, expectedOutput);

        message = "testing sin & parenthesis formatting";
        actualOutput = m.formatExpr("2(sin(3");
        expectedOutput = "2*(sin(3))";
        runAssertEqual(message, actualOutput, expectedOutput);

    }

    @SmallTest
    public void testIsOperator() {
        boolean actualOutput;
        Utility m = new Utility();

        actualOutput = m.isOperator("-");
        assertEquals(true, actualOutput);

        actualOutput = m.isOperator("+");
        assertEquals(true, actualOutput);

        actualOutput = m.isOperator("x");
        assertEquals(true, actualOutput);

        actualOutput = m.isOperator(Character.toString('\u00f7'));
        assertEquals(true, actualOutput);

        actualOutput = m.isOperator("/");
        assertEquals(false, actualOutput);

        actualOutput = m.isOperator("*");
        assertEquals(false, actualOutput);

        actualOutput = m.isOperator("5");
        assertEquals(false, actualOutput);
    }

    @SmallTest
    public void testHasDecimal() {
        boolean actualOutput;
        Utility u = new Utility();
        MainActivity m = new MainActivity();
        String entryText;
        int len;

//        m.setEntryText("");
//        entryText = m.getEntryText();
//        len = entryText.length();
//        actualOutput = u.hasDecimal(entryText, len);
//        assertEquals(false, actualOutput);
//
//        m.setEntryText("2.0");
//        entryText = m.getEntryText();
//        len = entryText.length();
//        actualOutput = u.hasDecimal(entryText, len);
//        assertEquals(true, actualOutput);
//
//        m.setEntryText("235/.3");
//        entryText = m.getEntryText();
//        len = entryText.length();
//        actualOutput = u.hasDecimal(entryText, len);
//        assertEquals(true, actualOutput);
//
//        m.setEntryText("321x324+789");
//        entryText = m.getEntryText();
//        len = entryText.length();
//        actualOutput = u.hasDecimal(entryText, len);
//        assertEquals(false, actualOutput);

    }

}
