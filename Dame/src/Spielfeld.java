/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spielfeld {

	/**
	 * @param spielbrett
	 *          Das Spielbrett
	 * @param spielfigur
	 *          Die Spielfigur
	 * @param id
	 *          die ID des Spielfelds
	 * @param istBelegt
	 *          bool wert ob ein Spielfeld besetzt ist oder nicht
	 * @param farbe
	 *          die Farbe des Spielfelds
	 * 
	 */
	private Spielbrett spielbrett;
	private Spielfigur spielfigur;
	private String id;
	private boolean istBelegt;
	private boolean istSchwarz;
	private int posX;
	private int posY;

	/**
	 * * Konstruktor für die Spielfelder
	 * 
	 * @param spielbrett
	 *          Das Spielbrett
	 * @param istSchwarz
	 *          Bool ob die Figur schwarz (true) ist oder nicht (false)
	 * @param x
	 *          x Koord. im Array
	 * @param y
	 *          y Koord. im Array
	 */
	public Spielfeld(Spielbrett spielbrett, boolean istSchwarz, int x, int y) {
		if (spielbrett != null) {
			this.spielbrett = spielbrett;
			this.istSchwarz = istSchwarz;
			this.posX = x;
			this.posY = y;
			setId();
		}

	}

	/**
	 * Gibt x zurueck
	 * 
	 * @return die Arraypos X
	 */
	public int getPosX() {

		return this.posX;
	}

	/**
	 * Gibt y zurueck
	 * 
	 * @return die Arraypos Y
	 */
	public int getPosY() {

		return this.posY;
	}

	/**
	 * gibt die id der Figur zurueck
	 * 
	 * @return gibt die id zurueck
	 */
	public String getId() {
		return this.id;

	}

	/**
	 * Setzt die Id fuer ein Feld
	 * 
	 * id besteht aus einem Char mit der pos in Form einer Zahl
	 */
	private void setId() {

		this.id = "" + (char) (65 + this.getPosY()) + (this.getPosX() + 1);

	}

	/**
	 * Gibt die akt Spielfigur auf einem Feld zurueck
	 * 
	 * @return die akt Spielfigur
	 */
	public Spielfigur getSpielfigur() {
		return this.spielfigur;
	}

	/**
	 * Setzt die akt SPielfigur auf ein Feld
	 */
	private void setSpielfigur() {
	//	this.spielfigur = //parameter fehlt;
	}

	/**
	 * gibt zurueck ob das derzeitige Feld besetzt ist
	 * 
	 * @return (true/false) besetzt/nicht besetzt
	 */
	public boolean getIstBelegt() {
		return istBelegt;
	}

	/**
	 * Setzt einen Bool wert auf ein Feld ob dieses besetzt ist oder nicht
	 * 
	 * @param istBelegt
	 *          setzt ob es belegt ist oder nicht
	 */
	public void setIstBelegt(boolean istBelegt) {
		this.istBelegt = istBelegt;
	}

	/**
	 * Ueberschreibt die toString
	 * 
	 * gibt ein feld zurueck mit seiner farbe x/o und seiner id nach
	 * Schachnotation
	 * 
	 */
	@Override
	public String toString() {

		if (getIstSchwarz() == true)
			return "o " + this.getId();
		// + "[" + getPosX() + "," + getPosY() + "]";
		else
			return "x " + this.getId();
		// + "[" + getPosX() + "," + getPosY() + "]";

	}

	/**
	 * gibt zurueck ob ein Feld schwarz ist
	 * 
	 * @return Gibt einen Bool Wert zurueck ob ein Feld schwarz ist oder nicht
	 */
	private boolean getIstSchwarz() {

		return this.istSchwarz;
	}

}

