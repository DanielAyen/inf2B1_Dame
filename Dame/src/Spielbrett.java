public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8

	/**
	 * 
	 */
	private Spielfeld spielfeld;

	private Spielfeld[][] Brett = new Spielfeld[12][12];

	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

	public String getBrett() {

		String s = "";

		for (int i = 0; i < Brett.length; i++) {
			// System.out.println(Brett[i]);

			for (int j = 0; j < Brett[i].length; j++) {
				// System.out.println(Brett[i][j]);

				s = Brett[i][j] + "  ";

			}
		}
		return s;

	}

	public String toString() {

		return "";

	}

}
