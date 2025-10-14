package analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tp1_HAI913I.MetricsContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Q11 {

    private final MetricsContext context;

    public Q11(MetricsContext context) {
        this.context = context;
    }

    public List<String> getClassesWithMoreThanXMethods(int x) {
        Map<String, List<MethodDeclaration>> classMethods = context.getClassVisitor().getClassMethods();

        
        List<String> result = classMethods.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > x)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Classes qui possèdent plus de " + x + " méthodes:");
        for (String cls : result) {
            System.out.println("Classe: " + cls + " (Nombre de méthodes: " + classMethods.get(cls).size() + ")");
        }

        return result;
    }
}
