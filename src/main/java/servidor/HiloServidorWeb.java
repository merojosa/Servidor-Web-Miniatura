package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import respuestas.HttpRespuesta;
import respuestas.RespuestaGET;
import respuestas.RespuestaHEAD;
import respuestas.RespuestaNoImplementado;
import respuestas.Solicitud;

public class HiloServidorWeb implements Runnable 
{
	private Socket socket;
	private HttpRespuesta respuesta;
	
	public HiloServidorWeb(Socket socket) 
	{
		this.socket = socket;
		respuesta = new RespuestaGET();
		respuesta.construirCadenaDeSolicitudes(new RespuestaHEAD())
					.construirCadenaDeSolicitudes(new RespuestaNoImplementado());
		
	}

	@Override
	public void run()
	{
		try 
		{
			procesarSolicitud(socket);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	// https://stackoverflow.com/questions/30901173/handling-post-request-via-socket-in-java
	private void procesarSolicitud(Socket socket) throws IOException
	{
		// Para leer la solicitud del cliente
		BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// Para responderle al cliente en bytes
        OutputStream salida =  socket.getOutputStream();
                
		Solicitud solicitud = procesarEntrada(entrada);
		respuesta.chequearSolicitud(solicitud, salida);

        salida.flush();
        salida.close();
        entrada.close();
        socket.close();
	}
	
	private Solicitud procesarEntrada(BufferedReader entrada) throws IOException
	{
        String linea = entrada.readLine();
        String llave = "";
        String valor = "";
        Solicitud solicitud = new Solicitud(entrada);
        int index_espacio = 0;

		while(linea.length() > 0)
		{
			index_espacio = linea.indexOf(' ');
			
			if(linea.contains(":"))
			{
				// Para no incluir los :
				
				llave = linea.substring(0, index_espacio - 1);
				valor = linea.substring(index_espacio + 1);

			}
			else
			{
				llave = linea.substring(0, index_espacio);
				
				// Incluir la expresion del medio, no la version http
				valor = linea.substring(index_espacio + 1);
				valor = valor.substring(0, valor.indexOf(' '));
			}
			
			solicitud.agregarEncabezado(llave, valor);
			linea = entrada.readLine();
		}
		
		return solicitud;
	}
}
