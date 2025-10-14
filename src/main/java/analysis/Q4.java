package analysis;

import org.eclipse.jdt.core.dom.CompilationUnit;

import tp1_HAI913I.MetricsContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q4 {

    private final MetricsContext context;

    public Q4(MetricsContext context) {
        this.context = context;
    }

    public int getTotalPackages() {
        Set<String> packages = new HashSet<>();
        List<CompilationUnit> compilationUnits = context.getCompilationUnits();

        for (CompilationUnit cu : compilationUnits) {
            if (cu.getPackage() != null && cu.getPackage().getName() != null) {
                packages.add(cu.getPackage().getName().toString());
            }
        }

        System.out.println("=== Liste des packages détectés ===");
        if (packages.isEmpty()) {
            System.out.println("Aucun package trouvé.");
        } else {
            for (String pkg : packages) {
                System.out.println("- " + pkg);
            }
        }

        System.out.println("\nNombre total de packages : " + packages.size());
        return packages.size();
    }
}
