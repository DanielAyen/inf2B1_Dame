/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8

	// private Spielfeld spielfeld;
	/**
	 * @param Spielfeld
	 *          [][] brett Array für die Spielfelder 12x12 bestehend aus
	 *          Spielfeldern
	 * @param maxBrett
	 *          Max Brettanzahl (1)
	 * @param anzBrett
	 *          Derzeitige Anzahl an Spielbrettern
	 * 
	 * 
	 */
	private Spielfeld[][] brett = new Spielfeld[12][12];

	private final int maxBrett = 1;
	private int anzBrett = 0;

	/**
	 * Konstruktor für das Spielbrett, es darf max ein Brett existieren
	 */
	public Spielbrett() {
		if (anzBrett < maxBrett) {
			anzBrett++;
			boolean feldSchwarz = false;

			for (int i = 0; i < brett.length; i++) {
				for (int j = 0; j < brett[i].length; j++) {

					// Spielfeld feld = new Spielfeld(this, feldSchwarz);
					this.brett[i][j] = new Spielfeld(this, feldSchwarz, i, j);
					feldSchwarz = !feldSchwarz;

				}
				feldSchwarz = !feldSchwarz;

			}

		} else {

			throw new RuntimeException("Es existiert bereits ein Spielbrett!");
		}

	}

	/**
	 * VERALTET, ersetzt durch die DisplayMethode
	 * 
	 * @param s
	 *          wird benutzt um das Brett auszugeben
	 * @return Gibt das komplette Spielbrett mit seinen Feldern auf der Konsole
	 *         aus
	 * 
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

	// // ////

	/**
	 * Anzeigemethode für das Array der Spielfelder
	 * 
	 * @param x
	 */
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
}
