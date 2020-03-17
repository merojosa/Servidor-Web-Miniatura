package respuestas;

public class RespuestaGET implements HttpRespuesta 
{

	@Override
	public String procesarSolicitud(String mensajeSolicitud) 
	{
		if(mensajeSolicitud.contains("/"))
		{
			// Retornar index.html
		}
		else
		{
			// Verificar si el documento solicitado
			
			// Si no existe, 404
		}
		return null;
	}

}
