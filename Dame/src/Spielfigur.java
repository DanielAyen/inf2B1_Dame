import java.io.Serializable;

/**
 * 
 * @author Baris, Daniel, Simon,Hannes
 *
 */
public class Spielfigur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6059774803133226820L;
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
	private boolean istDame;
	private static int i = 0;
	private static int j = 0;
	private int idS = 1;
	private int idW = 1;
	private int posX;
	private int posY;

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

	public Spielfigur(FarbEnum farbe, boolean istDame) {
		this.setFarbe(farbe);

		if (farbe == FarbEnum.SCHWARZ) {
			i++;
			this.idS = i;
			System.out.println("SpielfigurNr." + idS + " " + this.getFarbe());
		} else {
			j++;
			this.idW = j;
			System.out.println("SpielfigurNr." + idW + " " + this.getFarbe());
		}
	}

	/**
	 * Getter für Farbe
	 * 
	 * @return farbe
	 */

	public FarbEnum getFarbe() {
		return farbe;
	}

	public void setDame(boolean istDame) {
		this.istDame = istDame;

	}

	public boolean getDame(Spielfigur fig) {
		return this.istDame;
	}

	public int getIdW() {
		return this.idW;
	}

	public int getIdS() {
		return this.idS;
	}

	public void setPosX(int posX) {
		this.posX = posX;

	}

	public void setPosY(int posY) {
		this.posY = posY;

	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	/**
	 * Setter für die Farbe
	 * 
	 * @param farbe
	 *          die Farbe der Fig.
	 */

	private void setFarbe(FarbEnum farbe) {
		this.farbe = farbe;
	}

	@Override
	public String toString() {
		if (getFarbe() == FarbEnum.SCHWARZ) {
			if (this.idS < 10) {
				return "|s" + "0" + this.idS + "|";
			} else
				return "|s" + this.idS + "|";

		} else {
			if (this.idW < 10) {
				return "|w" + "0" + this.idW + "|";
			} else
				return "|w" + this.idW + "|";

		}
	}

}
