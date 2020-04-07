package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import servidor.BitacoraManager;
import servidor.ServidorWeb;

public class RespuestaGET extends HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
		
	@Override
	public boolean procesarSolicitud(Solicitud solicitud, OutputStream salida) 
	{
		if(solicitud.encabezadoExiste("GET"))
		{
			try 
			{
				Url url = procesarUrl(solicitud.obtenerValor("GET"), salida);
				
				// Obtener del archivo de acuerdo al path de parametro
				devolverArchivoSolicitado(new File(url.getLinkFisico()), salida);
				
				String referer = solicitud.obtenerValor("Referer");
				BitacoraManager.escribirEntrada("GET", referer == null ? "Sin referer" :  referer, url.getLinkRelativo(), url.getDatos());
				
				return true;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void devolverArchivoSolicitado(File archivoSolicitado, OutputStream salida) throws IOException
	{
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
			
			archivoSolicitado = new File(ServidorWeb.PATH_404);
			enviarEncabezadosComunes(salida, archivoSolicitado);
			responderArchivo(salida, archivoSolicitado);
		}
	}
}
