package org.mt4expert.javaexpert.mt4;

import org.mt4expert.javaexpert.Commander;
import org.mt4expert.javaexpert.reporter.SRReporter;
import org.mt4expert.javaexpert.config.ExpertConfigurator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mt4FolderProcessor {

    public void processFolder() {
        Mt4FilesComparator mt4FilesComparator = new Mt4FilesComparator();
        List<String> oldfilesMap = ExpertConfigurator.FILES_MAP.entrySet().stream()
                .map(Map.Entry::getKey).collect(Collectors.toList());
        processFilesInFolder(oldfilesMap);
        while (true) {
            try {
                Thread.sleep(ExpertConfigurator.THREAD_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, Long> actualFilesMap = Arrays.stream(new File(ExpertConfigurator.EXPERT_FILES_ABSOLUTE_PATH).listFiles()).collect(Collectors.toMap(l -> l.getName(), k -> k.length()));
            List<String> fileNamesDifferentialList = mt4FilesComparator.compareMap(actualFilesMap,ExpertConfigurator.FILES_MAP);
            if (fileNamesDifferentialList.size() == 0) {
                Commander.showNoChanges();
            } else {
                Commander.showChanges(fileNamesDifferentialList);
            }
            processFilesInFolder(fileNamesDifferentialList);
        }
    }

    private static void processFilesInFolder(List<String> filesMap) {
        for (String fileName : filesMap) {
            Commander.nextPair();
            SRReporter SRReporter = new SRReporter(ExpertConfigurator.EXPERT_FILES_ABSOLUTE_PATH + fileName);
            SRReporter.report();
        }
    }
}
