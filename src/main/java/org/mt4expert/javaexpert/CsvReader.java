package org.mt4expert.javaexpert;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class CsvReader {

    private String csvFullPathFile = null;
    private static BufferedReader br = null;
    private static String line = "";
    private static final String cvsSplitBy = ",";

    public CsvReader(String csvFullPathFile) {
        this.csvFullPathFile = csvFullPathFile;
    }

    public List<Candle> read() {
        List<Candle> candleList = new LinkedList<>();
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");

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
                    System.out.println("Błąd parsowania daty");
                    e.printStackTrace();
                    System.exit(0);
                }
                candleList.add(candle);

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
