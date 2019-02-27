package pl.somehost.javaexpert.interpreter;

import pl.somehost.javaexpert.Commander;
import pl.somehost.javaexpert.data.*;

import java.util.List;
import java.util.Set;


public class FalseBreakOutInterpreter {

    Support supports;
    Resistance resistances;

    public FalseBreakOutInterpreter(Support supports, Resistance resistances) {
        this.supports = supports;
        this.resistances = resistances;
    }

    public FalseBreakoutData checkForBreakOut(CandleData candleData, Set<Candle> resistanceSet, Set<Candle> supportSet) {
        List<Candle> candleList = candleData.getCandles();
        List<Candle> resistanceCandleList = resistances.getResistanceCandlesList();
        List<Candle> supportCandlesList = supports.getSupportCandlesList();

        for (Candle candle : resistanceCandleList) {
            if (candleList.get(0).getHigh() > candle.getHigh()) {
                candle.setBreakoutType(BreakoutType.RESISTANCE);
                System.out.println(Commander.showResistanceBreakOut() + candle.getSymbol()
                        + " | " + candle.getDateInReadableFormat() + " | " + candle.getHigh());
                return new FalseBreakoutData(candle,resistanceSet,supportSet);
            }
        }

        for (Candle candle : supportCandlesList) {
            if (candleList.get(0).getLow() < candle.getLow()) {
                candle.setBreakoutType(BreakoutType.SUPPORT);
                System.out.println(Commander.showSupportBreakOut() + candle.getSymbol()
                        + " | " + candle.getDateInReadableFormat() + " | " + candle.getLow());
                return new FalseBreakoutData(candle,resistanceSet,supportSet);
            }
        }
        return null;
    }
}
