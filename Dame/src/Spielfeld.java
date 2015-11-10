import java.io.Serializable;

/**
 * 
 * @author Baris, Daniel, Simon,Hannes
 *
 */
public class Spielfeld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632008787864445195L;
	/**
	 * 
	 * @param spielfigur
	 *          Die Spielfigur
	 * @param id
	 *          die ID des Spielfelds
	 * @param posX
	 *          der Wert der Position X
	 * @param posY
	 *          der Wert der Position y
	 * @param istBelegt
	 *          bool wert ob ein Spielfeld besetzt ist oder nicht
	 * @param farbe
	 *          die Farbe des Spielfelds
	 * 
	 */
	private Spielfigur spielfigur;
	private String id;
	private int posX;
	private int posY;
	private boolean istBelegt = false;
	private boolean istSchwarz;

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
	public Spielfeld(boolean istSchwarz, int x, int y) {

		this.istSchwarz = istSchwarz;
		this.posX = x;
		this.posY = y;
		setId();
	}

	/**
	 * Gibt die aktuelle Spielfigur auf einem Feld zurueck
	 * 
	 * @return die akt Spielfigur
	 */
	public Spielfigur getSpielfigur() {
		return this.spielfigur;
	}

	/**
	 * gibt die id der Figur zurueck
	 * 
	 * @return gibt die id in Schachnotation zurueck
	 */
	public String getId() {
		return this.id;
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
	 * Gibt zurueck ob das übergebene Spielfeld schwarz ist
	 * 
	 * @param feld
	 * 
	 * @return (true/false) Spielfeld schwarz
	 */
	public boolean getIstSchwarz(Spielfeld feld) {
		return this.istSchwarz;
	}

	/**
	 * gibt zurueck ob das derzeitige Feld besetzt ist
	 * 
	 * @return (true/false) besetzt/nicht besetzt
	 */
	public boolean getIstBelegt() {
		return this.istBelegt;
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
	 * Setzt die aktuelle Spielfigur auf ein Feld
	 * 
	 * @param s
	 */
	public void setSpielfigur(Spielfigur s) {
		if (s == null) {
			throw new RuntimeException("Keine Spielfigur übergeben!");
		}
		if (istBelegt != true) {
			this.spielfigur = s;
			this.istBelegt = true;
			spielfigur.setPosX(this.getPosX());
			spielfigur.setPosY(this.getPosY());

		} else
			throw new RuntimeException("Es ist bereits eine Spielfigur auf dem Brett!");
	}

	public void removeSpielfigur(Spielfigur s) {
		if (s == null) {
			throw new RuntimeException("Keine zu löschende Figur übergeben.");
		}
		s = null;
		this.spielfigur = s;
		this.istBelegt = false;

	}
	
	/**
	 * Gibt die Felder in CSV Notation aus
	 * @return
	 */
	
	public String getcsvAusgabe() {
		if (getIstSchwarz() == true) {
			if (this.getSpielfigur() == null) {
				return this.getId();
			}
			return this.getId() + "," + this.getSpielfigur();
		} else {
			if (this.getSpielfigur() == null) {
				return  this.getId();
			}
			return this.getId() + ","+ this.getSpielfigur();
		}
	}

	/**
	 * gibt zurueck ob ein Feld schwarz ist
	 * 
	 * @return Gibt einen Bool Wert zurueck ob ein Feld schwarz ist oder nicht
	 */
	public boolean getIstSchwarz() {
		return this.istSchwarz;
	}

	/**
	 * Ueberschreibt die toString Methode
	 * 
	 * gibt ein Feld zurueck mit seiner farbe x/o, seiner id nach Schachnotation
	 * und Spielfigur
	 * 
	 */

	@Override
	public String toString() {
			if (getIstSchwarz() == true) {
				if (this.getSpielfigur() == null) {
					//return "x " + this.getId() + "     ";
					return " x ";
				}
				//return "x " + this.getId() + this.getSpielfigur();
				return this.getSpielfigur().toString();
			} else {
				if (this.getSpielfigur() == null) {
				//	return "o " + this.getId() + "     ";
					return " o ";
				}
				return this.getSpielfigur().toString();
			}
		}
}
