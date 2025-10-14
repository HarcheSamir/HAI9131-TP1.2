package tp1_HAI913I;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
	
	public static ArrayList<File> listJavaFilesForFolder(File folder){
		ArrayList<File> javaFiles = new ArrayList<File>();
		
		for(File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if(fileEntry.getName().contains(".java")) {
				javaFiles.add(fileEntry);
			}
		}
		
		return javaFiles;
	}
	
    public static char[] readFileToCharArray(File file) throws IOException {
        FileReader fr = new FileReader(file);
        char[] buffer = new char[(int) file.length()];
        fr.read(buffer);
        fr.close();
        return buffer;
    }

}
