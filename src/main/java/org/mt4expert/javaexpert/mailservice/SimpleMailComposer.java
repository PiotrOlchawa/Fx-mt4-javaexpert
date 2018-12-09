package org.mt4expert.javaexpert.mailservice;

import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.BreakoutType;
import org.mt4expert.javaexpert.interpreter.FalseBreakoutData;
import org.mt4expert.javaexpert.interpreter.TrendInterpreter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SimpleMailComposer {
    FalseBreakoutData falseBreakoutData;

    public SimpleMailComposer(FalseBreakoutData falseBreakoutData) {
        this.falseBreakoutData = falseBreakoutData;
    }

    public String getEailSubject() {
        return ExpertConfigurator.EMAIL_SUBJECT_HEADER + " " + falseBreakoutData.getCandle().getSymbol()
                +" "+ falseBreakoutData.getCandle().getPeriodInReadableFormat();
    }

    public String getEmailText() {

        SimpleDateFormat sdf = new SimpleDateFormat(ExpertConfigurator.DATE_FORMAT);

        StringBuilder outputStringBuilder = new StringBuilder();
        outputStringBuilder.append("--------------");
        outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol());
        outputStringBuilder.append(" " + falseBreakoutData.getCandle().getPeriodInReadableFormat()+ "\n");
        outputStringBuilder.append("--------------\n");

        Map<Date, Double> resistanceMap = falseBreakoutData.getResistanceMap();
        Map<Date, Double> supportMap = falseBreakoutData.getSupportMap();
        TrendInterpreter trendInterpreter = new TrendInterpreter(resistanceMap.size(), supportMap.size());

        if (resistanceMap.size() > 0) {
            outputStringBuilder.append("\n" + ExpertConfigurator.RESISTANCE);
            outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol() + "\n");
            resistanceMap.entrySet().forEach(l -> outputStringBuilder.append(" | " + sdf.format(l.getKey()) + " | " + l.getValue() + "\n"));
        }

        if (supportMap.size() > 0) {
            outputStringBuilder.append("\n" + ExpertConfigurator.SUPPORT);
            outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol() + "\n");
            supportMap.entrySet().forEach(l -> outputStringBuilder.append(" | " + sdf.format(l.getKey()) + " | " + l.getValue() + "\n"));
        }

        if (falseBreakoutData.getCandle().getBreakoutType().equals(BreakoutType.RESISTANCE)) {
            outputStringBuilder.append(ExpertConfigurator.RESISTANCE_BREAKOUT
                    + " | " + falseBreakoutData.getCandle().getDateInReadableFormat() + " | " + falseBreakoutData.getCandle().getHigh() + "\n");
        }

        if (falseBreakoutData.getCandle().getBreakoutType().equals(BreakoutType.SUPPORT)) {
            outputStringBuilder.append(ExpertConfigurator.SUPPORT_BREAKOUT
                    + " | " + falseBreakoutData.getCandle().getDateInReadableFormat() + " | " + falseBreakoutData.getCandle().getLow() + "\n");
        }

        outputStringBuilder.append(trendInterpreter.getTrend() + "\n");
        return outputStringBuilder.toString();
    }
}
