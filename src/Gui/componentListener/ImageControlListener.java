package Gui.componentListener;

import Gui.componentEvent.ImageControlEvent;
import java.util.EventListener;

/**
 *
 * 
 */
public interface ImageControlListener extends EventListener {
    
    public void imageSizeEventOccurred(ImageControlEvent ev);  
}
