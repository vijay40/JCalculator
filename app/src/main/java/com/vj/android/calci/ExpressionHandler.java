package com.vj.android.calci;


/**
 * class for handling expression formatting.
 */
public class ExpressionHandler {

    private Utility utility;

    public ExpressionHandler() {
        utility = new Utility();
    }

    //  handle sin formatting
    private String handleTrig(String expr, String func) {
        int lastindex = 0, previndex;
        String lastchar;
        while (lastindex != -1) {
            lastindex = expr.indexOf(func, lastindex);
            if (lastindex != -1) {
                previndex = lastindex - 1;
                if (previndex >= 0) {
                    lastchar = expr.substring(previndex, lastindex);
                    if (!utility.isOperator(lastchar) && !"(".contains(lastchar)) {
                        expr = expr.substring(0, lastindex) + "*" + expr.substring(lastindex);
                    }
                }
                lastindex += 3;
            }
        }
        return expr;
    }

    private int handleTrailing(String expr) {
        int len = expr.length();

//        handle trailing operators and open brackets
        while (len > 0 && (utility.isOperator(expr.substring(len - 1, len)) || "(^√".contains(expr.substring(len - 1, len))))
            len--;

        return len;
    }

    private String handleExponential(String expr) {
        for (int i = 0; i < expr.length(); i++) {
            if (Utility.isLastExpo(expr.substring(0, i + 1)) && Utility.isLastOperand(expr.substring(0, i))) {
                expr = expr.substring(0, i) + "*" + expr.substring(i);
            }
        }

        return expr;
    }


    //  Method to format expression for calculation
    public String formatExpr(String expr) {
        if (expr.isEmpty())
            return "";

        String current, last, next;
        String res = "";
        int len = handleTrailing(expr);

//      handle invalid number of parenthesis
        int leftpara = 0;

        for (int i = 0; i < len; i++) {
            current = expr.substring(i, i + 1);
            if (current.equals("x"))
                res += "*";
            else if (current.charAt(0) == '\u00f7')
                res += "/";
            else if (current.charAt(0) == '\u221B')
                res += "cbrt";
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
                if (!utility.isOperator(last) && "1234567890.)".contains(last))
                    res = res.substring(0, i) + "*" + res.substring(i, res.length());
            } else if (current.equals(")") && i < res.length() - 1) {
                next = res.substring(i + 1, i + 2);
                if (!utility.isOperator(next) && "1234567890.".contains(next))
                    res = res.substring(0, i + 1) + "*" + res.substring(i + 1, res.length());
            }
        }

//        handles conditions like . to 0.
        for (int i = 0; i < res.length(); i++) {
            current = res.substring(i, i + 1);
            if (i == 0 && current.equals(".")) {
                res = "0" + res;
            } else if (current.equals(".") && !("0123456789".contains(res.substring(i - 1, i)))) {
                res = res.substring(0, i) + "0" + res.substring(i, res.length());
            }
        }
        res = handleTrig(res, "sin");
        res = handleTrig(res, "cos");
        res = handleTrig(res, "tan");

        res = handleExponential(res);

        return res;
    }

    public String handleDegreeCalc(String expr) {
        expr = expr.replaceAll("sin", "sind");
        expr = expr.replaceAll("cos", "cosd");
        expr = expr.replaceAll("tan", "tand");
        return expr;
    }

}
