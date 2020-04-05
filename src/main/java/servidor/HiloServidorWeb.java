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
	
	private void procesarSolicitud(Socket socket) throws IOException
	{
		// Para leer la solicitud del cliente
		BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// Para responderle al cliente en bytes
        OutputStream salida =  socket.getOutputStream();
        
		
        String linea = entrada.readLine();
		if(linea.length() != 0)
	  	{
			respuesta.chequearSolicitud(linea, salida);
	  	}

        salida.flush();
        salida.close();
        entrada.close();
        socket.close();
	}
}
