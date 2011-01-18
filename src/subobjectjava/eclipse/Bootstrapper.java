package subobjectjava.eclipse;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import jnome.core.language.Java;
import jnome.output.JavaCodeWriter;
import subobjectjava.build.JLoBuilder;
import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.language.Language;
import chameleon.editor.LanguageMgt;
import chameleon.editor.connector.EclipseBootstrapper;
import chameleon.editor.connector.EclipseEditorExtension;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.plugin.build.Builder;
import chameleon.plugin.output.Syntax;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlow";
	
	public void registerFileExtensions() {
//		addExtension("java"); This causes problems with the generated files after a refresh. Until
//		                      we have a source path, I will simply rename the API files to .jlow
		addExtension("jlow");
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
			FilenameFilter filter = LanguageMgt.fileNameFilter(".jlow");
			URL directory = LanguageMgt.pluginURL(PLUGIN_ID, "api/");
			List<File> files = LanguageMgt.allFiles(directory, filter);
			System.out.println("Loading "+files.size()+" API files.");
		  factory.initializeBase(files);
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setPlugin(EclipseEditorExtension.class, new SubobjectJavaEditorExtension());
		result.setPlugin(Syntax.class, new JavaCodeWriter());
		return result;
	}
	
	public Builder createBuilder(Language source, File projectDirectory) {
//		RootNamespace clone = source.defaultNamespace().clone();
//		result.cloneConnectorsFrom(source);
//		result.cloneProcessorsFrom(source);
//		result.setDefaultNamespace(clone);
		File outputDirectory = new File(projectDirectory.getAbsolutePath()+File.separator+"java");
		return new JLoBuilder((SubobjectJava) source, outputDirectory);
	}

}
