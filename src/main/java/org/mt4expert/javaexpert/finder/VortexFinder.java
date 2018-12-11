package org.mt4expert.javaexpert.finder;

import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VortexFinder {

    CandleData candleData;

    public VortexFinder(CandleData candleData) {
        this.candleData = candleData;
    }

    public Map<Candle, String> findVortexes() {
        List<Candle> candleList = candleData.getCandles();
        Map<Candle, String> vortexCandleMap = new HashMap<>();

        for (int i = 2; i < ((candleList.size() > ExpertConfigurator.CANDLE_LIST_SIZE_LIMIT) ? ExpertConfigurator.CANDLE_LIST_SIZE_LIMIT : candleList.size()) - 2; i++) {

            List<Candle> subCandles = candleList.subList(i - 2, i + 3);

            if (checkForHigh(subCandles)) {
                Candle candle = subCandles.get(2);
                boolean trueVortex = true;
                for (int j = i - 1; j >= ExpertConfigurator.HOW_MANY_CANDLES_CREATES_FB; j--) {
                    // jesli high swiecy jest powyzej high candle
                    if (candleList.get(j).getHigh() > candle.getHigh()) {
                        trueVortex = false;
                        break;
                    }
                }
                if (trueVortex) {
                    candleList.get(i).setIndex(i);
                    vortexCandleMap.put(candleList.get(i), ExpertConfigurator.VORTEX_HIGH);
                }
            }

            if (checkForLow(subCandles)) {
                Candle candle = subCandles.get(2);
                boolean trueVortex = true;
                for (int j = i - 1; j >= ExpertConfigurator.HOW_MANY_CANDLES_CREATES_FB; j--) {
                    // jesli low swiecy jest ponizej low candle
                    if (candleList.get(j).getLow() < candle.getLow()) {
                        trueVortex = false;
                        break;
                    }
                }
                if (trueVortex) {
                    candleList.get(i).setIndex(i);
                    vortexCandleMap.put(candleList.get(i), ExpertConfigurator.VORTEX_LOW);
                }
            }
        }
        return vortexCandleMap;
    }

/*    private Double returnForHighCandleOpenOrCloseDependsOnCloseDirection(Candle candle) {
        if (candle.getClose() - candle.getOpen() >= 0) {
            return candle.getClose();
        }
        return candle.getOpen();
    }

    private Double returnForLowCandleOpenOrCloseDependsOnCloseDirection(Candle candle) {
        if (candle.getClose() - candle.getOpen() >= 0) {
            return candle.getOpen();
        }
        return candle.getClose();
    }*/

    // sprawdza czy swieca jest high
    private boolean checkForHigh(List<Candle> subCandles) {
        double highCentral = subCandles.get(2).getHigh();
        double lowCentral = subCandles.get(2).getLow();
        double highForward = subCandles.get(3).getHigh();
        double lowForward = subCandles.get(3).getLow();
        double highForward1 = subCandles.get(4).getHigh();
        double lowForward1 = subCandles.get(4).getLow();
        double highBackward = subCandles.get(1).getHigh();
        double lowBackward = subCandles.get(1).getLow();
        double highBackward1 = subCandles.get(0).getHigh();
        double lowBackward1 = subCandles.get(0).getLow();

        if (ExpertConfigurator.VORTEX_DEEP == 2) {
            return highCentral > highForward && highCentral > highForward1
                    && highCentral > highBackward && highCentral > highBackward1
                    && (lowForward < lowCentral || lowForward1 < lowCentral)
                    && (lowBackward < lowCentral || lowBackward1 < lowCentral);
        }
        if (ExpertConfigurator.VORTEX_DEEP == 1) {
            return highCentral > highForward
                    && highCentral > highBackward
                    && lowBackward < lowCentral
                    && lowForward < lowCentral;
        }
        return false;
    }

    //sprawdza czy swieca jest low
    private boolean checkForLow(List<Candle> subCandles) {
        double lowCentral = subCandles.get(2).getLow();
        double highCentral = subCandles.get(2).getLow();
        double lowForward = subCandles.get(3).getLow();
        double lowForward1 = subCandles.get(4).getLow();
        double highForward = subCandles.get(3).getHigh();
        double highForward1 = subCandles.get(4).getHigh();
        double lowBackward = subCandles.get(1).getLow();
        double lowBackward1 = subCandles.get(0).getLow();
        double highBackward = subCandles.get(1).getHigh();
        double highBackward1 = subCandles.get(0).getHigh();

        if (ExpertConfigurator.VORTEX_DEEP == 2) {
            return lowCentral < lowForward && lowCentral < lowForward1
                    && lowCentral < lowBackward && lowCentral < lowBackward1
                    && (highForward > highCentral || highForward1 > highCentral)
                    && (highBackward > highCentral || highBackward1 > highCentral);
        }
        if (ExpertConfigurator.VORTEX_DEEP == 1) {
            return lowCentral < lowForward
                    && lowCentral < lowBackward
                    && highForward > highCentral
                    && highBackward > highCentral;
        }
        return false;
    }
}
