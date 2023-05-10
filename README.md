# chat
This repo contains programs to implement a multi-threaded TCP chat server and client

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java receives messages from a client and relays it to the other clients.


## Identifying Information

* Name: Ori Garibi
* Student ID: 2367830
* Email: garibi@chapman.edu
* Course: Data Comms
* Assignment: PA04

## Source Files
* Client.java
* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java

## References


## Known Errors

* N/A

## Build Insructions
* javac java
* java MtServer
* java MtClient


## Execution Instructions
*javac *.java
*java MtServer
*java MtClient
* Input username then send messages to server
