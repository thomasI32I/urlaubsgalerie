package Gui;

import Gui.componentEvent.ImageControlEvent;
import Gui.componentListener.ImageControlListener;
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
 * the {@link ImageGrid}.
 */
public class ImageControl extends JPanel {

    private final JLabel imageSizeLabel;
    private final JComboBox imageSizeSelection;
    
    private ImageControlListener imageControlListener;
    
    /**
     * Listener to handle the image displaying with different image sizes. 
     */
    private class ImageSizeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            float scaleSize = Float.parseFloat((String) imageSizeSelection.getSelectedItem());
            imageControlListener.imageSizeEventOccurred(new ImageControlEvent(ImageControl.this, scaleSize));
        }
    }

    /**
     * Constructor
     * 
     */
    public ImageControl() {
        
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

    public void setImageControlListener(ImageControlListener imageControlListener) {
        this.imageControlListener = imageControlListener;
    }

}
