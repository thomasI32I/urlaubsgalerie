package logic;

import Data.GridImage;
import Gui.ImageGrid;
import Gui.Progressbar;
import javax.swing.SwingWorker;

/**
 * This class initiates the loading and displaying of {@link GridImage}'s within
 * a backround thread.
 */
public class ImageReaderProgressPerformer extends SwingWorker<GridImage[], Object> {

    Progressbar progressBarComponent;
    ImageGrid imageGridComponent;

    String directoryPath;

    /**
     * Constructor
     * 
     * @param imageGridComponent. The component where {@link GridImage}'s are displayed.
     * @param directoryPath. The directory path.
     */
    public ImageReaderProgressPerformer(ImageGrid imageGridComponent,
            String directoryPath) {

        this.progressBarComponent = new Progressbar(imageGridComponent);
        progressBarComponent.createProgressUI();
        this.progressBarComponent.getProgressBar().setVisible(true);
        this.progressBarComponent.getProgressBar().setIndeterminate(true);

        this.imageGridComponent = imageGridComponent;
        this.directoryPath = directoryPath;
    }

    @Override
    protected GridImage[] doInBackground() throws Exception {

        GridImage[] images;
        images = ImageFileHandler.createGridImages(directoryPath);

        imageGridComponent.setInput(images);

        return images;
    }

    @Override
    protected void done() {
        progressBarComponent.getProgressBar().setVisible(false);
        progressBarComponent.setVisible(false);
    }

}
