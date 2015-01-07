package net.skytreader.kode.museic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if(args.length != 1){
            System.out.println("Please supply mp3 file to play.");
            System.exit(1);
        }

        System.out.println(args[0]);
        Media mp3 = new Media(args[0]);
        MediaPlayer player = new MediaPlayer(mp3);
        player.play();
    }
}
