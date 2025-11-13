/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;

import static java.awt.image.ImageObserver.*;
import Modelo.TipoDeTratamiento;
import Modelo.Tratamiento;
import Persistencia.TratamientoData;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Heber Gomez PC
 */
public class TratamientoModificarBuscar extends javax.swing.JInternalFrame {

    /**
     * Creates new form MasajistaModificarBuscar
     */
    public TratamientoModificarBuscar () {
        initComponents();
        imagenFondo();
        visualfondo();
        cargarEspecialidades();
        cargarEstados();
    }

    private void cargarEspecialidades () {
        // Limpia primero
        jComboBoxTipo.removeAllItems();
        for (TipoDeTratamiento tipo : TipoDeTratamiento.values()) {
            jComboBoxTipo.addItem(tipo);
        }
    }

    private void cargarEstados () {
        jComboBoxEstado.removeAllItems();
        jComboBoxEstado.addItem("Activo");
        jComboBoxEstado.addItem("Inactivo");
    }

    private void buscarTratamiento () {
        try {
            int codigo = Integer.parseInt(jTextFieldCodigo.getText());
            TratamientoData data = new TratamientoData();
//             Guardar resultado de la busqueda
            Tratamiento t = data.buscarTratamientoPorId(codigo);
            if (t == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontro el Tratamiento.");
                return;
            }
//              Cargar campos
            jTextFieldNombre.setText(t.getNombre());
            jComboBoxTipo.setSelectedItem(t.getTipo());
            jTextAreaDetalle.setText(t.getDetalle());
            jTextFieldDuracion.setText(String.valueOf(t.getDuracion()));
            jTextFieldCosto.setText(String.valueOf(t.getCosto()));
//            creamos esta alternativa por que se creo mal la Base de Datos que recibe si y no en lugar de Activo Inactivo
            if (t.getActivo().
                equalsIgnoreCase("si")) {
                jComboBoxEstado.setSelectedItem("Activo");
            }
            else {
                jComboBoxEstado.setSelectedItem("Inactivo");
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El codigo debe ser un nuymero valido.");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al buscar el tratamiento: " + ex.getMessage());
        }
    }

    private void modificarTratamiento () {
        try {
            int cod = Integer.parseInt(jTextFieldCodigo.getText());
            TratamientoData data = new TratamientoData();
//          Buscar masajista original
            Tratamiento t = data.buscarTratamientoPorId(cod);
            if (t == null) {
                JOptionPane.showMessageDialog(this,
                    "No se encontro el Tratamiento.");
                return;
            }
//          Tomar valores del formulario
            String nombre = jTextFieldNombre.getText().
                trim();
            String detalle = jTextAreaDetalle.getText().
                trim();
//          Validaciones
            if ( ! nombre.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this,
                    "El nombre solo puede contener letras y espacios.");
                return;
            }
            if (detalle.length() > 60) {
                JOptionPane.showMessageDialog(this,
                    "El detalle no puede tener mas de 60 caracteres.");
                return;
            }
            String duracionStr = jTextFieldDuracion.getText().
                trim();
            if ( ! duracionStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                    "La duracion debe ser un numero entero valido.");
                return;
            }
            int duracion = Integer.parseInt(duracionStr);
            if (duracion <= 0 || duracion >= 720) {
                JOptionPane.showMessageDialog(this,
                    "Solo se permiten duraciones entre 1 y 720 minutos.");
                return;
            }
            String costostr = jTextFieldCosto.getText().
                trim();
            if ( ! costostr.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this,
                    "El costo debe ser un numero.");
                return;
            }
//          Actualizar objeto Tratamiento
            t.setNombre(nombre);
            t.setTipo((TipoDeTratamiento) jComboBoxTipo.getSelectedItem());
            t.setDetalle(detalle);
            t.setDuracion(duracion);
            t.setCosto(Double.parseDouble(jTextFieldCosto.getText()));
//            alternativa del estado
            if (jComboBoxEstado.getSelectedItem().
                toString().
                equalsIgnoreCase("Activo")) {
                t.setActivo("si");
            }
            else {
                t.setActivo("no");
            }
