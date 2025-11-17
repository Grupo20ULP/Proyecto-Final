/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;

import Modelo.Sesion;
import Modelo.Tratamiento;
import Modelo.Masajista;
import Modelo.Consultorio;
import Modelo.Dia_De_Spa;
import Modelo.Instalacion;
import Persistencia.SesionData;
import Persistencia.TratamientoData;
import Persistencia.MasajistaData;
import Persistencia.ConsultorioData;
import Persistencia.Dia_De_SpaData;
import Persistencia.InstalacionData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
/**
 *
 * @author Federico Galan
 */
public class EditarSesion extends javax.swing.JInternalFrame {

    private SesionData sesionData;
    private TratamientoData tratamientoData;
    private MasajistaData masajistaData;
    private ConsultorioData consultorioData;
    private Dia_De_SpaData diaSpaData;
    private InstalacionData instalacionData;
    private Sesion sesionActual;

    /**
     * Creates new form EditarSesion
     */
    public EditarSesion() {
        initComponents();
        inicializarDatos();
        cargarCombos();
        cargarSesiones();
    }
    
    private void inicializarDatos() {
        sesionData = new SesionData();
        tratamientoData = new TratamientoData();
        masajistaData = new MasajistaData();
        consultorioData = new ConsultorioData();
        try {
            diaSpaData = new Dia_De_SpaData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar datos de días spa: " + e.getMessage());
        }
        instalacionData = new InstalacionData();
    }
    
    private void cargarCombos() {
        cargarTratamientos();
        cargarMasajistas();
        cargarConsultorios();
        cargarDiasSpa();
        cargarInstalaciones();
    }
    
    private void cargarSesiones() {
        try {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione sesión");
            
            List<Sesion> sesiones = sesionData.listarTodasLasSesiones();
            
            for (Sesion sesion : sesiones) {
                String nombreTratamiento = (sesion.getTratamiento() != null) ? 
                    sesion.getTratamiento().getNombre() : "Tratamiento no disponible";
                String nombreCliente = (sesion.getDiaSpa() != null && sesion.getDiaSpa().getCliente() != null) ? 
                    sesion.getDiaSpa().getCliente().getNombre_completo() : "Cliente no disponible";
                
                model.addElement(sesion.getCodSesion() + " - " + nombreTratamiento + " - " + nombreCliente);
            }
            
            ComboSesiones.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar sesiones: " + e.getMessage());
        }
    }
    
    private void cargarTratamientos() {
        try {
            List<Tratamiento> tratamientos = tratamientoData.listarTratamiento();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione tratamiento");
            for (Tratamiento t : tratamientos) {
                if ("si".equalsIgnoreCase(t.getActivo())) {
                    model.addElement(t.getCodTratam() + " - " + t.getNombre() + " ($" + t.getCosto() + ")");
                }
            }
            ComboTratamiento.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tratamientos: " + e.getMessage());
        }
    }
    
    private void cargarMasajistas() {
        try {
            List<Masajista> masajistas = masajistaData.listarMasajistas();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione masajista");
            for (Masajista m : masajistas) {
                if ("activo".equalsIgnoreCase(m.getEstado())) {
                    model.addElement(m.getMatricula() + " - " + m.getNombreYapellido() + " (" + m.getEspecialidad() + ")");
                }
            }
            ComboMasajista.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar masajistas: " + e.getMessage());
        }
    }
    
    private void cargarConsultorios() {
        try {
            List<Consultorio> consultorios = consultorioData.listarConsultorios();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione consultorio");
            for (Consultorio c : consultorios) {
                if ("si".equalsIgnoreCase(c.getApto())) {
                    model.addElement("Consultorio " + c.getNroConsultorio() + " (" + c.getUsos() + ")");
                }
            }
            ComboConsultorio.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar consultorios: " + e.getMessage());
        }
    }
    
