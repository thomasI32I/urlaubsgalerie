package Gui;

import Data.ImageDataTableModel;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * This GUI component visualizes a {@link Data.GridImage}'s meta data.
 * 
 */
public class ImageMetaDataView extends JPanel {
    
    private final JTable metaDataTable;
    private final JLabel tableLabel;

    /**
     * Constructor
     */
    public ImageMetaDataView() {
        
        metaDataTable = new JTable();
        metaDataTable.setModel(new ImageDataTableModel(new ArrayList<>()));
        
        tableLabel = new JLabel("Bild-Metadaten");
        //dataTableLabel.setBackground(Color.red);
        tableLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(tableLabel);
        add(Box.createVerticalStrut(10));
        add(new JScrollPane(metaDataTable));
    }
    
    
    public void setInput(ImageDataTableModel inputData) {
        metaDataTable.setModel(inputData);
    }
    
    /**
     * Clears the input of this component.
     */
    public void clear() {
        metaDataTable.setModel(new ImageDataTableModel(new ArrayList<>()));
    }
    
}
