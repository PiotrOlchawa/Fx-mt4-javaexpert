package org.mt4expert.javaexpert;



import java.util.ArrayList;
import java.util.List;

public class Resistance {

    List<Candle> supportCandlesList = new ArrayList<>();

    public List<Candle> getResistanceCandlesList() {
        return supportCandlesList;
    }

    public void setSupportCandlesList(List<Candle> supportCande) {
        this.supportCandlesList = supportCande;
    }
}
