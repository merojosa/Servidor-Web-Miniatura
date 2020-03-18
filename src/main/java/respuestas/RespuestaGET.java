package respuestas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;


import servidor.ProcesadorXML;

public class RespuestaGET implements HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
	
	private final String PATH_RAIZ = "src/main/resources/httpdoc";
	private ProcesadorXML procesadorXML;
	
	public RespuestaGET() 
	{
		procesadorXML = new ProcesadorXML();
	}
	
	@Override
	public void procesarSolicitud(String mensajeSolicitud, PrintWriter salida) 
	{
		File archivoSolicitado = null;
		// mensajeSolicitud tine / de primero
		String path = PATH_RAIZ + mensajeSolicitud;

		if(mensajeSolicitud.length() > 0 && mensajeSolicitud.charAt(mensajeSolicitud.length() - 1) == '/' )
		{			
			// Obtener index.html
			path = path.concat("index.html");
		}

		// Obtener del archivo de acuerdo al path de parametro
		archivoSolicitado = new File(path);
		
		
		if(archivoSolicitado.exists())
		{
			salida.print("HTTP/1.1 200 OK\r\n");
		
			
			salida.print("Date: " + Instant.now().toString()   + "GMT\r\n"); // FALTA FORMATO
			salida.print("Server: MiServidor/1.0\r\n");
			salida.print("Content-Length: " + archivoSolicitado.length() +"\r\n");
			
			try 
			{
				// Extraer el dominio y obtener el tipo de acuerdo a web.xml
				salida.print("Content-Type: " + procesadorXML.procesar(obtenerExtension(archivoSolicitado)) + "\r\n");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			salida.print("\r\n"); // Listo los headers, lo siguiente es el mensaje como tal
			
			responderArchivo(salida, archivoSolicitado);
		}
		else
		{
			// Error
		}
	}
	
	private void responderArchivo(PrintWriter salida, File archivo)
	{
		BufferedReader lector;
		
		try 
		{
			lector = new BufferedReader(new FileReader(archivo));
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
	
	private String obtenerExtension(File archivo)
	{
		String fileName = archivo.getName();
		
		// Si hay . y si el nombre no termina con .
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        else 
        {
        	return "";
        }
	}

}
