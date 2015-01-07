package net.skytreader.kode.museic;

import javafx.embed.swing.JFXPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import net.skytreader.kode.museic.core.DelayPlayer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        if(args.length != 2){
            System.out.println("Please supply mp3 file to play and delay in seconds.");
            System.exit(1);
        }

        int delay = Integer.parseInt(args[1]);
        DelayPlayer dp = new DelayPlayer(delay);
        dp.play(args[0]);
    }
}
