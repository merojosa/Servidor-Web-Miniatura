package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

// Envio lo mismo que un GET excepto el archivo
public class RespuestaHEAD extends HttpRespuesta 
{
	@Override
	public boolean procesarSolicitud(String mensajeSolicitud, OutputStream salida) 
	{
		if(mensajeSolicitud.contains("HEAD"))
		{
			String mensajeProcesado = extraerMensaje(mensajeSolicitud.toCharArray());
			String path = PATH_RAIZ + mensajeProcesado;

			if(mensajeProcesado.length() > 0 && mensajeProcesado.charAt(mensajeProcesado.length() - 1) == '/' )
			{			
				path = path.concat("index.html");
			}


			File archivoSolicitado = new File(path);
			
			if(archivoSolicitado.exists())
			{
				try 
				{
					salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.OK.obtenerMensaje() + "\r\n"));			
					enviarEncabezadosComunes(salida, archivoSolicitado);
					return true;
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				// Error
				try 
				{
					salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.NOT_FOUND.obtenerMensaje() + "\r\n"));
					archivoSolicitado = new File(PATH_404);
					enviarEncabezadosComunes(salida, archivoSolicitado);
					return true;
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

}
