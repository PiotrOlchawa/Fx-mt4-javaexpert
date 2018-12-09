package org.mt4expert.javaexpert.interpreter;

import org.mt4expert.javaexpert.data.Candle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FalseBreakoutData {

    Candle candle;
    Map<Date, Double> resistanceMap = new HashMap<>();
    Map<Date, Double> supporteMap = new HashMap<>();

    public FalseBreakoutData(Candle candle,Map<Date, Double> resistanceMap,Map<Date, Double> supportMap) {
        this.candle = candle;
        this.supporteMap =supportMap;
        this.resistanceMap =resistanceMap;
    }

    public Candle getCandle() {
        return candle;
    }

    public void setCandle(Candle candle) {
        this.candle = candle;
    }

    public Map<Date, Double> getResistanceMap() {
        return resistanceMap;
    }

    public void setResistanceMap(Map<Date, Double> resistanceMap) {
        this.resistanceMap = resistanceMap;
    }

    public Map<Date, Double> getSupporteMap() {
        return supporteMap;
    }

    public void setSupporteMap(Map<Date, Double> supporteMap) {
        this.supporteMap = supporteMap;
    }
}
