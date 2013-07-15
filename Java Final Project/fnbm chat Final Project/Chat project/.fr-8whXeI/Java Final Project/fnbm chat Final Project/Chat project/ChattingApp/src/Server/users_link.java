/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import util.Contact;
/**
 *
 * @author Bassem
 */

public class users_link {
        private Contact con;
        private String linkCode;

        public users_link(Contact con, String linkCode) {
            this.con = con;
            this.linkCode = linkCode;
        }

        public Contact getCon() {
            return con;
        }

        public String getLinkCode() {
            return linkCode;
        }

        public void setCon(Contact con) {
            this.con = con;
        }

        public void setLinkCode(String linkCode) {
            this.linkCode = linkCode;
        }
    }
