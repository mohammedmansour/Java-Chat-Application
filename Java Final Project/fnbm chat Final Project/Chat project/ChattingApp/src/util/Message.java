/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

 public class Message implements Serializable {
    private String type;    // Types of messages
    private Object Content; // Message Content
    
    public static final String SIGN_IN="si";
    public static final String SIGN_IN_ERROR="si_err";
    public static final String SIGN_UP ="su";
    public static final String SIGN_UP_ERROR="su_err";
    public static final String SIGN_UP_SUCCESS="su_success";
    public static final String STATUS="st";
    public static final String MESSAGE="msg";
    public static final String CONTACT_LIST="lst";
    public static final String ADD_CONTACT="add";
    public static final String CONFIRM_ADDING_CONTACT="confirm"; // is to be send to the user to confirm the contact / to send the server .. yes or no oprion
    public static final String ADDING_CONTACT_CONFIRMED ="confirmed";// to infrom the server the confirmation of the contat
    public static final String ANNOUNCEMENT="anno"; // to send annoucement to all connected users
    public static final String DELETE="del";
    public Message(String type, Object Content) {
        this.type = type;
        this.Content = Content;
    }
        
    /**
     * setType method
     * 
     * set private string type as types
     * @param types : string set to be equal type  
     */
    public void setType(String types) {
        type = types ;
    }
     /**
      * setContent method
      * 
      * @param set Contents object to be equal to private object Content
      */
    public void setContent(Object Contents) {
        Content=Contents;
    }
   
   /**
    * getType method
    * 
    * return type string
    * @return : string type 
    */
   
   public String getType() {
       return type;
   }
   /**
    * getContent method
    * @return object Content
    */
   public Object getContent() {
       return Content;
   }
}