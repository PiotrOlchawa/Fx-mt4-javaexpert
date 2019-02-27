package pl.somehost.javaexpert.mailservice;

import pl.somehost.javaexpert.config.ExpertConfigurator;
import pl.somehost.javaexpert.data.BreakoutType;
import pl.somehost.javaexpert.data.Candle;
import pl.somehost.javaexpert.data.FalseBreakoutData;
import pl.somehost.javaexpert.interpreter.TrendInterpreter;

import java.text.SimpleDateFormat;
import java.util.Set;

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
        outputStringBuilder.append("-------------");
        outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol());
        outputStringBuilder.append(" " + falseBreakoutData.getCandle().getPeriodInReadableFormat()+ " ");
        outputStringBuilder.append("--------------\n");

        Set<Candle> resistanceSet = falseBreakoutData.getResistanceSet();
        Set<Candle> supportSet = falseBreakoutData.getSupportSet();
        TrendInterpreter trendInterpreter = new TrendInterpreter(resistanceSet.size(), supportSet.size());

        if (resistanceSet.size() > 0) {
            outputStringBuilder.append("\n" + ExpertConfigurator.RESISTANCE);
            outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol() + "\n");
            resistanceSet.forEach(l -> outputStringBuilder.append(" | " + sdf.format(l.getDate()) + " | " + l.getHigh() + "\n"));
        }

        if (supportSet.size() > 0) {
            outputStringBuilder.append("\n" + ExpertConfigurator.SUPPORT);
            outputStringBuilder.append(falseBreakoutData.getCandle().getSymbol() + "\n");
            supportSet.forEach(l -> outputStringBuilder.append(" | " + sdf.format(l.getDate()) + " | " + l.getLow() + "\n"));
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
