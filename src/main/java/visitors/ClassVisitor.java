package visitors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ClassVisitor extends ASTVisitor {

    private final Map<String, List<MethodDeclaration>> classMethods = new HashMap<>();
    private final Map<String, List<String>> classAttributes = new HashMap<>();

    @Override
    public boolean visit(TypeDeclaration node) {
        if (!node.isInterface()) {
            classMethods.put(node.getName().toString(), Arrays.asList(node.getMethods()));

            List<String> fields = new ArrayList<>();
            for (FieldDeclaration f : node.getFields()) {
                for (Object frag : f.fragments()) {
                    VariableDeclarationFragment vdf = (VariableDeclarationFragment) frag;
                    fields.add(vdf.getName().toString());
                }
            }
            classAttributes.put(node.getName().toString(), fields);
        }
        return super.visit(node);
    }

    public Map<String, List<MethodDeclaration>> getClassMethods() {
        return classMethods;
    }

    public Map<String, List<String>> getClassAttributes() {
        return classAttributes;
    }
}