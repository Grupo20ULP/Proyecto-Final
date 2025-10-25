package Persistencia;

import Modelo.Masajista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author Heber Gomez
 */
public class MasajistaData {

    private Connection con = null;

    public MasajistaData () {
        con = (Connection) Conexion.getConectar();
    }

    // metodo insertar Masajista
    public void insertarMasajista (Masajista m) {
        String sql
            = "INSERT INTO masajista (nombre_apellido, telefono, especialidad, estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getNombreYapellido());
            ps.setInt(2, m.getTelefono());
            ps.setString(3, m.getEspecialidad());
            ps.setBoolean(4, m.isEstado());
            //Consulta telefono no va en int? y estado en Boolean?
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0 ) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    m.setMatricula(rs.getInt(1));
                    JOptionPane.showMessageDialog(null,
                        "Masajista guardado exitosamente. ID: " + m.
                            getMatricula());
                }
                rs.close();
            }
            ps.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar alumno: " + e.
                getMessage());
        }
    }
}
