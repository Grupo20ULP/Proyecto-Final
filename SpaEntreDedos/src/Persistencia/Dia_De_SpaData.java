package Persistencia;

/**
 *
 * @author Juan Carreras
 */
import Modelo.Dia_De_Spa;
import Modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dia_De_SpaData {
 private Connection con;

    public Dia_De_SpaData() throws SQLException {
        this.con = Conexion.getConectar();
    }


    // Inserta un nuevo registro en la tabla

    public void guardarDiaDeSpa(Dia_De_Spa dia) throws SQLException {
        String sql = "INSERT INTO dia_de_spa (codPack, fechaHora, preferencias, cliente_id, monto, estado) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, dia.getCodPack());
        ps.setTimestamp(2, Timestamp.valueOf(dia.getFechaHora()));
        ps.setString(3, dia.getPreferencias());
        ps.setInt(4, dia.getCliente().getCodCli());
        ps.setDouble(5, dia.getMonto());
        ps.setString(6, dia.getEstado());
        ps.executeUpdate();
        ps.close();
    }

    // Trae un dia de spa por su codigo
public Dia_De_Spa buscarDiaDeSpa(int codPack) throws SQLException {
    String sql = "SELECT d.codPack, d.fecha_hora, d.preferencias, d.cliente_id, d.monto_total, d.estado, " +
                "c.codCli, c.dni, c.nombre_completo, c.telefono, c.edad, c.afecciones, c.estado as estado_cliente " +
                "FROM dia_de_spa d JOIN cliente c ON d.cliente_id = c.codCli WHERE d.codPack = ?";
        
    PreparedStatement ps = con.prepareStatement(sql);
    ps.setInt(1, codPack);
    ResultSet rs = ps.executeQuery();

    Dia_De_Spa dia = null;
    if (rs.next()) {
        dia = new Dia_De_Spa();
        dia.setCodPack(rs.getInt("codPack"));
        dia.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        dia.setPreferencias(rs.getString("preferencias"));
        dia.setMonto(rs.getDouble("monto_total"));
        dia.setEstado(rs.getString("estado"));

        // Crear objeto Cliente asociado
        Cliente cli = new Cliente(
            rs.getInt("codCli"),
            rs.getInt("dni"),
            rs.getString("nombre_completo"),
            rs.getString("telefono"),
            rs.getInt("edad"),
            rs.getString("afecciones"),
            rs.getString("estado_cliente")
        );

        dia.setCliente(cli);
    }

    rs.close();
    ps.close();
    return dia;
}


    // Devuelve todos los dias de spa
/* 
    public List<Dia_De_Spa> listarDiasDeSpa() throws SQLException {
        List<Dia_De_Spa> lista = new ArrayList<>();
        String sql = "SELECT * FROM dia_de_spa";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Dia_De_Spa dia = new Dia_De_Spa();
            dia.setCodPack(rs.getInt("codPack"));
            dia.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
            dia.setPreferencias(rs.getString("preferencias"));
            dia.setMonto(rs.getDouble("monto"));
            dia.setEstado(rs.getString("estado"));
            lista.add(dia);
        }

        rs.close();
        st.close();
        return lista;
    }
 */
// Devuelve todos los dias de spa corregido
    public List<Dia_De_Spa> listarDiasDeSpa() throws SQLException {
        List<Dia_De_Spa> lista = new ArrayList<>();
        String sql = "SELECT d.codPack, d.fecha_hora, d.preferencias, d.cliente_id, d.monto_total, d.estado, " +
                    "c.codCli, c.dni, c.nombre_completo, c.telefono, c.edad, c.afecciones, c.estado as estado_cliente " +
                    "FROM dia_de_spa d JOIN cliente c ON d.cliente_id = c.codCli";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Dia_De_Spa dia = new Dia_De_Spa();
            dia.setCodPack(rs.getInt("codPack"));
            dia.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
            dia.setPreferencias(rs.getString("preferencias"));
            dia.setMonto(rs.getDouble("monto_total"));
            dia.setEstado(rs.getString("estado"));

            // Crear objeto Cliente asociado
            Cliente cli = new Cliente(
                rs.getInt("codCli"),
                rs.getInt("dni"),
                rs.getString("nombre_completo"),
                rs.getString("telefono"),
                rs.getInt("edad"),
                rs.getString("afecciones"),
                rs.getString("estado_cliente")
            );

            dia.setCliente(cli);
            lista.add(dia);
        }

        rs.close();
        ps.close();
        return lista;
}

    // Modifica los datos existentes

    public void actualizarDiaDeSpa(Dia_De_Spa dia) throws SQLException {
        String sql = "UPDATE dia_de_spa SET fecha_hora = ?, preferencias = ?, cliente_id = ?, monto_total = ?, estado = ? WHERE codPack = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(dia.getFechaHora()));
        ps.setString(2, dia.getPreferencias());
        ps.setInt(3, dia.getCliente().getCodCli());
        ps.setDouble(4, dia.getMonto());
        ps.setString(5, dia.getEstado());
        ps.setInt(6, dia.getCodPack());
        ps.executeUpdate();
        ps.close();
    }


    // Delete (Elimina el registro por codigo)
    public void eliminarDiaDeSpa(int codPack) throws SQLException {
        String sql = "DELETE FROM dia_de_spa WHERE codPack = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codPack);
        ps.executeUpdate();
        ps.close();
    }
    
//    //    Prueba
//    public boolean tieneSesiones(int codPack) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM sesion WHERE codPack = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, codPack);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0; // si hay al menos una sesión
//            }
//        }
//        return false;
//    }
}
