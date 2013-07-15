package util;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * ContactInfo Class
 * 
 * Contain all information about client that are stored in the DB,
 * or will store in the DB
 * 
 * @author M_UNIX
 */
public class ContactInfo implements Serializable {
    private String email, // User Account mail
            fname,        // User account First Name 
            lname,        // User account Last Name
            password,     // User account Password
            gender,       // User Gender
            dob,          // User's Date of Birth
            country;      // User's Country
        
    /**
     * ContactInfo Constructor
     * @param email: User's Email
     * @param fname: User's First Name
     * @param lname: User's Last Name
     * @param password: User's Password
     * @param gender: User's Gender
     * @param dob: User's Date of birth
     * @param country: User's Country
     */
    public ContactInfo(String v_Email, String v_Fname, String v_Lname, String v_password, String v_Gender, String v_Dob, String v_country) {
        this.email = v_Email;
        this.fname = v_Fname;
        this.lname = v_Lname;
        this.password = v_password;
        this.gender = v_Gender;
        this.dob = v_Dob;
        this.country = v_country;
    }

    /**
     * email Getter method
     * @return User's Email as a string
     */
    public String getEmail() {
        return email;
    }

    /**
     * fname Getter method
     * @return user's First Name as a string
     */
    public String getFname() {
        return fname;
    }

    /**
     * lname Getter method
     * @return user's Last Name as a string
     */
    public String getLname() {
        return lname;
    }

    /**
     * password Getter method
     * @return user's Password as a string
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * gender Getter method
     * @return User's Gender as a string
     */
    public String getGender() {
        return gender;
    }

    /**
     * dob Getter method
     * @return User's Date of Birth as a string
     */
    public String getDob() {
        return dob;
    }

    /**
     * country Getter method
     * @return User's Country as a string
     */
    public String getCountry() {
        return country;
    }

    /**
     * email Setter method
     * @param email 
     */
    public void setEmail(String v_Email) {
        this.email = v_Email;
    }

    /**
     * fname Setter method
     * @param fname 
     */
    public void setFname(String v_Fname) {
        this.fname = v_Fname;
    }

    /**
     * lname Setter method
     * @param lname 
     */
    public void setV_Lname(String v_Lname) {
        this.lname = v_Lname;
    }

    /**
     * password Setter method
     * @param password 
     */
    public void setV_password(String v_password) {
        this.password = v_password;
    }

    /**
     * gender Setter method
     * @param gender 
     */
    public void setV_Gender(String v_Gender) {
        this.gender = v_Gender;
    }

    /**
     * dob Setter method
     * @param dob 
     */
    public void setV_Dob(String v_Dob) {
        this.dob = v_Dob;
    }

    /**
     * country Setter method
     * @param country 
     */
    public void setV_Country(String v_Country) {
        this.country = v_Country;
    }
}