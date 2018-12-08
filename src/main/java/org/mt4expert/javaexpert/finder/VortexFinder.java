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
                Candle candle = candleList.get(i);
                boolean trueVortex = true;
                for (int j = i - 1; j >= ExpertConfigurator.HOW_MANY_CANDLES_CREATES_FB; j--) {
                    // jesli swieca zamyka sie wyzej high candle - high jest odrzucane
                    if (candleList.get(j).getClose() > candle.getHigh()) trueVortex = false;
                    // jesli zamkniecie jest wyzej zamkniecia candle oraz high jest wyzsze niz niz high candle - high jest odrzucane
                    if (candleList.get(j).getClose() > candle.getClose()
                            && candleList.get(j).getHigh() > candle.getHigh()) trueVortex = false;
                }
                if (trueVortex) {
                    candleList.get(i).setIndex(i);
                    vortexCandleMap.put(candleList.get(i), ExpertConfigurator.VORTEX_HIGH);
                }
            }

            if (checkForLow(subCandles)) {
                Candle candle = candleList.get(i);
                boolean trueVortex = true;
                for (int j = i - 1; j >= ExpertConfigurator.HOW_MANY_CANDLES_CREATES_FB; j--) {
                    if (candleList.get(j).getClose() < candle.getLow()) trueVortex = false;
                    if (candleList.get(j).getClose() < candle.getClose()
                            && candleList.get(j).getLow() > candle.getLow()) trueVortex = false;
                }
                if (trueVortex) {
                    candleList.get(i).setIndex(i);
                    vortexCandleMap.put(candleList.get(i), ExpertConfigurator.VORTEX_LOW);
                }
            }
        }
        return vortexCandleMap;
    }

    private boolean checkForHigh(List<Candle> subCandles) {
        double highCentral = subCandles.get(2).getHigh();
        double highForward = subCandles.get(3).getHigh();
        double highForward1 = subCandles.get(4).getHigh();
        double highBackward = subCandles.get(1).getHigh();
        double highBackward1 = subCandles.get(0).getHigh();

        return highCentral > highForward && highCentral > highForward1
                && highCentral > highBackward && highCentral > highBackward1;
    }

    private boolean checkForLow(List<Candle> subCandles) {
        double lowCentral = subCandles.get(2).getLow();
        double lowForward = subCandles.get(3).getLow();
        double lowForward1 = subCandles.get(4).getLow();
        double lowBackward = subCandles.get(1).getLow();
        double lowBackward1 = subCandles.get(0).getLow();

        return lowCentral < lowForward && lowCentral < lowForward1
                && lowCentral < lowBackward && lowCentral < lowBackward1;
    }
}
