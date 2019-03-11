package data;



import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * An object of this class represents the element which is displayed
 * in the {@link Gui.ImageGridComponent}. Its member variable imageIcon stores
 * the actual image to display.
 */
public class GridImage {
    
    private final String name;
    private ImageIcon imageIcon;
    private final List<ImageMetaData> metaData;
    //TODO: add private Directory directory; ? 
    
    /**
     * Constructor
     * 
     * @param name
     * @param image 
     */
    public GridImage(String name, ImageIcon image) {
        this.name = name;
        this.imageIcon = image;
        metaData = new ArrayList<>();
    }

    /**
     * Constructor
     * 
     * @param name
     * @param imageIcon
     * @param metaDataList 
     */
    public GridImage(String name, ImageIcon imageIcon, List<ImageMetaData> metaDataList) {
        this.name = name;
        this.imageIcon = imageIcon;
        this.metaData = metaDataList;
    }

    /**
     * Copy constructor
     * 
     * @param gridImageToCopy 
     */
    public GridImage(GridImage gridImageToCopy) {
        //string is imutable
        this.name = gridImageToCopy.name;
        this.imageIcon = gridImageToCopy.imageIcon;
        this.metaData = gridImageToCopy.metaData;
    }
    
    public String getName() {
        return name;
    }
    
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    
    public List<ImageMetaData> getMetaDataList() {
        return metaData;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
