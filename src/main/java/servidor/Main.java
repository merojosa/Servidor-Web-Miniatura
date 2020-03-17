package servidor;

import java.io.IOException;

public class Main 
{
    public static void main(String args[]) throws IOException
    {
    	ServidorWeb servidor = new ServidorWeb();
    	servidor.ejecutar();
    }
}
