package servidor;

import java.io.File;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// https://howtodoinjava.com/xml/java-xpath-expression-examples/
public class ProcesadorXML 
{
	public String obtenerTipo(String extension) throws Exception // Demasiadas excepciones, mejor manejar la general
	{
		// Obtengo el xml
        File archivo = new File("src/main/resources/web.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(archivo);
        
        NodeList listaExtensiones = documento.getElementsByTagName("mime-mapping");
        
        Node hijoExtension = null;
        Node hijoMimeType = null;
        
        for(int contador = 0; contador < listaExtensiones.getLength(); ++contador)
        {
        	hijoExtension = listaExtensiones.item(contador).getChildNodes().item(1);
        	
        	// Si encuentra la extension
        	if(extension.equals(hijoExtension.getTextContent()))
        	{
        		hijoMimeType = listaExtensiones.item(contador).getChildNodes().item(3);
        		return hijoMimeType.getTextContent();
        	}
        }

 
		return null;
	}
}
