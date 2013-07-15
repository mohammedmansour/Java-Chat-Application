/*
 * the window which the user can register or sign up 
 */
package view;

import ClientProject.ClientProject;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.Contact;
import util.ContactInfo;
import util.Message;

/**
 *
 * @author hp
 */
public class RegisterFrame extends javax.swing.JFrame implements Serializable {

    /**
     * Creates new form RegisterFrame
     */
    
    public ClientProject clientProject;
    
    public RegisterFrame(ClientProject clientProject) {
        initComponents();
        setTitle("Register");
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
        
        //adding items to days combobox
        for(int i=1;i<32;i++)
            jcb_day.addItem(i);
        jcb_day.setSelectedItem(1);
        
        //adding items to months combobox
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}; 
        for(int i=0;i<12;i++)
            jcb_month.addItem(months[i]); 
        jcb_month.setSelectedItem(months[0]);
       
        //adding items to years combobox
        for(int i=1920;i<2006;i++)
            jcb_year.addItem(i);
        jcb_year.setSelectedItem(1990);
      
        
        // submission button is clicked   
        jb_submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Message Response=null;
                ContactInfo contactInfo = null;
                /*
                 * make some checks
                 * check if any field is empty
                 * check if the two passwords not matching
                 * check if there is a socket or not, if not, intitalize one and its input/output/streams
                 * send the message of the new user to the server
                 * receive a message of confirmation or error
                 * if the registeration is true, the frame will lead the user to the main frame directly
                 */
                
                if(jtf_email.getText().equals("") || jtf_firstName.getText().equals("") || jtf_lastName.getText().equals("") || jpf_password.getPassword().equals("") || jpf_confirmPassword.getPassword().equals(""))
                    //some empty fields exist !
                    new  JOptionPane().showMessageDialog(RegisterFrame.this, "One Field is Empty");
                else if(!jpf_password.getText().equals(jpf_confirmPassword.getText()))
                    // the two passwords don't match !
                    new  JOptionPane().showMessageDialog(RegisterFrame.this, "Password not matched");
                else{
                    // creating a contant-info object
                    contactInfo = new ContactInfo(jtf_email.getText(), jtf_firstName.getText(), jtf_lastName.getText(), jpf_password.getText(), "f", "1/1/1990", "egypt");
                    
                    Message registerMessage = new Message(Message.SIGN_UP, contactInfo);
                    
                     if(RegisterFrame.this.clientProject.socket == null){
                       // There is no socket object yet
                       try {
            
                            //create a socket object that connect the server
                            RegisterFrame.this.clientProject.socket = new Socket("127.0.0.1", 5000);
                            //creating object output and input streams
                            RegisterFrame.this.clientProject.objectOutputStream = new ObjectOutputStream(RegisterFrame.this.clientProject.socket.getOutputStream());
                            RegisterFrame.this.clientProject.objectInputStream = new ObjectInputStream(RegisterFrame.this.clientProject.socket.getInputStream());

                        } catch (IOException ex) {
                            Logger.getLogger(ClientProject.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        
                   }
                      try {
                            //sending the message to the server
                            RegisterFrame.this.clientProject.objectOutputStream.writeObject(registerMessage);
                        } catch (IOException ex) {
                            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    try {
                        //read response message from server   
                        Response = (Message)RegisterFrame.this.clientProject.objectInputStream.readObject();
                    } catch (IOException ex) {
                        Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //check the confirmation Message 
                    if(Response.getType().equals(Message.SIGN_UP_SUCCESS)){
                        //registeration is successful
                        RegisterFrame.this.clientProject.myEmail = RegisterFrame.this.jtf_email.getText();
                        RegisterFrame.this.clientProject.mainFrame = new MainFrame(new Vector<Contact>(), RegisterFrame.this.clientProject);
                        RegisterFrame.this.clientProject.mainFrame.setVisible(true);
                        //RegisterFrame.this.clientProject.signInFrame.setVisible(false);
                        RegisterFrame.this.setVisible(false);
                    }
                    else // if registration fails
                        new JOptionPane().showMessageDialog(RegisterFrame.this, Response.getContent());
                    
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

        jbg_gender = new javax.swing.ButtonGroup();
        jl_firstName = new javax.swing.JLabel();
        jtf_firstName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jl_email = new javax.swing.JLabel();
        jtf_email = new javax.swing.JTextField();
        jl_lastName = new javax.swing.JLabel();
        jtf_lastName = new javax.swing.JTextField();
        jl_confirmPassword = new javax.swing.JLabel();
        jl_birthDate = new javax.swing.JLabel();
        jcb_day = new javax.swing.JComboBox();
        jl_gender = new javax.swing.JLabel();
        jrb_male = new javax.swing.JRadioButton();
        jrb_female = new javax.swing.JRadioButton();
        jcb_month = new javax.swing.JComboBox();
        jcb_year = new javax.swing.JComboBox();
        jb_submit = new javax.swing.JButton();
        jpf_password = new javax.swing.JPasswordField();
        jpf_confirmPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jl_firstName.setText("First Name:");

        jLabel1.setText("Password:");

        jl_email.setText("Email:");

        jl_lastName.setText("Last Name:");

        jl_confirmPassword.setText("Confirm Password:");

        jl_birthDate.setText("Birth Date:");

        jcb_day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_dayActionPerformed(evt);
            }
        });

        jl_gender.setText("Gender:");

        jbg_gender.add(jrb_male);
        jrb_male.setSelected(true);
        jrb_male.setText("Male");

        jbg_gender.add(jrb_female);
        jrb_female.setText("Female");

        jb_submit.setText("Submit");

        jpf_confirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpf_confirmPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_lastName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtf_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jl_confirmPassword)
                                    .addComponent(jl_birthDate))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jcb_day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jcb_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jcb_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jpf_confirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jl_gender)
                                .addGap(58, 58, 58)
                                .addComponent(jrb_male)
                                .addGap(18, 18, 18)
                                .addComponent(jrb_female))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jb_submit))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_firstName)
                            .addComponent(jl_email))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtf_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_email))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_firstName)
                    .addComponent(jtf_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_lastName))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_confirmPassword)
                    .addComponent(jpf_confirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_birthDate)
                    .addComponent(jcb_day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jl_gender)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jrb_male)
                        .addComponent(jrb_female)))
                .addGap(40, 40, 40)
                .addComponent(jb_submit)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jcb_day.getAccessibleContext().setAccessibleName("Day");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcb_dayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_dayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_dayActionPerformed

    private void jpf_confirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpf_confirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpf_confirmPasswordActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jb_submit;
    private javax.swing.ButtonGroup jbg_gender;
    private javax.swing.JComboBox jcb_day;
    private javax.swing.JComboBox jcb_month;
    private javax.swing.JComboBox jcb_year;
    private javax.swing.JLabel jl_birthDate;
    private javax.swing.JLabel jl_confirmPassword;
    private javax.swing.JLabel jl_email;
    private javax.swing.JLabel jl_firstName;
    private javax.swing.JLabel jl_gender;
    private javax.swing.JLabel jl_lastName;
    private javax.swing.JPasswordField jpf_confirmPassword;
    private javax.swing.JPasswordField jpf_password;
    private javax.swing.JRadioButton jrb_female;
    private javax.swing.JRadioButton jrb_male;
    private javax.swing.JTextField jtf_email;
    private javax.swing.JTextField jtf_firstName;
    private javax.swing.JTextField jtf_lastName;
    // End of variables declaration//GEN-END:variables
}
