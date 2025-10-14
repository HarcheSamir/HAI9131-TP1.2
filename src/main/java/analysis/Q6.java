package analysis;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.List;
import java.util.Map;

public class Q6 {

    private final MetricsContext context;

    public Q6(MetricsContext context) {
        this.context = context;
    }

    public double getAverageLinesPerMethod() {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();
        List<CompilationUnit> compilationUnits = context.getCompilationUnits();

        int totalMethods = 0;
        int totalLines = 0;

        for (CompilationUnit cu : compilationUnits) {
            for (List<MethodDeclaration> methods : classMethods.values()) {
                for (MethodDeclaration method : methods) {
                    if (method.getBody() != null) {
                        int startLine = cu.getLineNumber(method.getStartPosition());
                        int endLine = cu.getLineNumber(method.getStartPosition() + method.getLength());
                        totalLines += (endLine - startLine + 1);
                        totalMethods++;
                    }
                }
            }
        }

        double avgLinesPerMethod = totalMethods == 0 ? 0 : (double) totalLines / totalMethods;
        System.out.println("Nombre moyen de lignes de code par méthode: " + avgLinesPerMethod);
        return avgLinesPerMethod;
    }
}
