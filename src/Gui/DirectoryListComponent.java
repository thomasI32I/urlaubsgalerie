package Gui;

import logic.DatabaseAccessManager;
import Data.Directory;
import Data.SelectionCustomizedList;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import logic.ImageReaderProgressPerformer;

/**
 * This class represents the GUI component where the available {@link Directory}'s
 * are displayed. It is possible to select, unselect or remove these {@link Directory}'s
 * from this component.
 *
 */
public class DirectoryListComponent extends JPanel {

    private final JLabel directoryListHeading;
    private final JList<Directory> directoryList;
    private final DefaultListModel<Directory> directoryListModel;
    
    private final ImageGridComponent imageGridComponent;
    private final MetaDataComponent metaDataComponent;

    /**
     * Selection handling of {@link Directory}'s. Due to potentially long processing
     * times of images of the specific directories a backround thread, done by a
     * SwingWorker, is used to load and process images from hard disk.
     */
    private class DirectorySelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {

            if (!event.getValueIsAdjusting()) {
                try {
                    //reset meta data component
                    metaDataComponent.clear();
                    Directory selectedDirectory = directoryList.getSelectedValue();
                    String directoryPath = (selectedDirectory != null) ? selectedDirectory.getPath() : "";

                    ImageReaderProgressPerformer imageProgressPerformer = new ImageReaderProgressPerformer(imageGridComponent, directoryPath);
                    imageProgressPerformer.execute();

                } catch (Exception ex) {
                    Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Show tool tip text for {@link Directory}'s.
     */
    private class DirectoryToolTipListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent event) {
            JList list = (JList) event.getSource();
            ListModel model = list.getModel();

            int index = list.locationToIndex(event.getPoint());
            if (index > -1) {
                Directory directory = (Directory) model.getElementAt(index);
                list.setToolTipText(directory.getPath());
            }
        }
    }

    /**
     * Handles unselection events of {@link Directory}'s.
     */
    private class DirectoryClickedListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            JList list = (JList) event.getSource();

            //no item was hit
            if (list.locationToIndex(event.getPoint()) == -1
                    && !event.isShiftDown()
                    && !isMenuShortcutKeyDown(event)) {
                list.clearSelection();
            }
        }

        private boolean isMenuShortcutKeyDown(InputEvent event) {
            return (event.getModifiers()
                    & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
        }
    }

    /**
     * Handles deletion events of {@link Directory}'s. If a {@link Directory} is 
     * selected it can be removed by pressing the delete key.
     */
    private class DeleteDirectoryKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent event) {

            if (event.getKeyCode() == KeyEvent.VK_DELETE) {
                //check if a directory is selected
                Directory selectedDirectory = (Directory) directoryList.getSelectedValue();
                if (selectedDirectory != null) {
                    //show popup question dialog, where the user is asked if
                    //he really wants to delete directory
                    int response = JOptionPane.showConfirmDialog(null,
                            "Wollen sie den Ordner wirklich aus der Ordnerübersicht entfernen?",
                            "Ordner entfernen",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        //delete this directory from db and DirectoryListComponent
                        String path = selectedDirectory.getPath().replace("\\", "\\\\");
                        String sqlDeleteCommand = String.format("DELETE FROM %s WHERE %s = '%s'",
                                DatabaseAccessManager.TABLE_DIRECTORY_PATH,
                                DatabaseAccessManager.TABLE_COLUMN_PATH,
                                path);
                        DatabaseAccessManager.getInstance().execute(sqlDeleteCommand);
                        remove(selectedDirectory);
                    } else {
                        //do nothing
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    /**
     * Constructor
     *
     * @param imageGridComponent. GUI component ImageGridComponent.
     * @param metaDataComponent. GUI component MetaDataComponent.
     * @throws SQLException
     */
    public DirectoryListComponent(ImageGridComponent imageGridComponent,
            MetaDataComponent metaDataComponent) throws SQLException {

        this.imageGridComponent = imageGridComponent;
        this.metaDataComponent = metaDataComponent;

        directoryListHeading = new JLabel("Ordnerübersicht");
        directoryListHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        directoryListModel = new DefaultListModel();
        directoryList = new SelectionCustomizedList<>(directoryListModel);

        //TODO: Popup menue test
        JPopupMenu popUpMenue = new JPopupMenu();
        popUpMenue.add(new JMenuItem("Entfernen"));
        directoryList.setComponentPopupMenu(popUpMenue);

        directoryList.addListSelectionListener(new DirectorySelectionListener());
        directoryList.addMouseMotionListener(new DirectoryToolTipListener());
        directoryList.addMouseListener(new DirectoryClickedListener());
        directoryList.addKeyListener(new DeleteDirectoryKeyListener());

        DatabaseAccessManager.initializeDirectoryList(directoryListModel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(directoryListHeading);
        add(Box.createVerticalStrut(10));
        add(new JScrollPane(directoryList));
    }

    /**
     * Add directory to this component.
     *
     * @param directoryToAdd. Directory to add.
     */
    public void add(Directory directoryToAdd) {
        directoryListModel.addElement(directoryToAdd);
    }

    /**
     * Remove directory from this component.
     *
     * @param directoryToRemove. Directory to remove.
     */
    public void remove(Directory directoryToRemove) {
        directoryListModel.removeElement(directoryToRemove);
    }

}
