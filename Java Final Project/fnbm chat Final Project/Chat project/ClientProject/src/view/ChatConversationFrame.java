/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ClientProject.ClientProject;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.Chat;
import util.Contact;
import util.Message;

/**
 *
 * @author hp
 */
public class ChatConversationFrame extends javax.swing.JFrame implements Serializable{

    ClientProject clientProject;
    public Vector<Contact> contacts;
    public int conversationID;
    /**
     * Creates new form ChatFrame
     */
    public ChatConversationFrame(ClientProject clientProject, Vector<Contact> contacts ) {
        initComponents();
        setTitle("Chat Window");
        this.clientProject = clientProject;
        contacts.removeElementAt(contacts.size() -1);
        this.contacts = contacts;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        //write contacts
        for(Contact contact: contacts){
            System.out.println("chat frame contact : " + contact.getEmail());
        }
        
        //chat frame count
        System.out.println("chat frame no of contacts : " + contacts.size());
        
        
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
        
        //send message
        jb_sendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ChatConversationFrame.this.jta_chatBox.append("Me: " + jta_sendBox.getText()+"\n");    
                    Vector<Contact> contacts = new Vector<Contact>();
                    System.out.println("my email : " + ChatConversationFrame.this.clientProject.myEmail);
                    System.out.println("my status : " + ChatConversationFrame.this.clientProject.myStatus);
                    contacts.add(0,new Contact(ChatConversationFrame.this.clientProject.myEmail, ChatConversationFrame.this.clientProject.myStatus));
                    System.out.println("contact to send : " + ChatConversationFrame.this.contacts.elementAt(0).getEmail());
                    contacts.add(1, ChatConversationFrame.this.contacts.elementAt(0));
                    for(Contact contact: contacts){
                        System.out.println("send contact : " + contact.getEmail());
                    }
                    //contacts.add(0,new Contact(ChatFrame.this.clientProject.myEmail, ChatFrame.this.clientProject.myStatus));
                    System.out.println(jta_sendBox.getText());
                    Message chatMessage = new Message(Message.MESSAGE, new Chat(jta_sendBox.getText(), contacts));
                    ChatConversationFrame.this.clientProject.objectOutputStream.writeObject(chatMessage);
                    ChatConversationFrame.this.jta_sendBox.setText("");
                } catch (IOException ex) {
                    Logger.getLogger(ChatConversationFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        
        //saving conversation
        jb_saveConversation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                JFileChooser fa = new JFileChooser();
                String path = fa.getSelectedFile().getPath();
                try {
                    FileOutputStream fos = new FileOutputStream(path);
                    DataOutputStream dos = new DataOutputStream(fos);
                     String s = jta_chatBox.getText();
                     dos.writeUTF(s);
                }
                 catch (FileNotFoundException ex) {
                    Logger.getLogger(ChatConversationFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                 catch (IOException ex) {
                        Logger.getLogger(ChatConversationFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
              
                
            }
        });
        
        
        // send file button - not finished yet !
        jb_sendFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fa = new JFileChooser();
                String path = fa.getSelectedFile().getPath();
                
            }
        });
        
        //add contant to conversation - not finished yet !
        
    }
    
    
    public void showComingMessage(String message, String email){
         jta_chatBox.append(email + ": " + message +"\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jta_chatBox = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jta_sendBox = new javax.swing.JTextArea();
        jb_sendMessage = new javax.swing.JButton();
        jb_sendFile = new javax.swing.JButton();
        jb_saveConversation = new javax.swing.JButton();
        jb_addContact = new javax.swing.JButton();
        jl_messages = new javax.swing.JLabel();
        jl_contacts = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jli_contacts = new javax.swing.JList();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jta_chatBox.setEditable(false);
        jta_chatBox.setColumns(20);
        jta_chatBox.setRows(5);
        jta_chatBox.setName("jta_chatBox"); // NOI18N
        jScrollPane1.setViewportView(jta_chatBox);

        jta_sendBox.setColumns(20);
        jta_sendBox.setRows(5);
        jta_sendBox.setName("jta_sendBox"); // NOI18N
        jScrollPane2.setViewportView(jta_sendBox);

        jb_sendMessage.setText("Send");
        jb_sendMessage.setName("jb_sendMessage"); // NOI18N
        jb_sendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_sendMessageActionPerformed(evt);
            }
        });

        jb_sendFile.setText("Send File");
        jb_sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_sendFileActionPerformed(evt);
            }
        });

        jb_saveConversation.setText("Save Conversation");
        jb_saveConversation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_saveConversationActionPerformed(evt);
            }
        });

        jb_addContact.setText("Add Contact");

        jl_messages.setText("Messages");

        jl_contacts.setText("Contacts");

        jScrollPane4.setViewportView(jli_contacts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jb_sendFile)
                        .addGap(38, 38, 38)
                        .addComponent(jb_saveConversation))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jb_sendMessage))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_messages))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_contacts)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jb_addContact))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_messages)
                    .addComponent(jl_contacts))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jb_addContact))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jb_sendFile)
                    .addComponent(jb_saveConversation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jb_sendMessage)
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jb_sendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_sendMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_sendMessageActionPerformed

    private void jb_sendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_sendFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_sendFileActionPerformed

    private void jb_saveConversationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_saveConversationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_saveConversationActionPerformed

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
            java.util.logging.Logger.getLogger(ChatConversationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatConversationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatConversationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatConversationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new ChatFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jb_addContact;
    private javax.swing.JButton jb_saveConversation;
    private javax.swing.JButton jb_sendFile;
    private javax.swing.JButton jb_sendMessage;
    private javax.swing.JLabel jl_contacts;
    private javax.swing.JLabel jl_messages;
    private javax.swing.JList jli_contacts;
    private javax.swing.JTextArea jta_chatBox;
    private javax.swing.JTextArea jta_sendBox;
    // End of variables declaration//GEN-END:variables
}
