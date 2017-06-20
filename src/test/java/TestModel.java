import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class TestModel{
	
	public static final void main(String[] args){
		
		try(InputStream is = new BufferedInputStream(new FileInputStream("trainedModel"))){
								
			DoccatModel m = new DoccatModel(is);
			
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
			System.out.println(args[0]);

			double[] outcomes = myCategorizer.categorize(args);
			System.out.println(String.valueOf(outcomes));
			
			String allResults = myCategorizer.getAllResults(outcomes);
			
			System.out.println(allResults);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}