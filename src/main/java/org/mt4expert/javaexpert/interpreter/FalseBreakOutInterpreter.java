package org.mt4expert.javaexpert.interpreter;

import org.mt4expert.javaexpert.Commander;
import org.mt4expert.javaexpert.data.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class FalseBreakOutInterpreter {

    Support supports;
    Resistance resistances;

    public FalseBreakOutInterpreter(Support supports, Resistance resistances) {
        this.supports = supports;
        this.resistances = resistances;
    }

    public FalseBreakoutData checkForBreakOut(CandleData candleData, Map<Date, Double> resistanceMap, Map<Date, Double> supportMap) {
        List<Candle> candleList = candleData.getCandles();
        List<Candle> resistanceCandleList = resistances.getResistanceCandlesList();
        List<Candle> supportCandlesList = supports.getSupportCandlesList();

        for (Candle candle : resistanceCandleList) {
            if (candleList.get(0).getHigh() > candle.getHigh()) {
                candle.setBreakoutType(BreakoutType.RESISTANCE);
                System.out.println(Commander.showResistanceBreakOut() + candle.getSymbol()
                        + " | " + candle.getDateInReadableFormat() + " | " + candle.getHigh());
                return new FalseBreakoutData(candle,resistanceMap,supportMap);
            }
        }

        for (Candle candle : supportCandlesList) {
            if (candleList.get(0).getLow() < candle.getLow()) {
                candle.setBreakoutType(BreakoutType.SUPPORT);
                System.out.println(Commander.showSupportBreakOut() + candle.getSymbol()
                        + " | " + candle.getDateInReadableFormat() + " | " + candle.getLow());
                return new FalseBreakoutData(candle,resistanceMap,supportMap);
            }
        }
        return null;
    }
}
