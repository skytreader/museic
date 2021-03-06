package net.skytreader.kode.museic.ui;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

import javafx.embed.swing.JFXPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;

import javax.swing.filechooser.FileNameExtensionFilter;

import net.skytreader.kode.museic.core.JFXPlayer;
import net.skytreader.kode.museic.core.Player;

import net.skytreader.kode.museic.utils.ConfigReader;

public class MuseicRunnable implements Runnable{
    private Component parentComponent;
    private String filePathState;
    
    private JButton pause;
    private JButton play;
    private JButton stop;
    private JFileChooser mp3Chooser;
    private JFrame mainFrame;
    private JLabel countdownLabel;
    private JLabel filePathLabel;
    private JSlider seekBar;
    private JTextField delayField;
    
    private ConfigReader cfgReader;
    private Player museicPlayer;

    private final int DEFAULT_DELAY = 4;
    private final int DELAY_MS_UNIT = 1000;
    private final int DEFAULT_HEIGHT = 400;
    private final int DEFAULT_WIDTH = 400;

    // Label prefixes
    private final String FILEPATH_INDICATOR_LBL = "Loaded: ";
    private final String DELAY_CONFIG_LBL = "Delay (in seconds): ";

    public MuseicRunnable(String configFilePath) throws FileNotFoundException,
      IOException{
        JFXPanel foo = new JFXPanel();
        museicPlayer = new JFXPlayer();
        mp3Chooser = new JFileChooser();
        FileNameExtensionFilter mp3Filter = new FileNameExtensionFilter(
          "MP3 Files", "mp3");
        mp3Chooser.setFileFilter(mp3Filter);

        File configFile = new File(configFilePath);

        if(configFile.exists()){
            BufferedReader cfgBr = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            cfgReader = new ConfigReader(cfgBr);
            mp3Chooser.setCurrentDirectory(new File(cfgReader.getLastLoadDirectory()));
        } else{
            cfgReader = new ConfigReader();
        }
    }
   
    @Override
    public void run(){

        mainFrame = new JFrame(net.skytreader.kode.museic.App.APP_NAME);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Container mainFrameContainer = mainFrame.getContentPane();
        parentComponent = mainFrameContainer;
        mainFrameContainer.setLayout(new BoxLayout(mainFrameContainer, BoxLayout.Y_AXIS));

        JPanel filePanel = new JPanel();
        JButton filePrompt = new JButton("Choose MP3 file");
        filePrompt.addActionListener(new ChooseMP3Listener());
        filePathLabel = new JLabel(FILEPATH_INDICATOR_LBL + "None");
        filePanel.add(filePrompt);
        filePanel.add(filePathLabel);
        mainFrameContainer.add(filePanel);

        JPanel delayPanel = new JPanel();
        JLabel delayIndicatorLabel = new JLabel(DELAY_CONFIG_LBL);
        delayField = new JTextField("" + DEFAULT_DELAY, 2);
        delayPanel.add(delayIndicatorLabel);
        delayPanel.add(delayField);
        mainFrameContainer.add(delayPanel);

        countdownLabel = new JLabel("Stopped");
        Font countdownFont = countdownLabel.getFont();
        countdownLabel.setFont(new Font(countdownFont.getFontName(), countdownFont.getStyle(), 25));
        countdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainFrameContainer.add(countdownLabel);

        seekBar = new JSlider();
        seekBar.setMaximum(0);
        seekBar.setMinimum(0);
        seekBar.setValue(0);
        mainFrameContainer.add(seekBar);

        JPanel controlPanel = new JPanel();
        play = new JButton("Play");
        play.addActionListener(new PlayListener());
        play.setEnabled(false);
        pause = new JButton("Pause");
        pause.setEnabled(false);
        pause.addActionListener(new PauseListener());
        stop = new JButton("Stop");
        stop.addActionListener(new StopListener());
        stop.setEnabled(false);
        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(stop);
        mainFrameContainer.add(controlPanel);

        mainFrame.setVisible(true);
    }

    /**
    Call this whenever the status of the Player is STATUS_STOPPED.
    */
    private void setUIStateStopped(){
        countdownLabel.setText("Stopped");
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
    }

    /**
    Call this whenever the status of the Player is STATUS_PLAYING.
    */
    private void setUIStatePlaying(){
        play.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(true);
    }

    /**
    Call this whenever the status of the Player is STATUS_PAUSED.
    */
    private void setUIStatePaused(){
        countdownLabel.setText("Paused");
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(true);
    }

    private class ChooseMP3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            int resultVal = mp3Chooser.showOpenDialog(parentComponent);

            if(resultVal == JFileChooser.APPROVE_OPTION){
                File f = mp3Chooser.getSelectedFile();
                cfgReader.setConfig("last-load-directory", f.getAbsolutePath());
                // Actually, I think we should do this on close rather than on every config change.
                try{
                    cfgReader.writeOutConfig(System.getProperty("user.home") +
                      System.getProperty("file.separator") +
                      net.skytreader.kode.museic.App.CONFIG_FILE);
                } catch(Exception e){
                    e.printStackTrace();
                }
                filePathLabel.setText(FILEPATH_INDICATOR_LBL + f.getName());
                filePathState = f.getAbsolutePath();
                museicPlayer.stop();
                setUIStateStopped();
            }
        }
    }

    private class TimerStoppable{
        private Timer countdownTimer;

        public TimerStoppable(int delay){
            countdownTimer = new Timer(DELAY_MS_UNIT, new CountdownTimerListener(delay, this));
        }

        public void startTimer(){
            countdownTimer.start();
        }
        public void stopTimer(){
            countdownTimer.stop();
        }
    }

    private class PlayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            // Countdown first...
            int delay = Integer.parseInt(delayField.getText());
            TimerStoppable ts = new TimerStoppable(delay);
            ts.startTimer();
        }
    }

    /**
    Note: This method is very prone to race conditions.
    */
    private class DurationChecker implements Runnable{
        private int secondsElapsed = 0;
        @Override
        public void run(){
            while(true){
                if(museicPlayer.getCurrentStatus() != Player.STATUS_PLAYING){
                    museicPlayer.stop();
                    setUIStateStopped();
                    break;
                }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ie){
                    ie.printStackTrace();
                }
                if(museicPlayer.getCurrentStatus() == Player.STATUS_PLAYING){
                    secondsElapsed++;
                    seekBar.setValue(seekBar.getValue() + 1);
                }
            }
        }
    }

    private class PauseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            museicPlayer.pause();
            setUIStatePaused();
        }
    }

    private class StopListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            museicPlayer.stop();
            setUIStateStopped();
        }
    }

    private class CountdownTimerListener implements ActionListener{
        private int start;
        private TimerStoppable stopper;

        public CountdownTimerListener(int start, TimerStoppable stopper){
            this.start = start;
            this.stopper = stopper;
        }

        @Override
        public void actionPerformed(ActionEvent ae){
            if(start == 0){
                countdownLabel.setText("Playing");
                if(museicPlayer.getCurrentStatus() == Player.STATUS_PAUSED){
                    museicPlayer.play();
                } else if(museicPlayer.getCurrentStatus() == Player.STATUS_STOPPED){
                    seekBar.setValue(0);
                    seekBar.setMaximum(museicPlayer.getTrackLength());
                    museicPlayer.play(filePathState);
                    new Thread(new DurationChecker()).start();
                }
                setUIStatePlaying();
                stopper.stopTimer();
            } else{
                countdownLabel.setText("" + start);
                start--;
            }
        }
    }

}
