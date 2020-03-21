package respuestas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import com.google.common.io.ByteStreams;

import servidor.ProcesadorXML;

public class RespuestaGET implements HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
	
	private final static String PATH_RAIZ = "src/main/resources/httpdoc";
	private final static String PATH_404 = "src/main/resources/httpdoc/errores/error404.html";
	private ProcesadorXML procesadorXML;
	
	public RespuestaGET() 
	{
		procesadorXML = new ProcesadorXML();
	}
	
	@Override
	public void procesarSolicitud(String mensajeSolicitud, OutputStream salida) 
	{
		try 
		{
			procesarGET(mensajeSolicitud, salida);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void procesarGET(String mensajeSolicitud, OutputStream salida) throws IOException
	{
		// mensajeSolicitud tine / de primero
		String path = PATH_RAIZ + mensajeSolicitud;

		// Si la solicitud se hace sin especificar archivo, se supone un index.html
		if(mensajeSolicitud.length() > 0 && mensajeSolicitud.charAt(mensajeSolicitud.length() - 1) == '/' )
		{			
			// Obtener index.html
			path = path.concat("index.html");
		}

		// Obtener del archivo de acuerdo al path de parametro
		File archivoSolicitado = new File(path);
		
		
		if(archivoSolicitado.exists())
		{
			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.OK.obtenerMensaje() + "\r\n"));
			
			enviarEncabezadosComunes(salida, archivoSolicitado);
			
			responderArchivo(salida, archivoSolicitado);
			
			// El que llama al metodo se tiene que encargar de hacer flush y cerrar.
		}
		else
		{
			// Error
			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.NOT_FOUND.obtenerMensaje() + "\r\n"));
			
			archivoSolicitado = new File(PATH_404);
			enviarEncabezadosComunes(salida, archivoSolicitado);
			responderArchivo(salida, archivoSolicitado);

		}
	}
	
	// Incluye el fin de los encabezados
	private void enviarEncabezadosComunes(OutputStream salida, File archivoSolicitado) throws IOException
	{
		SimpleDateFormat formateador = new SimpleDateFormat();
		formateador.setTimeZone(new SimpleTimeZone(0, "GMT"));
		formateador.applyPattern("E, dd MMM yyyy HH:mm:ss z");
		Date fecha = new Date();
		
		salida.write(convertirBytes("Date: " + formateador.format(fecha) + "\r\n")); // FALTA FORMATO
		salida.write(convertirBytes("Server: MiServidor/1.0\r\n"));
		salida.write(convertirBytes("Content-Length: " + archivoSolicitado.length() +"\r\n"));
		
		try 
		{
			// Extraer el dominio y obtener el tipo de acuerdo a web.xml
			salida.write(convertirBytes("Content-Type: " + procesadorXML.obtenerTipo(obtenerExtension(archivoSolicitado)) + "\r\n"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		salida.write(convertirBytes("\r\n")); // Listo los headers, lo siguiente es el mensaje como tal
	}
	
	private byte[] convertirBytes(String string)
	{
		return string.getBytes();
	}
	
	private void responderArchivo(OutputStream salida, File archivo) throws IOException
	{
		InputStream entrada = new FileInputStream(archivo);
		
		ByteStreams.copy(entrada, salida);
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
