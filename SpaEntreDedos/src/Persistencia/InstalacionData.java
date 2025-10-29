package Persistencia;

import Modelo.Instalacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico Galan
 */
public class InstalacionData {
    
    private Connection con = null;

    public InstalacionData() {
        con = Conexion.getConectar();
    }

    public boolean insertarInstalacion(Instalacion instalacion) {
        String sql = "INSERT INTO instalacion (nombre, detalle_uso, precio30m, estado) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, instalacion.getNombre());
            ps.setString(2, instalacion.getDetalleUso());
            ps.setDouble(3, instalacion.getPrecio30m());
            ps.setString(4, instalacion.getEstado());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    instalacion.setCodInstal(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar instalación: " + e.getMessage());
        }
        return false;
    }

    public boolean modificarInstalacion(Instalacion instalacion) {
        String sql = "UPDATE instalacion SET nombre = ?, detalle_uso = ?, precio30m = ?, estado = ? WHERE codInstal = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, instalacion.getNombre());
            ps.setString(2, instalacion.getDetalleUso());
            ps.setDouble(3, instalacion.getPrecio30m());
            ps.setString(4, instalacion.getEstado());
            ps.setInt(5, instalacion.getCodInstal());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar instalación: " + e.getMessage());
        }
        return false;
    }

    public boolean bajaInstalacion(int codInstal) {
        String sql = "UPDATE instalacion SET estado = 'inactiva' WHERE codInstal = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja instalación: " + e.getMessage());
        }
        return false;
    }

    public boolean altaInstalacion(int codInstal) {
        String sql = "UPDATE instalacion SET estado = 'activa' WHERE codInstal = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al dar de alta instalación: " + e.getMessage());
        }
        return false;
    }

    public Instalacion buscarInstalacionPorId(int codInstal) {
        Instalacion instalacion = null;
        String sql = "SELECT * FROM instalacion WHERE codInstal = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                instalacion = new Instalacion();
                instalacion.setCodInstal(rs.getInt("codInstal"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setDetalleUso(rs.getString("detalle_uso"));
                instalacion.setPrecio30m(rs.getDouble("precio30m"));
                instalacion.setEstado(rs.getString("estado"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar instalación: " + e.getMessage());
        }
        return instalacion;
    }

    public List<Instalacion> listarTodasLasInstalaciones() {
        List<Instalacion> instalaciones = new ArrayList<>();
        String sql = "SELECT * FROM instalacion ORDER BY nombre";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Instalacion instalacion = new Instalacion();
                instalacion.setCodInstal(rs.getInt("codInstal"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setDetalleUso(rs.getString("detalle_uso"));
                instalacion.setPrecio30m(rs.getDouble("precio30m"));
                instalacion.setEstado(rs.getString("estado"));
                instalaciones.add(instalacion);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones: " + e.getMessage());
        }
        return instalaciones;
    }

    public List<Instalacion> listarInstalacionesActivas() {
        List<Instalacion> instalaciones = new ArrayList<>();
        String sql = "SELECT * FROM instalacion WHERE estado = 'activa' ORDER BY nombre";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Instalacion instalacion = new Instalacion();
                instalacion.setCodInstal(rs.getInt("codInstal"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setDetalleUso(rs.getString("detalle_uso"));
                instalacion.setPrecio30m(rs.getDouble("precio30m"));
                instalacion.setEstado(rs.getString("estado"));
                instalaciones.add(instalacion);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones activas: " + e.getMessage());
        }
        return instalaciones;
    }

    public List<Instalacion> buscarInstalacionesPorNombre(String nombre) {
        List<Instalacion> instalaciones = new ArrayList<>();
        String sql = "SELECT * FROM instalacion WHERE nombre LIKE ? ORDER BY nombre";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Instalacion instalacion = new Instalacion();
                instalacion.setCodInstal(rs.getInt("codInstal"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setDetalleUso(rs.getString("detalle_uso"));
                instalacion.setPrecio30m(rs.getDouble("precio30m"));
                instalacion.setEstado(rs.getString("estado"));
                instalaciones.add(instalacion);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar instalaciones por nombre: " + e.getMessage());
        }
        return instalaciones;
    }

    //testear funcionamiento q se me trabo el pati al crearlas 


    public List<Instalacion> listarInstalacionesLibres(Timestamp inicio, Timestamp fin) {
        List<Instalacion> instalacionesLibres = new ArrayList<>();
        String sql = "SELECT i.* FROM instalacion i " +
                    "WHERE i.estado = 'activa' AND i.codInstal NOT IN (" +
                    "    SELECT s.instalacion_id FROM sesion s " +
                    "    WHERE s.instalacion_id IS NOT NULL " +
                    "    AND s.estado IN ('pendiente', 'confirmada') " +
                    "    AND ((s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) " +
                    "    OR (s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) " +
                    "    OR (s.fecha_hora_inicio >= ? AND s.fecha_hora_fin <= ?)))";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, fin);
            ps.setTimestamp(2, inicio);
            ps.setTimestamp(3, fin);
            ps.setTimestamp(4, inicio);
            ps.setTimestamp(5, inicio);
            ps.setTimestamp(6, fin);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Instalacion instalacion = new Instalacion();
                instalacion.setCodInstal(rs.getInt("codInstal"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setDetalleUso(rs.getString("detalle_uso"));
                instalacion.setPrecio30m(rs.getDouble("precio30m"));
                instalacion.setEstado(rs.getString("estado"));
                instalacionesLibres.add(instalacion);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones libres: " + e.getMessage());
        }
        return instalacionesLibres;
    }

    public boolean estaDisponible(int codInstal, Timestamp inicio, Timestamp fin) {
        String sql = "SELECT COUNT(*) FROM sesion WHERE instalacion_id = ? " +
                    "AND estado IN ('pendiente', 'confirmada') " +
                    "AND ((fecha_hora_inicio < ? AND fecha_hora_fin > ?) " +
                    "OR (fecha_hora_inicio < ? AND fecha_hora_fin > ?) " +
                    "OR (fecha_hora_inicio >= ? AND fecha_hora_fin <= ?))";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInstal);
            ps.setTimestamp(2, fin);
            ps.setTimestamp(3, inicio);
            ps.setTimestamp(4, fin);
            ps.setTimestamp(5, inicio);
            ps.setTimestamp(6, inicio);
            ps.setTimestamp(7, fin);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar disponibilidad: " + e.getMessage());
        }
        return false;
    }
}