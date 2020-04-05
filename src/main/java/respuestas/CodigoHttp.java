package respuestas;

// Mensaje y explicacion de los codigos: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
public enum CodigoHttp 
{
	OK("200 OK"),
	NOT_FOUND("404 Not Found"),
	NOT_ACCEPTABLE("406 Not Acceptable"),
	NOT_IMPLEMENTED("501 Not Implemented");
	
	
	private final String mensaje;
	
	CodigoHttp(String mensaje)
	{
		this.mensaje = mensaje;
	}
	
	public String obtenerMensaje()
	{
		return mensaje;
	}
}
