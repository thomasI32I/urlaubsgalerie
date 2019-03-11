package gui;




import java.util.EventObject;

/**
 *
 * 
 */
public class ImageControlEvent extends EventObject {

    private float scaleSize;
    
    public ImageControlEvent(Object source) {
        super(source);
        scaleSize = 1.0f;
    }

    public ImageControlEvent(Object source, float scaleSize) {
        super(source);
        this.scaleSize = scaleSize;
    }

    public float getScaleSize() {
        return scaleSize;
    }

    public void setScaleSize(float scaleSize) {
        this.scaleSize = scaleSize;
    }
}
