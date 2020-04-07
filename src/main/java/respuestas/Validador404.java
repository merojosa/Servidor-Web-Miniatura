package respuestas;

import java.io.File;

public class Validador404 extends ValidadorErrorCliente 
{
	

	@Override
	protected boolean validarError(Url url, Solicitud solicitud) 
	{
		File archivoSolicitado = new File(url.getLinkFisico());
		
		return !archivoSolicitado.exists();
	}

	@Override
	protected String obtenerCodigo() 
	{
		return CodigoHttp.NOT_FOUND.obtenerMensaje();
	}



}
