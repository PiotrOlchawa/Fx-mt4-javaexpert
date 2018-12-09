package org.mt4expert.javaexpert.mt4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mt4FilesComparator {

    List<String> compareMap(Map<String, Long> actualFilesMap, Map<String, Long> filesMap) {
        //nowe pliki nazwy
        Map<String, Long> differentialFilenamesMap = actualFilesMap.entrySet().stream()
                .filter(l -> !filesMap.containsKey(l.getKey()))
                .collect(Collectors.toMap(l -> l.getKey(), l -> l.getValue()));

        differentialFilenamesMap.entrySet().stream()
                .forEach((k) -> filesMap.put(k.getKey(), k.getValue()));
        //stare pliki zawartosc
        getOldFilesStream(actualFilesMap, filesMap).forEach(l -> differentialFilenamesMap.put(l.getKey(), l.getValue()));
        getOldFilesStream(actualFilesMap, filesMap).forEach(l -> filesMap.put(l.getKey(), l.getValue()));

        return differentialFilenamesMap.entrySet().stream()
                .map(l -> l.getKey())
                .collect(Collectors.toList());
    }

    private Stream<Map.Entry<String, Long>> getOldFilesStream(Map<String, Long> actualFilesMap, Map<String, Long> filesMap) {
        return actualFilesMap.entrySet().stream()
                .filter(l -> filesMap.containsKey(l.getKey()))
                .filter(l -> !filesMap.containsValue(l.getValue()));
    }

}
