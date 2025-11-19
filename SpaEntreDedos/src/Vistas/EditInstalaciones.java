/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;


import Modelo.Instalacion;
import Persistencia.InstalacionData;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico Galan
 */
public class EditInstalaciones extends javax.swing.JInternalFrame {

    private InstalacionData instalacionData;
    private DefaultTableModel modeloTabla;
    private List<Instalacion> instalacionesOriginales;

    /**
     * Creates new form EditInstalaciones
     */
    public EditInstalaciones() {
        initComponents();
        instalacionData = new InstalacionData();
        configurarTabla();
        cargarInstalaciones();
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class; 
                    case 1: return String.class;  
                    case 2: return String.class;  
                    case 3: return Double.class;  
                    case 4: return String.class;  
                    default: return Object.class;
                }
            }
        };
        
        String[] columnas = {"Código", "Nombre", "Detalle", "Precio 30min", "Estado"};
        modeloTabla.setColumnIdentifiers(columnas);
        jTableInstalaciones.setModel(modeloTabla);
    }
    
    private void cargarInstalaciones() {
        try {
            modeloTabla.setRowCount(0);
            instalacionesOriginales = instalacionData.listarTodasLasInstalaciones();
            
            for (Instalacion inst : instalacionesOriginales) {
                Object[] fila = {
                    inst.getCodInstal(),
                    inst.getNombre(),
                    inst.getDetalleUso(),
                    inst.getPrecio30m(),
                    inst.getEstado()
                };
                modeloTabla.addRow(fila);
            }
            
            if (instalacionesOriginales.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay instalaciones registradas.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar instalaciones: " + e.getMessage());
        }
    }
    
    private void guardarTodosLosCambios() {
        try {
            int cambiosRealizados = 0;
            List<String> errores = new ArrayList<>();
            
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                try {
                    int codigo = (Integer) modeloTabla.getValueAt(i, 0);
                    String nombre = modeloTabla.getValueAt(i, 1).toString().trim();
                    String detalle = modeloTabla.getValueAt(i, 2).toString().trim();
                    double precio;
                    String estado = modeloTabla.getValueAt(i, 4).toString().trim();
                    
                    if (nombre.isEmpty()) {
                        errores.add("Fila " + (i + 1) + ": El nombre no puede estar vacío");
                        continue;
                    }
                    
                    try {
                        Object precioObj = modeloTabla.getValueAt(i, 3);
                        if (precioObj instanceof Double) {
                            precio = (Double) precioObj;
                        } else {
                            precio = Double.parseDouble(precioObj.toString());
                        }
                        if (precio < 0) {
                            errores.add("Fila " + (i + 1) + ": El precio no puede ser negativo");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        errores.add("Fila " + (i + 1) + ": El precio debe ser un número válido");
                        continue;
                    }
                    
                    if (!estado.equalsIgnoreCase("activa") && !estado.equalsIgnoreCase("inactiva")) {
                        errores.add("Fila " + (i + 1) + ": El estado debe ser 'activa' o 'inactiva'");
                        continue;
                    }
                                        
                    Instalacion instalacionOriginal = null;
                    for (Instalacion inst : instalacionesOriginales) {
                        if (inst.getCodInstal() == codigo) {
                            instalacionOriginal = inst;
                            break;
                        }
                    }
                    
                    if (instalacionOriginal != null) {
                        
                        boolean hayCambios = !instalacionOriginal.getNombre().equals(nombre) ||
                                           !instalacionOriginal.getDetalleUso().equals(detalle) ||
                                           instalacionOriginal.getPrecio30m() != precio ||
                                           !instalacionOriginal.getEstado().equalsIgnoreCase(estado);
                        
                        if (hayCambios) {
                            Instalacion instalacion = new Instalacion();
                            instalacion.setCodInstal(codigo);
                            instalacion.setNombre(nombre);
                            instalacion.setDetalleUso(detalle);
                            instalacion.setPrecio30m(precio);
                            instalacion.setEstado(estado.toLowerCase());
                            
                            if (instalacionData.modificarInstalacion(instalacion)) {
                                cambiosRealizados++;
                            } else {
                                errores.add("Fila " + (i + 1) + ": Error al guardar los cambios");
                            }
                        }
                    }
                } catch (Exception e) {
                    errores.add("Fila " + (i + 1) + ": Error - " + e.getMessage());
                }
            }
            
            StringBuilder mensaje = new StringBuilder();
            if (cambiosRealizados > 0) {
                mensaje.append("Se guardaron ").append(cambiosRealizados).append(" cambios exitosamente.\n");
            }
            
            if (!errores.isEmpty()) {
                mensaje.append("\nErrores encontrados:\n");
                for (String error : errores) {
                    mensaje.append("• ").append(error).append("\n");
                }
            }
            
            if (cambiosRealizados == 0 && errores.isEmpty()) {
                mensaje.append("No se detectaron cambios para guardar.");
            }
            
            JOptionPane.showMessageDialog(this, mensaje.toString());
            
            cargarInstalaciones();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error general al guardar: " + e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInstalaciones = new javax.swing.JTable();

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jTableInstalaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Detalle", "Precio 30m", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableInstalaciones);
        if (jTableInstalaciones.getColumnModel().getColumnCount() > 0) {
            jTableInstalaciones.getColumnModel().getColumn(0).setResizable(false);
            jTableInstalaciones.getColumnModel().getColumn(1).setResizable(false);
            jTableInstalaciones.getColumnModel().getColumn(2).setResizable(false);
            jTableInstalaciones.getColumnModel().getColumn(3).setResizable(false);
            jTableInstalaciones.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarTodosLosCambios();
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableInstalaciones;
    // End of variables declaration//GEN-END:variables
}
