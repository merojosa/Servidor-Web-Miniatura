package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import respuestas.HttpRespuesta;
import respuestas.RespuestaGET;


// Comunicacion cliente-servidor: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
// Threads: https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
public class ServidorWeb
{
    private ServerSocket socketServidor;
    private final int PUERTO = 9881;
    
    private HttpRespuesta respuesta = null;
    
	
	public void ejecutar() throws IOException
	{
        socketServidor = new ServerSocket(PUERTO);
        
        
        while(true)
        {
            System.out.println("***Esperando solicitud***");
            
            // Se queda esperando hasta que haya un request
            Socket socket = socketServidor.accept();
           
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream());
            
            
            // Leo lo que mando el cliente
            String linea;
            String mensajeSolicitud = "";
            while ((linea = entrada.readLine()) != null && respuesta == null) 
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
            	respuesta.procesarSolicitud(mensajeSolicitud, salida);
            }
            
            
            respuesta = null;
            salida.close();
            entrada.close();
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
