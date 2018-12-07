package org.mt4expert.javaexpert;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.BasicConfigurator;
import org.mt4expert.javaexpert.config.ExpertConfigurator;

public class Runner {

    private static final String ABSOLUTE_PATH = "/home/piotr/.wine/drive_c/Program Files (x86)/FxPro - MetaTrader 4/MQL4/Files/";
    private static Map<String, Long> filesMap = Arrays.stream(new File(ABSOLUTE_PATH).listFiles()).collect(Collectors.toMap(l -> l.getName(), k -> k.length()));

    public static void main(String[] args) {
        BasicConfigurator.configure();
        List<String> oldfilesMap = filesMap.entrySet().stream()
                .map(l -> l.getKey()).collect(Collectors.toList());
        processFiles(oldfilesMap);
        while (true) {
            try {
                Thread.sleep(ExpertConfigurator.getThreadSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, Long> actualFilesMap = Arrays.stream(new File(ABSOLUTE_PATH).listFiles()).collect(Collectors.toMap(l -> l.getName(), k -> k.length()));
            List<String> fileNamesDifferentialList = compareMap(actualFilesMap);
            if (fileNamesDifferentialList.size()==0) {
                System.out.println("No new files or changes, waiting..");
            } else {
                System.out.println("New files to process " + fileNamesDifferentialList);
            }
            processFiles(fileNamesDifferentialList);
        }
    }

    private static void processFiles(List<String> filesMap) {
        for (String fileName : filesMap) {
            System.out.println("-----------------------------------NEXT PAIR-----------------------------------");
            SRReporter SRReporter = new SRReporter(ABSOLUTE_PATH + fileName, 10000);
            SRReporter.run();
        }
    }

    private static List<String> compareMap(Map<String, Long> actualFilesMap) {
        //nowe pliki nazwy
        Map<String, Long> differentialFilenamesMap = actualFilesMap.entrySet().stream()
                .filter(l -> !filesMap.containsKey(l.getKey()))
                .collect(Collectors.toMap(l -> l.getKey(), l -> l.getValue()));

        differentialFilenamesMap.entrySet().stream()
                .forEach((k) -> filesMap.put(k.getKey(), k.getValue()));
        //stare pliki
        getOldFilesStream(actualFilesMap).forEach(l -> differentialFilenamesMap.put(l.getKey(), l.getValue()));
        getOldFilesStream(actualFilesMap).forEach(l -> filesMap.put(l.getKey(), l.getValue()));

        return differentialFilenamesMap.entrySet().stream()
                .map(l -> l.getKey())
                .collect(Collectors.toList());
    }

    private static Stream<Map.Entry<String, Long>> getOldFilesStream(Map<String, Long> actualFilesMap) {
        return actualFilesMap.entrySet().stream()
                .filter(l -> filesMap.containsKey(l.getKey()))
                .filter(l -> !filesMap.containsValue(l.getValue()));
    }

}

