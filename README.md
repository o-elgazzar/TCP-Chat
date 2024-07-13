# TCP Chat Application

This repository contains a TCP-based chat application that allows multiple users to connect and communicate in real-time. The chat server is written in Java and can handle multiple client connections simultaneously, allowing for real-time messaging between clients.

## Features

- **Real-Time Communication**: Users can send and receive messages instantly.
- **Multi-user Support**: Multiple clients can connect to the server at the same time.
- **Lightweight Protocol**: Utilizes TCP for reliable communication.
- **Simple and Intuitive UI**: Users can easily interact through a basic command-line interface.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:
Java JDK 11 or later

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

1. **Clone the repository**
   Open your terminal and run:
   ```bash
   git clone https://github.com/o-elgazzar/TCP-Chat.git

2.Navigate to the project directory
   Change into the project directory:
     cd Chat - Application
     
3.Compile the Java files
      Compile the Server and Client Java programs:
      javac Server.java
      javac Client.java

4.Start the Server
      Run the server:
      java Server

5. In a new terminal, start the Client
      Open another terminal window and run:
      java Client
      Repeat this step for each additional client you wish to connect.

Usage
To use the chat application, start the server component first and then connect using the client component. You can connect to the server from the same machine or from any other machine on the network, provided the firewall settings allow TCP connections on the used port.

Commands
/nick <name> - Sets or changes the user's nickname.
/leave - Disconnects from the chat server.

Acknowledgments
NeuralNine
