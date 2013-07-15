/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

//import javax.swing.UIManager;
import java.io.IOException;
import java.net.BindException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ServerGUI extends javax.swing.JFrame {

    private Thread th;
    /**
     * Creates new form ServerGUI
     */
    Server srvr; //identify object use from ServerGUI Class "Reference"

    public ServerGUI() {
        initComponents();
        srvr = new Server(); //initialize object

        jb_Connect.setEnabled(true);
        jb_Abort.setEnabled(false);
        jt_ann.setEnabled(false);
        jb_stat.setEnabled(false);
        jt_ann.append("\tServer Started at: " + new Date());
        /**
         * Look and feel
         */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jb_Connect = new javax.swing.JButton();
        jb_Abort = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jt_ann = new javax.swing.JTextArea();
        jb_stat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jb_Connect.setText("Connect");
        jb_Connect.setActionCommand("Open Server");
        jb_Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_ConnectActionPerformed(evt);
            }
        });

        jb_Abort.setText("Close Server");
        jb_Abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_AbortActionPerformed(evt);
            }
        });

        jt_ann.setColumns(20);
        jt_ann.setRows(5);
        jScrollPane1.setViewportView(jt_ann);

        jb_stat.setText("Show Statistics");
        jb_stat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_statActionPerformed(evt);
            }
        });

        jLabel1.setText("  FNBM Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jb_Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addComponent(jb_Abort, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jb_stat)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jb_Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_Abort, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jb_stat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * jb_ConnectActionPerformed button
     *
     * use identified object from class Server to open connection using
     * StartServer() method
     *
     * @param evt
     */
    private void jb_ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_ConnectActionPerformed
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jb_Connect.setEnabled(false);
                    jb_Abort.setEnabled(true);
                    jb_Abort.setFocusable(true);
                    jt_ann.setEnabled(true);
                    jb_stat.setEnabled(true);
                    srvr.startServer();
                } catch (BindException ex) {
                    JOptionPane.showMessageDialog(ServerGUI.this, "Server is already binded to port 5000");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        th.start();
    }//GEN-LAST:event_jb_ConnectActionPerformed

    /**
     * jb_AbortActionPerformed button
     *
     * use identified object from class Server to abort connection using
     * StopServer() method
     *
     * @param evt
     */
    private void jb_AbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_AbortActionPerformed
        try {
            th.stop();
            srvr.stopServer();
            jb_Connect.setEnabled(true);
            jb_Abort.setEnabled(false);
            jt_ann.setEnabled(false);
            jb_stat.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jb_AbortActionPerformed

    private void jb_statActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_statActionPerformed
        // TODO add your handling code here:
        int online = 0, offline = 0, busy = 0;
        String status;
        Vector<SocketServerHandler> connected = SocketServerHandler.getConnectedContacts();
        for (int i = 0; i < connected.size(); i++) {
            status = connected.get(i).getStatus();
            if (status.equals("online")) {
                online++;
            } else if (status.equals("offline")) {
                offline++;
            } else {
                busy++;
            }
        }
        JOptionPane.showMessageDialog(ServerGUI.this, "Number of online users is: " + online
                + "\nNumber of busy users is: " + busy
                + "\nNumber of offline users is: " + offline);
    }//GEN-LAST:event_jb_statActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        System.setProperty(
                "Quaqua.tabLayoutPolicy", "wrap");
        // set the Quaqua Look and Feel in the UIManager
        try {
            //  UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
            // set UI manager properties here that affect Quaqua
        } catch (Exception e) {
            // take an appropriate action here
        }
        try {

            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();

        }

//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jb_Abort;
    private javax.swing.JButton jb_Connect;
    private javax.swing.JButton jb_stat;
    private javax.swing.JTextArea jt_ann;
    // End of variables declaration//GEN-END:variables
}
