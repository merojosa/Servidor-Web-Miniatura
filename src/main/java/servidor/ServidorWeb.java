package servidor;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


// Comunicacion cliente-servidor: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
// Threads: https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
public class ServidorWeb
{
    private ServerSocket socketServidor;
    private final int PUERTO = 9881; 
	
	public void ejecutar() throws IOException
	{
        socketServidor = new ServerSocket(PUERTO);
        
        
        while(true)
        {
            System.out.println("Esperando solicitud");
            
            // Se queda esperando hasta que haya un request
            Socket socket = socketServidor.accept();
            
            System.out.println("Procesando solicitud");
            // Se crea un nuevo HiloServidorWeb y se ejecuta su metodo run.
            new Thread(new HiloServidorWeb(socket)).start();            
        }
	}
}
