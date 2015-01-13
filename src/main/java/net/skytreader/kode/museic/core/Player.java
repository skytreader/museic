package net.skytreader.kode.museic.core;

/**
Players are stateful objects.

@author Chad Estioco
*/
public interface Player{
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
}
