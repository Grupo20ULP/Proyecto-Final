/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;

import Modelo.Sesion;
import Persistencia.SesionData;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Federico Galan
 */
public class BuscarSesion extends javax.swing.JInternalFrame {

    private SesionData sesionData;
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form BuscarSesion
     */
    public BuscarSesion() {
        initComponents();
        inicializarDatos();
        configurarTabla();
        cargarTodasLasSesiones();
    }

    private void inicializarDatos() {
        sesionData = new SesionData();
    }
    
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        String[] columnas = {"CodSesion", "Hora Inicio", "Hora Fin", "Tratamiento", 
                           "Consultorio", "Masajista", "N° Dia Spa", "Instalacion", "Estado"};
        modeloTabla.setColumnIdentifiers(columnas);
        jTable1.setModel(modeloTabla);
    }
    private void buscarSesiones() {
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha para buscar");
            return;
        }
        
        try {
            LocalDateTime fechaBusqueda = construirFechaHora(
                jDateChooser1, jSpinhoraInicio, jSpinMinInicio
            );
            
            List<Sesion> sesiones = sesionData.buscarSesionesPorFecha(fechaBusqueda);
        
            modeloTabla.setRowCount(0);
            
            if (sesiones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron sesiones para la fecha: " + fechaBusqueda.toLocalDate());
                return;
            }
            
            for (Sesion sesion : sesiones) {
                Object[] fila = {
                    sesion.getCodSesion(),
                    sesion.getFechaHoraInicio().toString(),
                    sesion.getFechaHoraFin().toString(),
                    (sesion.getTratamiento() != null) ? sesion.getTratamiento().getNombre() : "No disponible",
                    (sesion.getConsultorio() != null) ? "Consultorio " + sesion.getConsultorio().getNroConsultorio() : "No asignado",
                    (sesion.getMasajista() != null) ? sesion.getMasajista().getNombreYapellido() : "No disponible",
                    (sesion.getDiaSpa() != null) ? "Día " + sesion.getDiaSpa().getCodPack() : "No asignado",
                    (sesion.getInstalacion() != null) ? sesion.getInstalacion().getNombre() : "No asignada",
                    sesion.getEstado()
                };
                modeloTabla.addRow(fila);
            }
            
            JOptionPane.showMessageDialog(this, "Se encontraron " + sesiones.size() + " sesiones para: " + fechaBusqueda.toLocalDate());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en búsqueda: " + e.getMessage());
        }
    }
    
    private void cargarTodasLasSesiones() {
        try {
            modeloTabla.setRowCount(0); 
            
            List<Sesion> sesiones = sesionData.listarTodasLasSesiones();
            
            if (sesiones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay sesiones registradas en la base de datos");
                return;
            }
            
            for (Sesion sesion : sesiones) {
                Object[] fila = {
                    sesion.getCodSesion(),
                    sesion.getFechaHoraInicio().toString(),
                    sesion.getFechaHoraFin().toString(),
                    (sesion.getTratamiento() != null) ? sesion.getTratamiento().getNombre() : "No disponible",
                    (sesion.getConsultorio() != null) ? "Consultorio " + sesion.getConsultorio().getNroConsultorio() : "No asignado",
                    (sesion.getMasajista() != null) ? sesion.getMasajista().getNombreYapellido() : "No disponible",
                    (sesion.getDiaSpa() != null) ? "Día " + sesion.getDiaSpa().getCodPack() : "No asignado",
                    (sesion.getInstalacion() != null) ? sesion.getInstalacion().getNombre() : "No asignada",
                    sesion.getEstado()
                };
                modeloTabla.addRow(fila);
            }
            
            JOptionPane.showMessageDialog(this, "Se cargaron " + sesiones.size() + " sesiones");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar sesiones: " + e.getMessage());
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
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jSpinhoraInicio = new com.toedter.components.JSpinField();
        jSpinMinInicio = new com.toedter.components.JSpinField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        BtnTodas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 153, 153));

        Escritorio.setBackground(new java.awt.Color(153, 153, 153));

        jSpinhoraInicio.setMaximum(23);
        jSpinhoraInicio.setMinimum(1);

        jSpinMinInicio.setMaximum(59);
        jSpinMinInicio.setMinimum(0);

        jLabel1.setText("Hora");

        jLabel2.setText("Minutos");

        jLabel3.setText("Dia");

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnTodas.setText("Ver Todas");
        BtnTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTodasActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CodSesion", "Hora Inicio", "Hora Fin", "Tratamiento", "Consultorio", "Masajista", "N° Dia Spa", "Instalacion", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinMinInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addComponent(BtnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnTodas)
                        .addGap(115, 115, 115))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnBuscar)
                            .addComponent(BtnTodas)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinMinInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinhoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        // TODO add your handling code here:
        buscarSesiones();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTodasActionPerformed
        // TODO add your handling code here:
        cargarTodasLasSesiones();
    }//GEN-LAST:event_BtnTodasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnTodas;
    private javax.swing.JPanel Escritorio;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.components.JSpinField jSpinMinInicio;
    private com.toedter.components.JSpinField jSpinhoraInicio;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
