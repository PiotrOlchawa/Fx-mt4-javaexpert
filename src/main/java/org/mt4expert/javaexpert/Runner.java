package org.mt4expert.javaexpert;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {

    private static final String ABSOLUTE_PATH = "/home/piotr/.wine/drive_c/Program Files (x86)/FxPro - MetaTrader 4/MQL4/Files/";
    private static Map<String, Long> filesMap = Arrays.stream(new File(ABSOLUTE_PATH).listFiles()).collect(Collectors.toMap(l -> l.getName(), k -> k.length()));

    public static void main(String[] args) {
        List<String> oldfilesMap = filesMap.entrySet().stream()
                .map(l -> l.getKey()).collect(Collectors.toList());
        processFiles(oldfilesMap);
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, Long> actualFilesMap = Arrays.stream(new File(ABSOLUTE_PATH).listFiles()).collect(Collectors.toMap(l -> l.getName(), k -> k.length()));
            List<String> fileNamesDifferentialList = compareMap(actualFilesMap);
            System.out.println(fileNamesDifferentialList);
            //System.out.println("processing.. old and actual and differential "+ filesMap + " " + actualFilesMap + " " + fileNamesDifferentialList);
            processFiles(fileNamesDifferentialList);
        }
    }

    private static void processFiles(List<String> filesMap) {
        for (String fileName : filesMap) {
            System.out.println("loop");
            ScanBreakoutTask scanBreakoutTask = new ScanBreakoutTask(ABSOLUTE_PATH + fileName, 10000);
            scanBreakoutTask.run();
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

