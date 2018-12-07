package org.mt4expert.javaexpert;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupportResistanceFinder {

    private Support supports = new Support();
    private Resistance resistances = new Resistance();
    Map<Candle, String> candleVortexMap;

    public SupportResistanceFinder(Map<Candle, String> candleVortexMap) {
        this.candleVortexMap = candleVortexMap;
    }

    public Support getSupports() {
        return supports;
    }

    public Resistance getResistances() {
        return resistances;
    }


    public void findSupportsAndResistances() {
        findSupports();
        findResistances();
    }

    @SuppressWarnings("Duplicates")
    private void findSupports() {

        List<Candle> possibleSupportVortexesList = candleVortexMap.entrySet().stream()
                .filter(l -> l.getValue().equals(VortexFinder.VORTEX_LOW))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (Candle candle : possibleSupportVortexesList) {
            for (Candle checkedCandle : possibleSupportVortexesList) {

                if (candle != checkedCandle) {
                    //check if up candle
                    if (candle.getClose() - candle.getOpen() > 0) {
                        //check if low of any candle is within range of low and open candle
                        if (checkedCandle.getLow() >= candle.getLow() && checkedCandle.getLow() < candle.getOpen()) {
                            supports.getSupportCandlesList().add(candle);
                        }
                    }
                    //check if down candle
                    if (candle.getClose() - candle.getOpen() < 0) {
                        //check if low and of any checkedCandle is within range of low and close candle
                        if (checkedCandle.getLow() >= candle.getLow() && checkedCandle.getLow() <= candle.getClose()) {
                            supports.getSupportCandlesList().add(candle);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void findResistances() {

        List<Candle> possibleSupportVortexesList = candleVortexMap.entrySet().stream()
                .filter(l -> l.getValue().equals(VortexFinder.VORTEX_HIGH))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (Candle candle : possibleSupportVortexesList) {
            for (Candle checkedCandle : possibleSupportVortexesList) {

                if (candle != checkedCandle) {
                    //check if down candle
                    if (candle.getClose() - candle.getOpen() < 0) {
                        //check if high of any candle is within range of high and close of candle
                        if (checkedCandle.getHigh() <= candle.getHigh() && checkedCandle.getHigh() >= candle.getOpen()) {
                            resistances.getResistanceCandlesList().add(candle);
                        }
                    }
                    //check if up candle
                    if (candle.getClose() - candle.getOpen() > 0) {
                        //check if low and of any checkedCandle is within range of low and close candle
                        if (checkedCandle.getHigh() <= candle.getHigh() && checkedCandle.getHigh() >= candle.getClose()) {
                            resistances.getResistanceCandlesList().add(candle);
                        }
                    }
                }
            }
        }
    }


}
