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
public class CrearSesion extends javax.swing.JInternalFrame {

    private SesionData sesionData;
    private TratamientoData tratamientoData;
    private MasajistaData masajistaData;
    private ConsultorioData consultorioData;
    private Dia_De_SpaData diaSpaData;
    private InstalacionData instalacionData;


    /**
     * Creates new form CrearSesion
     */
    public CrearSesion() {
        initComponents();
        inicializarDatos();
        cargarCombos();
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
            // Usando el método listarDiasDeSpa() de Dia_De_SpaData
            List<Dia_De_Spa> diasSpa = diaSpaData.listarDiasDeSpa();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione día spa");
            
            for (Dia_De_Spa dia : diasSpa) {
                // Verificar que el día spa esté en estado válido para agregar sesiones
                if (dia.getEstado().equalsIgnoreCase("Reservado") || 
                    dia.getEstado().equalsIgnoreCase("Activo") ||
                    dia.getEstado().equalsIgnoreCase("pendiente") ||
                    dia.getEstado().equalsIgnoreCase("confirmado")) {
                    
                    String clienteNombre = (dia.getCliente() != null) ? 
                        dia.getCliente().getNombre_completo() : "Cliente no disponible";
                    
                    model.addElement(dia.getCodPack() + " - " + clienteNombre + 
                                   " (" + dia.getFechaHora().toLocalDate() + ")");
                }
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
        
        // Validar que la fecha de fin sea posterior a la de inicio
        LocalDateTime inicio = construirFechaHora(jDateInicio, jSpinhoraInicio, jSpinMinInicio);
        LocalDateTime fin = construirFechaHora(jDateFin, jSpinHoraFin, jSpinMinFin);
        
        if (!fin.isAfter(inicio)) {
            JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio");
            return false;
        }
        
        return true;
    }
    
    private void guardarSesion() {
        try {
            Sesion sesion = new Sesion();
            
            LocalDateTime inicio = construirFechaHora(jDateInicio, jSpinhoraInicio, jSpinMinInicio);
            LocalDateTime fin = construirFechaHora(jDateFin, jSpinHoraFin, jSpinMinFin);
            
            sesion.setFechaHoraInicio(inicio);
            sesion.setFechaHoraFin(fin);
            
            String tratamientoStr = (String) ComboTratamiento.getSelectedItem();
            int codTratamiento = Integer.parseInt(tratamientoStr.split(" - ")[0]);
            Tratamiento tratamiento = tratamientoData.buscarTratamientoPorId(codTratamiento);
            sesion.setTratamiento(tratamiento);
            
            String masajistaStr = (String) ComboMasajista.getSelectedItem();
            int matricula = Integer.parseInt(masajistaStr.split(" - ")[0]);
            Masajista masajista = masajistaData.buscarMasajistaPorId(matricula);
            sesion.setMasajista(masajista);
            
            String diaSpaStr = (String) ComboDiaSpa.getSelectedItem();
            int codPack = Integer.parseInt(diaSpaStr.split(" - ")[0].split(" ")[1]);
            Dia_De_Spa diaSpa = diaSpaData.buscarDiaDeSpa(codPack);
            sesion.setDiaSpa(diaSpa);
            
            if (ComboConsultorio.getSelectedIndex() > 0) {
                String consultorioStr = (String) ComboConsultorio.getSelectedItem();
                int nroConsultorio = Integer.parseInt(consultorioStr.split(" ")[1]);
                Consultorio consultorio = consultorioData.buscarConsultorio(nroConsultorio);
                sesion.setConsultorio(consultorio);
            }
            
            if (ComboInstalacion.getSelectedIndex() > 0) {
                String instalacionStr = (String) ComboInstalacion.getSelectedItem();
                int codInstalacion = Integer.parseInt(instalacionStr.split(" - ")[0]);
                Instalacion instalacion = instalacionData.buscarInstalacionPorId(codInstalacion);
                sesion.setInstalacion(instalacion);
            }
            
            sesion.setEstado((String) ComboEstado.getSelectedItem());
            
            // Guardar sesión
            sesionData.insertarSesion(sesion);
            JOptionPane.showMessageDialog(this, "Sesión creada exitosamente");
            
            recalcularMontoDiaSpa(diaSpa);
            
            limpiarCampos();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de base de datos al crear sesión: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void recalcularMontoDiaSpa(Dia_De_Spa diaSpa) {
        try {
            diaSpa.recalcularMonto();
            
            diaSpaData.actualizarDiaDeSpa(diaSpa);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar monto del día spa: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al recalcular monto: " + e.getMessage());
        }
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
    
    public void actualizarListaDiasSpa() {
        cargarDiasSpa();
    }
    
    private void limpiarCampos() {
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
        jLabel1 = new javax.swing.JLabel();
        jDateInicio = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSpinhoraInicio = new com.toedter.components.JSpinField();
        jSpinMinInicio = new com.toedter.components.JSpinField();
        ComboTratamiento = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboMasajista = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ComboConsultorio = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        ComboDiaSpa = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        ComboInstalacion = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jDateFin = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jSpinHoraFin = new com.toedter.components.JSpinField();
        jLabel12 = new javax.swing.JLabel();
        jSpinMinFin = new com.toedter.components.JSpinField();
        BtnGuardar = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();

        jLabel1.setText("Inicio");

        jLabel2.setText("Hora");

        jLabel3.setText("Minutos");

        jSpinhoraInicio.setMaximum(23);
        jSpinhoraInicio.setMinimum(1);

        jSpinMinInicio.setMaximum(59);
        jSpinMinInicio.setMinimum(0);

        ComboTratamiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("Tratamiento");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("Masajista ");

        ComboMasajista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("Consultorio");

        ComboConsultorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setText("Dia Spa");

        ComboDiaSpa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setText("Instalacion");

        ComboInstalacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("Estado");

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pendiente", "confirmada", "realizada", "cancelada" }));

        jLabel10.setText("Fin");

        jLabel11.setText("Hora");

        jSpinHoraFin.setMaximum(23);
        jSpinHoraFin.setMinimum(1);

        jLabel12.setText("Minutos");

        jSpinMinFin.setMaximum(59);
        jSpinMinFin.setMinimum(0);

        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnLimpiar.setText("Limpiar");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EscritorioLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jDateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinMinInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EscritorioLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(EscritorioLayout.createSequentialGroup()
                                .addComponent(BtnLimpiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnGuardar))
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
                        .addGap(149, 149, 149)
                        .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ComboConsultorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboMasajista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboInstalacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboTratamiento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboDiaSpa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(70, 70, 70)))
                .addGap(58, 58, 58))
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinMinInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(ComboDiaSpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboMasajista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ComboTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnLimpiar))
                .addContainerGap())
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
        limpiarCampos();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {
            guardarSesion();
        }
        
    }//GEN-LAST:event_BtnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JComboBox<String> ComboConsultorio;
    private javax.swing.JComboBox<String> ComboDiaSpa;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboInstalacion;
    private javax.swing.JComboBox<String> ComboMasajista;
    private javax.swing.JComboBox<String> ComboTratamiento;
    private javax.swing.JPanel Escritorio;
    private com.toedter.calendar.JDateChooser jDateFin;
    private com.toedter.calendar.JDateChooser jDateInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.components.JSpinField jSpinHoraFin;
    private com.toedter.components.JSpinField jSpinMinFin;
    private com.toedter.components.JSpinField jSpinMinInicio;
    private com.toedter.components.JSpinField jSpinhoraInicio;
    // End of variables declaration//GEN-END:variables
}
