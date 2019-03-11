package logic;

import data.Directory;
import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.DefaultListModel;

/**
 * Class to establish a connection to a database and to query it.
 *
 */
public class DatabaseAccessManager {

    public final static String DATABASE_NAME = "javakurs";
    public final static String DATABASE_SERVER_PORT = "localhost:3306";

    public final static String TABLE_DIRECTORY_PATH = "directory_Path";
    public final static String TABLE_COLUMN_PATH = "path";

    //singleton pattern
    private static DatabaseAccessManager instance = null;

    private String driver;
    private String dbURL;
    private Connection connection;

    private Statement sqlStatement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;
    private int numberOfColumns;
    private String[] headlines;
    
    /**
     * Private Constructor. Instance is created via singleton pattern.
     *
     * @param serverPort Port of the database server.
     * @param database Name of the database to connect to.
     */
    private DatabaseAccessManager(String serverPort, String database) {
        
        driver = "com.mysql.cj.jdbc.Driver";
        //connection to a mysql database
        dbURL = String.format("jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                serverPort,
                database);

        try {
            // bindet Datenbanktreiber ein, holt die Klasse und legt Objekt an
            Class.forName(driver).newInstance();
            // bereite mit dem Treiber eine DB-Verbindung vor (URL, Usernane, Passwort)
            connection = DriverManager.getConnection(dbURL, "root", "");
            // erzeuge endgueltige Verbindung zur Datenbank
            sqlStatement = connection.createStatement();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Initializes a connection to a database.
     */
    public static DatabaseAccessManager initConnection() {

        if (instance == null) {
            instance = new DatabaseAccessManager(DATABASE_SERVER_PORT, DATABASE_NAME);
        }

        return instance;
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return
     */
    public static DatabaseAccessManager getInstance() {
        DatabaseAccessManager back = null;

        if (instance != null) {
            back = instance;
        }
        return back;
    }

    /**
     * Executes an sql query.
     *
     * @param sqlCommand The sql command to execute.
     * @return true, if the first result is a ResultSet object; false if it is
     * an update count or there are no results.
     */
    public boolean execute(String sqlCommand) {

        boolean returnsResultSet = false;

        try {
            returnsResultSet = sqlStatement.execute(sqlCommand);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnsResultSet;
    }

    /**
     * Executes an sql query and returns a resultSet.
     *
     * @param sqlCommand Sql command to execute.
     * @return Sql result set.
     */
    public ResultSet executeQuery(String sqlCommand) {
        try {
            resultSet = sqlStatement.executeQuery(sqlCommand);
            metaData = resultSet.getMetaData();
            numberOfColumns = metaData.getColumnCount();

            headlines = new String[numberOfColumns];
            for (int x = 1; x <= numberOfColumns; x++) {
                headlines[x - 1] = metaData.getColumnLabel(x);
            }

            resultSet.last();
            numberOfRows = resultSet.getRow();
            resultSet.beforeFirst();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Initializes the {@link Directory}'s of the {@link Gui.DirectoryListComponent}.
     * The necessary data is stored in a database.
     *
     * @param directoryListModel The DefaultListModel where the {@link Directory}'s
     * are added.
     * @throws SQLException
     */
    public static void initializeDirectoryList(DefaultListModel<Directory> directoryListModel) throws SQLException {

        String sqlCommand = String.format("SELECT %s FROM %s", TABLE_COLUMN_PATH, TABLE_DIRECTORY_PATH);
        ResultSet resultSet = getInstance().executeQuery(sqlCommand);

        //get existing directoryPaths from db
        while (resultSet.next()) {
            File currentDirectory = new File(resultSet.getString(1));
            directoryListModel.addElement(new Directory(currentDirectory.getName(),
                    currentDirectory.getAbsolutePath()));
        }
    }

    /**
     *
     * @param tableName The table of the value to be checked.
     * @param columnName The column of the value to be checked.
     * @param value The value to be checked.
     * @return
     */
    public static boolean valueExists(String tableName, String columnName, String value) {
        //query db with current path and eventually add it
        String sqlCommand = String.format("SELECT * FROM %s WHERE %s = '%s'",
                tableName,
                columnName,
                value);
        DatabaseAccessManager.getInstance().executeQuery(sqlCommand);

        return getInstance().getRowCount() > 0;
    }

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public String[] getHeadlines() {
        return headlines;
    }

    public int getColumnCount() {
        return numberOfColumns;
    }

    public int getRowCount() {
        return numberOfRows;
    }
}
