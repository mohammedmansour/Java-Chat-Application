/*
 *The main frame reprents the main chat window which appear when the user is logged in
 * it contains the list of contacts and other options to use the chat system
 */
package view;

import ClientProject.ClientProject;
import control.Monitor;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListDataListener;
import util.Chat;
import util.Contact;
import util.Message;


/**
 *
 * @author hp
 */
public class MainFrame extends javax.swing.JFrame implements Serializable {

    ClientProject clientProject;
    Vector<Contact> contacts;
    DefaultListModel<String> contactList;
    Vector<ChatFrame> chatFrames;
    Vector<ChatConversationFrame> chatConversationFrames;
    AddConversation addConversation;
    /**
     * Creates new form MainFrame
     */
    public MainFrame(Vector<Contact> contacts, ClientProject clientProject) {
        initComponents();
        setTitle("UNIX chat: " + clientProject.myEmail);
        this.clientProject = clientProject;
        jli_list.setModel(new DefaultListModel());
        this.contacts = contacts;
        chatFrames = new Vector<ChatFrame>();
        MainFrame.this.clientProject.monitor = new Monitor(MainFrame.this.clientProject);
        new Thread(MainFrame.this.clientProject.monitor).start();
       
        
        //check if the main frmae is opened
        System.out.println("mainframe is opened \n");
        for(Contact contact: contacts){
            System.out.println("mainframe contact : " + contact.getEmail());
        }
        
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
        
        
        contactList = (DefaultListModel<String>) jli_list.getModel();
        
        for(Contact contact: contacts){
            contactList.addElement(contact.getEmail() +" --> " + contact.getStatus());
        }
        
        
        //status combobox filling
        String[] status = {"online", "offline", "busy"};
        for(int i=0;i<3;i++){
            jcb_status.addItem(status[i]);
            jcb_status.setSelectedItem(status[0]);
        }
        
        
        
        
        // if the signout button is clicked
        jb_signOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //close main menu
                MainFrame.this.setVisible(false);
                //close all conversations
                if(chatFrames != null){
                    for(ChatFrame chatFrame: MainFrame.this.chatFrames)
                        chatFrame.setVisible(false);
                }
                //open login again
                MainFrame.this.clientProject.signInFrame.setVisible(true);
            }
        });
        
        
        /**
         * add Contact button
         * 
         * when pressed you're switched to new frame "addFriendFrame" to add new friend to your contact list
         */
        jmi_addContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               MainFrame.this.clientProject.addFriend=new AddFriendFrame(MainFrame.this.clientProject);        
               MainFrame.this.clientProject.addFriend.setVisible(true);
                       
            }
        });
        
       //setting action listener for the contacts
       MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                int index = -1;
                boolean alreadyOpened = false;
                //two clicks to open new conversation
                if(me.getClickCount() == 2){
                    
                    index = MainFrame.this.jli_list.getSelectedIndex();
                    System.out.println("Selected index : " + index);
                    System.out.println(index);
                    //check if there is already opened conversation
                    if(chatFrames != null){
                        for(ChatFrame chatFrame : chatFrames){                       
                        if(chatFrame.contacts.size() == 1 & MainFrame.this.contacts.elementAt(index) == chatFrame.contacts.elementAt(0)){
                            alreadyOpened = true;
                            //focus it
                            chatFrame.setFocusable(true);
                            break;
                         }
                     }
                    }
                     if(!alreadyOpened){
                         // open new chat conversation
                        Vector<Contact> contacts = new Vector<Contact>();                       
                        contacts.add(MainFrame.this.contacts.elementAt(index));
                         contacts.add(new Contact(MainFrame.this.clientProject.myEmail, MainFrame.this.clientProject.myStatus));
                        ChatFrame newChatFrame = new ChatFrame(MainFrame.this.clientProject,contacts);
                        newChatFrame.setVisible(true);
                        if(chatFrames.isEmpty())
                            chatFrames.add(0,newChatFrame);
                        else
                            chatFrames.add(newChatFrame);
                     }   
                 }
            }
            
            @Override
            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
       
      jli_list.addMouseListener(mouseListener);
       
       //change status
       jcb_status.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] status = {"online", "offline", "busy"};
                Message statusMessage = new Message(Message.STATUS, status[MainFrame.this.jcb_status.getSelectedIndex()]);
               try {
                   MainFrame.this.clientProject.objectOutputStream.writeObject(statusMessage);
               } catch (IOException ex) {
                   Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
               }
               
            }
        });
       
       
       
       //delete contact
       jmi_deleteContact.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!MainFrame.this.jli_list.isSelectionEmpty()){
                    try {
                        MainFrame.this.clientProject.objectOutputStream.writeObject(new Message(Message.DELETE, contactList.getElementAt(jli_list.getSelectedIndex()).split(" ")[0]));
                        contactList.removeElementAt(jli_list.getSelectedIndex());
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
       
       //open new conversation
       jmi_addConversation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("hell add conversation");
                MainFrame.this.addConversation = new AddConversation(MainFrame.this.clientProject);
                MainFrame.this.addConversation.setVisible(true);
            }
        });
       
       
            
    }
    
    
    
    /**
     * to asign message to a current openning conversation or open new one
     * @param chat 
     */
    public void assignFrame(Chat  chat){
        System.out.println("Trying to assign frame");
        Vector<Contact> contacts = (Vector<Contact>) chat.getReceiver();
        for(Contact contact: contacts){
            //
        }
        //contacts.remove(1);
        int chatFrameIndex = matchVector(contacts);
        if(chatFrameIndex >= 0){
            chatFrames.elementAt(chatFrameIndex).setVisible(true);
            chatFrames.elementAt(chatFrameIndex).showComingMessage(chat.getMessage(), chat.getReceiver().elementAt(0).getEmail());
        }else{
            
            ChatFrame newChatFrame = new ChatFrame(clientProject, contacts);
            newChatFrame.setVisible(true);
            chatFrames.add(newChatFrame);
            newChatFrame.showComingMessage(chat.getMessage(), chat.getReceiver().elementAt(0).getEmail());
        }
    }
    
    
    
    
    
    /**
     * confirm adding the contact requested by the user
     * it will add the user to the contacts and update the contact list
     * @param message // the confirmation message
     */
    public void confirmAddingContact(String message){
            JOptionPane.showMessageDialog(this, message + "is added !");
            contacts.add(new Contact((String) message, "offline"));
            contactList.addElement(message + " --> " + "offline");
        }
    
    /**
     * to update the contact list when the server is sending a fresh one
     * @param message // message contain vector of contacts
     */
    public void updateContactList(Message message){
        contactList.clear();
        for(Contact contact:(Vector<Contact>) message.getContent()){
            contactList.addElement(contact.getEmail() +" --> " + contact.getStatus());
        }      
    }
    
    
    private int matchVector(Vector<Contact> contacts){
        boolean matchedFrame = false;
        boolean matchedContact = false;
        int index = -1;
        System.out.println("Contacts size : " + contacts.size());
        
        if(contacts.size() == 2){
            for(ChatFrame chatFrame: chatFrames){
                System.out.println("chatFrame Contacts size : " + chatFrame.contacts.size());
                if(chatFrame.contacts.size() == 1){
                    if(contacts.elementAt(0).getEmail().equals(chatFrame.contacts.elementAt(0).getEmail())){
                        index = chatFrames.indexOf(chatFrame);
                        break;
                    }
                }else{
                    continue;
                }
                
            }
        }else{
                for(ChatFrame chatFrame: chatFrames){
                // checking if the chat frame contacts is identical to the new chat contacts
                matchedFrame = false;
                for(Contact contact: contacts){
                    // asking if the contact is a contact in this chat conversation
                    matchedContact = false;
                    for(Contact chatFrameContact: chatFrame.contacts){
                        if(contact.getEmail().equals(chatFrameContact.getEmail())){
                            matchedContact = true;
                        }  
                       else{
                            continue;
                        }
                    }
                }
                if(matchedContact){
                    matchedFrame = true;
                    index = chatFrames.indexOf(chatFrame);
                    break;
                }

            }
        }

        System.out.println("the index is : " + index);    
        return index;
    }
    
    
    public void changeStatus(String nameStatus){
        String email = nameStatus.split(";")[0];
        String status = nameStatus.split(";")[1];
        int index = -1;
        
        if(!contacts.isEmpty()){
            for(Contact contact: contacts){
                if(contact.getEmail().equals(email)){
                    index = contacts.indexOf(contact);
                    break;
                }                    
            }
        }
        if(index > -1)
            contactList.setElementAt(email + " --> " + status, index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jl_status = new javax.swing.JLabel();
        jcb_status = new javax.swing.JComboBox();
        jl_contactList = new javax.swing.JLabel();
        jb_signOut = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jli_list = new javax.swing.JList();
        jmb_menuBar = new javax.swing.JMenuBar();
        jm_messenger = new javax.swing.JMenu();
        jmi_signOut = new javax.swing.JMenuItem();
        jmi_exit = new javax.swing.JMenuItem();
        jm_contacts = new javax.swing.JMenu();
        jmi_addContact = new javax.swing.JMenuItem();
        jmi_deleteContact = new javax.swing.JMenuItem();
        jm_conversation = new javax.swing.JMenu();
        jmi_addConversation = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jl_status.setText("Status:");

        jcb_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_statusActionPerformed(evt);
            }
        });

        jl_contactList.setText("Contact List:");

        jb_signOut.setText("Sign Out");

        jli_list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Contacts" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jli_list);

        jm_messenger.setText("Messenger");

        jmi_signOut.setText("Sign Out");
        jmi_signOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_signOutActionPerformed(evt);
            }
        });
        jm_messenger.add(jmi_signOut);

        jmi_exit.setText("Exit");
        jm_messenger.add(jmi_exit);

        jmb_menuBar.add(jm_messenger);

        jm_contacts.setText("Contacts");

        jmi_addContact.setText("Add Contact");
        jmi_addContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_addContactActionPerformed(evt);
            }
        });
        jm_contacts.add(jmi_addContact);

        jmi_deleteContact.setText("Delete Contact");
        jm_contacts.add(jmi_deleteContact);

        jmb_menuBar.add(jm_contacts);

        jm_conversation.setText("Conversation");

        jmi_addConversation.setText("new conversation");
        jm_conversation.add(jmi_addConversation);

        jmb_menuBar.add(jm_conversation);

        setJMenuBar(jmb_menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jb_signOut))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jl_status)
                                    .addGap(18, 18, 18)
                                    .addComponent(jcb_status, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jl_contactList)))))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_status)
                    .addComponent(jcb_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jl_contactList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jb_signOut)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmi_signOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_signOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmi_signOutActionPerformed

    private void jmi_addContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_addContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmi_addContactActionPerformed

    private void jcb_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_statusActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainFrame(null, null);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jb_signOut;
    private javax.swing.JComboBox jcb_status;
    private javax.swing.JLabel jl_contactList;
    private javax.swing.JLabel jl_status;
    private javax.swing.JList jli_list;
    private javax.swing.JMenu jm_contacts;
    private javax.swing.JMenu jm_conversation;
    private javax.swing.JMenu jm_messenger;
    private javax.swing.JMenuBar jmb_menuBar;
    private javax.swing.JMenuItem jmi_addContact;
    private javax.swing.JMenuItem jmi_addConversation;
    private javax.swing.JMenuItem jmi_deleteContact;
    private javax.swing.JMenuItem jmi_exit;
    private javax.swing.JMenuItem jmi_signOut;
    // End of variables declaration//GEN-END:variables
}

