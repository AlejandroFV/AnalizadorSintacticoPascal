/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.analizadorControl;
import java.awt.Image;
import java.awt.Toolkit;
import Model.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Alejandro
 */
public class GUI extends javax.swing.JFrame {
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        setLocationRelativeTo(null);
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea_Escribir = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtArea_Resultado = new javax.swing.JTextArea();
        lblTitulo1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTitulo2 = new javax.swing.JLabel();
        lblVerificar = new javax.swing.JLabel();
        lblBorrar = new javax.swing.JLabel();
        lblEjemplos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analizador sintáctico de SQL");
        setIconImage(getIconImage());
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingrese su sentencia a analizar:");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(10, 110, 200, 20);

        txtArea_Escribir.setBackground(new java.awt.Color(204, 204, 255));
        txtArea_Escribir.setColumns(20);
        txtArea_Escribir.setRows(5);
        jScrollPane1.setViewportView(txtArea_Escribir);

        jLayeredPane1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 140, 587, 100);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Observaciónes:");
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(10, 250, 160, 30);

        txtArea_Resultado.setColumns(20);
        txtArea_Resultado.setEditable(false);
        txtArea_Resultado.setForeground(new java.awt.Color(204, 0, 0));
        txtArea_Resultado.setRows(5);
        jScrollPane2.setViewportView(txtArea_Resultado);

        jLayeredPane1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 280, 587, 96);

        lblTitulo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/titulo1.png"))); // NOI18N
        jLayeredPane1.add(lblTitulo1);
        lblTitulo1.setBounds(10, 20, 320, 47);
        jLayeredPane1.setLayer(lblTitulo1, javax.swing.JLayeredPane.POPUP_LAYER);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(0, 0, 610, 440);

        lblTitulo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/titulo2.png"))); // NOI18N
        jLayeredPane1.add(lblTitulo2);
        lblTitulo2.setBounds(300, 70, 300, 48);
        jLayeredPane1.setLayer(lblTitulo2, javax.swing.JLayeredPane.POPUP_LAYER);

        lblVerificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonVerificar.png"))); // NOI18N
        lblVerificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblVerificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVerificarMouseClicked(evt);
            }
        });
        jLayeredPane1.add(lblVerificar);
        lblVerificar.setBounds(400, 380, 100, 50);
        jLayeredPane1.setLayer(lblVerificar, javax.swing.JLayeredPane.PALETTE_LAYER);

        lblBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonBorrar.png"))); // NOI18N
        lblBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBorrarMouseClicked(evt);
            }
        });
        jLayeredPane1.add(lblBorrar);
        lblBorrar.setBounds(510, 380, 90, 50);
        jLayeredPane1.setLayer(lblBorrar, javax.swing.JLayeredPane.PALETTE_LAYER);

        lblEjemplos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonEjemplos.png"))); // NOI18N
        lblEjemplos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblEjemplos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEjemplosMouseClicked(evt);
            }
        });
        jLayeredPane1.add(lblEjemplos);
        lblEjemplos.setBounds(10, 380, 100, 50);
        jLayeredPane1.setLayer(lblEjemplos, javax.swing.JLayeredPane.PALETTE_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void lblVerificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVerificarMouseClicked
       txtArea_Resultado.setText("");
        File fichero = new File("salida.txt");
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fichero));
            writer.write(txtArea_Escribir.getText());
            writer.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar archivo de entrada debido a " + ex.toString());
        }
        analizadorControl a = new analizadorControl();
        txtArea_Resultado.setText("");
        txtArea_Resultado.setText(a.parsear());
    }//GEN-LAST:event_lblVerificarMouseClicked
    
    private void lblBorrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBorrarMouseClicked
        txtArea_Escribir.setText("");
        txtArea_Resultado.setText("");
    }//GEN-LAST:event_lblBorrarMouseClicked

    private void lblEjemplosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEjemplosMouseClicked
        
//        Ejemplos ventana = new Ejemplos();
//        ventana.setVisible(true);
        
        
    }//GEN-LAST:event_lblEjemplosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBorrar;
    private javax.swing.JLabel lblEjemplos;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblVerificar;
    public static javax.swing.JTextArea txtArea_Escribir;
    private javax.swing.JTextArea txtArea_Resultado;
    // End of variables declaration//GEN-END:variables
}
