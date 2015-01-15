package net.skytreader.kode.museic;

import java.awt.EventQueue;

import javax.swing.UIManager;

import net.skytreader.kode.museic.ui.MuseicRunnable;

/**
 * Hello world!
 *
 */
public class App{
    
    public static final String APP_NAME = "Museic";
    public static final String CONFIG_FILE = ".museic";

    public static void main(String[] args) throws Exception{
        String separator = System.getProperty("file.seperator");
        String homeDir = System.getProperty("user.home");
        String configFull = homeDir + separator + CONFIG_FILE;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new MuseicRunnable(configFull));
    }
}
