import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class DatenzugriffSerialisiert implements iDatenzugriff, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632008787864445195L;

	// public DatenzugriffSerialisiert(Spiel spiel) {
	// this.spiel = spiel;
	// }

	@Override
	public void speichern(Object o, String pfad) {

		try {
			FileOutputStream out = new FileOutputStream(pfad);
			ObjectOutputStream obout = new ObjectOutputStream(out);
			obout.writeObject(o);
			if (obout != null)
				obout.close();
		} catch (FileNotFoundException e) {
			System.err.println("Konnte Datei nicht erzeugen.");
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		} catch (Exception e) {
			System.err.println("Fehler beim Schließen");
		}
	}

	@Override
	public Object laden(File selectedFile) {

		try {
			// // System.out.println("1");
			FileInputStream fin = new FileInputStream(selectedFile.getAbsoluteFile());
			// // System.out.println("2");
			ObjectInputStream oin = new ObjectInputStream(fin);
			// // System.out.println("3");
			Object o = oin.readObject();
			// // System.out.println("4");
			if (oin != null)
				oin.close();
			// // System.out.println("5");
			return o;
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found");
		}
		return null;
	}
}