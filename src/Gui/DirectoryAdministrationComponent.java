package Gui;

import logic.DatabaseAccessManager;
import Data.Directory;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This GUI component enables it to load {@link Directory}'s (from hard disk)
 * into the application.
 *
 */
public class DirectoryAdministrationComponent extends JPanel {

    private final JButton directoryChooserButton;
    private final JFileChooser directoryChooser;

    DirectoryListComponent directoryListComponent;

    /**
     * Listener to start a JFileChooser dialog, in order to load a directory
     * into the application.
     */
    private class DirectoryChooserButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            directoryChooser.setCurrentDirectory(new java.io.File("c:\\"));
            if (directoryChooser.showOpenDialog(DirectoryAdministrationComponent.this)
                    == JFileChooser.APPROVE_OPTION) {

                File selectedDirectory = directoryChooser.getSelectedFile();
                String directoryPath = selectedDirectory.getAbsolutePath().replace("\\", "\\\\");
                //query db if directory path already is stored.
//                boolean pathExists = DatabaseAccessManager.valueExists(DatabaseAccessManager.TABLE_DIRECTORY_PATH,
//                        DatabaseAccessManager.TABLE_COLUMN_PATH,
//                        directoryPath);
                boolean pathExists = false;
                if (!pathExists) { //path does not exist yet in data base
                    //add path to database
//                    String sqlCommand = String.format("INSERT INTO %s (path) VALUES ('%s')",
//                            DatabaseAccessManager.TABLE_DIRECTORY_PATH,
//                            directoryPath);
//                    DatabaseAccessManager.getInstance().execute(sqlCommand);
                    //add path to directoryList model
                    directoryListComponent.add(new Directory(selectedDirectory.getName(),
                            selectedDirectory.getAbsolutePath()));
                } else { //path exists already
                    JOptionPane.showMessageDialog(null, "Der von ihnen eingegebene Ordnerpfad ist bereits vorhanden.");
                }
            } else {
                System.out.println("No Selection ");
            }
        }
    }

    /**
     * Constructor
     *
     * @param directoryListComponent GUI component DirectoryListComponent
     */
    public DirectoryAdministrationComponent(DirectoryListComponent directoryListComponent) {

        directoryChooserButton = new JButton("Ordner hinzufügen");
        directoryChooserButton.addActionListener(new DirectoryChooserButtonListener());

        directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle("Wählen Sie einen Ordner aus:");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //disable the "All files" option.
        directoryChooser.setAcceptAllFileFilterUsed(false);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(directoryChooserButton);

        this.directoryListComponent = directoryListComponent;
    }

}
