package org.mt4expert.javaexpert.audioservice;

import org.mt4expert.javaexpert.config.ExpertConfigurator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class AudioAllert {

    public static void allert()  {

        URL url = ExpertConfigurator.WAVE_ALLERT_FILENAME;
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(AudioSystem.getAudioInputStream(url));
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        clip.start();
    }
}
