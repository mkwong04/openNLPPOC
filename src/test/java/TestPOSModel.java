import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class TestPOSModel{
	
	public static final void main(String[] args){
		
		try(InputStream is = TestPOSModel.class.getResourceAsStream("en-pos-maxent.bin")){
								
			POSModel m = new POSModel(is);
			
			POSTaggerME myPOSTagger = new POSTaggerME(m);
			System.out.println(args[0]);
			
			String[] sent = args[0].split(" ");

			String tags[] = myPOSTagger.tag(sent);
			double probs[] = myPOSTagger.probs();

			for(int idx=0; idx<tags.length; idx++){
				System.out.println(tags[idx]+" : "+probs[idx]);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}