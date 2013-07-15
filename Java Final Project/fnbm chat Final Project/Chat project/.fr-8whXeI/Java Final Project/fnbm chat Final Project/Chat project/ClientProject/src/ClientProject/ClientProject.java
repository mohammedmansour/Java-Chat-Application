/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProject;

import control.Monitor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Message;
import view.AddFriendFrame;
import view.MainFrame;
import view.RegisterFrame;
import view.SignInFrame;

/**
 *
 * @author hp
 */
public class ClientProject implements Serializable{

    public Socket socket;
    public MainFrame mainFrame;
    public SignInFrame signInFrame;
    public RegisterFrame registerFrame;
    public AddFriendFrame addFriend;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    public Monitor monitor;
    public String myEmail;
    public String myStatus;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create an object othat contains socket, mainFrame, sign in frame
        ClientProject clientProject = new ClientProject();
        SignInFrame signInFrame = new SignInFrame(clientProject);
        signInFrame.setVisible(true);   
    }   
    
}
