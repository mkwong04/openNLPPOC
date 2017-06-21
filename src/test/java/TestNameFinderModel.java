import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class TestNameFinderModel{
	
	public static final void main(String[] args){
		
		try(InputStream nfis = TestNameFinderModel.class.getResourceAsStream("en-ner-location.bin");
			InputStream tkis = TestNameFinderModel.class.getResourceAsStream("en-token.bin");){
			
			TokenizerModel tm = new TokenizerModel(tkis);
			TokenizerME myTokenizer = new TokenizerME(tm);
			
			System.out.println(args[0]);
			
			String[] tokens = myTokenizer.tokenize(args[0]);
								
			TokenNameFinderModel m = new TokenNameFinderModel(nfis);
			
			NameFinderME myNameFinder = new NameFinderME(m);
			
			Span[] spans= myNameFinder.find(tokens);
			

			for(int idx=0; idx<spans.length; idx++){
				System.out.println(spans[idx].getType()+" : "+spans[idx].getProb() + " : "+tokens[spans[idx].getStart()] );
			}
			myNameFinder.clearAdaptiveData();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}