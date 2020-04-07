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
	public boolean procesarSolicitud(Solicitud solicitud, Url url, OutputStream salida) 
	{
		if(solicitud.obtenerValor("TipoRequest").equals("POST"))
		{
			try
			{
				byte[] datosPost = new byte[new Integer(solicitud.obtenerValor("Content-Length"))];
				solicitud.getEntrada().read(datosPost);	// Obtengo los bytes que se enviaron desde el cliente
				
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
