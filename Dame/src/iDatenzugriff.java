import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Interface iDatenzugriff mit den Methoden
 *
 */

public interface iDatenzugriff {

	
	public void oeffnen(Properties p) throws IOException;
    
	public void schreiben(Object object) throws IOException;

	public Object lesen() throws IOException;
    
	public void schliessen(Object object)throws IOException;

	
	
}