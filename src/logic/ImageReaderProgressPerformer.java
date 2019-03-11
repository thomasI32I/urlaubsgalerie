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

    Progressbar progressBar;
    ImageGrid imageGrid;

    String directoryPath;

    /**
     * Constructor
     * 
     * @param imageGridComponent. The component where {@link GridImage}'s are displayed.
     * @param directoryPath. The directory path.
     */
    public ImageReaderProgressPerformer(ImageGrid imageGridComponent,
            String directoryPath) {

        this.progressBar = new Progressbar(imageGridComponent);
        progressBar.createProgressUI();
        this.progressBar.getProgressBar().setVisible(true);
        this.progressBar.getProgressBar().setIndeterminate(true);

        this.imageGrid = imageGridComponent;
        this.directoryPath = directoryPath;
    }

    @Override
    protected GridImage[] doInBackground() throws Exception {

        GridImage[] images;
        images = ImageFileHandler.createGridImages(directoryPath);

        imageGrid.setInput(images);

        return images;
    }

    @Override
    protected void done() {
        progressBar.getProgressBar().setVisible(false);
        progressBar.setVisible(false);
    }

}
