package org.mt4expert.javaexpert;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VortexFinder {

    public static final String VORTEX_HIGH = "VORTEX_HIGH";
    public static final String VORTEX_LOW = "VORTEX_LOW";
    public static final int CANDLE_LIST_SIZE_LIMIT = 2000;
    CandleData candleData;

    public VortexFinder(CandleData candleData) {
        this.candleData = candleData;
    }

    Map<Candle, String> findVortexes() {
        List<Candle> candleList = candleData.getCandles();
        Map<Candle, String> vortexCandleMap = new HashMap<>();

            for (int i = 2; i < ((candleList.size() > CANDLE_LIST_SIZE_LIMIT) ? CANDLE_LIST_SIZE_LIMIT : candleList.size()) - 2; i++) {

                List<Candle> subCandles = candleList.subList(i - 2, i + 3);

                //System.out.println("Ilosc swieczek " + candleList.size() + " index " + i);

                if (checkForHigh(subCandles)) {
                    Candle candle = candleList.get(i);
                    boolean trueVortex = true;
                    for (int j = i - 1; j >= 0; j--) {
                        if (candleList.get(j).getClose() > candle.getHigh()) trueVortex = false;
                        if (candleList.get(j).getClose() > candle.getClose()
                                && candleList.get(j).getHigh() > candle.getHigh()) trueVortex = false;
                    }
                    if (trueVortex) {
                        candleList.get(i).setIndex(i);
                        vortexCandleMap.put(candleList.get(i), VORTEX_HIGH);
                    }
                }

                if (checkForLow(subCandles)) {
                    Candle candle = candleList.get(i);
                    boolean trueVortex = true;
                    for (int j = i - 1; j >= 0; j--) {
                        if (candleList.get(j).getClose() < candle.getLow()) trueVortex = false;
                        if (candleList.get(j).getClose() < candle.getClose()
                                && candleList.get(j).getLow() > candle.getLow()) trueVortex = false;
                    }
                    if (trueVortex) {
                        candleList.get(i).setIndex(i);
                        vortexCandleMap.put(candleList.get(i), VORTEX_LOW);
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
