package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.List;
import java.util.Map;

public class Q5 {

    private final MetricsContext context;

    public Q5(MetricsContext context) {
        this.context = context;
    }

    public double getAverageMethodsPerClass() {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();
        int totalClasses = classMethods.size();
        if (totalClasses == 0) return 0;

        int totalMethods = classMethods.values().stream()
                .mapToInt(List::size)
                .sum();

        double  avgMethodsPerClass = (double) totalMethods / totalClasses;
        
        System.out.println("Nombre moyen de méthodes par classe: " + avgMethodsPerClass);
       
        return avgMethodsPerClass;
    }
    
}