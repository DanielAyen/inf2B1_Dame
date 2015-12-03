import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Interface iDatenzugriff mit den Methoden
 *
 */

public interface iDatenzugriff {

	void speichern(Object o, String Pfad) throws IOException;

	Object laden(File selectedFile) throws IOException;

}
