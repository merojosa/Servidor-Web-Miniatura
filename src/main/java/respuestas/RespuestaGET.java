package respuestas;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import servidor.BitacoraManager;

public class RespuestaGET extends HttpRespuesta 
{
	// Estructura de una respuesta https://www.tutorialspoint.com/http/http_responses.htm
	private String url;
	private String datos;
	
	public RespuestaGET() 
	{
		super();
		url = "";
		datos = "";
	}
		
	@Override
	public boolean procesarSolicitud(Solicitud solicitud, OutputStream salida) 
	{
		if(solicitud.encabezadoExiste("GET"))
		{
			try 
			{
				procesarGET(solicitud.obtenerValor("GET"), salida);
				
				String referer = solicitud.obtenerValor("Referer");
				
				BitacoraManager.escribirEntrada("GET", referer == null ? "Sin referer" :  referer, url, datos);
				
				return true;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	// Se actualizan url y datos (si es que hay datos).
	private void procesarGET(String mensajeSolicitud, OutputStream salida) throws IOException
	{
		// mensajeSolicitud tiene / de primero
		String path = PATH_RAIZ;
		int indexDatos = mensajeSolicitud.indexOf('?');
		
		// Existen datos con ?
		if( indexDatos >= 0)
		{
			datos = mensajeSolicitud.substring(indexDatos + 1);
			url = mensajeSolicitud.substring(0, indexDatos); // No incluir lo que hay despues del ?
			path += url;
		}
		else
		{
			url = mensajeSolicitud;
			path += url;
		}

		// Si la solicitud se hace sin especificar archivo, se supone un index.html
		if(url.length() > 0 && url.charAt(url.length() - 1) == '/' )
		{			
			path = path.concat("index.html");
		}

		// Obtener del archivo de acuerdo al path de parametro
		devolverArchivoSolicitado(new File(path), salida);
	}
	
	public void devolverArchivoSolicitado(File archivoSolicitado, OutputStream salida) throws IOException
	{
		if(archivoSolicitado.exists())
		{
			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.OK.obtenerMensaje() + "\r\n"));
			enviarEncabezadosComunes(salida, archivoSolicitado);
			
			responderArchivo(salida, archivoSolicitado);
			
			// El que llama al metodo se tiene que encargar de hacer flush y cerrar.
		}
		else
		{
			// Error
			salida.write(convertirBytes("HTTP/1.1 " + CodigoHttp.NOT_FOUND.obtenerMensaje() + "\r\n"));
			
			archivoSolicitado = new File(PATH_404);
			enviarEncabezadosComunes(salida, archivoSolicitado);
			responderArchivo(salida, archivoSolicitado);
		}
	}
}
