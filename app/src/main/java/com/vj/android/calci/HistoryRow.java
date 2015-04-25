package com.vj.android.calci;

import java.io.Serializable;

/**
 * Created by I310588 on 4/24/2015.
 */
public class HistoryRow implements Serializable {
    String expression;
    String result;

    public HistoryRow(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }
}
