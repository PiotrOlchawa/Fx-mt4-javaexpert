package org.mt4expert.javaexpert;

import org.apache.log4j.Logger;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;
import org.mt4expert.javaexpert.data.Resistance;
import org.mt4expert.javaexpert.data.Support;

import java.util.List;

public class FalseBreakOutInterpreter {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FalseBreakOutInterpreter.class);
    Support supports;
    Resistance resistances;

    public FalseBreakOutInterpreter(Support supports, Resistance resistances) {
        this.supports = supports;
        this.resistances = resistances;
    }

    void checkForResistanceBreakOut(CandleData candleData) {
        List<Candle> candleList = candleData.getCandles();
        List<Candle> resistanceCandleList = resistances.getResistanceCandlesList();
        List<Candle> supportCandlesList = supports.getSupportCandlesList();
        for (Candle candle : resistanceCandleList) {
            if (candleList.get(0).getHigh() > candle.getHigh()) {
                System.out.println("---------------------------------Resistance BreakOut " + candle.getSymbol() + " !! at " + candle.getDate());
            }
        }
        for (Candle candle : supportCandlesList) {
            if (candleList.get(0).getLow() < candle.getLow()) {
                System.out.println("-------------------------------------Support BreakOut " + candle.getSymbol() + " !! at " + candle.getDate());
            }
        }
    }
}
