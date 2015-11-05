import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DatenzugriffCSV implements iDatenzugriff {
	private BufferedReader reader = null;
	private PrintWriter writer = null;

	public void oeffnen(File f) throws FileNotFoundException, IOException {
		try {
			boolean zumLesen =(f.length()!=0);
			if (zumLesen) {
				reader = new BufferedReader(new FileReader(f));
			} else
				writer = new PrintWriter(new FileWriter(f));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public Object laden(File f) throws IOException {
		if (reader == null) {
			throw new RuntimeException("Der Reader ist nicht offen");
		}
		try {
			return reader.readLine();
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: "+e);
			return null;
		}

	}


	public void speichern(File f, Object o) throws IOException {
		if (writer == null) {
			throw new RuntimeException("Der Reader ist nicht offen");
		}
		writer.println(o);	
	}

	public void schliessen(File f) throws IOException {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: "+e);
		}
		if (writer != null) {
			writer.close();
		}
	}



}
