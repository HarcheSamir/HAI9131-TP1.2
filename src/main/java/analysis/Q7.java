package analysis;

import java.util.List;
import java.util.Map;

import tp1_HAI913I.MetricsContext;

public class Q7 {

    private final MetricsContext context;

    public Q7(MetricsContext context) {
        this.context = context;
    }

    public double getAverageAttributesPerClass() {
        Map<String, List<String>> classAttributes = context.getClassVisitor().getClassAttributes();
        int totalClasses = classAttributes.size();
        if (totalClasses == 0) return 0;

        int totalAttributes = classAttributes.values().stream()
                .mapToInt(List::size)
                .sum();

        double avgAttributesPerClass = (double) totalAttributes / totalClasses;
        System.out.println("Nombre moyen d’attributs par classe: " + avgAttributesPerClass);
        return avgAttributesPerClass;
    }
}
