package Persistencia;

import Modelo.Cliente;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 *
 * @author Carreño Lucas (Alias, Kick-boxers)
 */
public class ClienteData {

    private Connection con = null;
    
    public ClienteData () {
        con = (Connection) Conexion.getConectar();
    }
    
/////////////// METODO INSERTAR CLIENTE /////////////////
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente(dni, nombre_completo, telefono, edad, afecciones, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getDni());
            ps.setString(2, cliente.getNombre_completo());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getAfecciones());
            ps.setString(6, cliente.getEstado());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setCodCli(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Cliente registrado :) . ID: " + cliente.getCodCli());
                    }
                }
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cliente :( : " + e.getMessage());
        }
    }
    
////////////////////// CONTROL DE DUPLICADO DE CLIENTE /////////////////////
    public boolean clienteDuplicado(int dni) {
    String sql = "SELECT COUNT(*) FROM cliente WHERE dni = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error existe otro cliente con el mismo DNI: " + ex.getMessage());
    }
    return false;
    }
    
///////////////////// METODO MOFICAR CLIENTE //////////////////////
    public void modificarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET dni = ?, nombre_completo = ?, telefono = ?, edad = ?, afecciones = ?, estado = ? WHERE codCli = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getDni());
            ps.setString(2, cliente.getNombre_completo());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getAfecciones());
            ps.setString(6, cliente.getEstado());
            ps.setInt(7, cliente.getCodCli());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente modificado.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra el cliente con ID: " + cliente.getCodCli());
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el cliente: " + e.getMessage());
        }
    }
    
///////////////////// ALTA DEL CLIENTE //////////////////////////
    public void alta (Cliente codCli) {
        String sql = "UPDATE cliente SET estado = 'Activo' WHERE codCli = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codCli.getCodCli());
        int fila = ps.executeUpdate();
            if (fila > 0) {
                JOptionPane.showMessageDialog(null, "Cliente dado de alta.");
            } else {
            JOptionPane.showMessageDialog(null, "No se encontro al cliente para dar de alta");
            }
            ps.close();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al dar de alta al cliente: " + e.getMessage());
        }
    }
    
///////////////////// BAJA DEL CLIENTE //////////////////////////
    public void baja (int codCli) {
        String sql = "UPDATE cliente SET estado = 'Inactivo' WHERE codCli = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codCli);
            int fila = ps.executeUpdate();
            if (fila > 0) {
                JOptionPane.showMessageDialog(null,
                    "Cliente dado de baja");
            }
            else {
                JOptionPane.showMessageDialog(null,
                    "No se encontro el cliente con ID: " + codCli);
            }
            ps.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja al cliente: "
                + e.getMessage());
        }
    }
    
////////////////////////// CLIENTES ACTIVOS /////////////////////////
    public List<Cliente> clientesActivos () {
        List<Cliente> clientesActivos = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE estado = 'Activo' ORDER BY nombre_completo";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodCli(rs.getInt("codCli"));
                cliente.setDni(rs.getInt("dni"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setAfecciones(rs.getString("afecciones"));
                cliente.setEstado(rs.getString("estado"));
                clientesActivos.add(cliente);
            }
            ps.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Clientes Activos: "
                + e.
                    getMessage());
        }
        return clientesActivos;
    }
/////////////////// CLIENTES INACTIVOS (TODOS) //////////////////////
    public List<Cliente> todosLosClientes() {
    List<Cliente> clientes = new ArrayList<>();
    String sql = "SELECT * FROM cliente WHERE estado = 'Inactivo' ORDER BY nombre_completo";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setCodCli(rs.getInt("codCli"));
            cliente.setDni(rs.getInt("dni"));
            cliente.setNombre_completo(rs.getString("nombre_completo"));
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setEdad(rs.getInt("edad"));
            cliente.setAfecciones(rs.getString("afecciones"));
            cliente.setEstado(rs.getString("estado"));
            clientes.add(cliente);
        }
        ps.close();
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error en actualizar clientes: " + e.getMessage());
        }
        return clientes;
    }
}