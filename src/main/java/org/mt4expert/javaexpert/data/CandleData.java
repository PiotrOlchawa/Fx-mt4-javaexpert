package org.mt4expert.javaexpert.data;

import java.util.List;

public class CandleData {

    private List<Candle> candles;

    public CandleData(List<Candle> candles) {
        this.candles = candles;
    }

    public List<Candle> getCandles() {
        return candles;
    }
}
