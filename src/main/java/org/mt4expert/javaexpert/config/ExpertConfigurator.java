package org.mt4expert.javaexpert.config;

import org.mt4expert.javaexpert.datareader.FileConfigReader;

import java.net.URL;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpertConfigurator {

    static final String MT4_DATA_FOLDER (){
        if(System.getProperty("os.name").equals("Linux")){
            return "linuxmt4DataFolder";
        }
        else return "mt4DataFolder";
    }

    public static final URL CONFIG_FILENAME = ExpertConfigurator.class.getClassLoader().getResource("expert.config");
    public static final URL WAVE_ALLERT_FILENAME = ExpertConfigurator.class.getClassLoader().getResource("allert.wav");
    public static final String MT4_FILES_FOLDER = MT4_DATA_FOLDER();

    private static final Map<String, String> CONFIG_MAP = FileConfigReader.readConfig();
    public static final String EXPERT_FILES_ABSOLUTE_PATH = CONFIG_MAP.get(MT4_DATA_FOLDER());
    public static final String EXCLUDE_FILES_WITH_PREFIX ="___";
    public static Map<String, Long> FILES_MAP = Arrays.stream(new File(EXPERT_FILES_ABSOLUTE_PATH).listFiles())
            .filter(l->!l.getName().startsWith(ExpertConfigurator.EXCLUDE_FILES_WITH_PREFIX))
            .collect(Collectors.toMap(l -> l.getName(), k -> k.length()));

    public static final int THREAD_SLEEP = 20000;
    public static final int CANDLE_LIST_SIZE_LIMIT = 100;
    public static final int HOW_MANY_CANDLES_CREATES_FB = 2;
    // VORTEX_DEEP - possible values are 1 or 2 deep means how many candles surrounding vortex (at each side) should be below high
    //for vortex high and vice versa for vortex low (should be scalable in future versions to any value).
    public static final int VORTEX_DEEP=2;

    public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
    public static final String MT4_DATE_FORMAT = "yyyy.MM.dd HH:mm";
    public static final String VORTEX_HIGH = "VORTEX_HIGH";

    public static final String VORTEX_LOW = "VORTEX_LOW";
    public static final String RESISTANCE_BREAKOUT = "-----------Resistance BreakOut ";
    public static final String SUPPORT_BREAKOUT = "-----------Support BreakOut " ;
    public static final String NO_CHANGES = "No new files or changes, waiting..";
    public static final String CHANGES = "New files to process ";
    public static final String NEXT_PAIR = "-----------------------------------NEXT PAIR-----------------------------------";
    public static final String SUPPORT = "Supports for ";
    public static final String RESISTANCE = "Resistances for ";
    public static final String UPTREND = "------------UPTREND--------------";
    public static final String DOWNTREND = "------------DOWNTREND------------";
    public static final String NO_TREND = "------------NO TREND------------";

    public static final String INPUT_PARSE_ERROR = "-------------------PARSE INPUT DATA(DATE) ERROR------------------------";
    public static final Boolean EMAIL_DEBUGGING = false ;
    public static final boolean EMAIL_ALLERT = Boolean.parseBoolean(CONFIG_MAP.get("email_allert"));

    public static final boolean Sound_ALLERT = Boolean.parseBoolean(CONFIG_MAP.get("sound_allert"));
    public static final String MAIL_HOST = CONFIG_MAP.get("mail_host");
    public static final int MAIL_PORT = Integer.parseInt(CONFIG_MAP.get("mail_port"));
    public static final String EMAIL_USER = CONFIG_MAP.get("email_user");
    public static final String EMAIL_PASSOWRD = CONFIG_MAP.get("email_password");
    public static final String EMAIL_USER_TO = CONFIG_MAP.get("email_user_to");
    public static final String EMAIL_USER_FROM = CONFIG_MAP.get("email_user_from");
    public static final String EMAIL_FROM_NAME = CONFIG_MAP.get("email_from_name");
    public static final String EMAIL_TO_NAME = CONFIG_MAP.get("email_to_name");
    public static final String EMAIL_SUBJECT_HEADER = CONFIG_MAP.get("email_subject_header");
    public static final boolean WRITE_BREAKOUT_FILES_FOR_MT4 = true;

}