public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8

	/**
	 * 
	 */
	private Spielfeld spielfeld;

	private Spielfeld[][] brett = new Spielfeld[12][12];

	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

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

	public Object getEinzelFeld(int x, int y) {

		return brett[x][y];
	}

	public String toString() {

		return "";

	}

}
