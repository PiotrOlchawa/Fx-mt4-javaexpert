package org.mt4expert.javaexpert;

import org.apache.log4j.BasicConfigurator;
import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.mt4.Mt4FolderProcessor;

import java.net.URL;
import java.nio.file.Paths;

public class Runner {

    static {
        String path = Runner.class.getClassLoader().getResource("expert.config").getPath();
        System.out.println(path);
        //ClassLoader classLoader = Runner.class.getClassLoader();
        //URL path  = classLoader.getResource("Commander.class");
        //System.out.println(path);
        //URL resource = Runner.class.getResource("/gradlew");
        //System.out.println(resource);
        //Paths.get(resource.toURI()).toFile();


        //System.out.println(ExpertConfigurator.class.getResource("expert.config").toString());
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Mt4FolderProcessor mt4FolderProcessor = new Mt4FolderProcessor();
        mt4FolderProcessor.processFolder();
    }
}

