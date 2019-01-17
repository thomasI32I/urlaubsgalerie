package Data;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Table model used to provide data for the GUI component {@link Gui.MetaDataComponent}.
 * 
 */
public class ImageDataTableModel implements TableModel {
    
    private final int NUMBER_COLUMNS = 2;
    
    private final List<ImageMetaData> imageMetaDataList;
    private final String[][] matrix;
    private final String[] titles;
    
    /**
     * Constructor.
     * 
     * @param imageMetaDataList_ List of {@link ImageMetaData}
     */
    public ImageDataTableModel(List<ImageMetaData> imageMetaDataList_) {
        
        imageMetaDataList = (imageMetaDataList_ != null) ? imageMetaDataList_ : new ArrayList<>();
        
        matrix = new String[NUMBER_COLUMNS][imageMetaDataList.size()];
        for (int y = 0; y < imageMetaDataList.size(); y++) {
            matrix[0][y] = imageMetaDataList.get(y).getProperty();            
            matrix[1][y] = imageMetaDataList.get(y).getValue();
        }
        
        titles = new String[NUMBER_COLUMNS];
        titles[0] = "Property";
        titles[1] = "Value";
    }
    
    /**
     *
     * @return Number of rows of this table
     */
    @Override
    public int getRowCount() {
        return imageMetaDataList.size();
    }
    
    /**
     *
     * @return Number of columns of this table
     */
    @Override
    public int getColumnCount() {
        return NUMBER_COLUMNS;
    }
    
    /**
     * Ueberschrift am gefragten Index
     *
     * @param x Spaltennummer
     * @return Ueberschrift an Spaltennummer
     */
    @Override
    public String getColumnName(int x) {
        return titles[x];
    }
    
    /**
     * Welchen Datentyp hat diee Spalte am Index x?
     *
     * @param x Spaltennummer
     * @return Datentyp der Spalte x
     */
    @Override
    public Class<?> getColumnClass(int x) {
        return String.class;
    }
    
    /**
     * Ist die Zelle an Position (x, y) editierbar?
     *
     * @param y
     * @param x
     * @return Editierbar oder nicht
     */
    @Override
    public boolean isCellEditable(int y, int x) {
        return false;
    }
    
    /**
     * Wert in Matrixzelle (x, y)
     *
     * @param y
     * @param x
     * @return Wert
     */
    @Override
    public Object getValueAt(int y, int x) {
        return matrix[x][y];
    }
    
    /**
     * Den Wert "o" an Indexposition speichern.
     *
     * @param o
     * @param y
     * @param x
     */
    @Override
    public void setValueAt(Object o, int y, int x) {
    }
    
    @Override
    public void addTableModelListener(TableModelListener tl) {
    }
    
    @Override
    public void removeTableModelListener(TableModelListener tl) {
    }
}
