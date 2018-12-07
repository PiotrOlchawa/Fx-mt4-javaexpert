package org.mt4expert.javaexpert;



import java.util.List;

public class CandleDataImporter {

    private String fullPathFilename;
    private CsvReader csvReader;

    public CandleDataImporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    List<Candle> importCandles() {
        csvReader = new CsvReader(fullPathFilename);
        List<Candle> candleList = csvReader.read();
        return candleList;
    }

}
