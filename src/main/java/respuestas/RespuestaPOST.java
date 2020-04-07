package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import servidor.BitacoraManager;

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
		if(solicitud.encabezadoExiste("POST"))
		{
			try
			{
				byte[] datosPost = new byte[new Integer(solicitud.obtenerValor("Content-Length"))];
				Url url = procesarUrl(solicitud.obtenerValor("POST"), salida);
				solicitud.getEntrada().read(datosPost);
				
				respuestaGET.devolverArchivoSolicitado(new File(url.getLinkFisico()), salida);
				
				String referer = solicitud.obtenerValor("Referer");
				BitacoraManager.escribirEntrada("POST", referer == null ? "Sin referer" :  referer, url.getLinkRelativo(), new String(datosPost));
								
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
