package com.vj.android.calci;

import java.io.Serializable;

/**
 * Created by I310588 on 4/24/2015.
 */
public class HistoryRow implements Serializable {
    private String expression;
    private String result;

    public HistoryRow(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }

    public String getExpr() {
        return expression;
    }

    public String getResult() {
        return result;
    }
}
