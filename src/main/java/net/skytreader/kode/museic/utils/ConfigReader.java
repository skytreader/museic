package net.skytreader.kode.museic.utils;

import java.io.IOException;
import java.io.Reader;

import java.util.Properties;

public class ConfigReader{
    
    private Properties configFile;
    
    public ConfigReader(Reader r) throws IOException{
        configFile = new Properties();
        configFile.load(r);
    }
    
    /**
    last-load-directory
    */
    public String getLastLoadDirectory(){
        return configFile.getProperty("last-load-directory");
    }
}
