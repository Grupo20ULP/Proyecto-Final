package Persistencia;

import Modelo.Consultorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 *
 * @author Nehuen Zerdá
 */
public class ConsultorioData {
    
    private Connection con = null;
    
    // Constructor: obtiene la conexión al crear el objeto
    public ConsultorioData() {
        con = Conexion.getConectar();
    }
    
    
    // --- ALTA ---
    public void guardarConsultorio(Consultorio consultorio) {
        String sql = "INSERT INTO consultorio (usos, equipamiento, apto) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, consultorio.getUsos());
            ps.setString(2, consultorio.getEquipamiento());
            ps.setString(3, consultorio.getApto());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                consultorio.setNroConsultorio(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Consultorio guardado correctamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar consultorio: " + ex.getMessage());
        }
    }
    
    
    // --- BAJA ---
    public void eliminarConsultorio(int nroConsultorio) {
        String sql = "DELETE FROM consultorio WHERE nroConsultorio = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el consultorio.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar consultorio: " + ex.getMessage());
        }
    }
    
    
    // --- MODIFICAR ---
    public void actualizarConsultorio(Consultorio consultorio) {
        String sql = "UPDATE consultorio SET usos = ?, equipamiento = ?, apto = ? WHERE nroConsultorio = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, consultorio.getUsos());
            ps.setString(2, consultorio.getEquipamiento());
            ps.setString(3, consultorio.getApto());
            ps.setInt(4, consultorio.getNroConsultorio());
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el consultorio a modificar.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar consultorio: " + ex.getMessage());
        }
    }
    
    
    // --- BUSCAR POR ID ---
    public Consultorio buscarConsultorio(int nroConsultorio) {
        String sql = "SELECT * FROM consultorio WHERE nroConsultorio = ?";
        Consultorio cons = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                cons = new Consultorio();
                cons.setNroConsultorio(rs.getInt("nroConsultorio"));
                cons.setUsos(rs.getString("usos"));
                cons.setEquipamiento(rs.getString("equipamiento"));
                cons.setApto(rs.getString("apto"));
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar consultorio: " + ex.getMessage());
        }
        return cons;
    }
    
    
    // --- LISTAR TODOS ---
    public List<Consultorio> listarConsultorios() {
        List<Consultorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultorio";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Consultorio cons = new Consultorio();
                cons.setNroConsultorio(rs.getInt("nroConsultorio"));
                cons.setUsos(rs.getString("usos"));
                cons.setEquipamiento(rs.getString("equipamiento"));
                cons.setApto(rs.getString("apto"));
                
                lista.add(cons);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar consultorios: " + ex.getMessage());
        }
        return lista;
    }

}
