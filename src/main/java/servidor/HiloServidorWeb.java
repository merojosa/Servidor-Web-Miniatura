package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import respuestas.HttpRespuesta;
import respuestas.RespuestaGET;

public class HiloServidorWeb implements Runnable 
{
	private Socket socket;
	
	public HiloServidorWeb(Socket socket) 
	{
		this.socket = socket;
		
	}

	@Override
	public void run()
	{
		try 
		{
			procesarSolicitud(socket);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void procesarSolicitud(Socket socket) throws IOException
	{
		BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter salida =  new PrintWriter(socket.getOutputStream());
        
        
		// Leo lo que mando el cliente
		HttpRespuesta respuesta = null;
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
			  	mensajeSolicitud = extraerMensaje(linea.toCharArray());
		  	}
		}

        
        if(respuesta != null)
        {
        	respuesta.procesarSolicitud(mensajeSolicitud, salida);
            System.out.println("Respuesta enviada");
        }
        
        respuesta = null;
        // Al hacer close, se hace el flush y por ende se envia el mensaje
        salida.close();
        entrada.close();
        socket.close();
	}
	
	// Para extraer lo que hay entre el GET/POST/HEAD y HTTP (o sea, el mensaje)
	private String extraerMensaje(char[] linea) 
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