/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ClientProject.ClientProject;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.ContactInfo;
import util.Message;

/**
 *
 * @author MAGhayaty
 */
public class AddFriendFrame extends javax.swing.JFrame {

    /**
     * Creates new form AddFriendFrame
     */
    
    public ClientProject clientProject;
    
    
    public AddFriendFrame(ClientProject clientProject) {
        initComponents();
        setTitle("Add Friend");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.clientProject = clientProject;
        
        
         /**
         * Look And feel 
         */
         try {
                UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                SwingUtilities.updateComponentTreeUI(this);
         } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SignInFrame.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ParseException ex) {
                Logger.getLogger(SignInFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         //when the send button is clicked
        jb_SendInvitation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
             if(jtf_email2.getText().equals("")) 
                    //some empty fields exist !
                 JOptionPane.showMessageDialog(AddFriendFrame.this, "the e-mail field is empty");
             else{
                    try {
                        System.out.println("Adding friend");
                        Message addMessage = new Message(Message.ADD_CONTACT, AddFriendFrame.this.jtf_email2.getText());
                        AddFriendFrame.this.clientProject.objectOutputStream.writeObject(addMessage);
                        AddFriendFrame.this.setVisible(false);
                        AddFriendFrame.this.clientProject.mainFrame.confirmAddingContact(jtf_email2.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(AddFriendFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
             }
            }   
            });
        
        
        
        
        
        
    }
            
        
        

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jl_email2 = new javax.swing.JLabel();
        jtf_email2 = new javax.swing.JTextField();
        jb_SendInvitation = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jl_email2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jl_email2.setText("E-mail");

        jb_SendInvitation.setText("Send Invitation");
        jb_SendInvitation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_SendInvitationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jb_SendInvitation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jl_email2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jtf_email2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_email2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_email2))
                .addGap(46, 46, 46)
                .addComponent(jb_SendInvitation)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb_SendInvitationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_SendInvitationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_SendInvitationActionPerformed

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
            java.util.logging.Logger.getLogger(AddFriendFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFriendFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AddFriendFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jb_SendInvitation;
    private javax.swing.JLabel jl_email2;
    private javax.swing.JTextField jtf_email2;
    // End of variables declaration//GEN-END:variables
}