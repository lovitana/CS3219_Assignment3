package sg.cs3219.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.cs3219.app.extract.XmlLoader;
import sg.cs3219.fetcher.XMLFetcher;
import sg.cs3219.query.FunctionnalQuery;
import sg.cs3219.query.Query;

public class Test {

	public static void main(String[] args) {
		try{
			File resources = new File("src/main/resources");
			File[] paths = resources.listFiles();
			List<String> files = new ArrayList<>();
			for(File path:paths){
				File[] ps = path.listFiles();
				for(File p:ps){
					for(String s: p.list()){
						files.add(p.getAbsolutePath()+ "/"+s);
					}
				}
			}
			

			XmlLoader loader = new XmlLoader();
			FunctionnalQuery.Criteria crit = x -> true;
			FunctionnalQuery.Combiner<Integer> comb = (l,r)->{
				if(l.size()==2){
					return r+1;
				}
				return r;
			};
			FunctionnalQuery.Output<Integer> out = r->{
				return r.toString();
			};
			
			Query<Integer> query = new FunctionnalQuery<>(0,true, crit, comb, out);
			
			XMLFetcher fetcher = new XMLFetcher(loader,files);
			System.out.println("There is " + fetcher.answer(query)+ " Documents");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
