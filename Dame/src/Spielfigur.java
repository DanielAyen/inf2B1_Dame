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
	//private int position; // Wird später benötigt
	//private int id; macht keinen Sinn, da Daten niemals benötigt
//	private int counter = 0; // Wird später benötigt
	private Spieler spieler;
	private boolean istDame=false;
	private int posX;
	private int posY;
	

	/**
	 * Konstruktor
	 * 
	 * @param id
	 * @param farbe
	 * 
	 *          Dem Konstruktor werden eine ID und die Farbe der Spielfigur
	 *          übergeben
	 */

	public Spielfigur(FarbEnum farbe, int posX,int posY) {
		this.setFarbe(farbe);
		this.posX=posX;
		this.posY=posY;
		
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
	 * @return 
	 * 
	 * @return id
	 */


public abstract void Spielzug();
}
