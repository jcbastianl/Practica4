/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package igu;



import controlador.TDA.listas.DynamicList;
import controlador.DAO.DaoImplement;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Venta.VentaControl;


import controlador.logica.AutoControl;
import controlador.logica.VendedorControl;


import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import logica.Auto;

import logica.Vendedor;
import logica.Venta;
import vista.lista.tablas.ModeloTablaVenta;

/**
 *
 * @author jsbal
 */
public class FrmRegistroVenta extends javax.swing.JFrame {

    private VentaControl ventaControl = new VentaControl();
    private AutoControl autoControl = new AutoControl();
    private VendedorControl vendedorControladora = new VendedorControl();
    private controlador.Venta.VentaControl control = new controlador.Venta.VentaControl();
    private ModeloTablaVenta modelotabla = new ModeloTablaVenta();

    public FrmRegistroVenta() throws EmptyException {
        initComponents();
        cargarVendedoresEnComboBox(cbxVendedores);
        cargarAutosEnComboBox(cbxAutos);
        limpiar();
        cargarTabla();

    }

    public void cargarVentas(DynamicList<Venta> ventas) {
        modelotabla.setVentas(ventas);
    }

    private void cargarVista() {

        int fila = tablaVenta.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Venta venta = modelotabla.getVentas().getInfo(fila);

                txtFecha.setDate(venta.getFechaVenta());
                txtMonto.setText(String.valueOf(venta.getMonto()));

                // Obtener vendedor seleccionado 
                Vendedor vendedor = (Vendedor) cbxVendedores.getSelectedItem();

                // Obtener casa seleccionada
                Auto auto = (Auto) cbxAutos.getSelectedItem();

            } catch (Exception ex) {
                Logger.getLogger(FrmRegistroVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    

    public void cargarAutosEnComboBox(JComboBox cbx) throws EmptyException {
    DaoImplement<Auto> autoDao = new DaoImplement<>(Auto.class); // Crear instancia del DAO para Auto

    cbx.removeAllItems();
    DynamicList<Auto> autosList = autoDao.all(); // Obtener la lista de autos desde el DAO

    if (autosList.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Lista de autos vacía");
    } else {
        for (int i = 0; i < autosList.getLenght(); i++) {
            Auto auto = autosList.getInfo(i);
            cbx.addItem(auto.getMarca() + " - Monto: " + auto.getPrecio()); // Agregar marca y monto del auto al JComboBox
        }
    }
}


    
    
   public void cargarVendedoresEnComboBox(JComboBox cbx) throws EmptyException {
    DaoImplement<Vendedor> vendedorDao = new DaoImplement<>(Vendedor.class); // Crear instancia del DAO para Vendedor

    cbx.removeAllItems();
    DynamicList<Vendedor> vendedoresList = vendedorDao.all(); // Obtener la lista de vendedores desde el DAO

    if (vendedoresList.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Lista de vendedores vacía");
    } else {
        for (int i = 0; i < vendedoresList.getLenght(); i++) {
            Vendedor vendedor = vendedoresList.getInfo(i);
            cbx.addItem(vendedor.getNombre() + " " + vendedor.getApellido()); // Agregar nombre y apellido del vendedor al JComboBox
        }
    }
}
    
    public Boolean verificar() {
        String monto = txtMonto.getText().trim();
        if (monto.isEmpty()) {
            return false;
        }
        Object vendedorSeleccionado = cbxVendedores.getSelectedItem();
        if (vendedorSeleccionado == null) {
            return false;
        }
        Object autoSeleccionada = cbxAutos.getSelectedItem();
        if (autoSeleccionada == null) {
            return false;
        }
        Date fechaSeleccionada = txtFecha.getDate();
        if (fechaSeleccionada == null) {
            return false;
        }
        return true;
    }

    private void limpiar() {

        tablaVenta.clearSelection();
        txtMonto.setText("");

    }

    private void cargarTabla() {
        modelotabla.setVentas(control.all());
        tablaVenta.setModel(modelotabla);
        tablaVenta.updateUI();
    }

    private void guardar() throws EmptyException {
        if (verificar()) {

            Vendedor vendedorSeleccionado = vendedorControladora.all().getInfo(cbxVendedores.getSelectedIndex());
            ventaControl.getVenta().setVendedor(vendedorSeleccionado);

            Auto autoSeleccionada = autoControl.all().getInfo(cbxAutos.getSelectedIndex());
            ventaControl.getVenta().setAuto(autoSeleccionada);

            try {

                double monto = Double.parseDouble(txtMonto.getText());
                ventaControl.getVenta().setMonto(monto);

                Date fechaVenta = txtFecha.getDate();
                ventaControl.getVenta().setFechaVenta(fechaVenta);

                control.persist(ventaControl.getVenta());
                JOptionPane.showMessageDialog(null, "Datos guardados");
                cargarTabla();
                limpiar();
                ventaControl.setVenta(null);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
    try {
        String criterio = cbxCriterio.getSelectedItem().toString().toLowerCase();
        String textoBusqueda = txtBusqueda.getText();
        String tipoBusqueda = cbxTipoBusqueda.getSelectedItem().toString();

        DynamicList<Venta> listaVentas;

        if (tipoBusqueda.equals("Lineal")) {
            listaVentas = ventaControl.busquedaLineal(textoBusqueda, ventaControl.getVentas(), criterio);
        } else if (tipoBusqueda.equals("Binaria")) {
            listaVentas = ventaControl.busquedaBinaria(textoBusqueda, ventaControl.getVentas(), criterio);
        } else {
            // Opción por defecto: búsqueda lineal
            listaVentas = ventaControl.busquedaLineal(textoBusqueda, ventaControl.getVentas(), criterio);
        }

        modelotabla.setVentas(listaVentas);
        tablaVenta.setModel(modelotabla);
        tablaVenta.updateUI();
    } catch (Exception e) {
        System.out.println("Error al buscar Venta: " + e.getMessage());
    }
}
    
 


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxVendedores = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbxAutos = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        cbxCriterio = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbxTipoBusqueda = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("REGISTRO VENTA");

        jLabel2.setText("SELECCIONE VENDEDOR");

        cbxVendedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxVendedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxVendedoresActionPerformed(evt);
            }
        });

        jLabel3.setText("SELECCIONE AUTO A VENDER");

        cbxAutos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("ESCRIBA EL MONTO");

        jLabel5.setText("FECHA VENTA");

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaVenta);

        jLabel6.setText("VENTAS REGISTRADAS");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        cbxCriterio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido", "Monto", "Marca" }));
        cbxCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCriterioActionPerformed(evt);
            }
        });

        jLabel7.setText("CRITERIO");

        cbxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Binaria", "Lineal", " " }));

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4)
                                        .addComponent(cbxAutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbxVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtMonto)))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(87, 87, 87))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnGuardar)
                                            .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBusqueda)))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jLabel1)))
                .addGap(91, 91, 91))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxAutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
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

    private void cbxVendedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxVendedoresActionPerformed

    }//GEN-LAST:event_cbxVendedoresActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            guardar();
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCriterioActionPerformed

    }//GEN-LAST:event_cbxCriterioActionPerformed

    private void txtBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed
           try {
            buscar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtBusquedaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmRegistroVenta().setVisible(true);
                } catch (EmptyException ex) {
                    Logger.getLogger(FrmRegistroVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxAutos;
    private javax.swing.JComboBox<String> cbxCriterio;
    private javax.swing.JComboBox<String> cbxTipoBusqueda;
    private javax.swing.JComboBox<String> cbxVendedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtBusqueda;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
