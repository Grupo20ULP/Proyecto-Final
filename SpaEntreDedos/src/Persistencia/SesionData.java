package Persistencia;

import Modelo.Sesion;
import Modelo.Tratamiento;
import Modelo.Cliente;
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
        String sql = "INSERT INTO sesion (fecha_hora_inicio, fecha_hora_fin, tratamiento_id, "
                + "consultorio_id, masajista_id, dia_spa_id, instalacion_id, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        String sql = "UPDATE sesion SET fecha_hora_inicio = ?, fecha_hora_fin = ?, "
                + "tratamiento_id = ?, consultorio_id = ?, masajista_id = ?, "
                + "dia_spa_id = ?, instalacion_id = ?, estado = ? WHERE codSesion = ?";

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
        String sql = "SELECT m.matricula, m.nombre_apellido, m.especialidad "
                + "FROM masajista m "
                + "WHERE m.estado = 'activo' AND m.matricula NOT IN ("
                + "    SELECT s.masajista_id FROM sesion s "
                + "    WHERE s.estado IN ('pendiente', 'confirmada') "
                + "    AND ((s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) "
                + "    OR (s.fecha_hora_inicio < ? AND s.fecha_hora_fin > ?) "
                + "    OR (s.fecha_hora_inicio >= ? AND s.fecha_hora_fin <= ?)))";

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

    public List<Sesion> listarTodasLasSesiones() {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT s.codSesion, s.fecha_hora_inicio, s.fecha_hora_fin, s.estado, "
                + "s.tratamiento_id, s.consultorio_id, s.masajista_id, s.dia_spa_id, s.instalacion_id, "
                + "t.nombre as tratamiento_nombre, t.costo as tratamiento_costo, "
                + "c.nroConsultorio, c.usos as consultorio_usos, "
                + "m.nombre_apellido as masajista_nombre, m.especialidad as masajista_especialidad, "
                + "d.codPack, d.cliente_id, cli.nombre_completo as cliente_nombre, "
                + "i.nombre as instalacion_nombre, i.precio30m as instalacion_precio "
                + "FROM sesion s "
                + "LEFT JOIN tratamiento t ON s.tratamiento_id = t.codTratam "
                + "LEFT JOIN consultorio c ON s.consultorio_id = c.nroConsultorio "
                + "LEFT JOIN masajista m ON s.masajista_id = m.matricula "
                + "LEFT JOIN dia_de_spa d ON s.dia_spa_id = d.codPack "
                + "LEFT JOIN cliente cli ON d.cliente_id = cli.codCli "
                + "LEFT JOIN instalacion i ON s.instalacion_id = i.codInstal "
                + "ORDER BY s.fecha_hora_inicio";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sesion sesion = construirSesionDesdeResultSet(rs);
                sesiones.add(sesion);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar sesiones: " + e.getMessage());
            e.printStackTrace();
        }
        return sesiones;
    }

    public Sesion buscarSesionPorId(int codSesion) {
        Sesion sesion = null;
        String sql = "SELECT s.codSesion, s.fecha_hora_inicio, s.fecha_hora_fin, s.estado, "
                + "s.tratamiento_id, s.consultorio_id, s.masajista_id, s.dia_spa_id, s.instalacion_id, "
                + "t.nombre as tratamiento_nombre, t.costo as tratamiento_costo, "
                + "c.nroConsultorio, c.usos as consultorio_usos, "
                + "m.nombre_apellido as masajista_nombre, m.especialidad as masajista_especialidad, "
                + "d.codPack, d.cliente_id, cli.nombre_completo as cliente_nombre, "
                + "i.nombre as instalacion_nombre, i.precio30m as instalacion_precio "
                + "FROM sesion s "
                + "LEFT JOIN tratamiento t ON s.tratamiento_id = t.codTratam "
                + "LEFT JOIN consultorio c ON s.consultorio_id = c.nroConsultorio "
                + "LEFT JOIN masajista m ON s.masajista_id = m.matricula "
                + "LEFT JOIN dia_de_spa d ON s.dia_spa_id = d.codPack "
                + "LEFT JOIN cliente cli ON d.cliente_id = cli.codCli "
                + "LEFT JOIN instalacion i ON s.instalacion_id = i.codInstal "
                + "WHERE s.codSesion = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                sesion = construirSesionDesdeResultSet(rs);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar sesión: " + e.getMessage());
            e.printStackTrace();
        }
        return sesion;
    }

    public List<Sesion> buscarSesionesPorFecha(LocalDateTime fecha) {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT s.codSesion, s.fecha_hora_inicio, s.fecha_hora_fin, s.estado, "
                + "s.tratamiento_id, s.consultorio_id, s.masajista_id, s.dia_spa_id, s.instalacion_id, "
                + "t.nombre as tratamiento_nombre, t.costo as tratamiento_costo, "
                + "c.nroConsultorio, c.usos as consultorio_usos, "
                + "m.nombre_apellido as masajista_nombre, m.especialidad as masajista_especialidad, "
                + "d.codPack, d.cliente_id, cli.nombre_completo as cliente_nombre, "
                + "i.nombre as instalacion_nombre, i.precio30m as instalacion_precio "
                + "FROM sesion s "
                + "LEFT JOIN tratamiento t ON s.tratamiento_id = t.codTratam "
                + "LEFT JOIN consultorio c ON s.consultorio_id = c.nroConsultorio "
                + "LEFT JOIN masajista m ON s.masajista_id = m.matricula "
                + "LEFT JOIN dia_de_spa d ON s.dia_spa_id = d.codPack "
                + "LEFT JOIN cliente cli ON d.cliente_id = cli.codCli "
                + "LEFT JOIN instalacion i ON s.instalacion_id = i.codInstal "
                + "WHERE DATE(s.fecha_hora_inicio) = DATE(?) "
                + "ORDER BY s.fecha_hora_inicio";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sesion sesion = construirSesionDesdeResultSet(rs);
                sesiones.add(sesion);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar sesiones por fecha: " + e.getMessage());
            e.printStackTrace();
        }
        return sesiones;
    }

    private Sesion construirSesionDesdeResultSet(ResultSet rs) throws SQLException {
        Sesion sesion = new Sesion();
        sesion.setCodSesion(rs.getInt("codSesion"));
        sesion.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio").toLocalDateTime());
        sesion.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin").toLocalDateTime());
        sesion.setEstado(rs.getString("estado"));

        if (rs.getObject("tratamiento_id") != null) {
            Tratamiento tratamiento = new Tratamiento();
            tratamiento.setCodTratam(rs.getInt("tratamiento_id"));
            tratamiento.setNombre(rs.getString("tratamiento_nombre"));
            tratamiento.setCosto(rs.getDouble("tratamiento_costo"));
            sesion.setTratamiento(tratamiento);
        }

        if (rs.getObject("consultorio_id") != null) {
            Consultorio consultorio = new Consultorio();
            consultorio.setNroConsultorio(rs.getInt("nroConsultorio"));
            consultorio.setUsos(rs.getString("consultorio_usos"));
            sesion.setConsultorio(consultorio);
        }

        if (rs.getObject("masajista_id") != null) {
            Masajista masajista = new Masajista();
            masajista.setMatricula(rs.getInt("masajista_id"));
            masajista.setNombreYapellido(rs.getString("masajista_nombre"));
            masajista.setEspecialidad(rs.getString("masajista_especialidad"));
            sesion.setMasajista(masajista);
        }

        if (rs.getObject("dia_spa_id") != null) {
            Dia_De_Spa diaSpa = new Dia_De_Spa();
            diaSpa.setCodPack(rs.getInt("codPack"));

            if (rs.getObject("cliente_id") != null) {
                Cliente cliente = new Cliente();
                cliente.setCodCli(rs.getInt("cliente_id"));
                cliente.setNombre_completo(rs.getString("cliente_nombre"));
                diaSpa.setCliente(cliente);
            }

            sesion.setDiaSpa(diaSpa);
        }

        if (rs.getObject("instalacion_id") != null) {
            Instalacion instalacion = new Instalacion();
            instalacion.setCodInstal(rs.getInt("instalacion_id"));
            instalacion.setNombre(rs.getString("instalacion_nombre"));
            instalacion.setPrecio30m(rs.getDouble("instalacion_precio"));
            sesion.setInstalacion(instalacion);
        }

        return sesion;
    }

}
