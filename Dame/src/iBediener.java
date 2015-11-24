import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * @author Baris, Daniel, Simon, Hannes
 *
 */
public interface iBediener {
	

	/**
	 * Methode spielStarten
	 * Startet das Spiel
	 */
	
	public void spielStarten();
	
	public Spielbrett getBrett();
	
	/**
	 * Methode aufbauen
	 * erstellt das Spielbrett
	 * @param x - Die Spielfeldgröße
	 * @return True wenn spiel aufgebaut wurde.
	 */

	public boolean aufbauen(int x);

	public boolean spielerErstellen(String name, FarbEnum farbe, boolean istKi);

	public boolean ziehen(String startp, String endp);

	public boolean kannWeiterZiehen();

	public void willWeiterZiehen();

	public void willNichtWeiterZiehen();

	public FarbEnum getAmZug();

	public KI getK1();

	public KI getK2();

	public boolean kizieh();

	public ArrayList<String> getZugFurLog();

	public Spieler getGewonnenerSpieler();

	public boolean starten();

	public void allesLoeschen();

	public void zugBeenden();
	
	void Speichern(String dateiname, String dateiende) throws IOException;

}
