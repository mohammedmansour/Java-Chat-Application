/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author MAGhayaty
 */
public class Chat implements Serializable {
    
    private String message; 
    private Vector<Contact> receivers;

    public Chat(String message, Vector<Contact> receivers) {
        this.message = message;
        this.receivers = receivers;
    }
      
    /**
     * setMessge method
     * 
     * set private String message as messages
     * @param messages : input parameter equal to private string message 
     */
    public void setMessage(String messages) {
        message=messages;
    } 

    /**
     * setReceiver method
     * @param Vector<Contact> receiver to equal receiver from the same type 
     */
    public void setReceiver(Vector<Contact> receiver) {
        receivers = receiver;
    }
    
    /**
     * getMessage method
     * 
     * return message string
     * @return : message string
     */
    
    public String getMessage() {
        return message;
    }
    /**
     * getReceiver method
     * @return Vector<Contact> receiver
     */
    public Vector<Contact> getReceiver() {
        return receivers;
    }
}

