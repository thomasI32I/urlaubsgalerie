package data;



import com.drew.lang.annotations.NotNull;

/**
 * Represents meta data of an image, like image name, image size and EXIF data.
 */
public class ImageMetaData {

    private final String property;
    private final String value;

    /**
     * Constructor
     * 
     * @param property Name of the property.
     * @param value Value of the property.
     */
    public ImageMetaData(@NotNull String property, String value) {        
        this.property = property; 
        this.value = (value != null) ? value : "";
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return  '{' + "Property = " + property + ", Value = " + value + '}';
    }
    
}
