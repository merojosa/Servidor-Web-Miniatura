package respuestas;

import java.io.OutputStream;

public interface HttpRespuesta 
{
	public void procesarSolicitud(String mensajeSolicitud, OutputStream salida);
}
