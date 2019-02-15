package Gui.componentEvent;

import java.util.EventObject;

/**
 *
 * 
 */
public class DirectoryViewEvent extends EventObject {

    private String selectedDirectory;
    
    public DirectoryViewEvent(Object source) {
        super(source);
        selectedDirectory = "";
    }

    public DirectoryViewEvent(Object source, String selectedDirectory) {
        super(source);
        this.selectedDirectory = selectedDirectory;
    }

    public String getSelectedDirectory() {
        return selectedDirectory;
    }

    public void setSelectedDirectory(String selectedDirectory) {
        this.selectedDirectory = selectedDirectory;
    }
}
