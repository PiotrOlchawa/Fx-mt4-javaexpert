package org.mt4expert.javaexpert.reporter;

import org.mt4expert.javaexpert.Commander;
import org.mt4expert.javaexpert.audioservice.AudioAllert;
import org.mt4expert.javaexpert.interpreter.FalseBreakOutInterpreter;
import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.mt4expert.javaexpert.data.Candle;
import org.mt4expert.javaexpert.data.CandleData;
import org.mt4expert.javaexpert.datareader.CandleDataImporter;
import org.mt4expert.javaexpert.finder.SupportResistanceFinder;
import org.mt4expert.javaexpert.finder.VortexFinder;
import org.mt4expert.javaexpert.interpreter.FalseBreakoutData;
import org.mt4expert.javaexpert.mailservice.SimpleMailComposer;
import org.mt4expert.javaexpert.mailservice.SimpleMailSender;
import org.mt4expert.javaexpert.mt4.BreakoutFilesWriterForMt4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SRReporter {

    static final Logger LOGGER = LoggerFactory.getLogger(SupportResistanceFinder.class);

    private String fullPathFilename;

    public SRReporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    public void report()  {
        CandleDataImporter candleDataImporter = new CandleDataImporter(fullPathFilename);
        CandleData candleData = new CandleData(candleDataImporter.importCandles());
        VortexFinder vortexFinder = new VortexFinder(candleData);
        if(LOGGER.isDebugEnabled()){
            vortexFinder.findVortexes().entrySet().stream()
                    .filter(l->l.getValue().equals(ExpertConfigurator.VORTEX_HIGH)).forEach(System.out::println);
        }
        SupportResistanceFinder supportResistanceFinder = new SupportResistanceFinder(vortexFinder.findVortexes());
        supportResistanceFinder.findSupportsAndResistances();
        SimpleDateFormat sdf = new SimpleDateFormat(ExpertConfigurator.DATE_FORMAT);

        Set<Candle> resistanceSet =new HashSet<>();
        Set<Candle> supportSet =new HashSet<>();

        List<Candle> supportCandleList = supportResistanceFinder.getSupports().getSupportCandlesList();
        int supportCandles = supportCandleList.size();

        if (supportCandles > 0) {
            Commander.showSupports(supportCandleList.get(0).getSymbol(), supportCandleList.get(0).getPeriodInReadableFormat());
            supportSet = new HashSet<>(supportResistanceFinder.getSupports().getSupportCandlesList());
            supportSet.forEach(l -> System.out.println(" | " + sdf.format(l.getDate()) + " | " +l.getSRType()+ " | " +  l.getLow()));
        }

        List<Candle> resistanceCandleList = supportResistanceFinder.getResistances().getResistanceCandlesList();
        int resistanceCandles = resistanceCandleList.size();

        if (resistanceCandles > 0) {
            Commander.showResistances(resistanceCandleList.get(0).getSymbol(), resistanceCandleList.get(0).getPeriodInReadableFormat());
            resistanceSet = new HashSet<>(supportResistanceFinder.getResistances().getResistanceCandlesList());
            resistanceSet.forEach(l -> System.out.println(" | " + sdf.format(l.getDate()) + " | " +l.getSRType()+ " | " +  l.getHigh()));
        }

        Commander.showUptrendOrDowntrend(supportCandles - resistanceCandles);
        FalseBreakOutInterpreter falseBreakOutInterpreter =
                new FalseBreakOutInterpreter(supportResistanceFinder.getSupports(), supportResistanceFinder.getResistances());


        FalseBreakoutData falseBreakoutData = falseBreakOutInterpreter.checkForBreakOut(candleData,resistanceSet,supportSet);
        Optional<FalseBreakoutData> optionalFalseBreakoutData = Optional.ofNullable(falseBreakoutData);

        if (optionalFalseBreakoutData.isPresent()) {
            if(ExpertConfigurator.WRITE_BREAKOUT_FILES_FOR_MT4){
                BreakoutFilesWriterForMt4 breakoutFilesWriterForMt4 = new BreakoutFilesWriterForMt4(optionalFalseBreakoutData.get());
                breakoutFilesWriterForMt4.write();
            }
            if(ExpertConfigurator.Sound_ALLERT){
                AudioAllert.allert();
            }
            if(ExpertConfigurator.EMAIL_ALLERT) {
                SimpleMailComposer simpleMailComposer = new SimpleMailComposer(optionalFalseBreakoutData.get());
                SimpleMailSender simpleMailSender = new SimpleMailSender(simpleMailComposer);
                simpleMailSender.sendmail();
            }
        }
    }
}
