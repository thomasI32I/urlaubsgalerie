package Gui.componentListener;

import Gui.componentEvent.ImageGridEvent;
import java.util.EventListener;

/**
 *
 * 
 */
public interface ImageGridListener extends EventListener {
    
    public void imageSelectionEventOccurred(ImageGridEvent imageSelectionEvent);
}
