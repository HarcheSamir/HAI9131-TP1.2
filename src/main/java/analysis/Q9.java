package analysis;

import java.util.*;
import java.util.stream.Collectors;

import tp1_HAI913I.MetricsContext;

public class Q9 {

    private final MetricsContext context;

    public Q9(MetricsContext context) {
        this.context = context;
    }

    public List<String> getTop10PercentClassesByAttributes() {
        Map<String, List<String>> classAttributes = context.getClassVisitor().getClassAttributes();
        int totalClasses = classAttributes.size();
        if (totalClasses == 0) return Collections.emptyList();

        List<Map.Entry<String, List<String>>> sorted = classAttributes.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toList());

        int topCount = Math.max(1, (int) Math.ceil(totalClasses * 0.1));
        List<Map.Entry<String, List<String>>> topClasses = sorted.subList(0, topCount);

        System.out.println("10% des classes avec le plus grand nombre d’attributs (trié par nombre d'attributs):");
        for (Map.Entry<String, List<String>> entry : topClasses) {
            System.out.println("Classe: " + entry.getKey() + " (Nombre d'attributs: " + entry.getValue().size() + ")");
            System.out.println("Attributs:");
            for (String attr : entry.getValue()) {
                System.out.println("  - " + attr);
            }
            System.out.println(); 
        }

        return topClasses.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
