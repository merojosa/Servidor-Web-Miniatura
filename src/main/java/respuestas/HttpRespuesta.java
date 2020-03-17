package respuestas;

import java.io.PrintWriter;

public interface HttpRespuesta 
{
	public void procesarSolicitud(String mensajeSolicitud, PrintWriter salida);
}
