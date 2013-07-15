/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DB.DBConnection;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Chat;
import util.Contact;
import util.ContactInfo;
import util.Message;
/**
 *
 * @author Bassem
 */

public class SocketServerHandler extends Thread implements Serializable {

    private ObjectInputStream ois;    // inputstream in which receives user inputs
    private ObjectOutputStream oos;   // outputstream in which sends data to a user
    private Socket soc;               // user's socket connection
    private Message msg;              // transferable message between server and online user
    private String email;
    private static Vector<SocketServerHandler> connectedContacts = new Vector<SocketServerHandler>();  // To save all online contacts on the server
    private DBConnection conn;      // DB messages handler
    private String status;
    private Vector<Contact> contactList;

    public SocketServerHandler(Socket soc, DB.DBConnection DB) throws IOException {
        this.soc = soc;
        conn = DB;
        // get the inputstream in which receives user inputs
        ois = new ObjectInputStream(this.soc.getInputStream());
        // get the outputstream in which sends data to a user
        oos = new ObjectOutputStream(this.soc.getOutputStream());

        // start the user's thread
        start();
    }

    public static Vector<SocketServerHandler> getConnectedContacts() {
        return connectedContacts;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public String getStatus() {
        return status;
    }
        
    @Override
    public void run() {
        while (true) {
            try {
                // get the first message between client and server
                msg = (Message) ois.readObject();
            } catch (SocketException ex) {
                 try {
                    soc.shutdownInput();
                    soc.shutdownOutput();
                    
                    oos.close();
                    ois.close();
                    
                    soc.close();
                    
                    connectedContacts.remove(this);
                    
                    stop();
                } catch (IOException ex1) {
                    Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (EOFException ex) {
                try {
                    soc.shutdownInput();
                    soc.shutdownOutput();
                    oos.close();
                    ois.close();
                    connectedContacts.remove(this);
                    stop();
                } catch (IOException ex1) {
                    Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } 
            switch (msg.getType()) {
                case Message.SIGN_IN:  // if the user want to signin
                    String userAndPass = (String) msg.getContent();   // get its username and password
                    try {
                        signin(userAndPass);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case Message.SIGN_UP:  // if he is a new user (signup)
                    ContactInfo contact = (ContactInfo) msg.getContent(); // get its information
                    try {
                        signup(contact);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case Message.MESSAGE:
                    try {
                        sendMessage((Chat)(msg.getContent()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case Message.ADD_CONTACT:
                    String friend = (String) msg.getContent();
                    addContact(friend);
                    break;
                case Message.ADDING_CONTACT_CONFIRMED:
                    String str = (String) msg.getContent();
                    String fr = str.split(";")[0];
                    String confMsg = str.split(";")[1];
                    /*if(confMsg.equalsIgnoreCase("yes")) {
                       SocketServerHandler soc = getSockectHandler();
                       if(soc != null)
                           soc.oos.writeObject(new Message(Message.STATUS, soc.status));
                    }*/
                    conn.updateUsersLink(email, fr,confMsg);
                    contactList.addElement(new Contact(fr, getSockectHandler(fr).status));
                    try {
                        oos.writeObject(new Message(Message.STATUS, fr + ";" + getSockectHandler(fr).status));
                    } catch (IOException ex) {
                        Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getSockectHandler(fr).contactList.addElement(new Contact(email, status));
                    try {
                        getSockectHandler(fr).oos.writeObject(new Message(Message.STATUS, email + ";" + status));
                    } catch (IOException ex) {
                        Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Message.STATUS:
                    status = (String) msg.getContent();
                    for(int i = 0; i < contactList.size(); i++) {
                        Contact c = contactList.get(i);
                        SocketServerHandler soc = getSockectHandler(c.getEmail());
                        if(soc != null) {
                            try {
                                soc.oos.writeObject(new Message(Message.STATUS, email + ";" + status));
                            } catch (IOException ex) {
                                Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                case Message.DELETE:
                    String delFriend = (String) msg.getContent();
                    deleteContact(delFriend);
            }
        }
    }

    /**
     * sendMessage Method
     *
     * Send Message to one or more of user's contacts
     *
     * @param chatMsg Contains the message string itself and list of contacts
     * @throws IOException
     */
    private void sendMessage(Chat chatMsg) throws IOException {
        Vector<Contact> contacts = chatMsg.getReceiver();
        Contact c;
        for (int i = 1; i < contacts.size(); i++) {
            c = contacts.get(i);
            SocketServerHandler soc = getSockectHandler(c.getEmail());
            if(soc != null) {
                soc.oos.writeObject(new Message(Message.MESSAGE, chatMsg));
            }
        }
    }

    /**
     * signin method
     *
     * To sign in the user and check its availability in the DB or not
     *
     * @param userAndPass
     */
    private void signin(String userAndPass) throws IOException {
        String user = userAndPass.split(";")[0], passwd = userAndPass.split(";")[1];
        //Vector<String> vec = conn.checkUserAccount(user, passwd);
        Vector<users_link> vec = conn.checkUserAccount(user, passwd);
        contactList = new Vector<Contact>();
        /**
         * Error in the DB connection
         */
        if (!vec.isEmpty() && vec.get(0).getLinkCode().equals("-1")) {
            // Send a message for a user's connection (socket)
            oos.writeObject(new Message(Message.SIGN_IN_ERROR, "DB Error"));
            // stop his thread
            this.stop();
        } else if (!vec.isEmpty() && vec.get(0).getLinkCode().equals("")) { // No user with this name
            // Send a message for a user's connection (socket)
            oos.writeObject(new Message(Message.SIGN_IN_ERROR, "Username or password is invalid"));
            // stop his thread
            this.stop();
        } else {    // there is a user
            email = user; 
            for (int i = 0; i < vec.size(); i++) {
                users_link lnk = vec.get(i);
                SocketServerHandler hand = getSockectHandler(lnk.getCon().getEmail());
                if (hand != null && lnk.getLinkCode().equals("1")) {
                    contactList.addElement(new Contact(lnk.getCon().getEmail(), hand.status));
                } else if (lnk.getLinkCode().equals("01")) {    // if the user have an add request
                    //contactList.addElement(new Contact(lnk.getCon().getEmail(), "offline"));
                   // oos.writeObject(new Message(Message.CONFIRM_ADDING_CONTACT, lnk.getCon().getEmail() + "wants to add you?"));
                } else {
                    contactList.addElement(new Contact(lnk.getCon().getEmail(), "offline"));
                }
            }
            oos.writeObject(new Message(Message.CONTACT_LIST, contactList));
            connectedContacts.add(this);
            checkRequestes(vec);
        }
    }

    private void checkRequestes(Vector<users_link> ul) {
        for(int i = 0; i < ul.size(); i++) {
            users_link link = ul.get(i);
            if(link.getLinkCode().equals("01")) {
                try {
                    oos.writeObject(new Message(Message.CONFIRM_ADDING_CONTACT, link.getCon().getEmail()));
                } catch (IOException ex) {
                    Logger.getLogger(SocketServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * signup method
     *
     * To add new user to the database
     *
     * @param contact
     */
    private void signup(ContactInfo contact) throws IOException {
        // store it in the database
        int sup = conn.Register(contact);
        if (sup == 1) {  // check if the operation succeedded
            oos.writeObject(new Message(Message.SIGN_UP_SUCCESS, "User added successfully"));
        } else if (sup == 0) {
            oos.writeObject(new Message(Message.SIGN_UP_ERROR, "Username is already registered"));
        } else {  // if the operation failed
            oos.writeObject(new Message(Message.SIGN_UP_ERROR, "signup failed"));
        }
    }

    /**
     *
     * @param email
     * @return socketServerHandler
     */
    private static SocketServerHandler getSockectHandler(String email) {
        SocketServerHandler soc = null;
        for (SocketServerHandler s : connectedContacts) {
            if (s.email.equals(email)) {
                soc = s;
                break;
            }
        }
        return soc;
    }
    /**
     * Add new contact to his/her list
     * @param email 
     */
    private void addContact(String email) {
        if (conn.add_contact(this.email, email)) {
            try {
                SocketServerHandler hand = getSockectHandler(email);
                if (hand != null) {
                    hand.oos.writeObject(new Message(Message.CONFIRM_ADDING_CONTACT, this.email));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                oos.writeObject(new Message(Message.CONFIRM_ADDING_CONTACT, "No such user " + email));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void deleteContact(String femail) {
        conn.del_contact(email, femail);
    }
}