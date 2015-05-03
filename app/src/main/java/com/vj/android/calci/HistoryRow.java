package com.vj.android.calci;

import java.io.Serializable;

class HistoryRow implements Serializable {
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
