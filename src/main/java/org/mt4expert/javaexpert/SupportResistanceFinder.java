package org.mt4expert.javaexpert;


import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.Resistance;
import org.mt4expert.javaexpert.data.Support;

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

    private void findResistances() {
        List<Candle> possibleSupportVortexesList = candleVortexMap.entrySet().stream()
                .filter(l -> l.getValue().equals(VortexFinder.VORTEX_HIGH))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        addResistanceCandle(possibleSupportVortexesList);
    }

    private void addResistanceCandle(List<Candle> possibleSupportVortexesList) {
        for (Candle candle : possibleSupportVortexesList) {
            for (Candle checkedCandle : possibleSupportVortexesList) {
                checkDependencyBetweenResistanceCandles(candle, checkedCandle);
            }
        }
    }

    private void checkDependencyBetweenResistanceCandles(Candle candle, Candle checkedCandle) {
        if (candle != checkedCandle) {
            //check if down candle doji dla =0
            if (candle.getClose() - candle.getOpen() <= 0) {
                //check if high of any candle is within range of high and open of candle
                if (checkedCandle.getHigh() <= candle.getHigh() && checkedCandle.getHigh() >= candle.getOpen()) {
                    resistances.getResistanceCandlesList().add(candle);
                }
                //check if high of checkedCandle is above the high of candle but close is below candle open
                if (checkedCandle.getHigh() > candle.getHigh() && checkedCandle.getClose() < candle.getOpen()) {
                    resistances.getResistanceCandlesList().add(candle);
                }
            }
            //check if up candle
            if (candle.getClose() - candle.getOpen() > 0) {
                //check if high of checkedCandle is within range of high candle and close of candle
                if (checkedCandle.getHigh() <= candle.getHigh() && checkedCandle.getHigh() >= candle.getClose()) {
                    resistances.getResistanceCandlesList().add(candle);
                }
                //check if high checkedCandle is above the high candle but close is below candle close
                if (checkedCandle.getHigh() > candle.getHigh() && checkedCandle.getClose() < candle.getClose()) {
                    resistances.getResistanceCandlesList().add(candle);
                }
            }
        }
    }

    private void findSupports() {
        List<Candle> possibleSupportVortexesList = candleVortexMap.entrySet().stream()
                .filter(l -> l.getValue().equals(VortexFinder.VORTEX_LOW))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        addSupportCandle(possibleSupportVortexesList);
    }

    private void addSupportCandle(List<Candle> possibleSupportVortexesList) {
        for (Candle candle : possibleSupportVortexesList) {
            for (Candle checkedCandle : possibleSupportVortexesList) {
                checkDependencyBetweenSupportCandles(candle, checkedCandle);
            }
        }
    }

    private void checkDependencyBetweenSupportCandles(Candle candle, Candle checkedCandle) {
        if (candle != checkedCandle) {
            //check if up candle - dla doji =0
            if (candle.getClose() - candle.getOpen() >= 0) {
                //check if low of checkedCandle is within range of low and open candle
                if (checkedCandle.getLow() >= candle.getLow() && checkedCandle.getLow() < candle.getOpen()) {
                    supports.getSupportCandlesList().add(candle);
                }
                //check if low of checkedCandle is below low of candle but close of checkedCandle is abowe open of candle
                if (checkedCandle.getLow() < candle.getLow() && checkedCandle.getClose() > candle.getOpen()) {
                    supports.getSupportCandlesList().add(candle);
                }
            }
            //check if down candle
            if (candle.getClose() - candle.getOpen() < 0) {
                //check if low and of any checkedCandle is within range of low and close candle
                if (checkedCandle.getLow() >= candle.getLow() && checkedCandle.getLow() <= candle.getClose()) {
                    supports.getSupportCandlesList().add(candle);
                }
                //check if low of checkedCandle is below low of candle but close of checkedCandle is abowe close of candle
                if (checkedCandle.getLow() < candle.getLow() && checkedCandle.getClose() > candle.getClose()) {
                    supports.getSupportCandlesList().add(candle);
                }
            }
        }
    }


}
