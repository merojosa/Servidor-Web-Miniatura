package respuestas;

import java.io.BufferedInputStream;
import java.util.Hashtable;

public class Solicitud 
{
	private Hashtable<String, String> datos;
	private BufferedInputStream entrada;
	
	public Solicitud(BufferedInputStream entrada)
	{
		datos = new Hashtable<String, String>();
		this.entrada = entrada;
	}
	
	public void agregarEncabezado(String encabezado, String valor)
	{
		datos.put(encabezado, valor);
	}
	
	public boolean encabezadoExiste(String encabezado)
	{
		return datos.containsKey(encabezado);
	}
	
	public String obtenerValor(String encabezado)
	{
		return datos.get(encabezado);
	}
	
	public BufferedInputStream getEntrada()
	{
		return entrada;
	}
}
