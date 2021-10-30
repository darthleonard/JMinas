package darth.jminas.errors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ErrorReporter {
	private String errorLogFileName = "JMinas_errorlog.txt";

	public void CreateLog(String path) {
		File errorLogFile = new File(errorLogFileName);
		try {
			if(!errorLogFile.exists()) {
				printFileHeader(errorLogFile);
			}
			printPathError(errorLogFile, path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void LimpiaErrores() {
		File f = new File(errorLogFileName);
		if(f.exists()) {
			f.delete();
		}
	}
	
	public boolean ExistenErrores() {
		return new File(errorLogFileName).exists();
	}
	
	private void printFileHeader(File errorLogFile) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(errorLogFile, true));
		pw.println("***************************************************************************");
		pw.println("**                               ERROR LOG                               **");
		pw.println("** Revisa que los archivos en la lista existan o que esten bien escritos **");
		pw.println("***************************************************************************\n");
		pw.close();
	}
	
	private void printPathError(File errorLogFile, String path) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(errorLogFile, true));
		pw.append(path + "\n");
		pw.close();
	}
}
