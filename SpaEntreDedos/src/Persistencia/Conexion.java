package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico Galan
 */
public class Conexion {
    private static final String URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB = "spa_entre_dedos_gp20";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";
    private static Connection conexion;
    
    
    public static Connection getConectar () {
        if (conexion == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conexion = (Connection) DriverManager.getConnection(URL + DB,
                    USUARIO, CLAVE);               
            }
            catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                    "Problemas accediendo a la clase " + e.getMessage());
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                    "Error al conectar a la base de datos " + ex.getMessage());
            }
        }
        return conexion;
    }
}
