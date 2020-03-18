package servidor;

import java.io.File;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;

// https://howtodoinjava.com/xml/java-xpath-expression-examples/
public class ProcesadorXML 
{
	public String procesar(String extension) throws Exception // Demasiadas excepciones, mejor manejar la general
	{
		// Obtengo el xml
        File archivo = new File("src/main/resources/web.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(archivo);

        // Obtengo el mime-type de la extension dada
        XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expresion = xpath.compile("/web-app/mime-mapping[extension=" + extension + "]/mime-type");
		
		return expresion.evaluate(documento, XPathConstants.STRING).toString();
	}
}
