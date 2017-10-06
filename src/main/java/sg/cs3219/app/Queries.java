package sg.cs3219.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Queries {
	private static int totalCitations = 0;
	private static int totalCitedAuthors = 0;
	private static Set<String> uniqueCitationSet = new HashSet<String>();
	private static int[] rangeOfYears = new int[2];
	private static ArrayList<String> XMLpathList = new ArrayList<String>();

	public static void main(String[] args) {
		String path = "src/main/resources";
		findTotalDocuments(path);
		System.out.println("Q1: Total documents: " + XMLpathList.size());
		findTotalCitations();
		System.out.println("Q2: Total citations: " + totalCitations);
		System.out.println("Q3: Total unique citations: " + uniqueCitationSet.size());
		findTotalAuthorsCited();
		System.out.println("Q4: Total authors in citations: " + totalCitedAuthors);
		findRangeOfYears();
		System.out.println("Q5: Range of Years of cited documents: " + rangeOfYears[0] + " - " + rangeOfYears[1]);
	}

	// QUERY 1
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

	// QUERY 2
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

	// QUERY 3
	private static void getUniqueCitations(NodeList list) {
		for (int i = 0; i < list.getLength(); i++) {
			uniqueCitationSet.add(list.item(i).getTextContent());
		}
	}

	// QUERY 4
	private static void findTotalAuthorsCited() {
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList list = doc.getElementsByTagName("authors");
				totalCitedAuthors += list.getLength();
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (SAXException sae) {
				sae.printStackTrace();
			}
		}
	}

	// QUERY 5
	private static void findRangeOfYears() {
		rangeOfYears[0] = 3000;
		rangeOfYears[1] = 0;
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList list = doc.getElementsByTagName("date");
				for (int i = 0; i < list.getLength(); i++) {
					if (!list.item(i).hasAttributes()) {
						String date = list.item(i).getTextContent().toString();
						if (date != "") {
							Integer year = Integer.parseInt(date);
							if (year < rangeOfYears[0]) {
								rangeOfYears[0] = year;
							} else if (year > rangeOfYears[1]) {
								rangeOfYears[1] = year;
							}
						}
					}
				}
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (SAXException sae) {
				sae.printStackTrace();
			}
		}
	}
}
