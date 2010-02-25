package subobjectjava.eclipse;

import jnome.core.language.Java;
import subobjectjava.model.language.SubobjectJava;
import subobjectjava.translate.IncrementalJavaTranslator;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.language.Language;
import chameleon.editor.connector.Builder;
import chameleon.exception.ModelException;

public class JLowBuilder extends Builder {
	
	public JLowBuilder(SubobjectJava source, Java target) {
		_translator = new IncrementalJavaTranslator(source, target);
	}

	@Override
	public void build(CompilationUnit compilationUnit) throws ModelException {
		_translator.build(compilationUnit);
	}

	public Language targetLanguage() {
		return _translator.targetLanguage();
	}

	public Language sourceLanguage() {
		return _translator.sourceLanguage();
	}

	private IncrementalJavaTranslator _translator;
}
