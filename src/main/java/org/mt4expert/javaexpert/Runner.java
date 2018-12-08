package org.mt4expert.javaexpert;

import org.apache.log4j.BasicConfigurator;
import org.mt4expert.javaexpert.mt4.Mt4FolderProcessor;

public class Runner {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Mt4FolderProcessor mt4FolderProcessor = new Mt4FolderProcessor();
        mt4FolderProcessor.processFolder();
    }
}

