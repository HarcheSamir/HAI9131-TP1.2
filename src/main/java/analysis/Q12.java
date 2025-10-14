package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.*;
import java.util.stream.Collectors;

public class Q12 {

    private final MetricsContext context;

    public Q12(MetricsContext context) {
        this.context = context;
    }

    public Map<String, List<String>> getTop10PercentMethodsByLines() {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();
        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<String, List<MethodDeclaration>> entry : classMethods.entrySet()) {
            String className = entry.getKey();
            List<MethodDeclaration> methods = entry.getValue();

            if (methods.isEmpty()) continue;

            // Map method name -> number of lines
            Map<String, Integer> methodLines = new HashMap<>();
            for (MethodDeclaration m : methods) {
                if (m.getBody() != null) {
                    int lines = m.getBody().toString().split("\r?\n").length;
                    methodLines.put(m.getName().toString(), lines);
                }
            }

            // Sort by number of lines descending
            List<Map.Entry<String, Integer>> sortedMethods = methodLines.entrySet()
                    .stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                    .collect(Collectors.toList());

            // Top 10% of methods per class
            int topCount = Math.max(1, (int) Math.ceil(sortedMethods.size() * 0.1));
            List<String> topMethodNames = sortedMethods.subList(0, topCount)
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            result.put(className, topMethodNames);

            // Print nicely
            System.out.println("Classe: " + className);
            System.out.println("10% des méthodes avec le plus grand nombre de lignes:");
            for (String methodName : topMethodNames) {
                System.out.println("  - " + methodName + " (" + methodLines.get(methodName) + " lignes)");
            }
            System.out.println();
        }

        return result;
    }
}
