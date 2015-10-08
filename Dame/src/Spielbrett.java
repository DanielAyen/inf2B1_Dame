public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8

	/**
	 * 
	 */
	private Spielfeld spielfeld;

	private Spielfeld[][] Brett = new Spielfeld[12][12];

	public void sysoBrett() {

		for (int i = 0; i < Brett.length; i++) {
			System.out.println(Brett[i]);

			for (int j = 0; j < Brett[i].length; j++) {
				System.out.println(Brett[i][j]);
			}
		}

	}

	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

}
