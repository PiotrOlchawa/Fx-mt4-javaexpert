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

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SRReporter {

    private String fullPathFilename;

    public SRReporter(String fullPathFilename) {
        this.fullPathFilename = fullPathFilename;
    }

    public void report()  {
        CandleDataImporter candleDataImporter = new CandleDataImporter(fullPathFilename);
        CandleData candleData = new CandleData(candleDataImporter.importCandles());
        VortexFinder vortexFinder = new VortexFinder(candleData);
        SupportResistanceFinder supportResistanceFinder = new SupportResistanceFinder(vortexFinder.findVortexes());
        supportResistanceFinder.findSupportsAndResistances();
        SimpleDateFormat sdf = new SimpleDateFormat(ExpertConfigurator.DATE_FORMAT);

        Map<Date, Double> resistanceMap= new HashMap<>();
        Map<Date, Double> supportMap =new HashMap<>();

        List<Candle> supportCandleList = supportResistanceFinder.getSupports().getSupportCandlesList();
        int supportCandles = supportCandleList.size();

        if (supportCandles > 0) {
            Commander.showSupports(supportCandleList.get(0).getSymbol(), supportCandleList.get(0).getPeriodInReadableFormat());

            supportMap = supportResistanceFinder.getSupports().getSupportCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose));
            supportMap.entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }

        List<Candle> resistanceCandleList = supportResistanceFinder.getResistances().getResistanceCandlesList();
        int resistanceCandles = resistanceCandleList.size();

        if (resistanceCandles > 0) {
            Commander.showResistances(resistanceCandleList.get(0).getSymbol(), resistanceCandleList.get(0).getPeriodInReadableFormat());

            resistanceMap = supportResistanceFinder.getResistances().getResistanceCandlesList().stream()
                    .collect(Collectors.toSet()).stream()
                    .collect(Collectors.toMap(Candle::getDate, Candle::getClose));
            resistanceMap.entrySet().forEach(l -> System.out.println(" | " + sdf.format(l.getKey()) + " | " + l.getValue()));
        }

        Commander.showUptrendOrDowntrend(supportCandles - resistanceCandles);
        FalseBreakOutInterpreter falseBreakOutInterpreter =
                new FalseBreakOutInterpreter(supportResistanceFinder.getSupports(), supportResistanceFinder.getResistances());

        FalseBreakoutData falseBreakoutData = falseBreakOutInterpreter.checkForBreakOut(candleData,resistanceMap,supportMap);
        Optional<FalseBreakoutData> optionalFalseBreakoutCandle = Optional.ofNullable(falseBreakoutData);

        if (optionalFalseBreakoutCandle.isPresent()) {
            if(ExpertConfigurator.Sound_ALLERT){
                AudioAllert.allert();
            }
            if(ExpertConfigurator.EMAIL_ALLERT) {
                SimpleMailComposer simpleMailComposer = new SimpleMailComposer(optionalFalseBreakoutCandle.get());
                SimpleMailSender simpleMailSender = new SimpleMailSender(simpleMailComposer);
                simpleMailSender.sendmail();
            }
        }
    }
}
