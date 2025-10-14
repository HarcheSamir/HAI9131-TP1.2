package tp1_HAI913I;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import visitors.ClassVisitor;
import visitors.PackageVisitor;

public class MetricsContext {

    private List<CompilationUnit> compilationUnits = new ArrayList<>();
    
    private ClassVisitor classVisitor = new ClassVisitor();
    private PackageVisitor packageVisitor = new PackageVisitor();

    public void addCompilationUnit(CompilationUnit cu) {
        compilationUnits.add(cu);
        cu.accept(classVisitor);
        cu.accept(packageVisitor);
    }

    public ClassVisitor getClassVisitor() { return classVisitor; }
    public PackageVisitor getPackageVisitor() { return packageVisitor; }
    public List<CompilationUnit> getCompilationUnits() { return compilationUnits; }
}
