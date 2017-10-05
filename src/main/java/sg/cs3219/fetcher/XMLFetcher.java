package sg.cs3219.fetcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sg.cs3219.app.extract.Loader;
import sg.cs3219.dataModel.BasePaper;
import sg.cs3219.dataModel.DataModel;
import sg.cs3219.dataModel.RefPaper;
import sg.cs3219.query.Query;

public class XMLFetcher implements Answerer{

	private Loader<Document> loader;
	private List<Document> docs;
	
	public XMLFetcher(Loader<Document> loader,Collection<String> srcs) {
		this.loader = loader;
		setSources(srcs);
	}
	
	public void setSources(Collection<String> srcs){
		docs = Objects.requireNonNull(loader.loadMultiple(srcs));
	}
	
	
	@Override
	public <T> String answer(Query<T> q) {
		
		T result = q.zero();
		for(Document doc : docs){
	         NodeList nList = doc.getElementsByTagName("algorithm");
	         
	         /*
	          * BASE PAPER
	          */
	         Map<String,String> attr = new HashMap<>();
	         if(nList.getLength()>1){
	        	 Node baseNode = nList.item(1);
	        	 if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element basePaper = (Element) baseNode;
	        		 for(String s: BasePaper.ATT){
	        			 NodeList att = basePaper.getElementsByTagName(s);
	        			 if(att.getLength()>0){
	        				 attr.put(s,att.item(0).getTextContent());
	        			 }
	        			 
	        		 }
	        	 }
	        	 
	         }
	         BasePaper base = new BasePaper(attr);
	         List<DataModel> data1 = Arrays.asList(base);
			 if(q.criteria(data1)){
				 result = q.combine(data1, result);
			 }
	         
	         /*
	          * References Papers
	          */
	         for (int temp = 2; temp < nList.getLength(); temp++) {
	        	 Node citNode =nList.item(temp);
	        	 if (citNode.getNodeType() == Node.ELEMENT_NODE) {
	        		 NodeList citations = ((Element) citNode).getElementsByTagName("citation");
	        		 

	        		 for(int i = 0; i<citations.getLength();i++){
	        			 if(citations.item(i).getNodeType() == Node.ELEMENT_NODE){
	        				 Map<String,String> attrRef = new HashMap<>();
	        				 Element citation = (Element) citations.item(i);
		        			 for(String s: RefPaper.ATT){
		        				 NodeList att = citation.getElementsByTagName(s);
			        			 if(att.getLength()>0){
			        				 attr.put(s,att.item(0).getTextContent());
			        			 }
			        			 
			        		 }
		        			 RefPaper ref = new RefPaper(attrRef);
		        			 List<DataModel> data = Arrays.asList(base,ref);
		        			 if(q.criteria(data)){
		        				 result = q.combine(data, result);
		        			 }
	        			 }

		        		 
	        		 }
	        		 
	        		 
	        	 }
	         }
			
		}
		return q.output(result);
	}

}
