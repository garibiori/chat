import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MTServer.java
 *
 * <p>This program implements a simple multithreaded chat server.  Every client that
 * connects to the server can broadcast data to all other clients.
 * The server stores an ArrayList of clients to perform the broadcast.
 *
 * <p>The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * <p>To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */

public class MtServer {
  // Maintain list of all client sockets for broadcast
  private ArrayList<Client> clientList;

  public MtServer() {
    clientList = new ArrayList<Client>();
  }

  private void getConnection() {
    // Wait for a connection from the client
    try {
      System.out.println("Waiting for client connections on port 9011.");
      ServerSocket serverSock = new ServerSocket(9011);
      // This is an infinite loop, the user will have to shut it down
      // using control-c

      while (true) {
        //check for same users
        boolean sameUser = true;
        Socket connectionSock = serverSock.accept();
        BufferedReader userInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
        //write to the client
        BufferedWriter userOutput = new BufferedWriter(
          new OutputStreamWriter(connectionSock.getOutputStream()));
        
        userOutput.write("Enter username: \n");
        userOutput.flush();
        String username = userInput.readLine();
        //check all the users to see if any were taken and then prompt the client for a new username
        while(clientList.size() == 0){
          if(username.equals("host")){
            userOutput.write("You may use the following commands: \nSCORES - shows everyone everyone's scores\nWho? - shows who is in the game\nQUIT - quits the game\n[username]+ - awards a point to that username\n");
            break;
          }
          if(!username.equals("host")){
              userOutput.write("You must be the host: \n");
              userOutput.flush();
              username = userInput.readLine();
          }

        }
        if (clientList.size() > 0) {
          while (sameUser == true) {
            for (Client c : clientList) {
              if(username.equals(c.getUsername())){
                System.out.println("Username Taken!");
                userOutput.write("User taken -> Enter Username: \n");
                userOutput.flush();
                username = userInput.readLine();
                sameUser = true;
                break;
              }
              else {
                sameUser = false;
              }
            }
          }
          
        }
        if(username.equals("host")){
          userOutput.flush();
          userOutput.write("Commands: \n");
        }
        Client client = new Client(connectionSock, username);
        clientList.add(client); // add client here 
        // Send to ClientHandler the socket and arraylist of all sockets
        ClientHandler handler = new ClientHandler(client, this.clientList);
        Thread theThread = new Thread(handler);
        theThread.start();

      }
      // Will never get here, but if the above loop is given
      // an exit condition then we'll go ahead and close the socket
      //serverSock.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    MtServer server = new MtServer();
    server.getConnection();
  }
} // MtServer
