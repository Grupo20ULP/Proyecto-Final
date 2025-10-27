package Persistencia;

import Modelo.Sesion;
import Modelo.Tratamiento;
import Modelo.Consultorio;
import Modelo.Masajista;
import Modelo.Dia_De_Spa;
import Modelo.Instalacion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico Galan
 */
public class SesionData {
    
    private Connection con = null;

    public SesionData() {
        con = Conexion.getConectar();
    }
    
     public void insertarSesion(Sesion sesion) {
        String sql = "INSERT INTO sesion (fecha_hora_inicio, fecha_hora_fin, tratamiento_id, " +
                     "consultorio_id, masajista_id, dia_spa_id, instalacion_id, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaHoraInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaHoraFin()));
            ps.setInt(3, sesion.getTratamiento().getCodTratam());
            
            if (sesion.getConsultorio() != null) {
                ps.setInt(4, sesion.getConsultorio().getNroConsultorio());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            ps.setInt(5, sesion.getMasajista().getMatricula());
            ps.setInt(6, sesion.getDiaSpa().getCodPack());
            
            if (sesion.getInstalacion() != null) {
                ps.setInt(7, sesion.getInstalacion().getCodInstal());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            
            ps.setString(8, sesion.getEstado());
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        sesion.setCodSesion(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Sesión registrada. ID: " + sesion.getCodSesion());
                    }
                }
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar sesión: " + e.getMessage());
        }
    }

    public void modificarSesion(Sesion sesion) {
        String sql = "UPDATE sesion SET fecha_hora_inicio = ?, fecha_hora_fin = ?, " +
                     "tratamiento_id = ?, consultorio_id = ?, masajista_id = ?, " +
                     "dia_spa_id = ?, instalacion_id = ?, estado = ? WHERE codSesion = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaHoraInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaHoraFin()));
            ps.setInt(3, sesion.getTratamiento().getCodTratam());
            
            if (sesion.getConsultorio() != null) {
                ps.setInt(4, sesion.getConsultorio().getNroConsultorio());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            
            ps.setInt(5, sesion.getMasajista().getMatricula());
            ps.setInt(6, sesion.getDiaSpa().getCodPack());
            
            if (sesion.getInstalacion() != null) {
                ps.setInt(7, sesion.getInstalacion().getCodInstal());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            
            ps.setString(8, sesion.getEstado());
            ps.setInt(9, sesion.getCodSesion());
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Sesión modificada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar sesión: " + e.getMessage());
        }
    }

    public void anularSesion(int codSesion) {
        String sql = "UPDATE sesion SET estado = 'cancelada' WHERE codSesion = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Sesión anulada exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la sesión con ID: " + codSesion);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al anular sesión: " + e.getMessage());
        }
    }
    
        public List<Object[]> listarMasajistasLibres(LocalDateTime inicio, LocalDateTime fin) {
        List<Object[]> resultados = new ArrayList<>();
        String sql = "SELECT m.matricula, m.nombre_apellido, m.especialidad " +
                     "FROM masajista m " +
                     "WHERE m.estado = 'activo' AND m.matricula NOT IN (" +
                     "    SELECT s.masajista_id FROM sesion s " +
                     "    WHERE s.estado IN ('pendiente', 'confirmada') " +
                     "    AND ((s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) " +
                     "    OR (s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) " +
                     "    OR (s.fecha_hora_inicio >= ? AND s.fecha_hora_fin <= ?)))";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fin));
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));
            ps.setTimestamp(4, Timestamp.valueOf(inicio));
            ps.setTimestamp(5, Timestamp.valueOf(inicio));
            ps.setTimestamp(6, Timestamp.valueOf(fin));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("matricula");
                fila[1] = rs.getString("nombre_apellido");
                fila[2] = rs.getString("especialidad");
                resultados.add(fila);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas libres: " + e.getMessage());
        }
        return resultados;
    }
}
