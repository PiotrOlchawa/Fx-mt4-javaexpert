package org.mt4expert.javaexpert.interpreter;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.mt4expert.javaexpert.Commander;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;
import org.mt4expert.javaexpert.data.Resistance;
import org.mt4expert.javaexpert.data.Support;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j
public class FalseBreakOutInterpreter {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FalseBreakOutInterpreter.class);
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
                System.out.println(Commander.showResistanceBreakOut() + candle.getSymbol() + " !! at " + candle.getDate());
                return new FalseBreakoutData(candle,resistanceMap,supportMap);
            }
        }

        for (Candle candle : supportCandlesList) {
            if (candleList.get(0).getLow() < candle.getLow()) {
                System.out.println(Commander.showSupportBreakOut() + candle.getSymbol() + " !! at " + candle.getDate());
                return new FalseBreakoutData(candle,resistanceMap,supportMap);
            }
        }
        return null;
    }
}
