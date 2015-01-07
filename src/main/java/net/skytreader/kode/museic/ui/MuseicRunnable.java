package net.skytreader.kode.museic.ui;

import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MuseicRunnable implements Runnable{
    
    private JFrame mainFrame;
    private JLabel filePathLabel;

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

        JPanel filePanel = new JPanel();
        JButton filePrompt = new JButton("Choose MP3 file");
        filePathLabel = new JLabel("Currently playing: ");
        filePanel.add(filePrompt);
        filePanel.add(filePathLabel);
        mainFrameContainer.add(filePanel);

        JPanel controlPanel = new JPanel();

        mainFrame.setVisible(true);
    }

}
