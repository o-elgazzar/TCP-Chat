import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {

        try {
            client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMsg;
            while ((inMsg = in.readLine()) != null) {
                System.out.println(inMsg);
            }
        } catch (IOException e) {
                shutDown();
        }

    }

    public void shutDown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }

        } catch(IOException e) {

        }

    }

    class InputHandler implements Runnable {

        @Override
        public void run() {

            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if (message.equals("/leave")) {
                        inReader.close();
                        shutDown();
                        break;
                    } else {
                        out.println(message);
                    }
                }

            } catch(IOException e) {
                shutDown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        new Thread(client).start();
    }

}