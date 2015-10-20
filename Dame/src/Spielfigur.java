/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spielfigur {
	/**
	 * Attribute
	 * 
	 * @param farbe
	 *          Die Farbe der Figur
	 * @param spieler
	 *          der zugehoerige Spieler
	 * @param posX
	 *          x Koord der Fig.
	 * @param posY
	 *          y Koord der Fig.
	 */

	private FarbEnum farbe;
	private Spieler spieler;
	private int posX;
	private int posY;
	private Spielbrett brett;
	private boolean istDame;

	/**
	 * Konstruktor
	 * 
	 * @param farbe
	 *          setzt die Farbe der figur
	 * @param posX
	 *          setzt die x pos
	 * @param posY
	 *          setzt die y pos
	 */

	public Spielfigur(FarbEnum farbe, int posX, int posY, Spielbrett brett, boolean istDame) {
		this.setFarbe(farbe);
		this.setPosX(posX);
		this.setPosY(posY);
		this.brett = brett;
		this.setDame(istDame);
	}

	/**
	 * Getter für Farbe
	 * 
	 * @return farbe
	 */

	public FarbEnum getFarbe() {
		return farbe;
	}

	private void setDame(boolean istDame) {
		// if(istDame==false||Figuramendedesfelds||Farbestimmt){ }
		this.istDame = istDame;

	}

	public boolean getDame(Spielfigur fig) {
		return this.istDame;
	}

	/**
	 * Getter für Position x
	 * 
	 * @return
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Setter für Position X
	 * 
	 * @param posX
	 */
	public void setPosX(int posX) {
		if (posX < 0 || posX > brett.getBrettGroesse()) {// /////////////////
		} else
			this.posX = posX;
	}

	/**
	 * Getter für Position Y
	 * 
	 * @return
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Setter für Position Y
	 * 
	 * @param posY
	 */
	public void setPosY(int posY) {
		if (posY < 0 || posY > brett.getBrettGroesse()) {// ////////////////
		}
		// Code für Überprüfung Spielbrettgröße
		this.posY = posY;
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

	public Spieler getSpieler() {
		return spieler;
	}

	@Override
	public String toString() {
		return "Spieler: " + getSpieler() + ", Farbe: " + getFarbe();
	}

	/**
	 * Zu überschreibende Methode für die erbenden Klassen Stein und Dame
	 */
	public void Spielzug() {
	}

}
