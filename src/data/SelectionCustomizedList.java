package data;



import java.awt.Point;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 * Customized JList to achieve improved item selection behaviour.
 * 
 * @param <T> Type parameter.
 */
public class SelectionCustomizedList<T> extends JList<T> {
    
    
    public SelectionCustomizedList() {
        super();
    }

    public SelectionCustomizedList(ListModel<T> dataModel) {
        super(dataModel);
    }

    public SelectionCustomizedList(T[] listData) {
        super(listData);
    }

    public SelectionCustomizedList(Vector<? extends T> listData) {
        super(listData);
    }
    
    @Override
    public int locationToIndex(Point location) {
        int index = super.locationToIndex(location);
        if (index != -1 && !getCellBounds(index, index).contains(location)) {
            return -1;
        } else {
            return index;
        }
    }
}
