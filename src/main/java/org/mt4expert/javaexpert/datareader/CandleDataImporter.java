package org.mt4expert.javaexpert.datareader;

import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.datareader.CsvReader;

import java.util.List;

public class CandleDataImporter {

    private String fullPathFilename;
    private CsvReader csvReader;

    public CandleDataImporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    public List<Candle> importCandles() {
        csvReader = new CsvReader(fullPathFilename);
        List<Candle> candleList = csvReader.read();
        return candleList;
    }

}
