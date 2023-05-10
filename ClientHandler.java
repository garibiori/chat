import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */
public class ClientHandler implements Runnable {
  private Client client = null;
  private ArrayList<Client> clientList;

  ClientHandler(Client newclient, ArrayList<Client> clientList) {
    this.client = newclient;
    this.clientList = clientList;  // Keep reference to master list
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      Socket connectionSock = client.connectionSock;
      // System.out.println("Connection made with socket " + connectionSock);
      System.out.println(client.username + " has joined the chat.");
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      boolean quit = false;
      while (true) {
        // Get data sent from a client
        String clientText = clientInput.readLine();
        if (clientText != null) {
          //check if the client wants to leave the chat
          
          System.out.println(client.username + ": " + clientText);
          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          boolean scorePlayer = false;
          switch(clientText){
            case "QUIT":
              System.out.println(client.username + " has left the chat.");
              clientList.remove(client);
              //connectionSock.close();
              //clientText = null;
              quit = true;

              for(Client f:clientList){
                OutputStream connectOut = f.connectionSock.getOutputStream();
                DataOutputStream clientOutput = new DataOutputStream(connectOut);
                clientOutput.writeBytes(client.username + ": " + "I left the chat" + "\n");
                
              }
              break;
            
            
              //break;
            case "SCORES":
                if(client.username.equals("host")){
                for(Client x: clientList){
                  OutputStream connectOutScores = x.connectionSock.getOutputStream();
                  DataOutputStream clientOutputScores = new DataOutputStream(connectOutScores);
                  if(!x.username.equals(client.username)){
                    for(Client y:clientList){
                      if(!y.username.equals("host")){
                        clientOutputScores.writeBytes("Score for "+y.username+": "+y.score+"\n");
                      }
                    }
                  }
                }
              }
              break;
            case "Who?":
            OutputStream connectOutMe = client.connectionSock.getOutputStream();
            for (Client k : clientList) {
              DataOutputStream clientOutput = new DataOutputStream(connectOutMe);
              if(!k.username.equals(client.username)){
                clientOutput.writeBytes("User: "+k.username+"\n");
              }
            }
            break;
          
          default:
            for(Client f:clientList){
              if(clientText.equals(f.username+"+") && client.username.equals("host")){
                scorePlayer = true;
                f.score++;
              }
            }
          
        }
          for (Client c : clientList) {
            if (c.connectionSock != connectionSock) {
              OutputStream connectOut = c.connectionSock.getOutputStream();
              DataOutputStream clientOutput = new DataOutputStream(connectOut);
              if(quit == false && !clientText.equals("Who?") && !(clientText.equals("SCORES") && client.username.equals("host")) && !(scorePlayer && client.username.equals("host"))){
                clientOutput.writeBytes(client.username + ": " + clientText + "\n");
              }
        
            }
          }
          scorePlayer = false;
        } else {
          // Connection was lost
          System.out.println(client.username + " has left the chat.");
          // System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          clientList.remove(client);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      clientList.remove(client);
    }
  }
} // ClientHandler for MtServer.java
