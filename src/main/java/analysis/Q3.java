package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;
import visitors.ClassVisitor;

public class Q3 {

    private MetricsContext context;

    public Q3(MetricsContext context) {
        this.context = context;
    }


    public int getTotalMethods() {
        ClassVisitor classVisitor = context.getClassVisitor();

        int totalMethods = 0;
        for (var entry : classVisitor.getClassMethods().entrySet()) {
            String className = entry.getKey();
            var methods = entry.getValue();
            totalMethods += methods.size();

            System.out.println("Class: " + className + " has " + methods.size() + " methods.");
            for (MethodDeclaration method : methods) {
                System.out.println("  - " + method.getName());
            }
        }

        System.out.println("Total number of methods in the application: " + totalMethods);
        return totalMethods;
    }
}