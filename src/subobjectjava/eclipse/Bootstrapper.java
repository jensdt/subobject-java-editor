package subobjectjava.eclipse;

import java.io.IOException;

import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.language.Language;
import chameleon.editor.connector.EclipseBootstrapper;
import chameleon.editor.connector.EclipseEditorExtension;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.output.Syntax;


public class Bootstrapper implements EclipseBootstrapper {
	
	public String getLanguageName() {
		return "Subobject Java";
	}

	public String getLanguageVersion() {
		return "1.0";
	}

	public String getVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "Jnome: a Chameleon model for Java";
	}

	public String getLicense() {
		return "";
	}

	public Syntax getCodeWriter() {
		return null;
	}

	public Language createLanguage() throws IOException, ParseException {
		SubobjectJava result = new SubobjectJava();
		ModelFactory factory = new SubobjectJavaModelFactory(result);
		factory.setLanguage(result, ModelFactory.class);
		result.setConnector(EclipseEditorExtension.class, new SubobjectJavaEditorExtension());
		return result;
	}

}