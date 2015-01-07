package net.skytreader.kode.museic;

import java.awt.EventQueue;

import net.skytreader.kode.museic.ui.MuseicRunnable;

/**
 * Hello world!
 *
 */
public class App{
    
    public static final String APP_NAME = "Museic";

    public static void main(String[] args) throws Exception{
        EventQueue.invokeLater(new MuseicRunnable());
    }
}
