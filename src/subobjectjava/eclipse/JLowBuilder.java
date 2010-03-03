package subobjectjava.eclipse;

import java.io.File;
import java.io.IOException;

import jnome.core.language.Java;
import subobjectjava.model.language.SubobjectJava;
import subobjectjava.translate.CompilationUnitWriter;
import subobjectjava.translate.IncrementalJavaTranslator;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.language.Language;
import chameleon.editor.connector.Builder;
import chameleon.exception.ModelException;

public class JLowBuilder extends Builder {
	
	public JLowBuilder(SubobjectJava source, Java target, File outputDir) {
		_translator = new IncrementalJavaTranslator(source, target);
		_writer = new CompilationUnitWriter(outputDir);
	}
	
	CompilationUnitWriter _writer;
	
	@Override
	public void build(CompilationUnit compilationUnit) throws ModelException, IOException {
		try {
		CompilationUnit translated = _translator.build(compilationUnit);
		_writer.write(translated);
		} catch(Error e) {
			e.printStackTrace();
			throw e;
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
	}

	public Language targetLanguage() {
		return _translator.targetLanguage();
	}

	public Language sourceLanguage() {
		return _translator.sourceLanguage();
	}

	private IncrementalJavaTranslator _translator;
}
