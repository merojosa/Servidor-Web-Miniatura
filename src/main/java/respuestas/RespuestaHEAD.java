package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import servidor.BitacoraManager;
import servidor.ServidorWeb;

// Envio lo mismo que un GET excepto el archivo
public class RespuestaHEAD extends HttpRespuesta 
{	
	@Override
	public boolean procesarSolicitud(Solicitud solicitud, OutputStream salida) 
	{
		if(solicitud.encabezadoExiste("HEAD"))
		{
			try 
			{
				procesarHEAD(solicitud, salida);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

			return true;
		}
		
		return false;
	}
	
	private void procesarHEAD(Solicitud solicitud, OutputStream salida) throws IOException
	{
		Url url = procesarUrl(solicitud.obtenerValor("HEAD"), salida);

		File archivoSolicitado = new File(url.getLinkFisico());
		
		if(archivoSolicitado.exists())
		{
			try 
			{
				salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.OK.obtenerMensaje() + "\r\n"));			
				enviarEncabezadosComunes(salida, archivoSolicitado);
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			// Error

			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.NOT_FOUND.obtenerMensaje() + "\r\n"));
			archivoSolicitado = new File(ServidorWeb.PATH_404);
			enviarEncabezadosComunes(salida, archivoSolicitado);

		}
		String referer = solicitud.obtenerValor("Referer");

		BitacoraManager.escribirEntrada("HEAD", referer == null ? "Sin referer" :  referer, url.getLinkRelativo(), url.getDatos());
	}

}
