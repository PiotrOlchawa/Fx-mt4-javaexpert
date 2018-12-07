package org.mt4expert.javaexpert;


import java.util.stream.Collectors;

public class ScanBreakoutTask {

    private String fullPathFilename = "/home/piotr/.wine/drive_c/Program Files (x86)/FxPro - MetaTrader 4/MQL4/Files/export_USDCAD_1.csv";
    private Integer delayExecution = 10000;

    public ScanBreakoutTask(String fullPathFilename, Integer delayExecution) {
        this.fullPathFilename = fullPathFilename;
        this.delayExecution = delayExecution;
    }

    public void run() {
     /*   try {
            Thread.sleep(delayExecution);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        CandleDataImporter candleDataImporter = new CandleDataImporter(fullPathFilename);
        CandleData candleData = new CandleData(candleDataImporter.importCandles());
        VortexFinder vortexFinder = new VortexFinder(candleData);
        SupportResistanceFinder supportResistanceFinder = new SupportResistanceFinder(vortexFinder.findVortexes());
        supportResistanceFinder.findSupportsAndResistances();
        System.out.println("Supports for " + fullPathFilename);
        supportResistanceFinder.getSupports().getSupportCandlesList().stream()
                .collect(Collectors.toSet()).stream()
                .collect(Collectors.toMap(Candle::getClose, Candle::getDate))
                .entrySet().forEach(System.out::println);

        System.out.println("Resistances for " + fullPathFilename);
        supportResistanceFinder.getResistances().getResistanceCandlesList().stream()
                .collect(Collectors.toSet()).stream()
                .collect(Collectors.toMap(Candle::getClose, Candle::getDate))
                .entrySet().forEach(System.out::println);
        FalseBreakOutInterpreter falseBreakOutInterpreter =
                new FalseBreakOutInterpreter(candleData, supportResistanceFinder.getSupports(), supportResistanceFinder.getResistances());
        falseBreakOutInterpreter.checkForResistanceBreakOut();

    }
}
