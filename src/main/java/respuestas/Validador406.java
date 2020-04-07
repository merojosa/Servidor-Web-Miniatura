package respuestas;

public class Validador406 extends ValidadorErrorCliente
{

	@Override
	protected boolean validarError(Url url, Solicitud solicitud)
	{
		return false;
	}

	protected String obtenerCodigo() 
	{
		return CodigoHttp.NOT_FOUND.obtenerMensaje();
	}

}
