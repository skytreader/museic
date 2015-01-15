package net.skytreader.kode.museic.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void setConfig(String configKey, String configVal){
        configFile.setProperty(configKey, configVal);
    }

    /**
    After calling setConfig to all the configKey=configVal combinations you
    want, call this to write the config file to disk.
    */
    public void writeOutConfig(String filepath) throws ClassCastException,
      IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(filepath));
        configFile.list(pw);
    }
}
