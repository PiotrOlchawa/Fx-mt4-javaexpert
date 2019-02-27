package pl.somehost.javaexpert.datareader;

import pl.somehost.javaexpert.config.ExpertConfigurator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileConfigReader {

    private String csvFullPathFile = null;
    private static BufferedReader br = null;
    private static String line = "";
    private static final String configParametersSplitBy = "=";


    public static Map<String, String> readConfig() {

        Map<String, String> configParameters = new HashMap<>();
        try {
            br = new BufferedReader(new InputStreamReader(ExpertConfigurator.CONFIG_FILENAME.openStream()));
            while ((line = br.readLine()) != null) {
                String[] configParam = line.split(configParametersSplitBy);
                if(configParam.length==2) {
                    configParameters.put(configParam[0], configParam[1]);
                }
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
        return configParameters;
    }
}
