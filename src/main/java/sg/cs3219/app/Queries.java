package sg.cs3219.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Queries {
	private static int totalDocuments = 0;
	private static ArrayList<String> XMLpathList = new ArrayList<String>();
	private static int[][] numberOfCitedDocumentsPerYears = new int[4][16];
	private static final int START_YEAR = 2000;
	private static final int END_YEAR = 2015;
	private static final String EMNLP_FULL_FORM = "EMPIRICAL METHODS IN NATURAL LANGUAGE PROCESSING";
	private static final String CONLL_FULL_FORM = "COMPUTATIONAL NATURAL LANGUAGE LEARNING";
	private static final String AUTHOR_FULL_FORM = "YOSHUA BENGIO";
	private static final String AUTHOR_SHORT_FORM = "Y. BENGIO";

	public static void main(String[] args) {
		String path = "src/main/resources";
		initializations();

		// QUERY 1
		int totalDocs = findTotalDocuments(path);
		System.out.println("Q1: Total documents: " + totalDocs);

		// QUERY 2
		int totalCitations = findTotalCitations();
		System.out.println("Q2: Total citations: " + totalCitations);

		// QUERY 3
		int totalUniqueCitations = findUniqueCitations();
		System.out.println("Q3: Total unique citations: " + totalUniqueCitations);

		// QUERY 4
		int totalCitedAuthors = findTotalAuthorsCited();
		System.out.println("Q4: Total authors in citations: " + totalCitedAuthors);

		// QUERY 5
		int[] rangeOfYears = new int[2];
		rangeOfYears = findRangeOfYears();
		System.out.println("Q5: Range of Years of cited documents: " + rangeOfYears[0] + " - " + rangeOfYears[1]);

		// QUERY 6
		int[] numberOfCitedDocumentsD12 = new int[16];
		numberOfCitedDocumentsD12 = countYearOfCitedDocumentD12();
		System.out.println("Q6: Number of cited documents published in following years in D12: ");
		int initialYear = START_YEAR;
		for (int i = 0; i < numberOfCitedDocumentsD12.length - 1; i++) {
			System.out.print(initialYear + ": " + numberOfCitedDocumentsD12[i] + " , ");
			initialYear++;
		}
		System.out.println(END_YEAR + ": " + numberOfCitedDocumentsD12[15]);

		// QUERY 7
		int[] numberOfCitedDocumentsD13 = new int[16];
		numberOfCitedDocumentsD13 = countNumberOfCitedDocumentsD13();
		System.out.println("Q7: Number of cited documents published in EMNLP: " + numberOfCitedDocumentsD13[0]
				+ " CoNLL: " + numberOfCitedDocumentsD13[1]);

		// QUERY 8
		int[] numberOfCitedDocumentsYoshuaBengio = new int[16];
		numberOfCitedDocumentsYoshuaBengio = findTotalYoshuaBengio();
		System.out.println("Q8: Number of cited documents published in following years by Yoshua Bengio: ");
		initialYear = START_YEAR;
		for (int i = 0; i < numberOfCitedDocumentsYoshuaBengio.length - 1; i++) {
			System.out.print(initialYear + ": " + numberOfCitedDocumentsYoshuaBengio[i] + " , ");
			initialYear++;
		}
		System.out.println(END_YEAR + ": " + numberOfCitedDocumentsYoshuaBengio[15]);

		// QUERY 9
		findPerYear();
		System.out.println("Q9: number of cited documents published in each of the years from 2010 to 2015: ");
		for (int i = 0; i < numberOfCitedDocumentsPerYears.length; i++) {
			for (int j = 0; j < numberOfCitedDocumentsPerYears[0].length; j++) {
				System.out.print((j + 2000) + " " + numberOfCitedDocumentsPerYears[i][j] + " ");
			}
		}

	}

	// INITIALIZATIONS
	private static void initializations() {
		for (int i = 0; i < numberOfCitedDocumentsPerYears.length; i++) {
			for (int j = 0; j < numberOfCitedDocumentsPerYears[0].length; j++) {
				numberOfCitedDocumentsPerYears[i][j] = 0;
			}
		}
	}

	// QUERY 1
	private static int findTotalDocuments(String path) {
		File f = new File(path);
		File[] files = f.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) {
					findTotalDocuments(file.getAbsolutePath());
				} else {
					XMLpathList.add(file.getAbsolutePath());
					totalDocuments++;
				}
			}
		}
		return totalDocuments;
	}

	// QUERY 2
	private static int findTotalCitations() {
		int totalCitations = 0;
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList citations = doc.getElementsByTagName("citation");
				for (int i = 0; i < citations.getLength(); i++) {
					if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element citation = (Element) citations.item(i);
						if (!citation.getAttribute("valid").equals("true")) {
							continue;
						}
						totalCitations++;
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
		return totalCitations;
	}

	// QUERY 3
	private static int findUniqueCitations() {
		Set<String> uniqueCitationSet = new HashSet<String>();
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList citations = doc.getElementsByTagName("citation");
				for (int i = 0; i < citations.getLength(); i++) {
					if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element citation = (Element) citations.item(i);
						if (!citation.getAttribute("valid").equals("true")) {
							continue;
						}
						uniqueCitationSet.add(citation.getTextContent());
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
		return uniqueCitationSet.size();
	}

	// QUERY 4
	private static int findTotalAuthorsCited() {
		Set<String> authorSet = new HashSet<String>();
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList citations = doc.getElementsByTagName("citation");
				for (int i = 0; i < citations.getLength(); i++) {
					if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element citation = (Element) citations.item(i);
						if (!citation.getAttribute("valid").equals("true")) {
							continue;
						}
						NodeList authors = citation.getElementsByTagName("author");
						for (int j = 0; j < authors.getLength(); j++) {
							authorSet.add(authors.item(j).getTextContent());
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
		return authorSet.size();
	}

	// QUERY 5
	private static int[] findRangeOfYears() {
		int[] rangeOfYears = new int[2];
		rangeOfYears[0] = 3000;
		rangeOfYears[1] = 0;
		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList citations = doc.getElementsByTagName("citation");
				for (int i = 0; i < citations.getLength(); i++) {
					if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element citation = (Element) citations.item(i);
						if (!citation.getAttribute("valid").equals("true")) {
							continue;
						}
						NodeList dates = citation.getElementsByTagName("date");
						for (int j = 0; j < dates.getLength(); j++) {
							if (!dates.item(j).hasAttributes()) {
								String date = dates.item(j).getTextContent();
								if (date != "") {
									int year = Integer.parseInt(date);
									if (year < rangeOfYears[0]) {
										rangeOfYears[0] = year;
									} else if (year > rangeOfYears[1]) {
										rangeOfYears[1] = year;
									}
								}
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

		return rangeOfYears;
	}

	// QUERY 6
	private static int[] countYearOfCitedDocumentD12() {
		int[] numberOfCitedDocumentsD12 = new int[16];
		for (int i = 0; i < numberOfCitedDocumentsD12.length; i++) {
			numberOfCitedDocumentsD12[i] = 0;
		}

		for (String filepath : XMLpathList) {
			if (filepath.contains("D12")) {
				try {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(filepath);
					NodeList citations = doc.getElementsByTagName("citation");
					for (int i = 0; i < citations.getLength(); i++) {
						if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element citation = (Element) citations.item(i);
							if (!citation.getAttribute("valid").equals("true")) {
								continue;
							}
							NodeList dates = citation.getElementsByTagName("date");
							for (int j = 0; j < dates.getLength(); j++) {
								if (!dates.item(j).hasAttributes()) {
									String date = dates.item(j).getTextContent();
									if (date != "") {
										int year = Integer.parseInt(date);
										if ((year >= START_YEAR) && (year <= END_YEAR)) {
											int x = year % 2000;
											numberOfCitedDocumentsD12[x]++;
										}
									}
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

		return numberOfCitedDocumentsD12;
	}

	// QUERY 7
	private static int[] countNumberOfCitedDocumentsD13() {
		int[] numberOfCitedDocumentsD13 = new int[2];
		for (int j = 0; j < numberOfCitedDocumentsD13.length; j++) {
			numberOfCitedDocumentsD13[j] = 0;
		}
		for (String filepath : XMLpathList) {
			if (filepath.contains("D13")) {
				try {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(filepath);
					NodeList citations = doc.getElementsByTagName("citation");
					for (int i = 0; i < citations.getLength(); i++) {
						if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element citation = (Element) citations.item(i);
							if (!citation.getAttribute("valid").equals("true")) {
								continue;
							}
							NodeList booktitles = citation.getElementsByTagName("booktitle");
							for (int k = 0; k < booktitles.getLength(); k++) {
								String title = booktitles.item(k).getTextContent().toUpperCase();
								if ((title.contains("EMNLP")) || (title.contains(EMNLP_FULL_FORM))) {
									numberOfCitedDocumentsD13[0]++;
								} else if ((title.contains("CONLL")) || (title.contains(CONLL_FULL_FORM))) {
									numberOfCitedDocumentsD13[1]++;
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
		return numberOfCitedDocumentsD13;
	}

	// QUERY 8
	private static int[] findTotalYoshuaBengio() {
		int[] numberOfCitedDocumentsYoshuaBengio = new int[16];
		for (int k = 0; k < numberOfCitedDocumentsYoshuaBengio.length; k++) {
			numberOfCitedDocumentsYoshuaBengio[k] = 0;
		}

		for (String filepath : XMLpathList) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);
				NodeList citations = doc.getElementsByTagName("citation");
				for (int i = 0; i < citations.getLength(); i++) {
					if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element citation = (Element) citations.item(i);
						if (!citation.getAttribute("valid").equals("true")) {
							continue;
						}
						NodeList authors = citation.getElementsByTagName("author");
						for (int j = 0; j < authors.getLength(); j++) {
							String author = authors.item(j).getTextContent().toUpperCase();
							if ((author.equals(AUTHOR_FULL_FORM)) || (author.equals(AUTHOR_SHORT_FORM))) {
								NodeList dates = citation.getElementsByTagName("date");
								for (int m = 0; m < dates.getLength(); m++) {
									if (!dates.item(m).hasAttributes()) {
										String date = dates.item(m).getTextContent();
										if (date != "") {
											int year = Integer.parseInt(date);
											if ((year >= START_YEAR) && (year <= END_YEAR)) {
												int x = year % 2000;
												numberOfCitedDocumentsYoshuaBengio[x]++;
											}
										}
									}
								}
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

		return numberOfCitedDocumentsYoshuaBengio;

	}

	// QUERY 9
	private static void findPerYear() {
		for (String filepath : XMLpathList) {
			if (filepath.contains("J14") || filepath.contains("W14") || filepath.contains("Q14")
					|| filepath.contains("D14"))
				try {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(filepath);
					NodeList citations = doc.getElementsByTagName("citation");
					for (int i = 0; i < citations.getLength(); i++) {
						if (citations.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element citation = (Element) citations.item(i);

							if (!citation.getAttribute("valid").equals("true")) {
								continue;
							}

							NodeList yearEl = citation.getElementsByTagName("date");
							if (yearEl.getLength() > 0) {
								String year = yearEl.item(0).getTextContent();
								if (year == "") {
									continue;
								}
								int y = Integer.parseInt(year) - 2000;
								if (y < 0 || y > 15) {
									continue;
								}
								String confName = filepath.split("/")[4];
								if (filepath.contains("J14"))
									numberOfCitedDocumentsPerYears[0][y]++;

								if (filepath.contains("J14"))
									numberOfCitedDocumentsPerYears[1][y]++;

								if (filepath.contains("J14"))
									numberOfCitedDocumentsPerYears[2][y]++;

								if (filepath.contains("J14"))
									numberOfCitedDocumentsPerYears[3][y]++;

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
