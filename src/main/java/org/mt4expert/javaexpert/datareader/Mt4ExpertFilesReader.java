package org.mt4expert.javaexpert.datareader;


import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.mt4.Commander;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Mt4ExpertFilesReader {

    private String csvFullPathFile = null;
    private static BufferedReader br = null;
    private static String line = "";
    private static final String cvsSplitBy = ",";

    public Mt4ExpertFilesReader(String csvFullPathFile) {
        this.csvFullPathFile = csvFullPathFile;
    }

    public List<Candle> read() {
        List<Candle> candleList = new LinkedList<>();
        DateFormat format = new SimpleDateFormat(ExpertConfigurator.MT4_DATE_FORMAT);

        try {
            br = new BufferedReader(new FileReader(csvFullPathFile));
            while ((line = br.readLine()) != null) {

                Candle candle = new Candle();
                // use comma as separator
                String[] oneCandle = line.split(cvsSplitBy);
                candle.setOpen(Double.parseDouble(oneCandle[0]));
                candle.setHigh(Double.parseDouble(oneCandle[1]));
                candle.setLow(Double.parseDouble(oneCandle[2]));
                candle.setClose(Double.parseDouble(oneCandle[3]));
                try {
                    candle.setDate(format.parse(oneCandle[4]));
                } catch (ParseException e) {
                    Commander.inputParseError();
                }
                candle.setSymbol(oneCandle[5]);
                candleList.add(candle);
                if(oneCandle.length>6){
                    try {
                        candle.setPeriod(Integer.parseInt(oneCandle[6]));
                    } catch (NumberFormatException e) {
                        Commander.inputParseError();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return candleList;
    }
}
