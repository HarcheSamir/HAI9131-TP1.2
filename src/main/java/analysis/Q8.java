package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.*;
import java.util.stream.Collectors;

public class Q8 {

    private final MetricsContext context;

    public Q8(MetricsContext context) {
        this.context = context;
    }

    public List<String> getTop10PercentClassesByMethods() {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();
        int totalClasses = classMethods.size();
        if (totalClasses == 0) return Collections.emptyList();

        List<Map.Entry<String, List<MethodDeclaration>>> sorted = classMethods.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toList());

        int topCount = Math.max(1, (int) Math.ceil(totalClasses * 0.1));
        List<Map.Entry<String, List<MethodDeclaration>>> topClasses = sorted.subList(0, topCount);

        System.out.println("10% des classes avec le plus grand nombre de méthodes et leurs méthodes:");
        for (Map.Entry<String, List<MethodDeclaration>> entry : topClasses) {
            System.out.println("Classe: " + entry.getKey());
            System.out.println("Méthodes:");
            for (MethodDeclaration method : entry.getValue()) {
                System.out.println("  - " + method.getName());
            }
            System.out.println(); 
        }

        return topClasses.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
