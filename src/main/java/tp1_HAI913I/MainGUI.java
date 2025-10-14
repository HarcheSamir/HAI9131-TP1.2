package tp1_HAI913I; // Make sure this matches your package name

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import javax.swing.*;

import analysis.*; // Imports all your analysis classes

public class MainGUI {

    // --- HARDCODED PATH ---
    public static final String PROJECT_SOURCE_PATH = "C:\\HAI913I\\HARCHE_Samir_TP1\\src\\main\\java\\sample";

    // GUI Components
    private JFrame frame;
    private JTextArea resultsArea;
    private JComboBox<String> analysisSelector;
    private JLabel statusLabel;

    // Analysis context
    private MetricsContext context;
    private List<File> javaFiles;

    public MainGUI() {
        prepareAnalysis();
        setupUI();
    }

    private void prepareAnalysis() {
        System.out.println("GUI starting... Parsing source files, please wait.");
        File projectDir = new File(PROJECT_SOURCE_PATH);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            JOptionPane.showMessageDialog(null, "FATAL ERROR: The project path is invalid.\nPlease check the PROJECT_SOURCE_PATH in MainGUI.java.", "Path Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        this.javaFiles = Utils.listJavaFilesForFolder(projectDir);
        this.context = new MetricsContext();

        for (File file : javaFiles) {
            try {
                char[] source = Utils.readFileToCharArray(file);
                context.addCompilationUnit(Parser.parse(source));
            } catch (Exception e) {
                System.err.println("Warning: Could not parse file: " + file.getName());
            }
        }
        System.out.println("Analysis context is ready.");
    }

    private void setupUI() {
        frame = new JFrame("HAI913I - Interactive Java Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // --- CORRECTED TEXT FOR ITEM 14 ---
        String[] analyses = {
            "-- Select an Analysis --",
            "1. Number of classes",
            "2. Number of lines of code",
            "3. Number of methods",
            "4. Number of packages",
            "5. Average methods per class",
            "6. Average lines of code per method",
            "7. Average attributes per class",
            "8. Top 10% classes by methods",
            "9. Top 10% classes by attributes",
            "10. Classes in both top 10% categories",
            "11. Classes with more than 5 methods",
            "12. Top 10% methods by lines",
            "13. Method with max parameters",
            "14. Generate and open call graph" // Corrected text
        };
        analysisSelector = new JComboBox<>(analyses);
        
        topPanel.add(new JLabel("Choose an analysis to run:"));
        topPanel.add(analysisSelector);

        resultsArea = new JTextArea("Welcome!\nPlease select an analysis from the dropdown menu above to begin.");
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultsArea.setEditable(false);
        resultsArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        statusLabel = new JLabel("  Ready. Analyzing project: " + PROJECT_SOURCE_PATH);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());

        analysisSelector.addActionListener(e -> {
            int selectedIndex = analysisSelector.getSelectedIndex();
            if (selectedIndex > 0) {
                runSelectedAnalysis(selectedIndex);
            }
        });

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void runSelectedAnalysis(int index) {
        statusLabel.setText("  Running analysis: " + analysisSelector.getSelectedItem().toString());
        resultsArea.setText("Please wait, generating result...");
        
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                // --- CORRECTED ACTION FOR CASE 14 ---
                switch (index) {
                    case 1: return captureOutput(() -> new Q1(context).getTotalClasses());
                    case 2: return captureOutput(() -> new Q2(javaFiles).getTotalLines());
                    case 3: return captureOutput(() -> new Q3(context).getTotalMethods());
                    case 4: return captureOutput(() -> new Q4(context).getTotalPackages());
                    case 5: return captureOutput(() -> new Q5(context).getAverageMethodsPerClass());
                    case 6: return captureOutput(() -> new Q6(context).getAverageLinesPerMethod());
                    case 7: return captureOutput(() -> new Q7(context).getAverageAttributesPerClass());
                    case 8: return captureOutput(() -> new Q8(context).getTop10PercentClassesByMethods());
                    case 9: return captureOutput(() -> new Q9(context).getTop10PercentClassesByAttributes());
                    case 10: return captureOutput(() -> new Q10(context).getClassesInBothTopCategories());
                    case 11: return captureOutput(() -> new Q11(context).getClassesWithMoreThanXMethods(5));
                    case 12: return captureOutput(() -> new Q12(context).getTop10PercentMethodsByLines());
                    case 13: return captureOutput(() -> new Q13(context).getMaxParameters());
                    case 14: 
                        // This case is now handled differently to provide better feedback.
                        CallGraphBuilder builder = new CallGraphBuilder(context);
                        builder.generateAndOpenGraph(); // This opens the image externally.
                        
                        // Return a confirmation message to display in the GUI.
                        return "Graph generation process started.\n\n"
                             + "The graph should open in your default image viewer.\n\n"
                             + "If it does not appear, please ensure that Graphviz is installed and\n"
                             + "that its 'bin' directory is in your system's PATH environment variable.";
                    default: return "Invalid selection.";
                }
            }

            @Override
            protected void done() {
                try {
                    resultsArea.setText(get());
                    resultsArea.setCaretPosition(0);
                    statusLabel.setText("  Done. Result for '" + analysisSelector.getSelectedItem() + "' is displayed.");
                } catch (Exception e) {
                    resultsArea.setText("An error occurred during analysis:\n" + e.getMessage());
                    statusLabel.setText("  Error.");
                }
            }
        };
        worker.execute();
    }

    private String captureOutput(Runnable analysisTask) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            analysisTask.run();
        } finally {
            System.setOut(originalOut);
        }
        return baos.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}