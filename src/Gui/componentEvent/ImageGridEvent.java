package Gui.componentEvent;

import Data.GridImage;
import java.util.EventObject;

/**
 * 
 */
public class ImageGridEvent extends EventObject {

    private GridImage selectedImage;
    
    /**
     * Constructor
     * 
     * @param source 
     */
    public ImageGridEvent(Object source) {
        super(source);
        selectedImage = null;
    }
    
    /**
     * Constructor
     * 
     * @param source
     * @param selectedGridImage 
     */
    public ImageGridEvent(Object source, GridImage selectedGridImage) {
        super(source);
        this.selectedImage = selectedGridImage;
    }
    
    public GridImage getSelectedGridImage() {
        return selectedImage;
    }

    public void setSelectedGridImage(GridImage selectedGridImage) {
        this.selectedImage = selectedGridImage;
    }
    
}
