/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DB.DBConnection;
import java.io.*;
//import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Message;


public class Server implements Serializable {
    private static ServerSocket server;   // Server Socket
    private Socket userSocket;            // Socket for a user connection
    private DBConnection conn;
    /**
     * start method
     * 
     * To start the server up and bind it to 5000 local port
     * @throws IOException if I/O occurs when opening the socket
     */
    public void startServer() throws IOException, BindException {
        if (server == null || server.isClosed()) {  // Check if the server is closed or not
            server = new ServerSocket(5000);
            conn = new DBConnection();
            
            acceptUserConnection();
        }
        else {  // if the server is already started
            System.out.println("Server is already started and binded to 5000 port");
            server.close();
        }
    }
    
    /**
     * stop method
     * 
     * To stop the server socket and release its port,
     * any associated channel will also closed
     * @throws IOException if I/O occurs when opening the socket
     */
    public void stopServer() throws IOException {
        if (server != null && ! server.isClosed()) {    // Check if the Server is already closed
            server.close();
            conn.closeConn();
        }
        else {  // if the Server is already closed
            System.out.println("Server is already stopped");
        }
    }
        
    /**
     * acceptUserConnetion method
     * 
     * Accept any user connection
     * @throws IOException if an I/O error occurs when waiting for a connection. 
     */
    private void acceptUserConnection() throws IOException {
        while (true) {
            // Waiting for any new connection & accept it
            userSocket = server.accept();
            // Start thread handler
            new SocketServerHandler(userSocket, conn);
        }
    }
}
