package org.mt4expert.javaexpert;

import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;
import org.mt4expert.javaexpert.datareader.CandleDataImporter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class SRReporter {

    private String fullPathFilename;
    private Integer delayExecution;

    public SRReporter(String fullPathFilename, Integer delayExecution) {
        this.fullPathFilename = fullPathFilename;
        this.delayExecution = delayExecution;
    }

    public void run() {
        CandleDataImporter candleDataImporter = new CandleDataImporter(fullPathFilename);
        CandleData candleData = new CandleData(candleDataImporter.importCandles());
        VortexFinder vortexFinder = new VortexFinder(candleData);
        SupportResistanceFinder supportResistanceFinder = new SupportResistanceFinder(vortexFinder.findVortexes());
        supportResistanceFinder.findSupportsAndResistances();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");

        List<Candle> supportCandleList = supportResistanceFinder.getSupports().getSupportCandlesList();
        int supportCandles = supportCandleList.size();
        if (supportCandles > 0) {
            System.out.println("Supports for " + supportCandleList.get(0).getSymbol());
            supportResistanceFinder.getSupports().getSupportCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose))
                    .entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }

        List<Candle> resistanceCandleList = supportResistanceFinder.getResistances().getResistanceCandlesList();
        int resistanceCandles = resistanceCandleList.size();
        if (resistanceCandles > 0) {
            System.out.println("Resistances for " + resistanceCandleList.get(0).getSymbol());

            supportResistanceFinder.getResistances().getResistanceCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose))
                    .entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }
        System.out.println((supportCandles-resistanceCandles>0 ? "------------UPTREND------------"
                : "------------DOWNTREND------------"));


        FalseBreakOutInterpreter falseBreakOutInterpreter =
                new FalseBreakOutInterpreter(supportResistanceFinder.getSupports(), supportResistanceFinder.getResistances());
        falseBreakOutInterpreter.checkForResistanceBreakOut(candleData);

    }
}
