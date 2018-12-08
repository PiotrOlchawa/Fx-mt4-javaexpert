package org.mt4expert.javaexpert.config;

import org.mt4expert.javaexpert.datareader.FileConfigReader;

import java.net.URL;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpertConfigurator {

    //public static final String CONFIG_FILENAME = ExpertConfigurator.class.getClassLoader().getResource("expert.config").getPath();
    public static final URL CONFIG_FILENAME = ExpertConfigurator.class.getClassLoader().getResource("expert.config");
    private static final Map<String, String> CONFIG_MAP = FileConfigReader.readConfig();
    public static final String EXPERT_FILES_ABSOLUTE_PATH = CONFIG_MAP.get("mt4DataFolder");
    public static Map<String, Long> FILES_MAP = Arrays.stream(new File(EXPERT_FILES_ABSOLUTE_PATH).listFiles())
            .collect(Collectors.toMap(l -> l.getName(), k -> k.length()));
    public static final int THREAD_SLEEP = 5000;
    public static final int CANDLE_LIST_SIZE_LIMIT = 500;
    public static final int HOW_MANY_CANDLES_CREATES_FB = 1;
    public static final String DATE_FORMAT = "dd.MM.yyy HH:mm:ss";
    public static final String MT4_DATE_FORMAT = "yyyy.MM.dd HH:mm";
    public static final String VORTEX_HIGH = "VORTEX_HIGH";
    public static final String VORTEX_LOW = "VORTEX_LOW";
    public static final String RESISTANCE_BREAKOUT = "---------------------------------Resistance BreakOut ";
    public static final String SUPPORT_BREAKOUT = "-------------------------------------Support BreakOut " ;

    public static final String NO_CHANGES = "No new files or changes, waiting..";
    public static final String CHANGES = "New files to process ";
    public static final String NEXT_PAIR = "-----------------------------------NEXT PAIR-----------------------------------";
    public static final String SUPPORT = "Supports for ";
    public static final String RESISTANCE = "Resistances for ";
    public static final String UPTREND = "------------UPTREND--------------";
    public static final String DOWNTREND = "------------DOWNTREND------------";
    public static final String INPUT_PARSE_ERROR = "-------------------PARSE INPUT DATA(DATE) ERROR------------------------";

    public static final boolean EMAIL_ALLERT = Boolean.parseBoolean(CONFIG_MAP.get("email_allert"));
    public static final String MAIL_HOST = CONFIG_MAP.get("mail_host");
    public static final int MAIL_PORT = Integer.parseInt(CONFIG_MAP.get("mail_port"));
    public static final String EMAIL_USER = CONFIG_MAP.get("email_user");
    public static final String EMAIL_PASSOWRD = CONFIG_MAP.get("email_password");
    public static final String EMAIL_USER_TO = CONFIG_MAP.get("email_user_to");
    public static final String EMAIL_USER_FROM = CONFIG_MAP.get("email_user_from");
    public static final String EMAIL_FROM_NAME = CONFIG_MAP.get("email_from_name");;
    public static final String EMAIL_TO_NAME = CONFIG_MAP.get("email_to_name");;
}