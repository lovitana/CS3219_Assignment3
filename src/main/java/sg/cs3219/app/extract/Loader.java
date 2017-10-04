package sg.cs3219.app.extract;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Loader {
	public static void main(String[] args) {
		try {
			File inputFile = new File("src/main/ressources/D/D12/D12-1000-parscit.130908.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

	        XPath xPath =  XPathFactory.newInstance().newXPath();
	        String expression = "/algorithms/algorithm";
	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
	            doc, XPathConstants.NODESET);
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
