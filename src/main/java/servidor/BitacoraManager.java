package servidor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class BitacoraManager 
{	
	private static final String PATH_BITACORA = "src/main/resources/bitacora.txt";
	
	
	public static void escribirEntrada(String tipoSolicitud, String refiere, String url, String datos)
	{
		StringBuilder constructorString = new StringBuilder();
		PrintWriter writer;
		try 
		{
			
			writer = new PrintWriter(new FileOutputStream(PATH_BITACORA, true));
			
			constructorString.append(tipoSolicitud + "\t");
			constructorString.append(ServidorWeb.obtenerFechaServidorGMT() + "\t");
			constructorString.append(ServidorWeb.NOMBRE_SERVIDOR + "\t");
			constructorString.append(refiere + "\t");
			constructorString.append(url + "\t");
			constructorString.append(datos);

			
			writer.println(constructorString.toString());
			
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
