package gui;



import java.util.EventListener;

/**
 *
 *
 */
public interface DirectoryViewListener extends EventListener {
    
    public void directorySelectionEventOccurred(DirectoryViewEvent ev);
}