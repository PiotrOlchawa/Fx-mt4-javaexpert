package org.mt4expert.javaexpert.interpreter;

import java.util.Date;

public class FalseBreakoutCandle {

    private String pair;
    private Date candleDate;

    public FalseBreakoutCandle(String pair, Date candleDate) {
        this.pair = pair;
        this.candleDate = candleDate;
    }

    public Date getCandleDate() {
        return candleDate;
    }

    public void setCandleDate(Date candleDate) {
        this.candleDate = candleDate;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }
}
