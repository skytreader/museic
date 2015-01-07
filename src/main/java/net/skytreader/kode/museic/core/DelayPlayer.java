package net.skytreader.kode.museic.core;

import javafx.embed.swing.JFXPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DelayPlayer{
    
    private int delay;

    /**
    Construct a new DelayPlayer with the delay specified in seconds.
    */
    public DelayPlayer(int delay){
        this.delay = delay;
        JFXPanel foo = new JFXPanel();
    }

    public void play(String filepath) throws InterruptedException{
        String spaceEscape = filepath.replace(" ", "%20");
        String fileURI = "file://" + spaceEscape;

        Media mp3 = new Media(fileURI);
        MediaPlayer player = new MediaPlayer(mp3);
        Thread.sleep(delay);
        player.play();
    }

}
