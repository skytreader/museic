package net.skytreader.kode.museic.core;

import javafx.embed.swing.JFXPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JFXPlayer{

    /**
    Construct a new DelayPlayer with the delay specified in seconds.
    */
    public JFXPlayer(){
        JFXPanel foo = new JFXPanel();
    }

    @Override
    public void play(String filepath){
        String spaceEscape = filepath.replace(" ", "%20");
        String fileURI = "file://" + spaceEscape;

        Media mp3 = new Media(fileURI);
        MediaPlayer player = new MediaPlayer(mp3);
        player.play();
    }

}
