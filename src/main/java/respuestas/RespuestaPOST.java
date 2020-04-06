package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

// Procesar un POST: https://stackoverflow.com/questions/30901173/handling-post-request-via-socket-in-java
public class RespuestaPOST extends HttpRespuesta 
{
	// Todo POST necesita devolver un archivo solicitado
	private RespuestaGET respuestaGET;
	public RespuestaPOST()
	{
		super();
		respuestaGET = new RespuestaGET();
		
	}

	@Override
	public boolean procesarSolicitud(Solicitud solicitud, OutputStream salida) 
	{
		byte[] datosPost = new byte[new Integer(solicitud.obtenerValor("Content-Length"))];
		if(solicitud.encabezadoExiste("POST"))
		{
			try
			{
				solicitud.getEntrada().read(datosPost);
				procesarPOST(solicitud.obtenerValor("POST"), new String(datosPost), salida);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private void procesarPOST(String mensajeSolicitud, String datos, OutputStream salida) throws IOException
	{
		String path = PATH_RAIZ + mensajeSolicitud;
		
		// Si la solicitud se hace sin especificar archivo, se supone un index.html
		if(mensajeSolicitud.length() > 0 && mensajeSolicitud.charAt(mensajeSolicitud.length() - 1) == '/' )
		{			
			// Obtener index.html
			path = path.concat("index.html");
		}
		
		File archivoSolicitado = new File(path);

		// Obtener del archivo de acuerdo al path de parametro
		respuestaGET.devolverArchivoSolicitado(archivoSolicitado, salida);
	}

}
