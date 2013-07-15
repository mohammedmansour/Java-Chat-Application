/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author just_imagine
 */
public class ClientSendFile implements Serializable {
    
    //private String src; 
    //private String dest; 
    //private byte[] file;
    ////////////////////////////
    private Vector<Contact> receivers;
    private byte[] file;

    public ClientSendFile(byte[] file, Vector<Contact> receivers) {
        this.file = file;
        this.receivers = receivers;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }
    public byte[] getFile() {
        return file;
    }
    public void setReceiver(Vector<Contact> receiver) {
        receivers = receiver;
    }
    public Vector<Contact> getReceiver() {
        return receivers;
    }
    ////////////////////////////////

    /*public ClientSendFile(String src, String dest, byte[] file) {
        this.src = src;
        this.dest = dest;
        this.file = file;
    }*/

   /* public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public byte[] getFile() {
        return file;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
     */ 
    
}

