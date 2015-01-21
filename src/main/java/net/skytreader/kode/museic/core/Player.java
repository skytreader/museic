package net.skytreader.kode.museic.core;

/**
Players are stateful objects.

@author Chad Estioco
*/
public interface Player{
    
    public static final int STATUS_STOPPED = 0;
    public static final int STATUS_PLAYING = 1;
    public static final int STATUS_PAUSED = 2;

    /**
    Return the state of this player, see constants STATUS_STOPPED, STATUS_PLAYING,
    and STATUS_PAUSED.
    */
    public int getCurrentStatus();
    /**
    Play the mp3 file indicated in the filepath.
    */
    public void play(String filepath);
    /**
    Play the last track given play with String argument. If track is paused,
    invoking play should start from where pause was invoked.
    */
    public void play();
    /**
    Pause whatever is playing, if any.
    */
    public void pause();
    /**
    Stop whatever is playing, if any.
    */
    public void stop();
    /**
    Returns true if the track is done playing.
    */
    public boolean isDonePlaying();
}
