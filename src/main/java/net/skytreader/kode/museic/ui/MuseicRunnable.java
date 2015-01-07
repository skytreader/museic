package net.skytreader.kode.museic.ui;

import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class MuseicRunnable implements Runnable{
    
    private JFrame mainFrame;
    private JLabel filePathLabel;
    private JProgressBar seekBar;

    private final int DEFAULT_WIDTH = 500;
    private final int DEFAULT_HEIGHT = 500;
    
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
        mainFrameContainer.setLayout(new BoxLayout(mainFrameContainer, BoxLayout.Y_AXIS));

        JPanel filePanel = new JPanel();
        JButton filePrompt = new JButton("Choose MP3 file");
        filePathLabel = new JLabel("Currently playing: ");
        filePanel.add(filePrompt);
        filePanel.add(filePathLabel);
        mainFrameContainer.add(filePanel);

        seekBar = new JProgressBar();
        mainFrameContainer.add(seekBar);

        JPanel controlPanel = new JPanel();
        JButton play = new JButton("Play");
        JButton pause = new JButton("Pause");
        JButton stop = new JButton("Stop");
        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(stop);
        mainFrameContainer.add(controlPanel);

        mainFrame.setVisible(true);
    }

}
