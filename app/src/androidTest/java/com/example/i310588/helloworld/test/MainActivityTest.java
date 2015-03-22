package com.example.i310588.helloworld.test;

import android.content.Intent;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.i310588.helloworld.ExpressionHandler;
import com.example.i310588.helloworld.MainActivity;
import com.example.i310588.helloworld.Utility;

import junit.framework.TestCase;


/**
 * Created by I310588 on 2/12/2015.
 */
public class MainActivityTest extends TestCase {

   public void runAssertEqual(String message, String actualOutput, String expectedOutput) {
       message += "\n" +"Expected : " + expectedOutput + "\t";
       message += "Actual : " + actualOutput + "\t\t";
       assertEquals(message, actualOutput, expectedOutput);
   }

   @MediumTest
    public void testFormatExpr() {
       ExpressionHandler m = new ExpressionHandler();
       String message;
       String actualOutput;
       String expectedOutput;

       message = "testing completion of paranthesis";
       actualOutput = m.formatExpr("2+(1");
       expectedOutput = "2+(1)";
       runAssertEqual(message, actualOutput, expectedOutput);

       message = "testing change of x & ÷ and completion of paranthesis";
       actualOutput = m.formatExpr("7x(9-4÷3÷");
       expectedOutput = "7*(9-4/3)";
       runAssertEqual(message, actualOutput, expectedOutput);

       message = "testing number( changes to number*( and completion of paranthesis";
       actualOutput = m.formatExpr("4+3(5");
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

        m.setEntryText("");
        actualOutput = u.hasDecimal(m.getEntryText());
        assertEquals(false, actualOutput);

        m.setEntryText("2.0");
        actualOutput = u.hasDecimal(m.getEntryText());
        assertEquals(true, actualOutput);

        m.setEntryText("235/.3");
        actualOutput = u.hasDecimal(m.getEntryText());
        assertEquals(true, actualOutput);

        m.setEntryText("321x324+789");
        actualOutput = u.hasDecimal(m.getEntryText());
        assertEquals(false, actualOutput);

    }

    @MediumTest
    public void testOpClick() {
        MainActivity m = new MainActivity();
        String message;
        String actualOutput;
        String expectedOutput;

////      Test 1
//        message = "testing + click on empty entryText";
//        m.setEntryText("");
//        m.opClick("+");
//        actualOutput = m.getEntryText();
//        expectedOutput = "";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 2
//        message = "testing - click on empty entryText";
//        m.setEntryText("");
//        m.opClick("-");
//        actualOutput = m.getEntryText();
//        expectedOutput = "-";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 3
//        message = "testing x click on empty entryText";
//        m.setEntryText("");
//        m.opClick("x");
//        actualOutput = m.getEntryText();
//        expectedOutput = "";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 4
//        message = "testing ÷ click on empty entryText";
//        m.setEntryText("");
//        m.opClick("÷");
//        actualOutput = m.getEntryText();
//        expectedOutput = "";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 5
//        message = "testing ÷ click on - entryText";
//        m.setEntryText("-");
//        m.opClick("÷");
//        actualOutput = m.getEntryText();
//        expectedOutput = "-";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 6
//        message = "testing + click on - entryText";
//        m.setEntryText("-");
//        m.opClick("+");
//        actualOutput = m.getEntryText();
//        expectedOutput = "";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 7
//        message = "testing x click on - entryText";
//        m.setEntryText("-");
//        m.opClick("x");
//        actualOutput = m.getEntryText();
//        expectedOutput = "-";
//        runAssertEqual(message,actualOutput,expectedOutput);
//
////      Test 6
//        message = "testing - click on - entryText";
//        m.setEntryText("-");
//        m.opClick("-");
//        actualOutput = m.getEntryText();
//        expectedOutput = "-";
//        runAssertEqual(message,actualOutput,expectedOutput);


    }
}
