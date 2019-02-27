package pl.somehost.javaexpert;

import pl.somehost.javaexpert.mt4.Mt4FolderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        String path = Runner.class.getClassLoader().getResource("expert.config").getPath();
        System.out.println("Configuration file path: " + path);
    }

    public static void main(String[] args) {
        Mt4FolderProcessor mt4FolderProcessor = new Mt4FolderProcessor();
        mt4FolderProcessor.processFolder();
    }
}

