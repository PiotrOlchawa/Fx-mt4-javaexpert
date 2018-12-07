package org.mt4expert.javaexpert;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.util.List;

public class FalseBreakOutInterpreter {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FalseBreakOutInterpreter.class);
    CandleData candleData;
    Support supports;
    Resistance resistances;


    public FalseBreakOutInterpreter(CandleData candleData, Support supports, Resistance resistances) {
        this.candleData = candleData;
        this.supports = supports;
        this.resistances = resistances;
    }

    void checkForResistanceBreakOut() {
    //first option - should check for last three candles breakout
        List<Candle> candleList = candleData.getCandles();
        List<Candle> resistanceCandleList = resistances.getResistanceCandlesList();
        List<Candle> supportCandlesList = supports.getSupportCandlesList();
        for(Candle candle: resistanceCandleList){
            if (candleList.get(0).getHigh()>candle.getHigh()){

                System.out.println("-------------------------------------Resistance BreakOut " +candle.getSymbol()+" !! at " + candle.getDate());
            }
        }
        for(Candle candle: supportCandlesList){
           /* //System.out.println(supportCandlesList);
            System.out.println("candle low "+candle.getLow());
            System.out.println("candleList low "+ (candleList.get(0).getLow()));*/
            if (candleList.get(0).getLow()<candle.getLow()){
                System.out.println("-------------------------------------Support BreakOut " +candle.getSymbol()+" !! at " + candle.getDate());
            }
        }

    }


}
