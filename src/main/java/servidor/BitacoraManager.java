package servidor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BitacoraManager 
{	
	private static final String PATH_BITACORA = "src/main/resources/bitacora.txt";
	
	public static void escribirEntrada(String linea)
	{
		PrintWriter writer;
		try 
		{
			writer = new PrintWriter(PATH_BITACORA, "UTF-8");
			writer.println(linea);
			writer.close();
		} 
		catch (FileNotFoundException | UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}
}
