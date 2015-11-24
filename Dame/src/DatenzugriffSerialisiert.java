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
	private static final long serialVersionUID = 1L;
	private Spiel spiel;

//	public DatenzugriffSerialisiert(Spiel spiel) {
//		this.spiel = spiel;
//	}

	@Override
	public void speichern(String dateiname, String dateiende, Object o) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream( "DameSER.ser"));
			oos.writeObject(o);
		} catch (FileNotFoundException e) {
			//!// System.err.println("konnte 'out.ser' nicht erzeugen");
		} catch (IOException e) {
			//!// System.err.println("Fehler bei der Ein-/Ausgabe" + e);

		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				//!// System.err.println("Fehler beim Schliessen der Datei");
			}
		}
	}
	
	
	
	@Override
	public Object laden(String dateiname) {
		Object s=null;
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream ( new FileInputStream ("DameSER.ser"));
		
			try {
				s = ois.readObject();
				//!// System.out.println("Spiel geladen");
				return s;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			//!// System.err.println("konnte 'out.ser' nicht finden");
		} catch (IOException e) {
			//!// System.err.println("Fehler bei der Ein-/Ausgabe" + e);

		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				//!// System.err.println("Fehler beim Schliessen der Datei");
			}
		}
		return null;
	}


		
	
		
	}