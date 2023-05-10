# mtchat
This repo contains programs to implement a multi-threaded TCP chat server and client

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java receives messages from a client and relays it to the other clients.


## Identifying Information

* Name: Trey Alexander Ori Garibi
* Student ID: 2374235 2367830
* Email: roalexander@chapman.edu garibi@chapman.edu
* Course: Data Comms
* Assignment: PA04

## Source Files
* Client.java
* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java

## References
* Contributions:
* Trey: Fixed errors and README/Comments, for the second submission he initiated the quit command and took new inputs for the username before allowing a client to join
* Ori: Implemented initial Client class to store username with socket information, for submission 2 he figured out how to check the client list for any repetitions

## Known Errors

* N/A

## Build Insructions
* javac java
* java MtServer
* java MtClient


## Execution Instructions
* Input username then send messages to server
