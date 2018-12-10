package org.mt4expert.javaexpert.interpreter;

import org.mt4expert.javaexpert.data.Candle;

import java.util.*;

public class FalseBreakoutData {

    Candle candle;
    Set<Candle> resistanceSet = new HashSet<>();
    Set<Candle> supportSet = new HashSet<>();

    public FalseBreakoutData(Candle candle, Set<Candle> resistanceSet, Set<Candle> supportSet) {
        this.candle = candle;
        this.resistanceSet = resistanceSet;
        this.supportSet = supportSet;
    }

    public Candle getCandle() {
        return candle;
    }

    public void setCandle(Candle candle) {
        this.candle = candle;
    }

    public Set<Candle> getResistanceSet() {
        return resistanceSet;
    }

    public void setResistanceSet(Set<Candle> resistanceSet) {
        this.resistanceSet = resistanceSet;
    }

    public Set<Candle> getSupportSet() {
        return supportSet;
    }

    public void setSupportSet(Set<Candle> supportSet) {
        this.supportSet = supportSet;
    }
}
