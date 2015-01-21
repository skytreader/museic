package net.skytreader.kode.museic.core;

import javafx.embed.swing.JFXPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

public class JFXPlayer implements Player{
    private MediaPlayer player;
    private int currentStatus;

    /**
    Construct a new DelayPlayer with the delay specified in seconds.
    */
    public JFXPlayer(){
        JFXPanel foo = new JFXPanel();
        currentStatus = Player.STATUS_STOPPED;
    }

    @Override
    public void play(String filepath){
        String spaceEscape = filepath.replace(" ", "%20");
        String fileURI = "file://" + spaceEscape;

        Media mp3 = new Media(fileURI);
        player = new MediaPlayer(mp3);
        player.play();
        currentStatus = Player.STATUS_PLAYING;
    }

    @Override
    public void play(){
        if(player == null){
        } else{
            player.play();
            currentStatus = Player.STATUS_PLAYING;
        }
    }

    @Override
    public void pause(){
        if(player == null){
        } else{
            player.pause();
            currentStatus = Player.STATUS_PAUSED;
        }
    }

    @Override
    public void stop(){
        if(player == null){
        } else{
            player.stop();
            currentStatus = Player.STATUS_STOPPED;
        }
    }

    @Override
    public int getCurrentStatus(){
        return currentStatus;
    }

    @Override
    public boolean isDonePlaying(){
        Duration totalLength = player.getTotalDuration();
        return totalLength.equals(player.getCurrentTime());
    }

}
