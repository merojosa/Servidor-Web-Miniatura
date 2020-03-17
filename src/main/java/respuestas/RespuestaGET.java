package respuestas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RespuestaGET implements HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
	
	@Override
	public void procesarSolicitud(String mensajeSolicitud, PrintWriter salida) 
	{
		File archivoSolicitado = null;

		if(mensajeSolicitud.contains("/"))
		{			
			// Obtener index.html
			archivoSolicitado = new File("src/main/resources/index.html");
		}
		else
		{
			// Obtener del archivo de acuerdo al path de parametro
		}
		
		if(archivoSolicitado.exists())
		{
			salida.print("HTTP/1.1 200 OK\r\n");
			salida.print("Date: Mon, 27 Jul 2009 12:28:53 GMT\r\n");
			salida.print("Server: MiServidor/1.0\r\n");
			salida.print("Content-Length: " + archivoSolicitado.length() +"\r\n");
			salida.print("Content-Type: text/html\r\n");
			salida.print("Connection: Closed\r\n");
			salida.print("\r\n"); // Listo los headers, lo siguiente es el mensaje como tal
			
			responderArchivo(salida);
		}
		else
		{
			// error
		}
	}
	
	private void responderArchivo(PrintWriter salida)
	{
		BufferedReader lector;
		
		try 
		{
			lector = new BufferedReader(new FileReader("src/main/resources/index.html"));
			String linea = null;
			
			// Linea por linea para leer y enviar todo el archivo
			while ((linea = lector.readLine()) != null) 
			{
				salida.print(linea + "\r\n");
			}
			lector.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
