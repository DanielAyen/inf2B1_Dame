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

	
	void speichern(String dateiname, String dateiende, Object o) throws IOException;
	
	Object laden(String dateiname);

	
	
}
