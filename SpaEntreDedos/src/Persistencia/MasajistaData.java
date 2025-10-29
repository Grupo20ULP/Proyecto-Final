package Persistencia;

import Modelo.Masajista;
import Modelo.TipoDeTratamiento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

// metodo insertar Masajista con tipo booleano para retornar y mostrar mensaje desde la interfaz grafica
    public boolean insertarMasajista (Masajista m) {
        String sql
            = "INSERT INTO masajista (nombre_apellido, telefono, especialidad, estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getNombreYapellido());
            ps.setString(2, m.getTelefono());
            ps.setString(3, m.getEspecialidad());
            ps.setString(4, m.getEstado());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    m.setMatricula(rs.getInt(1));
//        metodo con void para mostrar mensaje no es buena practica poco profesional
//                    JOptionPane.showMessageDialog(null,
//                        "Masajista guardado exitosamente. ID: " + m.
//                            getMatricula());
                }
                rs.close();
                return true;
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al guardar Masajista: "
                + e.
                    getMessage());
        }
        return false;
    }

// metodo Baja Fisica Masajista con tipo booleano para retornar y mostrar mensaje desde la interfaz grafica
    public boolean bajaFisicaMasajista (Masajista bm) {
        String sql
            = "DELETE FROM masajista WHERE matricula=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bm.getMatricula());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
//     metodo con void para mostrar mensaje no es buena practica poco profesional
//            int filasAfectadas = ps.executeUpdate();
//            if (filasAfectadas > 0) {
//                ResultSet rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    bm.setMatricula(rs.getInt(1));
//                    JOptionPane.showMessageDialog(null,
//                        "Masajista Eliminado exitosamente. ID: " + bm.
//                            getMatricula());
        }
        catch (SQLException e) {
            System.err.println("Error al Eliminar Masajista: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Alta Masajista
    public boolean altaBajaMasajista (Masajista abm) {
        String sql
            = "UPDATE masajista SET estado=? WHERE matricula=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, abm.getEstado());
            ps.setInt(2, abm.getMatricula());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
//            metodo con void para mostrar mensaje no es buena practica poco profesional
//            int filasAfectadas = ps.executeUpdate();
//            if (filasAfectadas > 0) {
//                JOptionPane.showMessageDialog(null,
//                    "El masajista con matricula " + abm.getMatricula()
//                    + " ahora esta " + abm.getEstado() + ".");
//            }
//            else {
//                JOptionPane.showMessageDialog(null,
//                    "No se encontro ningun masajista con matricula " + abm.
//                        getMatricula() + ".");
        }
        catch (SQLException e) {
            System.err.println("Error al actualizar el estado del Masajista: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Modificar Masajista
    public boolean modificarMasajista (Masajista modm) {
        String sql
            = "UPDATE masajista SET nombre_apellido=?, telefono=?, especialidad=?, estado=? WHERE matricula=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, modm.getNombreYapellido());
            ps.setString(2, modm.getTelefono());
            ps.setString(3, modm.getEspecialidad());
            ps.setString(4, modm.getEstado());
            ps.setInt(5, modm.getMatricula());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
//            metodo con void para mostrar mensaje no es buena practica poco profesional
//            int filasAfectadas = ps.executeUpdate();
//            if (filasAfectadas > 0) {
//                JOptionPane.showMessageDialog(null,
//                    "La modificacion del Masajista " + modm.
//                        getMatricula() + " fue Exitosa ");
//            }
//            else {
//                JOptionPane.showMessageDialog(null,
//                    "No se encontro ningun masajista con matricula " + modm.
//                        getMatricula());
//            }
//            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al modificar al Masajista: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Buscar Masajista
    public Masajista buscarMasajistaPorId (int matricula) {
        Masajista masajista = null;
        String sql
            = "SELECT * FROM masajista WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                masajista = new Masajista();
                masajista.setMatricula(rs.getInt("matricula"));
                masajista.setNombreYapellido(rs.getString("nombre_apellido"));
                masajista.setTelefono(rs.getString("telefono"));
                masajista.setEspecialidad(rs.getString("especialidad"));
                masajista.setEstado(rs.getString("estado"));
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al buscar masajista: "
                + e.
                    getMessage());
        }
        return masajista;
    }

    public List<Masajista> listarMasajistas () {
        List<Masajista> masajistas = new ArrayList<>();
        String sql = "SELECT * FROM masajista";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombreYapellido(rs.getString("nombre_apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString(
                    "especialidad"));
                m.setEstado(rs.getString("estado"));
                masajistas.add(m);
                rs.close();
            }
            ps.close();
        }
        catch (SQLException ex) {
            System.err.print("Error al listar masajistas: " + ex.getMessage());
        }
        return masajistas;
    }

    public List<Masajista> listarMasajistasPorEspecialidad (TipoDeTratamiento tipo) {
        List<Masajista> masajistas = new ArrayList<>();
        String sql = "SELECT * FROM masajista WHERE especialidad = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipo.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombreYapellido(rs.getString("nombre_apellido"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString(
                    "especialidad"));
                m.setEstado(rs.getString("estado"));
                masajistas.add(m);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al listar Masajista por tipo: " + e.
                getMessage());
        }
        return masajistas;
    }
}
