package org.mt4expert.javaexpert;

import java.util.ArrayList;
import java.util.List;

public class Support {

    List<Candle> supportCandlesList = new ArrayList<>();
    public List<Candle> getSupportCandlesList() {
        return supportCandlesList;
    }
    public void setSupportCandlesList(List<Candle> supportCande) {
        this.supportCandlesList = supportCande;
    }
}
