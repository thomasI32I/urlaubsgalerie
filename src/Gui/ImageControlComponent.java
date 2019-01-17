package Gui;

import logic.ImageFileHandler;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.ImageScalerProgressPerformer;

/**
 * This GUI component allows to change the image size of the images displayed in
 * the {@link ImageGridComponent}.
 */
public class ImageControlComponent extends JPanel {

    private final JLabel imageSizeLabel;
    private final JComboBox imageSizeSelection;
    
    private final ImageGridComponent imageGridComponent;
    
    /**
     * Listener to handle the image displaying with different image sizes. 
     */
    private class ImageSizeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            
            float scaleSize = Float.parseFloat((String) imageSizeSelection.getSelectedItem());
            float hundred = 100.0F;
            ImageFileHandler.setScalingFactor(scaleSize / hundred);
            
            ImageScalerProgressPerformer imageScalerProgressPerformer = new ImageScalerProgressPerformer(imageGridComponent);
            imageScalerProgressPerformer.execute();
        }
    }

    /**
     * Constructor
     * 
     * @param imageGalleryComponent 
     */
    public ImageControlComponent(ImageGridComponent imageGalleryComponent) {

        this.imageGridComponent = imageGalleryComponent;

        imageSizeLabel = new JLabel("Bildgröße in %:");
        String[] sizes = {"20", "40", "60", "80", "100"};
        imageSizeSelection = new JComboBox(sizes);
        imageSizeSelection.setSelectedItem("40");
        imageSizeSelection.addItemListener(new ImageSizeListener());

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(imageSizeLabel);
        add(imageSizeSelection);
    }

    //TODO: necessary?
//    public String getImageSize() {
//        return (String) imageSizeSelection.getSelectedItem();
//    }

}
