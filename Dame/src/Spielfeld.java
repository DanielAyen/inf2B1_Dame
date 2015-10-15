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
	private FarbEnum farbe;
	private boolean istSchwarz;
	private int posX;
	private int posY;

	/**
	 * Konstruktor für die Spielfelder
	 * 
	 * @param spielbrett
	 *          Das Spielbrett
	 */
	public Spielfeld(Spielbrett spielbrett, boolean istSchwarz, int x, int y) {
		if(spielbrett!=null){
			this.spielbrett = spielbrett;
			this.istSchwarz = istSchwarz;
			this.posX = x;
			this.posY = y;
			setId();
		}
		
		

	}

	public int getPosX() {

		return this.posX;
	}

	public int getPosY() {

		return this.posY;
	}

	public String getId() {
		return this.id;

	}

	private void setId() {

		this.id = "" + (char)(65+this.getPosY())+(this.getPosX()+1);
				
	}

	public Spielfigur getSpielfigur() {
		return this.spielfigur;
	}

	private void setSpielfigur() {
		this.spielfigur = spielfigur;
	}

	public boolean isIstBelegt() {
		return istBelegt;
	}

	public void setIstBelegt(boolean istBelegt) {
		this.istBelegt = istBelegt;
	}

	@Override
	public String toString() {

		if (getIstSchwarz() == true)
			return "o "+this.getId();
					//+ "[" + getPosX() + "," + getPosY() + "]";
		else
			return "x "+this.getId();
					//+ "[" + getPosX() + "," + getPosY() + "]";

	}

	private boolean getIstSchwarz() {

		return this.istSchwarz;
	}

}
/*
 * Spielfeld ansprechen mit char
 */
