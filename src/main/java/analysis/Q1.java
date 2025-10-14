package analysis;

import java.util.List;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tp1_HAI913I.MetricsContext;

public class Q1 {

    private MetricsContext context;

    public Q1(MetricsContext context) {
        this.context = context;
    }

    public int getTotalClasses() {
        List<TypeDeclaration> classes = context.getPackageVisitor().getClasses();
        System.out.println("Total classes in the application: " + classes.size());

        int index = 1;
        for (TypeDeclaration cls : classes) {
            System.out.println(index + ". " + cls.getName());
            index++;
        }

        return classes.size();
    }
}
