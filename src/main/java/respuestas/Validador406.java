package respuestas;

import servidor.ProcesadorXML;
import servidor.ServidorWeb;

public class Validador406 extends ValidadorErrorCliente
{
	private ProcesadorXML procesadorXML;
	
	public Validador406()
	{
		procesadorXML = new ProcesadorXML();
	}
	
	@Override
	protected boolean validarError(Url url, Solicitud solicitud)
	{
		// Este if entra unicamente por requerimiento de la tarea, solo se valida el error 406 con curl
		if(solicitud.obtenerValor("User-Agent").contains("curl") == true)
		{
			if(solicitud.encabezadoExiste("Accept"))
			{
				try 
				{
					String tipoRequerido = procesadorXML.obtenerTipo(ServidorWeb.obtenerExtension(url.getLinkFisico()));
					String[] tiposSolicitados = solicitud.obtenerValor("Accept").split(",");
					
					for(int contador = 0; contador < tiposSolicitados.length; ++contador)
					{
						// El tipo requerido y uno de los tipos solicitados si calzan.
						if(tiposSolicitados[contador].equals(tipoRequerido) == true)
						{
							// No hay error
							return false;
						}
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			return true;
		}
		else 
		{
			return false;
		}
	}

	protected String obtenerCodigo() 
	{
		return CodigoHttp.NOT_ACCEPTABLE.obtenerMensaje();
	}

}