//          Guardar cambios
            data.modificarTratamiento(t);
            JOptionPane.showMessageDialog(this,
                "Tratamiento modificado con exito.");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Error Verifique bien el Formulario a Modificar.");
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldCosto = new javax.swing.JTextField();
        jTextFieldDuracion = new javax.swing.JTextField();
        jComboBoxEstado = new javax.swing.JComboBox<>();
        jLabelEstado = new javax.swing.JLabel();
        jButtonAtras = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButtonGuardar = new javax.swing.JButton();
        jLabelCosto = new javax.swing.JLabel();
        jLabelDuracion = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        jTextFieldCodigo = new javax.swing.JTextField();
        jLabelTipo = new javax.swing.JLabel();
        jLabelDetalle = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        jLabelCodTra = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDetalle = new javax.swing.JTextArea();

        setClosable(true);

        jLabelEstado.setBackground(new java.awt.Color(204, 204, 204));
        jLabelEstado.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelEstado.setForeground(new java.awt.Color(0, 0, 0));
        jLabelEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/estado.png"))); // NOI18N
        jLabelEstado.setText("Estado");
        jLabelEstado.setOpaque(true);

        jButtonAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/volver.png"))); // NOI18N
        jButtonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("SansSerif", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modificado.png"))); // NOI18N
        jLabel7.setText("Modificar Tratamiento");
        jLabel7.setOpaque(true);

        jButtonGuardar.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButtonGuardar.setText("Guardar Cambios");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jLabelCosto.setBackground(new java.awt.Color(204, 204, 204));
        jLabelCosto.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelCosto.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCosto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ahorrar-dinero.png"))); // NOI18N
        jLabelCosto.setText("Costo");
        jLabelCosto.setOpaque(true);

        jLabelDuracion.setBackground(new java.awt.Color(204, 204, 204));
        jLabelDuracion.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelDuracion.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reloj-de-arena.png"))); // NOI18N
        jLabelDuracion.setText("Duracion");
        jLabelDuracion.setOpaque(true);

        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });

        jButtonBuscar.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jLabelTipo.setBackground(new java.awt.Color(204, 204, 204));
        jLabelTipo.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelTipo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/masaje.png"))); // NOI18N
        jLabelTipo.setText("TIpo de Tratamiento");
        jLabelTipo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelTipo.setOpaque(true);

        jLabelDetalle.setBackground(new java.awt.Color(204, 204, 204));
        jLabelDetalle.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelDetalle.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/detalle.png"))); // NOI18N
        jLabelDetalle.setText("Detalle");
        jLabelDetalle.setOpaque(true);

        jLabelNombre.setBackground(new java.awt.Color(204, 204, 204));
        jLabelNombre.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        jLabelNombre.setText("Nombre");
        jLabelNombre.setOpaque(true);

        jLabelCodTra.setBackground(new java.awt.Color(204, 204, 204));
        jLabelCodTra.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabelCodTra.setForeground(new java.awt.Color(0, 0, 0));
        jLabelCodTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/numerico.png"))); // NOI18N
        jLabelCodTra.setText("Codigo del Tratamiento");
        jLabelCodTra.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelCodTra.setOpaque(true);

        jTextAreaDetalle.setColumns(20);
        jTextAreaDetalle.setLineWrap(true);
        jTextAreaDetalle.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDetalle);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(30, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelDetalle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCodTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jLabelTipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelCosto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelDuracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonBuscar))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonGuardar)
                .addGap(128, 128, 128))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelCodTra, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldCodigo)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabelDetalle))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelCosto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelEstado))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAtras))))
        );

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAtrasActionPerformed

    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        buscarTratamiento();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        modificarTratamiento();        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtras;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JComboBox<String> jComboBoxEstado;
    private javax.swing.JComboBox<TipoDeTratamiento> jComboBoxTipo;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelCodTra;
    private javax.swing.JLabel jLabelCosto;
    private javax.swing.JLabel jLabelDetalle;
    private javax.swing.JLabel jLabelDuracion;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaDetalle;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldCosto;
    private javax.swing.JTextField jTextFieldDuracion;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
 private void visualfondo () {
        // Reemplazamos el content pane
        setContentPane(jDesktopPane1);
        // Layout para que el panel se vea
        jDesktopPane1.setLayout(new java.awt.BorderLayout(HEIGHT, WIDTH));
        // Panel visible sobre el fondo
        jPanel1.setOpaque(false);
        jPanel1.setVisible(true);
        // Limpiamos y agregamos
        jDesktopPane1.removeAll();
        jDesktopPane1.add(jPanel1, java.awt.BorderLayout.CENTER);
        jDesktopPane1.repaint();
    }

    private void imagenFondo () {
        // Fondo 
        jDesktopPane1 = new javax.swing.JDesktopPane() {
            @Override
            protected void paintComponent (Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon(getClass().
                    getResource("/img/ModificarTratamiento.png")).
                    getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
}
