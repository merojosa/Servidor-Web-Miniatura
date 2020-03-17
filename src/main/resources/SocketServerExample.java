import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class SocketServerExample {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9880;
    
    // https://www.journaldev.com/741/java-socket-programming-server-client#java-socket-server-example
    // http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        // Create the socket server object
    	
        server = new ServerSocket(port);
        // Keep listens indefinitely until receives 'exit' call or program terminates
        while(true)
        {
            System.out.println("\nWAITING FOR THE CLIENT REQUEST\n");
            
            // Creating socket and waiting for client connection
            Socket socket = server.accept();
           
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            
            // Leo lo que mando el cliente
            String line;
            while ((line = in.readLine()) != null) {
              if (line.length() == 0)
                break;
              
              if(line.contains("GET"))
            	  System.out.println("ES GET");
            }
            
            
            
            socket.close();

        }
    }
    
}
