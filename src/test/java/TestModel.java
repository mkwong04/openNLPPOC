import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class TestModel{
	
	public static final void main(String[] args){
		
		try(InputStream is = TestModel.class.getResourceAsStream("trainedModel")){
								
			DoccatModel m = new DoccatModel(is);
			
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
			System.out.println(args[0]);

			double[] outcomes = myCategorizer.categorize(args[0].split(" "));
			System.out.println(String.valueOf(outcomes));
			
			String allResults = myCategorizer.getAllResults(outcomes);
			
			System.out.println(allResults);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}