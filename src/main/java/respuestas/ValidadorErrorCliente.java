package respuestas;

public abstract class ValidadorErrorCliente 
{
	private ValidadorErrorCliente siguiente;

	public ValidadorErrorCliente()
	{
		siguiente = null;
	}
	
	// True si es error
	protected abstract boolean validarError(Url url, Solicitud solicitud);
	
	protected abstract String obtenerCodigo();
	
	public String buscarError(Url url, Solicitud solicitud)
	{
		// Si no hay error
		if(validarError(url, solicitud) == false)
		{
			if(siguiente != null)
			{
				// Buscar el siguiente
				return siguiente.buscarError(url, solicitud);
			}
			else
			{
				// No hay siguiente, no hay error por completo.
				return null;
			}
		}
		// Error
		
		return obtenerCodigo();
	}
	
	public ValidadorErrorCliente contruirValidador(ValidadorErrorCliente siguiente)
	{
		this.siguiente = siguiente;
		return siguiente;
	}
}
