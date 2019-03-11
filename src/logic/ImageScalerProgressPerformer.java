package logic;

import data.GridImage;
import gui.ImageGrid;
import gui.Progressbar;
import javax.swing.SwingWorker;

/**
 * This class initiates the scaling and displaying of {@link GridImage}'s within
 * a backround thread.
 */
public class ImageScalerProgressPerformer extends SwingWorker<GridImage[], Object> {

    Progressbar progressBarComponent;
    ImageGrid imageGalleryComponent;

    /**
     * Constructors
     * 
     * @param imageGalleryComponent GUI component ImageGridComponent.
     */
    public ImageScalerProgressPerformer(ImageGrid imageGalleryComponent) {

        this.progressBarComponent = new Progressbar(imageGalleryComponent);
        progressBarComponent.createProgressUI();
        this.progressBarComponent.getProgressBar().setVisible(true);
        this.progressBarComponent.getProgressBar().setIndeterminate(true);

        this.imageGalleryComponent = imageGalleryComponent;
    }

    @Override
    protected GridImage[] doInBackground() throws Exception {
        
        GridImage[] currentGalleryImages = ImageFileHandler.scaleCurrentGalleryImages();
        imageGalleryComponent.setInput(currentGalleryImages);
        
        return currentGalleryImages;
    }

    @Override
    protected void done() {
        progressBarComponent.getProgressBar().setVisible(false);
        progressBarComponent.setVisible(false);
    }

}
