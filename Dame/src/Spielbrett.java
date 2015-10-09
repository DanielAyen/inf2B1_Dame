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

		String s = "";
		return s + brett[x][y];
	}

	/**
	 * toString
	 */
	public String toString() {

		return "";

	}
	
	//////

	String[][] erstesArray = { { "a12", "b12", "c12", "d12", "e12", "f12", "g12", "h12", "i12", "j12", "k12", "l12" }, { "a11", "b11", "c11", "d11", "e11", "f11", "g11", "h11", "i11", "j11", "k11", "l11" }, { "a10", "b10", "c10", "d10", "e10", "f10", "g10", "h10", "i10", "j10", "k10", "l10" },
			{ "a9", "b9", "c9", "d9", "e9", "f9", "g9", "h9", "i9", "j9", "k9", "l9" }, { "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "i8", "j8", "k8", "l8" }, { "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "i7", "j7", "k7", "l7" },
			{ "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "i6", "j6", "k6", "l6" }, { "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "i5", "j5", "k5", "l5" }, { "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "i4", "j4", "k4", "l4" },
			{ "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "i3", "j3", "k3", "l3" }, { "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "i2", "j2", "k2", "l2" }, { "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1" } };
/**
 * weitere anzeigemethode für das Array
 * 
 * @param x
 */
	public static void display(String[][] x) {
		for (int zeile = 0; zeile < x.length; zeile++) {

			if (x[zeile] != null) {
				for (int spalte = 0; spalte < x[zeile].length; spalte++) {
					System.out.print(x[zeile][spalte] + "\t");
				}
			}
			System.out.println();
		}

	}
}
