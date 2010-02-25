package subobjectjava.eclipse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jnome.core.language.Java;

import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.language.Language;
import chameleon.core.namespace.RootNamespace;
import chameleon.editor.connector.Builder;
import chameleon.editor.connector.EclipseBootstrapper;
import chameleon.editor.connector.EclipseEditorExtension;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.output.Syntax;


public class Bootstrapper extends EclipseBootstrapper {
	
	public void registerFileExtensions() {
		addExtension("java");
	}
	
	public String getLanguageName() {
		return "JLow";
	}

	public String getLanguageVersion() {
		return "1.0";
	}

	public String getVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "Java with subobjects";
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
		try {
		  factory.initializeBase(new ArrayList<File>());
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setConnector(EclipseEditorExtension.class, new SubobjectJavaEditorExtension());
		return result;
	}
	
	public Builder createBuilder(Language source) {
		RootNamespace clone = source.defaultNamespace().clone();
		Java result = new Java();
		result.cloneConnectorsFrom(source);
		result.cloneProcessorsFrom(source);
		result.setDefaultNamespace(clone);
		return new JLowBuilder((SubobjectJava) source, result);
	}

}
