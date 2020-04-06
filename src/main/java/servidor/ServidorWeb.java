package servidor;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


// Comunicacion cliente-servidor: http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm
// Threads: https://www.tutorialspoint.com/javaexamples/net_multisoc.htm
public class ServidorWeb
{
    public static final String NOMBRE_SERVIDOR = "RojasServidor/1.0";

	
    private ServerSocket socketServidor;
    private final int PUERTO = 9885;
   	
	public void ejecutar() throws IOException
	{
        socketServidor = new ServerSocket(PUERTO);
        
        
        while(true)
        {
            System.out.println("Esperando solicitud");
            
            // Se queda esperando hasta que haya un request
            Socket socket = socketServidor.accept();
            
            System.out.println("Procesando solicitud");
            // Se crea un nuevo HiloServidorWeb y se ejecuta su metodo run.
            new Thread(new HiloServidorWeb(socket)).start();            
        }
	}
	
	public static String obtenerFechaServidorGMT()
	{
		SimpleDateFormat formateador = new SimpleDateFormat();
		formateador.setTimeZone(new SimpleTimeZone(0, "GMT"));
		formateador.applyPattern("E, dd MMM yyyy HH:mm:ss z");
		Date fecha = new Date();
		return formateador.format(fecha);
	}
}
