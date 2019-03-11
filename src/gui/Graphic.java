package gui;



import data.Directory;
import data.GridImage;
import data.ImageDataTableModel;
import MyTools.MyFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import logic.ImageFileHandler;
import logic.ImageReaderProgressPerformer;
import logic.ImageScalerProgressPerformer;

/**
 * Graphical user interface of the application.
 */
public class Graphic {

    // member variables
    final static String APPLICATION_NAME = "Fotogalerie";

    private MyFrame frame;
    private ImageControl imageControl; //TOP
    private DirectoryView directoryView; //LEFT
    private ImageGrid imageGrid; //CENTER
    private ImageMetaDataView imageMetaDataView; //RIGHT
    private DirectoryAdministration directoryAdministration; //END

    /**
     * Constructor
     *
     * @throws java.sql.SQLException
     */
    public Graphic() throws SQLException {

        /*
         * The complete Swing processing is done in a thread called
         * EDT (Event Dispatching Thread). Therefore you would block the GUI
         * if you would compute some long lasting calculations within this thread. 
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new MyFrame(APPLICATION_NAME);
                frame.setLayout(new BorderLayout(5, 5));

                imageControl = new ImageControl();
                directoryView = new DirectoryView();
                imageGrid = new ImageGrid();
                imageMetaDataView = new ImageMetaDataView();
                directoryAdministration = new DirectoryAdministration();

                wireComponents();

                //frame component building
                frame.add(imageControl, BorderLayout.PAGE_START);
                frame.add(directoryView, BorderLayout.LINE_START);
                frame.add(imageGrid, BorderLayout.CENTER);
                frame.add(imageMetaDataView, BorderLayout.LINE_END);
                frame.add(directoryAdministration, BorderLayout.PAGE_END);

                frame.show(1500, 600);
            }
        });
    }

    /**
     * Set gui components interaction with each other
     */
    private void wireComponents() {

        imageControl.setImageControlListener(new ImageControlListener() {
            @Override
            public void imageSizeEventOccurred(ImageControlEvent ev) {
                float hundred = 100.0F;
                ImageFileHandler.setScalingFactor(ev.getScaleSize() / hundred);

                ImageScalerProgressPerformer imageScalerProgressPerformer
                        = new ImageScalerProgressPerformer(imageGrid);
                imageScalerProgressPerformer.execute();
            }
        });

        directoryView.setDirectoryViewListener(new DirectoryViewListener() {
            @Override
            public void directorySelectionEventOccurred(DirectoryViewEvent ev) {
                imageMetaDataView.clear();
                String selectedDirectory = ev.getSelectedDirectory();
                ImageReaderProgressPerformer imageProgressPerformer
                        = new ImageReaderProgressPerformer(imageGrid, selectedDirectory);
                imageProgressPerformer.execute();
            }
        });

        imageGrid.setImageGridListener((ImageGridEvent imageSelectionEvent) -> {
            imageMetaDataView.clear();
            GridImage gridImage = imageSelectionEvent.getSelectedGridImage();
            if (gridImage != null) {
                imageMetaDataView.setInput(new ImageDataTableModel(gridImage.getMetaDataList()));
            }
        });

        directoryAdministration.setDirectoryAdminListener(new DirectoryAdministrationListener() {
            @Override
            public void directorySelectionEventOccurred(DirectoryAdministrationEvent ev) {
                Directory directoryToAdd
                        = new Directory(ev.getDirectory().getName(), ev.getDirectory().getAbsolutePath());
                directoryView.add(directoryToAdd);
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                imageGrid.updateLayout();
            }
        });
    }
}
