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
	private int id=0;
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
		id=i;
//		System.out.println("this is the ID :"+id);
//		System.out.println("this is the Counter :"+i);
		System.out.println("Spielfigur nr " + id +" "+this.getPosX()+ " " + this.getPosY());
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
	
	public int getId() {
		return this.id;
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
		if (getFarbe() == FarbEnum.SCHWARZ)
			return this.id +this.getPosX() + " " + this.getPosY();

		else
			return this.id +this.getPosX() + " " + this.getPosY();

	}

}