    private void cargarDiasSpa() {
        try {
            List<Dia_De_Spa> diasSpa = diaSpaData.listarDiasDeSpa();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione día spa");
            
            for (Dia_De_Spa dia : diasSpa) {
                String clienteNombre = (dia.getCliente() != null) ? 
                    dia.getCliente().getNombre_completo() : "Cliente no disponible";
                
                model.addElement("Día " + dia.getCodPack() + " - " + clienteNombre + 
                               " (" + dia.getFechaHora().toLocalDate() + ")");
            }
            
            ComboDiaSpa.setModel(model);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar días spa: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al cargar días spa: " + e.getMessage());
        }
    }
    
    private void cargarInstalaciones() {
        try {
            List<Instalacion> instalaciones = instalacionData.listarInstalacionesActivas();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Sin instalación");
            for (Instalacion i : instalaciones) {
                model.addElement(i.getCodInstal() + " - " + i.getNombre() + " ($" + i.getPrecio30m() + "/30min)");
            }
            ComboInstalacion.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar instalaciones: " + e.getMessage());
        }
    }
    
    
    private void cargarDatosSesion() {
        try {
            String sesionSeleccionada = (String) ComboSesiones.getSelectedItem();
            int codSesion = Integer.parseInt(sesionSeleccionada.split(" - ")[0]);
            

            sesionActual = sesionData.buscarSesionPorId(codSesion);
            
            if (sesionActual != null) {
                jDateInicio.setDate(java.sql.Date.valueOf(sesionActual.getFechaHoraInicio().toLocalDate()));
                jSpinhoraInicio.setValue(sesionActual.getFechaHoraInicio().getHour());
                jSpinMinInicio.setValue(sesionActual.getFechaHoraInicio().getMinute());
                
                jDateFin.setDate(java.sql.Date.valueOf(sesionActual.getFechaHoraFin().toLocalDate()));
                jSpinHoraFin.setValue(sesionActual.getFechaHoraFin().getHour());
                jSpinMinFin.setValue(sesionActual.getFechaHoraFin().getMinute());
                
                seleccionarComboPorId(ComboTratamiento, sesionActual.getTratamiento().getCodTratam());
                seleccionarComboPorId(ComboMasajista, sesionActual.getMasajista().getMatricula());
                seleccionarComboPorId(ComboDiaSpa, sesionActual.getDiaSpa().getCodPack());
                
                if (sesionActual.getConsultorio() != null) {
                    seleccionarComboPorId(ComboConsultorio, sesionActual.getConsultorio().getNroConsultorio());
                } else {
                    ComboConsultorio.setSelectedIndex(0);
                }
                
                if (sesionActual.getInstalacion() != null) {
                    seleccionarComboPorId(ComboInstalacion, sesionActual.getInstalacion().getCodInstal());
                } else {
                    ComboInstalacion.setSelectedIndex(0);
                }
                
                for (int i = 0; i < ComboEstado.getItemCount(); i++) {
                    if (ComboEstado.getItemAt(i).equals(sesionActual.getEstado())) {
                        ComboEstado.setSelectedIndex(i);
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Datos de sesión cargados correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la sesión seleccionada");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de la sesión: " + e.getMessage());
        }
    }
    
    private void seleccionarComboPorId(javax.swing.JComboBox<String> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (item.startsWith(id + " - ")) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (item.contains(" " + id + " ") || item.endsWith(" " + id)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(0);
    }
    
    
    private boolean validarCampos() {
        if (ComboTratamiento.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento");
            return false;
        }
        if (ComboMasajista.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un masajista");
            return false;
        }
        if (ComboDiaSpa.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un día spa");
            return false;
        }
        if (jDateInicio.getDate() == null || jDateFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione fechas de inicio y fin");
            return false;
        }
        
        LocalDateTime inicio = construirFechaHora(jDateInicio, jSpinhoraInicio, jSpinMinInicio);
        LocalDateTime fin = construirFechaHora(jDateFin, jSpinHoraFin, jSpinMinFin);
        
        if (!fin.isAfter(inicio)) {
            JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio");
            return false;
        }
        
        return true;
    }
    
    private void actualizarSesion() {
        try {
            LocalDateTime inicio = construirFechaHora(jDateInicio, jSpinhoraInicio, jSpinMinInicio);
            LocalDateTime fin = construirFechaHora(jDateFin, jSpinHoraFin, jSpinMinFin);
            
            sesionActual.setFechaHoraInicio(inicio);
            sesionActual.setFechaHoraFin(fin);
            sesionActual.setEstado((String) ComboEstado.getSelectedItem());
            
            String tratamientoStr = (String) ComboTratamiento.getSelectedItem();
            int codTratamiento = Integer.parseInt(tratamientoStr.split(" - ")[0]);
            Tratamiento tratamiento = tratamientoData.buscarTratamientoPorId(codTratamiento);
            sesionActual.setTratamiento(tratamiento);

            String masajistaStr = (String) ComboMasajista.getSelectedItem();
            int matricula = Integer.parseInt(masajistaStr.split(" - ")[0]);
            Masajista masajista = masajistaData.buscarMasajistaPorId(matricula);
            sesionActual.setMasajista(masajista);

            String diaSpaStr = (String) ComboDiaSpa.getSelectedItem();
            int codPack = Integer.parseInt(diaSpaStr.split(" - ")[0].split(" ")[1]);
            Dia_De_Spa diaSpa = diaSpaData.buscarDiaDeSpa(codPack);
            sesionActual.setDiaSpa(diaSpa);
            
            if (ComboConsultorio.getSelectedIndex() > 0) {
                String consultorioStr = (String) ComboConsultorio.getSelectedItem();
                int nroConsultorio = Integer.parseInt(consultorioStr.split(" ")[1]);
                Consultorio consultorio = consultorioData.buscarConsultorio(nroConsultorio);
                sesionActual.setConsultorio(consultorio);
            } else {
                sesionActual.setConsultorio(null);
            }
            
            if (ComboInstalacion.getSelectedIndex() > 0) {
                String instalacionStr = (String) ComboInstalacion.getSelectedItem();
                int codInstalacion = Integer.parseInt(instalacionStr.split(" - ")[0]);
                Instalacion instalacion = instalacionData.buscarInstalacionPorId(codInstalacion);
                sesionActual.setInstalacion(instalacion);
            } else {
                sesionActual.setInstalacion(null);
            }
            
            
            sesionData.modificarSesion(sesionActual);
            
            JOptionPane.showMessageDialog(this, "Sesión actualizada exitosamente");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de base de datos al actualizar sesión: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar sesión: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        ComboSesiones.setSelectedIndex(0);
        ComboTratamiento.setSelectedIndex(0);
        ComboMasajista.setSelectedIndex(0);
        ComboConsultorio.setSelectedIndex(0);
        ComboDiaSpa.setSelectedIndex(0);
        ComboInstalacion.setSelectedIndex(0);
        ComboEstado.setSelectedIndex(0);
        jDateInicio.setDate(null);
        jDateFin.setDate(null);
        jSpinhoraInicio.setValue(9);
        jSpinMinInicio.setValue(0);
        jSpinHoraFin.setValue(10);
        jSpinMinFin.setValue(0);
        sesionActual = null;
    }
    
    private LocalDateTime construirFechaHora(com.toedter.calendar.JDateChooser dateChooser, 
                                           com.toedter.components.JSpinField hora, 
                                           com.toedter.components.JSpinField minutos) {
        java.util.Date fecha = dateChooser.getDate();
        return LocalDateTime.of(
            fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
            java.time.LocalTime.of(hora.getValue(), minutos.getValue())
        );
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Escritorio = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ComboSesiones = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        BtnCargarDatosSesion = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jDateInicio = new com.toedter.calendar.JDateChooser();
        jSpinhoraInicio = new com.toedter.components.JSpinField();
        jSpinMinInicio = new com.toedter.components.JSpinField();
        jLabel10 = new javax.swing.JLabel();
        jDateFin = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jSpinHoraFin = new com.toedter.components.JSpinField();
        jLabel12 = new javax.swing.JLabel();
        jSpinMinFin = new com.toedter.components.JSpinField();
        ComboTratamiento = new javax.swing.JComboBox<>();
        ComboMasajista = new javax.swing.JComboBox<>();
        ComboConsultorio = new javax.swing.JComboBox<>();
        ComboDiaSpa = new javax.swing.JComboBox<>();
        ComboInstalacion = new javax.swing.JComboBox<>();
        ComboEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BtnLimpiar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        ComboSesiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboSesiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboSesionesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel1.setText("Seleccione Sesion");

        BtnCargarDatosSesion.setText("Cargar Datos");
        BtnCargarDatosSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCargarDatosSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboSesiones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCargarDatosSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ComboSesiones, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(BtnCargarDatosSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("Inicio");

        jSpinhoraInicio.setMaximum(23);
        jSpinhoraInicio.setMinimum(1);

        jSpinMinInicio.setMaximum(59);
        jSpinMinInicio.setMinimum(0);

        jLabel10.setText("Fin");

        jLabel11.setText("Hora");

        jSpinHoraFin.setMaximum(23);
        jSpinHoraFin.setMinimum(1);

        jLabel12.setText("Minutos");

        jSpinMinFin.setMaximum(59);
        jSpinMinFin.setMinimum(0);

        ComboTratamiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboMasajista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboConsultorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboDiaSpa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboInstalacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pendiente", "confirmada", "realizada", "cancelada" }));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("Estado");

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setText("Instalacion");

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setText("Dia Spa");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("Consultorio");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("Masajista ");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("Tratamiento");

        BtnLimpiar.setText("Limpiar");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EscritorioLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jDateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addGap(94, 94, 94)
                        .addComponent(jSpinMinInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EscritorioLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EscritorioLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(EscritorioLayout.createSequentialGroup()
                                        .addComponent(BtnLimpiar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BtnActualizar))
                                    .addGroup(EscritorioLayout.createSequentialGroup()
                                        .addComponent(jDateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinHoraFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSpinMinFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EscritorioLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ComboConsultorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboMasajista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboTratamiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboDiaSpa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboInstalacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)))))
                .addGap(141, 141, 141))
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinMinInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinMinFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboMasajista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboDiaSpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnLimpiar))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        // TODO add your handling code here:
         limpiarCampos();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        // TODO add your handling code here:
        if (validarCampos() && sesionActual != null) {
            actualizarSesion();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sesión y complete todos los campos obligatorios");
        }
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnCargarDatosSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCargarDatosSesionActionPerformed
        // TODO add your handling code here:
        if(ComboSesiones.getSelectedIndex()> 0){
            cargarDatosSesion();
        }else{
            limpiarCampos();
        }
    }//GEN-LAST:event_BtnCargarDatosSesionActionPerformed

    private void ComboSesionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboSesionesActionPerformed
        // TODO add your handling code here:
        /*if(ComboSesiones.getSelectedIndex()> 0){
            cargarDatosSesion();
        }else{
            limpiarCampos();
        }*/
    }//GEN-LAST:event_ComboSesionesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JToggleButton BtnCargarDatosSesion;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JComboBox<String> ComboConsultorio;
    private javax.swing.JComboBox<String> ComboDiaSpa;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboInstalacion;
    private javax.swing.JComboBox<String> ComboMasajista;
    private javax.swing.JComboBox<String> ComboSesiones;
    private javax.swing.JComboBox<String> ComboTratamiento;
    private javax.swing.JPanel Escritorio;
    private com.toedter.calendar.JDateChooser jDateFin;
    private com.toedter.calendar.JDateChooser jDateInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private com.toedter.components.JSpinField jSpinHoraFin;
    private com.toedter.components.JSpinField jSpinMinFin;
    private com.toedter.components.JSpinField jSpinMinInicio;
    private com.toedter.components.JSpinField jSpinhoraInicio;
    // End of variables declaration//GEN-END:variables
}
