package darth.jminas.errors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import darth.jminas.Variables;

public class LoadErrorChecker {
	public static boolean flagErrorImagenes = false;

	public void CreateLog() {
		try {
			PrintWriter pw = new PrintWriter(new File("JMinas_errorlog.txt"));
			writeFile(pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null,
				"No se pudieron cargar algunas imagenes,\n" +
						"se creo el archivo JMinas_errorlog.txt con\n" +
						"informacion al respecto",
						"ERROR",
						JOptionPane.ERROR_MESSAGE);
	}
	
	private void writeFile(PrintWriter pw) {
		pw.println("******************************************************************************");
		pw.println("**                            REVISANDO IMAGENES                            **");
		pw.println("** Si no encuentra algun archivo, revisa que exista o que este bien escrito **");
		pw.println("******************************************************************************\n");
		File f = new File(getClass().getClassLoader().getResource("").getFile() + "img");

		if(f.exists()) {
			File[] file = f.listFiles();
			boolean flag = false;
			int cont = 0;
			for (int i = 0; i < Variables.archivos.length; i++) {
				flag = false;
				for (int j = 0; j < file.length; j++) {
					if(Variables.archivos[i].equals(file[j].getName())) {
						flag = true;
						break;
					}
				}
				if(flag)
					continue;
				pw.println(++cont + ": No se encuentra el archivo \""+ Variables.archivos[i] +"\"");
			}
		}else
			pw.println("La carpeta de imagenes (\"img\") no se encuentra");

		pw.close();
	}
}
