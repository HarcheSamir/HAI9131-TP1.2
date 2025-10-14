package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.List;
import java.util.Map;

public class Q13 {

    private final MetricsContext context;

    public Q13(MetricsContext context) {
        this.context = context;
    }

    public void getMaxParameters() {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();

        int maxParams = 0;
        String classNameWithMax = "";
        String methodNameWithMax = "";

        for (Map.Entry<String, List<MethodDeclaration>> entry : classMethods.entrySet()) {
            String className = entry.getKey();
            List<MethodDeclaration> methods = entry.getValue();

            for (MethodDeclaration method : methods) {
                int paramCount = method.parameters().size();
                if (paramCount > maxParams) {
                    maxParams = paramCount;
                    classNameWithMax = className;
                    methodNameWithMax = method.getName().toString();
                }
            }
        }

        System.out.println("Nombre maximal de paramètres parmi toutes les méthodes: " + maxParams);
        System.out.println("Méthode avec ce nombre maximal: " + methodNameWithMax + " (Classe: " + classNameWithMax + ")");
    }
}
