import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {

            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 9999 or listening for a connection");
            System.out.println(e.getMessage());
            shutDown();
        }

    }

    public void broadcast(String message) {
        for(ConnectionHandler ch : connections) {
            if (ch != null)
                ch.sendMessage(message);
        }
    }

    public void shutDown() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }

        } catch (IOException e) {
            System.out.println("Error during server shutdown: " + e.getMessage());
        }

    }

    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickName;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {
               out = new PrintWriter(client.getOutputStream(), true);
               in = new BufferedReader(new InputStreamReader(client.getInputStream()));
               out.println("Please enter a nickname: ");
               nickName = in.readLine();
                System.out.println(nickName + " connected!");
                broadcast(nickName + " has joined the chat!");
                String message;
                while((message = in.readLine()) != null) {

                    if (message.startsWith("/nick ")) {

                        String[] messageSplit = message.split(" ", 2);

                        if (messageSplit.length == 2) {
                            broadcast(nickName + " has changed their nickname to " + messageSplit[1]);
                            System.out.println((nickName + " has changed their nickname to: " + messageSplit[1]));
                            nickName = messageSplit[1];
                            out.println("Nickname has been successfully changed to " + nickName);
                        } else {
                            out.println("No nickname was provided.");
                        }

                    } else if (message.startsWith("/leave ")) {
                        broadcast(nickName + " has left the chat.");
                        System.out.println((nickName + " disconnected."));
                        shutDown();
                    } else {
                        broadcast(nickName + ": " + message);
                    }
                }
            } catch(IOException e) {
                shutDown();
            }

        }


        public void sendMessage(String message)  {
            out.println(message);
        }

        public void shutDown() {
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                //leave it as it is
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }
}