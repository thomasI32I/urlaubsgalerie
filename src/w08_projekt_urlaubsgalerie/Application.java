package w08_projekt_urlaubsgalerie;

import gui.Graphic;
import java.sql.SQLException;

/**
 *
 *
 */
public class Application {

    Graphic graphic;

    public void start() throws SQLException {
        //init database connection
        //DatabaseAccessManager.initConnection();
        graphic = new Graphic();
    }
}
