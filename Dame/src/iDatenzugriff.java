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

	
	public Object laden(File f) throws IOException;
	public void speichern(File f, Object o) throws IOException ;
	public void oeffnen(File f)throws FileNotFoundException,IOException;
	public void schliessen(File f)throws FileNotFoundException,IOException;

	
	
}
