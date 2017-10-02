package sg.cs3219.app.extract;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class Loader {
	public static void main(String[] args) {
		try {
			File inputFile = new File("src/main/ressources/D/D12/D12-1000-parscit.130908.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			NodeList nList = doc.getElementsByTagName("algorithm");
			 for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            System.out.println("\nCurrent Element :" + nNode.getNodeName());
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		                Element eElement = (Element) nNode;
		                System.out.println("title : " 
		                   + eElement.getElementsByTagName("author").item(0).getTextContent());
		            }
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
