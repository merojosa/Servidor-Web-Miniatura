package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class RespuestaGET extends HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
		
	@Override
	public boolean procesarSolicitud(String mensajeSolicitud, OutputStream salida) 
	{
		if(mensajeSolicitud.contains("GET"))
		{
			try 
			{
				procesarGET(extraerMensaje(mensajeSolicitud.toCharArray()), salida);
				return true;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private void procesarGET(String mensajeSolicitud, OutputStream salida) throws IOException
	{
		// mensajeSolicitud tiene / de primero
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
}
