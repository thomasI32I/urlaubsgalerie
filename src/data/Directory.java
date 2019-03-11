package data;



/**
 * An object of this class represents the element which is displayed
 * in the {@link Gui.DirectoryListComponent}.
 *
 */
public class Directory {

    private final String name;
    private final String path;

    /**
     * Constuctor
     * 
     * @param name Name of the directory.
     * @param path Path of the directory.
     */
    public Directory(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }
}
