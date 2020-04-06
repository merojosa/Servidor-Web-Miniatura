package respuestas;

import java.io.IOException;
import java.io.OutputStream;

// Procesar un POST: https://stackoverflow.com/questions/30901173/handling-post-request-via-socket-in-java
public class RespuestaPOST extends HttpRespuesta {

	@Override
	public boolean procesarSolicitud(Solicitud solicitud, OutputStream salida) 
	{
		byte[] contenidoPost = new byte[new Integer(solicitud.obtenerValor("Content-Length"))];
		if(solicitud.encabezadoExiste("POST"))
		{
			try
			{
				solicitud.getEntrada().read(contenidoPost);
				System.out.println(new String(contenidoPost));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
