package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import respuestas.HttpRespuesta;
import respuestas.RespuestaGET;


// http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
public class ServidorWeb 
{
	
    //static ServerSocket variable
    private ServerSocket socketServidor;
    //socket server port on which it will listen
    private final int PUERTO = 9881;
    
    private HttpRespuesta respuesta = null;
    
	
	public void ejecutar() throws IOException
	{
        // Create the socket server object
        socketServidor = new ServerSocket(PUERTO);
        
        
        // Keep listens indefinitely until receives 'exit' call or program terminates
        while(true)
        {
            System.out.println("***Esperando solicitud***");
            
            // Creating socket and waiting for client connection
            Socket socket = socketServidor.accept();
           
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            
            // Leo lo que mando el cliente
            String linea;
            String mensajeSolicitud = "";
            while ((linea = in.readLine()) != null && respuesta == null) 
            {
              if(linea.length() == 0)
              {
            	  break;
              }
              
              
              if(linea.contains("GET"))
              {
            	  System.out.println("Solicitud GET");
            	  respuesta = new RespuestaGET();
            	  mensajeSolicitud = procesarMensaje(linea.toCharArray());
              }
            }
            
            if(respuesta != null)
            {
            	respuesta.procesarSolicitud(mensajeSolicitud, out);
            }
            
            
            respuesta = null;
            out.close();
            in.close();
            socket.close();
        }
	}


	// Para extraer lo que hay entre el GET/POST/HEAD y HTTP (o sea, el mensaje)
	private String procesarMensaje(char[] linea) 
	{
		int inicio = -1;
		int fin = -1;
		for(int contador = 0; contador < linea.length; ++contador)
		{
			if(linea[contador] == ' ')
			{
				if(inicio == -1)
				{
					inicio = contador + 1;
				}
				else
				{
					fin = contador;
					break;
				}
			}
		}

		return new String(linea).substring(inicio, fin);
	}
}
