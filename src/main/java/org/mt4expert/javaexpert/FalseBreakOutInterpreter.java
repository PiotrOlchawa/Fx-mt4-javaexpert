package org.mt4expert.javaexpert;

import java.util.List;

public class FalseBreakOutInterpreter {

    CandleData candleData;
    Support supports;
    Resistance resistances;

    public FalseBreakOutInterpreter(CandleData candleData, Support supports, Resistance resistances) {
        this.candleData = candleData;
        this.supports = supports;
        this.resistances = resistances;
    }

    void checkForResistanceBreakOut() {
    //first option - should check for last three candles breakout
        List<Candle> candleList = candleData.getCandles();
        List<Candle> resistanceCandleList = resistances.getResistanceCandlesList();
        for(Candle candle: resistanceCandleList){
            if (candleList.get(candleList.size()-1).getHigh()>candle.getHigh()){
                System.out.println("Resistance BreakOut !! at " + candle.getDate());
            }
        }

    }


}
