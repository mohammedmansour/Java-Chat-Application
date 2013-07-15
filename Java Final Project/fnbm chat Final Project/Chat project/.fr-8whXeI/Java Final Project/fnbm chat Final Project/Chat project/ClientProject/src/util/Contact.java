/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author MAGhayaty
 */
public class Contact implements Serializable {
    
    private String email;
    private String Status;

    public Contact(String email, String Status) {
        this.email = email;
        this.Status = Status;
    }
    
    /**
     * setEmail method
     * 
     * @param emails : string equal to private string email
     */
    
    public void setEmail(String emails) {
        email = emails;
    }
    
   /**
    * setStatus method
    * 
    * 
    * @param stat : String equal to private string status
    */
    public void setStatus(String stat) {
        Status = stat;
    }
    
    /**
     * getEmail method
     * 
     * 
     * @return string V_email 
     */
    public String getEmail() {
        return email;
    }
    /**
     * getStatus method
     * 
     * @return string V_status
     */
    public String getStatus() {
        return Status;
    }
}
