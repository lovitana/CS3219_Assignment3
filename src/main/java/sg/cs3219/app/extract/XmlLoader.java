package sg.cs3219.app.extract;

import java.io.File;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public final class XmlLoader implements Loader<Document> {

	@Override
	public Document loadFile(String src) {
		try {
	         File inputFile = new File(src);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         Objects.requireNonNull(doc);
	         return doc;
		} catch (Exception e) {
	         throw new IllegalArgumentException("Couldn't load the file "+ src);
	    }
	}

}
