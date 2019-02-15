package Gui;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * This class represents a progress bar with label.
 * 
 */
public class Progressbar extends JDialog {

    private final JLabel progressLabel;
    private final JProgressBar progressBar;
    
    private final JComponent component;
    

    /**
     * Constructor
     *
     * @param component The JComponent above which this progress bar is placed.
     */
    public Progressbar(JComponent component) {

        this.component = component;
        this.progressBar = new JProgressBar();
        progressLabel = new JLabel("Loading...");
    }
    
    /**
     * Setting up the component.
     */
    public void createProgressUI() {

        add(progressLabel, BorderLayout.PAGE_START);
        add(progressBar, BorderLayout.CENTER);
        
        setLocationRelativeTo(component);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setVisible(true);
        setSize(100, 40);
    }
    
    public JProgressBar getProgressBar() {
        return progressBar;
    }
}
