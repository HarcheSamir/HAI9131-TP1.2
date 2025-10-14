package visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodVisitor extends ASTVisitor {

    private final MethodDeclaration method;
    private final List<MethodInvocation> invocations = new ArrayList<>();

    public MethodVisitor(MethodDeclaration method) {
        this.method = method;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        invocations.add(node);
        return super.visit(node);
    }

    public Set<String> getInvokedMethodNames() {
        Set<String> names = new HashSet<>();
        for (MethodInvocation mi : invocations) names.add(mi.getName().toString());
        return names;
    }

    public String getReturnType() {
        return method.getReturnType2() != null ? method.getReturnType2().toString() : "void";
    }
}