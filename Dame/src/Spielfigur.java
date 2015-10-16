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
	 *          Die Farbe der Figur
	 * @param ID
	 *          Die id einer Figur
	 * @param pos
	 *          Die derzeitige Pos einer Figur
	 * @param spieler
	 *          der zugehoerige Spieler
	 * @param ist
	 *          Dame ob eine Figur eine Dame ist
	 * @param posX
	 *          x Koord der Fig.
	 * @param posY
	 *          y Koor der Fig.
	 */

	private FarbEnum farbe;
	// private int position; // Wird später benötigt
	// private int id; macht keinen Sinn, da Daten niemals benötigt
	// private int counter = 0; // Wird später benötigt
	private Spieler spieler;
	private boolean istDame = false;
	private int posX;
	private int posY;

	/**
	 * Konstruktor
	 * 
	 * @param id
	 *          setzt die Id der Figur
	 * @param farbe
	 *          setzt die Farbe der figur
	 * 
	 *          Dem Konstruktor werden eine ID und die Farbe der Spielfigur
	 *          übergeben
	 * @param posX
	 *          setzt die x pos
	 * @param posY
	 *          setzt die y pos
	 */

	public Spielfigur(FarbEnum farbe, int posX, int posY) {
		this.setFarbe(farbe);
		this.posX = posX;
		this.posY = posY;

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
	 *          die Farbe der Fig.
	 */

	public void setFarbe(FarbEnum farbe) {
		this.farbe = farbe;
	}

	/**
	 * Getter für ID
	 *
	 * 
	 * @return id die Id der Fig.
	 */

	public abstract void Spielzug();
}
