package gui;



import data.GridImage;
import logic.ImageFileHandler;
import data.SelectionCustomizedList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This GUI component displays the available {@link GridImage}'s.
 *
 */
public class ImageGrid extends JPanel {

    public static int MAX_GRID_IMAGE_WIDTH = 450;
    public static int MAX_GRID_IMAGE_HEIGHT = 300;

    /**
     * Model and list to display {@link GridImage}'s
     */
    private final DefaultListModel<GridImage> imageGridModel;
    private final JList<GridImage> imageGrid;

    private ImageGridListener imageGridListener;

    /**
     * Sets the property to display of a {@link GridImage}.
     */
    private class ImageListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            if (value != null) {
                GridImage image = (GridImage) value;
                value = image.getImageIcon();
            } else {
                value = "";
            }
            return super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
        }
    }

    /**
     * Handling of {@link GridImage} selection events.
     */
    private class GridImageSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {

            if (!event.getValueIsAdjusting()) {
                GridImage image = imageGrid.getSelectedValue();
                ImageGridEvent ev = new ImageGridEvent(ImageGrid.this, image);
                if (imageGridListener != null) {
                    imageGridListener.imageSelectionEventOccurred(ev);
                }
            }
        }
    }

    /**
     * Show tool tip text for a {@link GridImage}.
     */
    private class ImageToolTipListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent event) {
            JList list = (JList) event.getSource();
            ListModel model = list.getModel();

            int index = list.locationToIndex(event.getPoint());
            if (index > -1) {
                GridImage image = (GridImage) model.getElementAt(index);
                list.setToolTipText(image.getName());
            }
        }
    }

    /**
     * Handles unselection and double click events of {@link GridImage}'s.
     */
    private class ImageClickedListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {

            JList list = (JList) event.getSource();
            //no item was hit
            if (list.locationToIndex(event.getPoint()) == -1
                    && !event.isShiftDown()
                    && !isMenuShortcutKeyDown(event)) {
                list.clearSelection();
            } else {
                //item was double clicked
                if (event.getClickCount() == 2) {
                    int index = list.locationToIndex(event.getPoint());
                    GridImage image = (GridImage) list.getModel().getElementAt(index);

                    ImageFileHandler.show(image);
                }
            }
        }

        private boolean isMenuShortcutKeyDown(InputEvent event) {
            return (event.getModifiers()
                    & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
        }
    }

    /**
     * Constructor
     *
     */
    public ImageGrid() {

        imageGridModel = new DefaultListModel();
        imageGrid = new SelectionCustomizedList<>(imageGridModel);

        imageGrid.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        imageGrid.setVisibleRowCount(2);
        imageGrid.setCellRenderer(new ImageListCellRenderer());

        imageGrid.addListSelectionListener(new GridImageSelectionListener());
        imageGrid.addMouseMotionListener(new ImageToolTipListener());
        imageGrid.addMouseListener(new ImageClickedListener());

        setLayout(new BorderLayout());
        add(new JScrollPane(imageGrid));
    }

    /**
     * Sets the input for this GUI component. First the imageGridModel is
     * emptied. Then the new {@link GridImage}'s are added to imageGridModel.
     * Finally the imageGrid's layout is updated.
     *
     * @param galleryImages. An array of {@link GridImage}'s to set.
     */
    public void setInput(GridImage[] galleryImages) {

        clear();

        for (GridImage galleryImage : galleryImages) {
            imageGridModel.addElement(galleryImage);
        }

        updateLayout();
    }

    /**
     * Sets the layout of the imageGrid in this way, that a horizontal scrolling
     * is not necessary to see all {@link GridImage}'s.
     */
    public void updateLayout() {
        imageGrid.setVisibleRowCount(calculateGridRowCount(imageGrid.getModel().getSize()));
    }

    /**
     * This methods calculates the number of rows for a imageGrid's layout
     * depending on the number of {@link GridImage}'s to be displayed.
     *
     * @param numberOfImagesToDisplay The number of {@link GridImage}'s to
     * display.
     * @return number of rows for the imageGrid
     */
    private int calculateGridRowCount(int numberOfImagesToDisplay) {

        int gridWidth = this.getWidth();
        int imageWitdh = (int) (MAX_GRID_IMAGE_WIDTH * ImageFileHandler.getScalingFactor());

        int numberOfDisplayableImagesPerRow = gridWidth / imageWitdh;

        int numberOfGalleryRows = (numberOfImagesToDisplay % numberOfDisplayableImagesPerRow == 0)
                ? numberOfImagesToDisplay / numberOfDisplayableImagesPerRow
                : (numberOfImagesToDisplay / numberOfDisplayableImagesPerRow) + 1;

        return numberOfGalleryRows;
    }

    /**
     * Removes all {@link GridImage}'s from the imageGrid.
     */
    public void clear() {
        imageGridModel.clear();
    }

    public void setImageGridListener(ImageGridListener imageGridListener) {
        this.imageGridListener = imageGridListener;
    }
}
