package org.mt4expert.javaexpert.mt4;

import org.mt4expert.javaexpert.config.ExpertConfigurator;

import java.util.List;

public class Commander {


    public static void showNoChanges() {
        System.out.println(ExpertConfigurator.NO_CHANGES);
    }

    public static void showChanges(List<String> fileNamesDifferentialList) {
        System.out.println(ExpertConfigurator.CHANGES + fileNamesDifferentialList);
    }

    public static void nextPair() {
        System.out.println(ExpertConfigurator.NEXT_PAIR);
    }

    public static void showSupports(String symbol, String timeFrame) {
        System.out.println(ExpertConfigurator.SUPPORT + symbol + timeFrame);
    }

    public static void showResistances(String symbol, String timeFrame) {
        System.out.println(ExpertConfigurator.RESISTANCE + symbol + timeFrame);
    }

    public static void showUptrendOrDowntrend(int i) {
        System.out.println((i > 0 ? Commander.showUpTrend()
                : Commander.showDownTrend()));
    }

    private static String showUpTrend() {
        return ExpertConfigurator.UPTREND;
    }

    private static String showDownTrend() {
        return ExpertConfigurator.DOWNTREND;
    }

    public static void inputParseError() {
        System.out.println(ExpertConfigurator.INPUT_PARSE_ERROR);
    }
}
