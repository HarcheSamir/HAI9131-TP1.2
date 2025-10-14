package analysis;

import java.util.List;
import java.util.stream.Collectors;

import tp1_HAI913I.MetricsContext;

public class Q10 {

    private final MetricsContext context;

    public Q10(MetricsContext context) {
        this.context = context;
    }

    public List<String> getClassesInBothTopCategories() {
        // Get top 10% classes by methods
        Q8 question8 = new Q8(context);
        List<String> topMethodClasses = question8.getTop10PercentClassesByMethods();

        // Get top 10% classes by attributes
        Q9 question9 = new Q9(context);
        List<String> topAttributeClasses = question9.getTop10PercentClassesByAttributes();

        List<String> intersection = topMethodClasses.stream()
                .filter(topAttributeClasses::contains)
                .collect(Collectors.toList());

        // Print result
        System.out.println("Classes qui font partie des deux catégories (top 10% méthodes et attributs):");
        intersection.forEach(cls -> System.out.println("Classe: " + cls));

        return intersection;
    }
}
