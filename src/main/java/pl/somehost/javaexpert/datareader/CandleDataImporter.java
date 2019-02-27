package pl.somehost.javaexpert.datareader;

import pl.somehost.javaexpert.data.Candle;

import java.util.List;

public class CandleDataImporter {

    private String fullPathFilename;
    private Mt4ExpertFilesReader mt4ExpertFilesReader;

    public CandleDataImporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    public List<Candle> importCandles() {
        mt4ExpertFilesReader = new Mt4ExpertFilesReader(fullPathFilename);
        List<Candle> candleList = mt4ExpertFilesReader.read();
        return candleList;
    }

}
