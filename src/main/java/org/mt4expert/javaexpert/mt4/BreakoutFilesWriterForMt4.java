package org.mt4expert.javaexpert.mt4;

import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.BreakoutType;
import org.mt4expert.javaexpert.interpreter.FalseBreakoutData;

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
            writer = new PrintWriter(ExpertConfigurator.EXPERT_FILES_ABSOLUTE_PATH+"___"+falseBreakoutData.getCandle().getSymbol()+
                    falseBreakoutData.getCandle().getPeriod()+".csv", "UTF-8");
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
