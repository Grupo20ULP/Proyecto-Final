package Persistencia;

import Modelo.TipoDeTratamiento;
import Modelo.Tratamiento;
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
public class TratamientoData {

    private Connection con = null;

    public TratamientoData () {
        con = (Connection) Conexion.getConectar();
    }

// metodo insertar Tratamiento con tipo booleano para retornar y mostrar mensaje desde la interfaz grafica
    public boolean insertarTratamiento (Tratamiento tratamiento) {
        String sql
            = "INSERT INTO tratamiento (nombre, tipo, detalle, duracion, costo, activo) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tratamiento.getNombre());
            ps.setString(2, tratamiento.getTipo().
                name());
            ps.setString(3, tratamiento.getDetalle());
            ps.setInt(4, tratamiento.getDuracion());
            ps.setDouble(5, tratamiento.getCosto());
            ps.setString(6, tratamiento.getActivo());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tratamiento.setCodTratam(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al guardar Tratamiento: "
                + e.
                    getMessage());
        }
        return false;
    }

// metodo Baja Fisica Tratamiento con tipo booleano para retornar y mostrar mensaje desde la interfaz grafica
    public boolean bajaFisicaTratamiento (Tratamiento t) {
        String sql
            = "DELETE FROM tratamiento WHERE codTratam=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, t.getCodTratam());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al Eliminar Tratamiento: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Alta Tratamiento
    public boolean altaBajaTratamiento (Tratamiento t) {
        String sql
            = "UPDATE tratamiento SET activo =? WHERE codTratam = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getActivo());
            ps.setInt(2, t.getCodTratam());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al actualizar el estado de Tratamiento: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Modificar Tratamiento
    public boolean modificarTratamiento (Tratamiento t) {
        String sql
            = "UPDATE tratamiento SET nombre=?, tipo=?, detalle=?, duracion=?, costo=?, activo=? WHERE codTratam=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getTipo().
                name());
            ps.setString(3, t.getDetalle());
            ps.setInt(4, t.getDuracion());
            ps.setDouble(5, t.getCosto());
            ps.setString(6, t.getActivo());
            ps.setInt(7, t.getCodTratam());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al modificar el Tratamiento: "
                + e.
                    getMessage());
        }
        return false;
    }

    // metodo Buscar Tratamiento
    public Tratamiento buscarTratamientoPorId (int t) {
        Tratamiento tratamiento = null;
        String sql
            = "SELECT * FROM tratamiento WHERE codTratam = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, t);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tratamiento = new Tratamiento();
                tratamiento.setCodTratam(rs.getInt("codTratam"));
                tratamiento.setNombre(rs.getString("nombre"));
                String tipoStr = rs.getString("tipo");
                tratamiento.setTipo(TipoDeTratamiento.valueOf(tipoStr.
                    toUpperCase()));
                tratamiento.setDetalle(rs.getString("detalle"));
                tratamiento.setDuracion(rs.getInt("duracion"));
                tratamiento.setCosto(rs.getDouble("costo"));
                tratamiento.setActivo(rs.getString("activo"));
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al buscar Tratamiento: "
                + e.
                    getMessage());
        }
        return tratamiento;
    }

    public List<Tratamiento> listarTratamiento () {
        List<Tratamiento> tratamiento = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tratamiento t = new Tratamiento();
                t.setCodTratam(rs.getInt("codTratam"));
                t.setNombre(rs.getString("nombre"));
                String tipoStr = rs.getString("tipo");
                t.setTipo(TipoDeTratamiento.valueOf(tipoStr.toUpperCase()));
                t.setDetalle(rs.getString("detalle"));
                t.setDuracion(rs.getInt("duracion"));
                t.setCosto(rs.getDouble("costo"));
                t.setActivo(rs.getString("activo"));
                tratamiento.add(t);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.print("Error al listar Tratamiento: " + ex.getMessage());
        }
        return tratamiento;
    }

    public List<Tratamiento> listarTratamientosPorTipo (TipoDeTratamiento tipo) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE tipo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipo.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tratamiento t = new Tratamiento();
                t.setCodTratam(rs.getInt("codTratam"));
                t.setNombre(rs.getString("nombre"));
                t.setTipo(tipo);
                t.setDetalle(rs.getString("detalle"));
                t.setDuracion(rs.getInt("duracion"));
                t.setCosto(rs.getDouble("costo"));
                t.setActivo(rs.getString("activo"));
                tratamientos.add(t);
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error al listar tratamientos por tipo: " + e.
                getMessage());
        }
        return tratamientos;
    }
}
