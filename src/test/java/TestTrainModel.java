import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class TestTrainModel {

	public static final void main(String[] args) {
		DoccatModel model = null;

		try(OutputStream os = new BufferedOutputStream(new FileOutputStream("./src/test/resources/trainedModel"))) {
			
			InputStreamFactory mfisf = new ResourceAsStreamFactory(TestTrainModel.class, "train-data.txt");

			ObjectStream<String> lineStream = new PlainTextByLineStream(mfisf, "UTF-8");

			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

			model = DocumentCategorizerME.train("en", sampleStream, 
												TrainingParameters.defaultParams(),
												new DoccatFactory());
			
			model.serialize(os);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ResourceAsStreamFactory implements InputStreamFactory {

	private Class<?> clazz;
	private String name;

	public ResourceAsStreamFactory(Class<?> clazz, String name) {
	    this.clazz = Objects.requireNonNull(clazz, "callz must not be null");
	    this.name = Objects.requireNonNull(name, "name must not be null");
	}

	@Override
	public InputStream createInputStream() throws IOException {
		return clazz.getResourceAsStream(name);
	}
}