package DB;

import Server.users_link;
import com.mysql.jdbc.Driver;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.nio.cs.ext.GB18030;
import util.Contact;
import util.ContactInfo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bassem
 */
public class DBConnection implements Serializable {

    private Connection conn;    //db connection name

    public DBConnection() {
        openConn();
    }

    /**
     * opening connection with db
     */
    private void openConn() {
        try {
            DriverManager.registerDriver(new Driver());
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "root", "iti");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * closing connection with db
     *
     * @return -1 if data insertion failed
     */
    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    /**
     * inserting new account information
     *
     * @param ci new user information
     * @return 1 --> user added to the database 0 --> user already in the
     * database -1 --> Database connection failed
     */
    public int Register(ContactInfo ci) {
        try {
            Statement st = conn.createStatement();
            String qu = new String("insert into users values( '" + ci.getEmail() + "' ,'" + ci.getPassword() + "' ,'" + ci.getFname() + "' ,'" + ci.getLname());
            if (st.executeUpdate(qu) == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            //ex.printStackTrace();
            return -1;
        }
    }

    /**
     * checking if the log in info. is valid or not
     *
     * @param v_email
     * @param v_pass
     * @return Vector of String list --> user's contact list --> empty contact
     * list --> -1, user is not found in the DB
     */
    public Vector<users_link> checkUserAccount(String v_email, String v_pass) {
        ResultSet rs = null;
        int count = 0;
        Vector<users_link> vec_contacts = new Vector<users_link>();
        try {
            Statement st1 = conn.createStatement();
            int x = 0;
            x = st1.executeUpdate("update users set email='" + v_email + "' where email='" + v_email + "' and pass='" + v_pass + "'");
            if (x != 0) {
                rs = st1.executeQuery("select friend_email, link from users_relation where user_email='" + v_email + "'");
                while (rs.next()) {
                    vec_contacts.add(new users_link(new Contact(rs.getString(1), "offline"), rs.getString(2)));
                }
            } else {
                vec_contacts.add(new users_link(new Contact("", ""), ""));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            vec_contacts.add(new users_link(new Contact("-1", "-1"), "-1"));
        } finally {
            return vec_contacts;
        }
    }
    
    public boolean add_contact(String user, String friend) {
        boolean res = false;
        try {
            Statement st = conn.createStatement();
            st.execute("insert into users_relation values( '" + user + "' ,'" + friend + "', '00')");
            st.execute("insert into users_relation values( '" + friend + "' ,'" + user + "', '01')");
            res = true;
        } catch (SQLException ex) {
            res = false;
        } finally {
            return res;
        }
    }

    public boolean del_contact(String user, String friend) {
        boolean res = false;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("delete from users_relation where user_email= '" + user + "' and friend_email='" + friend + "'");
            st.executeUpdate("delete from users_relation where user_email= '" + friend + "' and friend_email='" + user + "'");
            res = true;
        } catch (SQLException ex) {
            res = false;
        } finally {
            return res;
        }
    }

    public void updateUsersLink(String email, String fr, String confMsg) {
        try {
            Statement st = conn.createStatement();
            if (confMsg.equalsIgnoreCase("yes")) {

                st.executeUpdate("update users_relation set link='1' where user_email='" + email + "' and friend_email='" + fr + "'");
                st.executeUpdate("update users_relation set link='1' where user_email='" + fr + "' and friend_email='" + email + "'");

            } else {
                st.executeUpdate("delete from users_relation where user_email='" + email + "' and friend_email='" + fr + "'");
                st.executeUpdate("delete from users_relation where user_email='" + fr + "' and friend_email='" + email + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}