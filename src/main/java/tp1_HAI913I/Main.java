package tp1_HAI913I; // Or your correct package name

import java.io.File;
import java.util.List;
import java.util.Scanner;
import org.eclipse.jdt.core.dom.CompilationUnit;
import analysis.*;

public class Main {

    // --- HARDCODED PATH ---
    public static final String PROJECT_SOURCE_PATH = "C:\\HAI913I\\HARCHE_Samir_TP1\\src\\main\\java\\sample";

    private final MetricsContext context;
    private final List<File> javaFiles;

    public Main(String projectPath) {
        // ... (this constructor is unchanged)
        File projectDir = new File(projectPath);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.err.println("FATAL ERROR: The hardcoded project path is invalid or not a directory.");
            System.err.println("Path: " + projectPath);
            System.exit(1);
        }
        System.out.println("Analyzing project at: " + projectPath);
        this.javaFiles = Utils.listJavaFilesForFolder(projectDir);
        this.context = new MetricsContext();
        if (javaFiles.isEmpty()) {
            System.err.println("Warning: No .java files were found.");
        } else {
             System.out.println("Found " + javaFiles.size() + " Java files. Analysis context is ready.");
        }
        for (File file : javaFiles) {
            try {
                char[] source = Utils.readFileToCharArray(file);
                context.addCompilationUnit(Parser.parse(source));
            } catch (Exception e) {
                System.err.println("Warning: Could not parse file: " + file.getName());
            }
        }
    }
    
    // --- UPDATED MENU TEXT ---
    private void printMenu() {
        String border = "+-----------------------------------------+-----------------------------------------+";
        int totalWidth = 81;
        String titleText = "HAI913I - Java Code Analyzer";
        int titlePaddingSize = (totalWidth - titleText.length()) / 2;
        String titlePadding = " ".repeat(titlePaddingSize);
        String centeredTitle = titlePadding + titleText;
        String exitText = "0. Exit";
        int exitPaddingSize = (totalWidth - exitText.length()) / 2;
        String exitPadding = " ".repeat(exitPaddingSize);
        String centeredExit = exitPadding + exitText;

        System.out.println("\n" + border);
        System.out.printf("| %-81s |\n", centeredTitle);
        System.out.println(border);
        System.out.printf("| %-39s | %-39s |\n", "[COUNTS]", "[REPORTS]");
        System.out.printf("|   %-37s |   %-37s |\n", "1. Number of classes", "8. Top 10% classes (methods)");
        System.out.printf("|   %-37s |   %-37s |\n", "2. Number of lines of code", "9. Top 10% classes (attribs)");
        System.out.printf("|   %-37s |   %-37s |\n", "3. Number of methods", "10. Classes in both top 10%");
        System.out.printf("|   %-37s |   %-37s |\n", "4. Number of packages", "11. Classes > 5 methods");
        System.out.printf("| %-39s |   %-37s |\n", "", "12. Top 10% methods (lines)");
        System.out.printf("| %-39s |   %-37s |\n", "[AVERAGES]", "13. Method with max params");
        System.out.printf("|   %-37s | %-39s |\n", "5. Methods per class", "");
        System.out.printf("|   %-37s | %-39s |\n", "6. Lines of code per method", "[GRAPH]");
        // UPDATED TEXT FOR OPTION 14
        System.out.printf("|   %-37s |   %-37s |\n", "7. Attributes per class", "14. Generate and open call graph");
        System.out.println(border);
        System.out.printf("| %-81s |\n", centeredExit);
        System.out.println(border);
        System.out.print("Enter choice (0-14) -> ");
    }
    
    public void run() {
        // ... (this method is unchanged)
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nPress Enter to start the analyzer...");
            scanner.nextLine();
            int choice = -1;
            while (choice != 0) {
                printMenu();
                try {
                    String line = scanner.nextLine();
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("\n[ERROR] Invalid input. Please enter a number.");
                    choice = -1;
                }
                handleChoice(choice, scanner);
            }
            System.out.println("\nExiting application.");
        }
    }

    private void printResultBox(String title, Runnable analysisTask) {
        // ... (this method is unchanged)
        String border = "+-----------------------------------------------------------------------------------+";
        System.out.println(border);
        System.out.printf("| RESULTS FOR: %-63s |\n", title);
        System.out.println(border);
        analysisTask.run();
        System.out.println(border);
    }

    // --- UPDATED ACTION FOR CASE 14 ---
    private void handleChoice(int choice, Scanner scanner) {
        if (choice == 0) return;
        if (choice < 0 || choice > 14) {
            if (choice != -1) System.out.println("\n[ERROR] Invalid choice. Please select an option from 0 to 14.");
            System.out.println("\nPress Enter to return to the menu...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n----------------------------- RESULTS ------------------------------------");
        switch (choice) {
            case 1:  printResultBox("Number of Classes", () -> new Q1(context).getTotalClasses()); break;
            case 2:  printResultBox("Number of Lines of Code", () -> new Q2(javaFiles).getTotalLines()); break;
            case 3:  printResultBox("Number of Methods", () -> new Q3(context).getTotalMethods()); break;
            case 4:  printResultBox("Number of Packages", () -> new Q4(context).getTotalPackages()); break;
            case 5:  printResultBox("Average Methods per Class", () -> new Q5(context).getAverageMethodsPerClass()); break;
            case 6:  printResultBox("Average Lines of Code per Method", () -> new Q6(context).getAverageLinesPerMethod()); break;
            case 7:  printResultBox("Average Attributes per Class", () -> new Q7(context).getAverageAttributesPerClass()); break;
            case 8:  printResultBox("Top 10% Classes by Methods", () -> new Q8(context).getTop10PercentClassesByMethods()); break;
            case 9:  printResultBox("Top 10% Classes by Attributes", () -> new Q9(context).getTop10PercentClassesByAttributes()); break;
            case 10: printResultBox("Classes in Both Top 10% Categories", () -> new Q10(context).getClassesInBothTopCategories()); break;
            case 11: printResultBox("Classes with More Than 5 Methods", () -> new Q11(context).getClassesWithMoreThanXMethods(5)); break;
            case 12: printResultBox("Top 10% Methods by Lines of Code", () -> new Q12(context).getTop10PercentMethodsByLines()); break;
            case 13: printResultBox("Method with Maximum Parameters", () -> new Q13(context).getMaxParameters()); break;
            // UPDATED ACTION FOR CASE 14
            case 14:
                new CallGraphBuilder(context).generateAndOpenGraph();
                break;
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Main analyzer = new Main(PROJECT_SOURCE_PATH);
        analyzer.run();
    }
}