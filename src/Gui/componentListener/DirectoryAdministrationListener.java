package Gui.componentListener;

import Gui.componentEvent.DirectoryAdministrationEvent;
import java.util.EventListener;

/**
 *
 * 
 */
public interface DirectoryAdministrationListener extends EventListener {
    
    public void directorySelectionEventOccurred(DirectoryAdministrationEvent ev);
}
