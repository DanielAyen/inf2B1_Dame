/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public abstract class Spielfigur {
	/**
	 * Attribute
	 * 
	 * @param farbe
	 * @param ID
	 * @param pos
	 */

	private FarbEnum farbe;
	private int position; // Wird später benötigt
	private int id;
	private int counter = 0; // Wird später benötigt
	private Spieler spieler;
	private boolean istDame=false;
	

	/**
	 * Konstruktor
	 * 
	 * @param id
	 * @param farbe
	 * 
	 *          Dem Konstruktor werden eine ID und die Farbe der Spielfigur
	 *          übergeben
	 */

	public Spielfigur(int id, FarbEnum farbe, boolean istDame) {
		this.setFarbe(farbe);
		this.setDame(istDame);
	}

	public void setDame(boolean istDame) {

		this.istDame=istDame;
	}

	public boolean getDame(int id){
		return istDame;
		
	}
	
	/**
	 * Getter für Farbe
	 * 
	 * @return farbe
	 */

	public FarbEnum getFarbe() {
		return farbe;
	}

	/**
	 * Setter für die Farbe
	 * 
	 * @param farbe
	 */

	public void setFarbe(FarbEnum farbe) {
		this.farbe = farbe;
	}

	/**
	 * Getter für ID
	 * 
	 * @return id
	 */

	public int getId() {
		return id;
	}

	/**
	 * Setter für die id
	 * 
	 * @param id
	 */

	public void setId(int id) {
		this.id = id;
	}
}
