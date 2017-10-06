package sg.cs3219.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Queries {
	private static int totalCitations = 0;
	private static Set<String> uniqueCitationSet = new HashSet<String>();
	private static ArrayList<String> XMLpathList = new ArrayList<String>();

	public static void main(String[] args) {
		String path = "src/main/resources";
		findTotalDocuments(path);
		System.out.println("Q1: Total documents: " + XMLpathList.size());
		findTotalCitations();
		System.out.println("Q2: Total citations: " + totalCitations);
		System.out.println("Q3: Total unique citations: " + uniqueCitationSet.size());
	}
	
	//QUERY 1
	private static void findTotalDocuments(String path) {
		File f = new File(path);
		File[] files = f.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) {
					findTotalDocuments(file.getAbsolutePath());
				} else {
					XMLpathList.add(file.getAbsolutePath());
				}
			}
		}
	}
	
	//QUERY 2
	private static void findTotalCitations() {
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList list = doc.getElementsByTagName("citation");
				getUniqueCitations(list);
				totalCitations += list.getLength();
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (SAXException sae) {
				sae.printStackTrace();
			}
		}
	}
	
	//QUERY 3
	private static void getUniqueCitations(NodeList list) {
		for(int i = 0; i < list.getLength(); i++) {
			uniqueCitationSet.add(list.item(i).getTextContent());
		}
	}
}
