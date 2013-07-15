/*
 * the first frame openned in the client app, It just open an interface to enable user
 * login to the server
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import ClientProject.ClientProject;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.Message;
//import util.Message;

/**
 *
 * @author hp
 */
public class SignInFrame extends javax.swing.JFrame implements Serializable {
    
    //reference to carry the ClientProject Object
    public ClientProject clientProject;
   
    
    /**
     * Creates new form SignInFrame
     */
    public SignInFrame(ClientProject clientProject) {
        initComponents();
        setTitle("UNIX chat - Sign in");   
         this.clientProject = clientProject;  
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         
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
        
        //if the log in button clicked
        jb_signIn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Message response=null;
                if(jtf_email.getText().equals("") || jpf_password.getText().equals("")){
                    /* the email or password is empty !
                     * so an error message to be shown
                     */
                     new JOptionPane().showMessageDialog(SignInFrame.this, "Empty Email or Password");
                }else{
                    /* the email and passwors are entered
                    *check if there is a socket or not, if not, intitalize one and its input/output/streams
                    * send the email/password to ther server to check
                    *receives a message from the server
                    * if the message is a contact list message .. login successfull, otherwise, there is a wrong entry
                    */
                   if(SignInFrame.this.clientProject.socket == null){
                       // There is no socket object yet
                       try {
            
                            //create a socket object that connect the server
                            SignInFrame.this.clientProject.socket = new Socket("127.0.0.1", 5000);
                            //creating object output and input streams
                            SignInFrame.this.clientProject.objectOutputStream = new ObjectOutputStream(SignInFrame.this.clientProject.socket.getOutputStream());
                            SignInFrame.this.clientProject.objectInputStream = new ObjectInputStream(SignInFrame.this.clientProject.socket.getInputStream());

                        } catch (IOException ex) {
                            Logger.getLogger(ClientProject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
                   // receiving a message !
                   Message signInMessage=new Message(Message.SIGN_IN,jtf_email.getText()+";"+ jpf_password.getText());
                    try {
                        SignInFrame.this.clientProject.objectOutputStream.writeObject(signInMessage);
                        response =(Message)SignInFrame.this.clientProject.objectInputStream.readObject();
                       
                    } catch (IOException ex) {
                        Logger.getLogger(SignInFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (ClassNotFoundException ex) {
                        Logger.getLogger(SignInFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                    if(response.getType().equals(Message.CONTACT_LIST)){
                        //the login is successfull
                        SignInFrame.this.clientProject.myEmail = SignInFrame.this.jtf_email.getText();
                        SignInFrame.this.clientProject.mainFrame = new MainFrame((Vector)response.getContent(), SignInFrame.this.clientProject);
                        SignInFrame.this.clientProject.mainFrame.setVisible(true);                    
                        SignInFrame.this.setVisible(false); 
                        SignInFrame.this.clientProject.myStatus = "online";
                        try {
                            SignInFrame.this.clientProject.objectOutputStream.writeObject(new Message(Message.STATUS, "online"));
                        } catch (IOException ex) {
                            Logger.getLogger(SignInFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        // incorrect email or password
                        new JOptionPane().showMessageDialog(SignInFrame.this, "Incorrect Email or Password");
                    }
                
            
                }
            }

        });
        
        // the register button is clicked
        jb_register.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               SignInFrame.this.clientProject.registerFrame = new RegisterFrame(SignInFrame.this.clientProject);
               SignInFrame.this.clientProject.registerFrame.setVisible(true);
               
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

        jl_email = new javax.swing.JLabel();
        jl_password = new javax.swing.JLabel();
        jtf_email = new javax.swing.JTextField();
        jb_signIn = new javax.swing.JButton();
        jb_register = new javax.swing.JButton();
        jpf_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jl_email.setText("Email:");

        jl_password.setText("Password:");

        jb_signIn.setText("Sign In");

        jb_register.setText("Register");
        jb_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_registerActionPerformed(evt);
            }
        });

        jpf_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpf_passwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jb_signIn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jb_register)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl_email)
                                    .addComponent(jl_password))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtf_email, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                    .addComponent(jpf_password))))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_email)
                    .addComponent(jtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_password)
                    .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jb_signIn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jb_register)
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jpf_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpf_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpf_passwordActionPerformed

    private void jb_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_registerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_registerActionPerformed

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
            java.util.logging.Logger.getLogger(SignInFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignInFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignInFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignInFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jb_register;
    private javax.swing.JButton jb_signIn;
    private javax.swing.JLabel jl_email;
    private javax.swing.JLabel jl_password;
    private javax.swing.JPasswordField jpf_password;
    private javax.swing.JTextField jtf_email;
    // End of variables declaration//GEN-END:variables
}
