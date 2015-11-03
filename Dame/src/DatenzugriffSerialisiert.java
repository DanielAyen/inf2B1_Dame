
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatenzugriffSerialisiert implements iDatenzugriff {
	private ObjectOutputStream objectOut = null;
	private ObjectInputStream objectIn = null;

	public void speichern(File f, Object o) throws IOException {

		try {
			objectOut.writeObject(o);
		} catch (FileNotFoundException e) {
			System.err.println("Konnte Datei nicht erzeugen.");
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		} catch (Exception e) {
			System.err.println("Fehler beim Schließen");
		}
	}

	public Object laden(File f) throws IOException {

		try {
			Object m = objectIn.readObject();
			return m;
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found");
		}
		return null;
	}

	public void oeffnen(File f) {
		try {
			boolean zumLesen = (f.length() != 0);
			if (zumLesen) {
				objectIn = new ObjectInputStream(new FileInputStream(f));
			} else {
				objectOut = new ObjectOutputStream(new FileOutputStream(f));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		}
	}

	public void schliessen(File f) {
		if (objectIn != null) {
			try {
				objectIn.close(); //
			} catch (Exception e) {
				System.err.println("Fehler beim Schließen");
			}
		}
		if (objectOut != null) {
			try {
				objectOut.close(); // 
			} catch (Exception e) {
				System.err.println("Fehler beim Schließen");
			}
		}

	}
}