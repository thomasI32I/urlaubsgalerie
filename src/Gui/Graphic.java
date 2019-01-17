package Gui;

import MyTools.MyFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Graphical user interface of the application.
 *
 */
public class Graphic {

    // member variables
    final static String APPLICATION_NAME = "Fotogalerie";

    private MyFrame frame;
    private ImageControlComponent imageControlComponent; //TOP
    private DirectoryListComponent directoryListComponent; //LEFT
    private ImageGridComponent imageGridComponent; //CENTER
    private MetaDataComponent metaDataComponent; //RIGHT
    private DirectoryAdministrationComponent directoryAdministration; //END

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

                metaDataComponent = new MetaDataComponent();
                imageGridComponent = new ImageGridComponent(metaDataComponent);
                
                try {
                    directoryListComponent = new DirectoryListComponent(imageGridComponent, metaDataComponent);
                } catch (SQLException ex) {
                    Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                directoryAdministration = new DirectoryAdministrationComponent(directoryListComponent);
                imageControlComponent = new ImageControlComponent(imageGridComponent);

                //frame component building
                frame.add(imageControlComponent, BorderLayout.PAGE_START);
                frame.add(directoryListComponent, BorderLayout.LINE_START);
                frame.add(imageGridComponent, BorderLayout.CENTER);
                frame.add(metaDataComponent, BorderLayout.LINE_END);
                frame.add(directoryAdministration, BorderLayout.PAGE_END);

                frame.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        imageGridComponent.updateLayout();
                    }
                });
                frame.show(1500, 600);
            }
        });
    }
}
