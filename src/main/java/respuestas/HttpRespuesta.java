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

public abstract class HttpRespuesta 
{
	protected ProcesadorXML procesadorXML;
	
	protected final static String PATH_RAIZ = "src/main/resources/httpdoc";
	protected final static String PATH_404 = "src/main/resources/httpdoc/errores/error404.html";

	
	protected HttpRespuesta()
	{
		procesadorXML = new ProcesadorXML();
	}
		
	// Incluye el fin de los encabezados
	protected void enviarEncabezadosComunes(OutputStream salida, File archivoSolicitado) throws IOException
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
	
	protected byte[] convertirBytes(String string)
	{
		return string.getBytes();
	}
	
	protected String obtenerExtension(File archivo)
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
	
	protected void responderArchivo(OutputStream salida, File archivo) throws IOException
	{
		InputStream entrada = new FileInputStream(archivo);
		
		ByteStreams.copy(entrada, salida);
	}
	
	public abstract void procesarSolicitud(String mensajeSolicitud, OutputStream salida);
		
}
