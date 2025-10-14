package tp1_HAI913I;


import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {
	
    public static CompilationUnit parse(char[] classSource) {
        ASTParser parser = ASTParser.newParser(AST.JLS4); 
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(classSource);
        parser.setResolveBindings(false);
        return (CompilationUnit) parser.createAST(null);
    }
	
}
