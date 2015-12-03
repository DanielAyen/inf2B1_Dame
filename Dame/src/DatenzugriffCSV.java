import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DatenzugriffCSV implements iDatenzugriff {
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	Spiel s;
	Spieler s2;
	Spieler s1;
	Spielbrett brett;

	/**
	 *
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public Object laden(File selectedFile) throws IOException {
		System.out.println("Reader wird erstellt");
		reader = new BufferedReader(new FileReader(selectedFile.getAbsoluteFile()));
		System.out.println("Eine Arraylist wird jetzt erstellt");
		ArrayList<String[]> csv = new ArrayList<String[]>();
		if (reader == null) {
			throw new RuntimeException("Der Reader ist nicht offen");
		}
		System.out.println("Reader auf, los gehts");
		try {
			System.out.println("Ich trye");
			String line;
			line = reader.readLine();
			System.out.println("Jetzt wird gewhilt");
			while (line != null) {
				String[] gesplittet = line.split(";");
				csv.add(gesplittet);
				line = reader.readLine();
			}
			System.out.println("Ich reTÖRNE");
			return csv;
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
			return null;
		}
	}

	public void schliessen(File f) throws IOException {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		}
		if (writer != null) {
			writer.close();
		}
	}

	@Override
	public void speichern(Object o, String pfad) throws IOException {

		try {
			writer = new PrintWriter(new FileWriter(pfad));
			// } catch (Exception e) {
			// // !// System.err.println("Kein String übergeben");}
		} catch (NullPointerException e) {
			// !// throw new IOException("UNerwartetes Dateiende");
		} catch (NumberFormatException e) {
			// !// throw new IOException("Falsches Elementformat ");
		} catch (IndexOutOfBoundsException e) {
			// !// throw new IOException("zu wenig Datenelemente");
		}
		writer.write((String) (o));
		writer.flush();
		writer.close();
	}
}

// public void speichern(String dateiname, String dateiende, Object o) {
// ObjectOutputStream oos = null;
// try {
// oos = new ObjectOutputStream(new FileOutputStream("DameSER.ser"));
// oos.writeObject(o);
// } catch (FileNotFoundException e) {
// // !// System.err.println("konnte 'out.ser' nicht erzeugen");
// } catch (IOException e) {
// // !// System.err.println("Fehler bei der Ein-/Ausgabe" + e);
//
// } finally {
// try {
// oos.flush();
// oos.close();
// } catch (Exception e) {
// // !// System.err.println("Fehler beim Schliessen der Datei");
// }
// }
// }

