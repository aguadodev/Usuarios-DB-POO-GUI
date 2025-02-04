package usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Conexion {

    // Datos de conexión a la base de datos (localhost)
    static final String HOST = "127.0.0.1";
    static final String DATABASE = "compartirviaje";
    static final String USER = "root";
    static final String PASSWORD = "";
    static final String PORT = "3306";

    /**
     * Conecta con la base de datos
     * 
     * @return Conexión con la base de datos
     */
    public static Connection conectar() {
        Connection con = null;

        String url = "jdbc:mysql://" + Conexion.HOST + ":" + Conexion.PORT + "/" + Conexion.DATABASE;

        try {
            con = DriverManager.getConnection(url, Conexion.USER, Conexion.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD.");
        }

        return con;
    }
}

/*
 * // Datos de conexión a la base de datos (filess.io)
 * static final String HOST = "7qq-d.h.filess.io";
 * static final String DATABASE = "dam1_notecornam";
 * static final String USER = "dam1_notecornam";
 * static final String PASSWORD = "5d909372ed1f2f177f80c7ddae92ba8981f43fff";
 * static final String PORT = "3305";
 */

/*
 * // Datos de conexión a la base de datos (freedb.tech)
 * static final String HOST = "sql.freedb.tech";
 * static final String DATABASE = "freedb_dam12425";
 * static final String USER = "freedb_dam12425";
 * static final String PASSWORD = "N6ynS8#UsHUKRpG";
 * static final String PORT = "3306";
 */