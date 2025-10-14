package analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Q2 {

    private List<File> javaFiles;

    public Q2(List<File> javaFiles) {
        this.javaFiles = javaFiles;
    }


    public int getTotalLines() {
        int total = 0;

        for (File file : javaFiles) {
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                int lineCount = lines.size();
                System.out.println(file.getName() + " has " + lineCount + " lines");
                total += lineCount;
            } catch (IOException e) {
                System.err.println("Cannot read file: " + file.getName());
            }
        }

        System.out.println("Total lines of code: " + total);
        return total;
    }
}
