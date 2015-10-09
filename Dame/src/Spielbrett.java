public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8

	/**
	 * Spielfelnd spielfelnd Brett kennt Feld Spielfeld[][] Das Brett Array
	 * gefüllt mit Feldern
	 * 
	 */
	private Spielfeld spielfeld;

	private Spielfeld[][] brett = new Spielfeld[12][12];

	/**
	 * 
	 * @return gibt das Spielfed zurueck
	 */
	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

	/**
	 * 
	 * @return Gibt das komplette Spielbrett mit seinen Feldern auf der Konsole
	 *         aus
	 */
	public String getBrett() {

		String s = "";

		for (int i = 0; i < brett.length; i++) {
			// System.out.println(Brett[i]);

			for (int j = 0; j < brett[i].length; j++) {
				// System.out.println(Brett[i][j]);

				s = brett[i][j] + "  ";

			}
		}
		return s;

	}

	/**
	 * 
	 * @param x
	 *          ArrayIndex 1
	 * @param y
	 *          ArrayIndex 2
	 * @return Gibt ein einzelnes Feld anhand des Index zurueck
	 */
	public Object getEinzelFeld(int x, int y) {

		String s="";
		return s+brett[x][y];
	}

	/**
	 * toString
	 */
	public String toString() {

		return "";

	}

}
