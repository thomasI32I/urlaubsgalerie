package Gui.componentEvent;

import java.io.File;
import java.util.EventObject;

/**
 *
 * 
 */
public class DirectoryAdministrationEvent extends EventObject {

    private File directory;
    
    public DirectoryAdministrationEvent(Object source) {
        super(source);
        directory = new File("");
    }

    public DirectoryAdministrationEvent(Object source, File directory) {
        super(source);
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }
}
