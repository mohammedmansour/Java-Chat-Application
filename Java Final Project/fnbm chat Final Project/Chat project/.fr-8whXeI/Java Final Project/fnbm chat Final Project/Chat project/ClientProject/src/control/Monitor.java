/*
 * there is the monitor to listen and receive any message from the server when the user is logged in
 */
package control;

import ClientProject.ClientProject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Chat;
import util.Message;

/**
 *
 * @author aelshafei, nehal essam
 */
public class Monitor implements Runnable{
    
    ClientProject clientProject;
    
    public Monitor(ClientProject clientProject) {
        this.clientProject = clientProject;
        System.out.println("12");
    }
    
    
    @Override
    public void run() {
        while(true){
            Message message = null;
            try {
                message = (Message) clientProject.objectInputStream.readObject();
                System.out.println("receiveing message" + "type : " + message.getType());
            } catch (IOException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }

            switch(message.getType()){
                case Message.MESSAGE:
                    // chat message
                    System.out.println(message.getContent());
                    //
                    clientProject.mainFrame.assignFrame((Chat) message.getContent());
                    break;
                case Message.ANNOUNCEMENT:
                    JOptionPane.showMessageDialog(clientProject.mainFrame, message.getContent());
                    break;
                case Message.STATUS:
                    clientProject.mainFrame.changeStatus((String) message.getContent());
                    break;
                case Message.ADDING_CONTACT_CONFIRMED:
                    //inform the user that his friend request is accepted
                    //clientProject.mainFrame.confirmAddingContact(message);
                    break;
                case Message.CONTACT_LIST:
                    // updating contact list
                    clientProject.mainFrame.updateContactList(message);
                    break;
                case Message.CONFIRM_ADDING_CONTACT:
                    // confirming the freind request by the requested the user
                    int descion = JOptionPane.showConfirmDialog(clientProject.mainFrame, "confirm adding " + message.getContent() + " ? ", "Confirm adding contact", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(descion == JOptionPane.YES_OPTION){
                        try {
                                clientProject.objectOutputStream.writeObject(new Message(Message.ADDING_CONTACT_CONFIRMED, message.getContent() + ";" + "yes"));
                                clientProject.mainFrame.confirmAddingContact((String) message.getContent());
                        } catch (IOException ex) {
                            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        try {
                                clientProject.objectOutputStream.writeObject(new Message(Message.ADDING_CONTACT_CONFIRMED,message.getContent() + ";" + "no"));
                        } catch (IOException ex) {
                            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
            }
        }
        
    }
    
    
    
}
