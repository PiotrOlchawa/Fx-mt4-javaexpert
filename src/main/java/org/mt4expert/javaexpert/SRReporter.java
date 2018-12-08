package org.mt4expert.javaexpert;

import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;
import org.mt4expert.javaexpert.datareader.CandleDataImporter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class SRReporter {

    private String fullPathFilename;

    public SRReporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    public void report() {
        CandleDataImporter candleDataImporter = new CandleDataImporter(fullPathFilename);
        CandleData candleData = new CandleData(candleDataImporter.importCandles());
        VortexFinder vortexFinder = new VortexFinder(candleData);
        SupportResistanceFinder supportResistanceFinder = new SupportResistanceFinder(vortexFinder.findVortexes());
        supportResistanceFinder.findSupportsAndResistances();
        SimpleDateFormat sdf = new SimpleDateFormat(ExpertConfigurator.DATE_FORMAT);
        List<Candle> supportCandleList = supportResistanceFinder.getSupports().getSupportCandlesList();
        int supportCandles = supportCandleList.size();
        if (supportCandles > 0) {
            Commander.showSupports(supportCandleList.get(0).getSymbol(),supportCandleList.get(0).getPeriodInReadableFormat());
            supportResistanceFinder.getSupports().getSupportCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose))
                    .entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }

        List<Candle> resistanceCandleList = supportResistanceFinder.getResistances().getResistanceCandlesList();
        int resistanceCandles = resistanceCandleList.size();
        if (resistanceCandles > 0) {
            Commander.showResistances(resistanceCandleList.get(0).getSymbol(),resistanceCandleList.get(0).getPeriodInReadableFormat());
            supportResistanceFinder.getResistances().getResistanceCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose))
                    .entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }
        Commander.showUptrendOrDowntrend(supportCandles - resistanceCandles);
        FalseBreakOutInterpreter falseBreakOutInterpreter =
                new FalseBreakOutInterpreter(supportResistanceFinder.getSupports(), supportResistanceFinder.getResistances());
        falseBreakOutInterpreter.checkForResistanceBreakOut(candleData);

    }
}
