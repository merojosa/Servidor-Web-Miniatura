package respuestas;

import java.io.IOException;
import java.io.OutputStream;

public class RespuestaNoImplementado extends HttpRespuesta {

	@Override
	public boolean procesarSolicitud(Solicitud solicitud, Url url, OutputStream salida) 
	{
		try 
		{
			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.NOT_IMPLEMENTED.obtenerMensaje() + "\r\n"));
			enviarEncabezadosComunes(salida, null);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return true;
	}

}
