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
	private static int i = 0;

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
		this.brett = brett;
		this.setFarbe(farbe);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setDame(istDame);
		i++;
		System.out.println("Spielfigur nr " + i +" "+this.getPosX()+ " " + this.getPosY());
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
	 * @param posXg
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

	public void display() {

		System.out.println("o=weiß x=schwarz  Array bei der Ausgabe gedreht(0|0 ist unten links)");
		for (int zeile = brett.length - 1; zeile >= 0; zeile--) {

			if (brett[zeile] != null) {
				for (int spalte = 0; spalte < brett[zeile].length; spalte++) {
					// Die if is dafür da dass das Brett so wie es z.Z.ist ( farbe in form
					// von x/o und die notation h3) schön untereinander da steht
					if (zeile > 8) { //
						System.out.print(brett[zeile][spalte] + "  ");
					} else { //
						System.out.print(brett[zeile][spalte] + "   "); //
					}
				}
			}
			System.out.println();
		}

	}

	@Override
	public String toString() {
		if (getFarbe() == FarbEnum.SCHWARZ)
			return "x " + this.getPosX() + " " + this.getPosY();

		else
			return "o " + this.getPosX() + " " + this.getPosY();

	}

	/**
	 * Zu überschreibende Methode für die erbenden Klassen Stein und Dame
	 */
	public void Spielzug() {
	}

}
