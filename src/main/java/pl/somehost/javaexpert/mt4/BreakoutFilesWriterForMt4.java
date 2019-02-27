package pl.somehost.javaexpert.mt4;

import pl.somehost.javaexpert.config.ExpertConfigurator;
import pl.somehost.javaexpert.data.BreakoutType;
import pl.somehost.javaexpert.data.FalseBreakoutData;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BreakoutFilesWriterForMt4 {

    private FalseBreakoutData falseBreakoutData;

    public BreakoutFilesWriterForMt4(FalseBreakoutData falseBreakoutData) {
        this.falseBreakoutData = falseBreakoutData;
    }

    public void write() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(ExpertConfigurator.EXPERT_FILES_ABSOLUTE_PATH+ExpertConfigurator.EXCLUDE_FILES_WITH_PREFIX
                    +falseBreakoutData.getCandle().getSymbol()+ falseBreakoutData.getCandle().getPeriod()+".csv", "UTF-8");
            if (falseBreakoutData.getCandle().getBreakoutType().equals(BreakoutType.SUPPORT)) {
                writer.println(falseBreakoutData.getCandle().getLow()+";"+falseBreakoutData.getCandle().getDateInReadableFormat());
            }
            if (falseBreakoutData.getCandle().getBreakoutType().equals(BreakoutType.RESISTANCE)) {
                writer.println(falseBreakoutData.getCandle().getHigh()+";"+falseBreakoutData.getCandle().getDateInReadableFormat());
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
