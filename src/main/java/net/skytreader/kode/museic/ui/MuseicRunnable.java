package net.skytreader.kode.museic.ui;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import java.io.File;

import javafx.embed.swing.JFXPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class MuseicRunnable implements Runnable{
    private Component parentComponent;
    private String filePathState;
    
    private JFileChooser mp3Chooser = new JFileChooser();
    private JFrame mainFrame;
    private JLabel countdownLabel;
    private JLabel filePathLabel;
    private JProgressBar seekBar;
    private JTextField delayField;

    private final int DEFAULT_DELAY = 4;
    private final int DEFAULT_HEIGHT = 400;
    private final int DEFAULT_WIDTH = 400;

    public MuseicRunnable(){
        JFXPanel foo = new JFXPanel();
    }
    
    @Override
    public void run(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        mainFrame = new JFrame(net.skytreader.kode.museic.App.APP_NAME);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Container mainFrameContainer = mainFrame.getContentPane();
        parentComponent = mainFrameContainer;
        mainFrameContainer.setLayout(new BoxLayout(mainFrameContainer, BoxLayout.Y_AXIS));

        JPanel filePanel = new JPanel();
        JButton filePrompt = new JButton("Choose MP3 file");
        filePrompt.addActionListener(new ChooseMP3Listener());
        filePathLabel = new JLabel("Playing: None");
        filePanel.add(filePrompt);
        filePanel.add(filePathLabel);
        mainFrameContainer.add(filePanel);

        JPanel delayPanel = new JPanel();
        JLabel delayIndicatorLabel = new JLabel("Delay (in seconds): ");
        delayField = new JTextField("" + DEFAULT_DELAY, 2);
        delayPanel.add(delayIndicatorLabel);
        delayPanel.add(delayField);
        mainFrameContainer.add(delayPanel);

        countdownLabel = new JLabel("Playing in: NA");
        mainFrameContainer.add(countdownLabel);

        seekBar = new JProgressBar();
        mainFrameContainer.add(seekBar);

        JPanel controlPanel = new JPanel();
        JButton play = new JButton("Play");
        play.addActionListener(new PlayListener());
        JButton pause = new JButton("Pause");
        JButton stop = new JButton("Stop");
        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(stop);
        mainFrameContainer.add(controlPanel);

        mainFrame.setVisible(true);
    }

    private class ChooseMP3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            int resultVal = mp3Chooser.showOpenDialog(parentComponent);

            if(resultVal == JFileChooser.APPROVE_OPTION){
                File f = mp3Chooser.getSelectedFile();
                filePathLabel.setText("Playing: " + f.getName());
                filePathState = f.getAbsolutePath();
            }
        }
    }

    private class PlayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            // Countdown first...
            int delay = Integer.parseInt(delayField.getText());
            for(int i = 0; i < delay; i++){
                countdownLabel.setText("Playing in: " + (delay - i));
                countdownLabel.repaint();
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }

            countdownLabel.setText("Playing in: Now Playing");
        }
    }

}
