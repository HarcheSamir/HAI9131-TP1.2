package analysis;

import tp1_HAI913I.MetricsContext; 
import visitors.ClassVisitor;
import visitors.MethodVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.awt.Desktop; // Used to open the final image
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CallGraphBuilder {

    private final MetricsContext context;
    private final Map<String, Set<String>> callGraph = new HashMap<>();

    public CallGraphBuilder(MetricsContext context) {
        this.context = context;
    }

    /**
     * Builds the internal representation of the call graph. This must be called first.
     */
    public void buildCallGraph() {
        if (!callGraph.isEmpty()) return; // Avoid rebuilding

        ClassVisitor classVisitor = context.getClassVisitor();
        Map<String, List<MethodDeclaration>> classMethods = classVisitor.getClassMethods();
        Map<String, List<String>> methodLookup = new HashMap<>();

        for (Map.Entry<String, List<MethodDeclaration>> entry : classMethods.entrySet()) {
            String className = entry.getKey();
            for (MethodDeclaration method : entry.getValue()) {
                String fullMethodName = className + "." + method.getName().toString();
                methodLookup.computeIfAbsent(method.getName().toString(), k -> new ArrayList<>()).add(fullMethodName);
            }
        }

        for (Map.Entry<String, List<MethodDeclaration>> entry : classMethods.entrySet()) {
            String className = entry.getKey();
            for (MethodDeclaration method : entry.getValue()) {
                String fullMethodName = className + "." + method.getName().toString();
                MethodVisitor methodVisitor = new MethodVisitor(method);
                method.accept(methodVisitor);

                Set<String> fullCallees = new HashSet<>();
                for (String calleeName : methodVisitor.getInvokedMethodNames()) {
                    List<String> possible = methodLookup.get(calleeName);
                    if (possible != null && !possible.isEmpty()) {
                        // This simple logic just takes the first match. More complex logic could handle polymorphism.
                        fullCallees.add(possible.get(0));
                    } else {
                        // Could be a call to a library method, e.g., System.out.println
                         fullCallees.add(calleeName); 
                    }
                }
                callGraph.put(fullMethodName, fullCallees);
            }
        }
    }

    /**
     * Generates a Graphviz DOT file, converts it to a PNG image, and opens the image.
     */
    public void generateAndOpenGraph() {
        // Step 1: Ensure the graph is built
        buildCallGraph();

        // Step 2: Generate DOT language code from the graph data
        StringBuilder dot = new StringBuilder();
        dot.append("digraph CallGraph {\n");
        dot.append("    rankdir=LR; // Layout from Left to Right\n");
        dot.append("    node [shape=box, style=rounded, fontname=\"Helvetica\"];\n");
        dot.append("    edge [fontname=\"Helvetica\"];\n\n");

        for (Map.Entry<String, Set<String>> entry : callGraph.entrySet()) {
            String caller = entry.getKey();
            if (entry.getValue().isEmpty()) {
                dot.append("    \"").append(caller).append("\";\n"); // Ensure nodes with no calls are drawn
            } else {
                for (String callee : entry.getValue()) {
                    dot.append("    \"").append(caller).append("\" -> \"").append(callee).append("\";\n");
                }
            }
        }
        dot.append("}");

        // Step 3: Write the DOT code to a temporary file
        File dotFile = null;
        try {
            dotFile = File.createTempFile("callgraph", ".dot");
            try (FileWriter writer = new FileWriter(dotFile)) {
                writer.write(dot.toString());
            }
        } catch (IOException e) {
            System.err.println("Error: Could not create temporary DOT file.");
            e.printStackTrace();
            return;
        }

        // Step 4: Use Graphviz 'dot' command to convert the .dot file to a .png image
        File pngFile = new File(dotFile.getParent(), "callgraph.png");
        System.out.println("Generating graph image... Please wait.");
        
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "dot",
                "-Tpng",
                dotFile.getAbsolutePath(),
                "-o",
                pngFile.getAbsolutePath()
            );
            Process process = pb.start();
            int exitCode = process.waitFor(); // Wait for the command to finish

            if (exitCode != 0) {
                System.err.println("Error: Graphviz 'dot' command failed with exit code " + exitCode);
                System.err.println("Please ensure Graphviz is installed and the 'bin' directory is in your system's PATH.");
                return;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("\n--- CRITICAL ERROR ---");
            System.err.println("Could not run the Graphviz 'dot' command.");
            System.err.println("Please ensure Graphviz is correctly installed and that its 'bin' directory is included in your system's PATH environment variable.");
            System.err.println("----------------------\n");
            return;
        }

        // Step 5: Open the generated PNG file with the default image viewer
        try {
            if (Desktop.isDesktopSupported()) {
                System.out.println("Opening generated graph: " + pngFile.getAbsolutePath());
                Desktop.getDesktop().open(pngFile);
            } else {
                System.out.println("Desktop is not supported. Please open the graph manually: " + pngFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error: Could not open the generated image file.");
            System.err.println("You can find it at: " + pngFile.getAbsolutePath());
        } finally {
            dotFile.deleteOnExit(); // Clean up the temporary .dot file
        }
    }
}