package w08_projekt_urlaubsgalerie;

import Gui.Graphic;
import java.sql.SQLException;
import logic.DatabaseAccessManager;

/**
 *
 *
 */
public class Application {

    Graphic graphic;

    public void start() throws SQLException {
        //init database connection
        DatabaseAccessManager.initConnection();
        graphic = new Graphic();
    }
}
