/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spielbrett {

	// Unten links schwarz a1 und oben rechts schwarz h8
	/**
	 * @param Spielfeld
	 *          [][] brett Array für die Spielfelder 12x12 bestehend aus
	 *          Spielfeldern
	 * @param maxBrett
	 *          Max Brettanzahl (1)
	 * @param anzBrett
	 *          Derzeitige Anzahl an Spielbrettern 
	 */
	private Spielfeld[][] brett;
private final int maxBrett = 1;
	private int anzBrett = 0;

	/**
	 * Konstruktor für das Spielbrett, es darf max ein Brett existieren Das brett
	 * ist ein 2Dim Array welches mit Feldern gefüllt wird
	 */
	public Spielbrett(int groesse) {
		if (anzBrett < maxBrett) {
			if (groesse == 8 || groesse == 10 || groesse == 12) {
				anzBrett++;
				boolean feldSchwarz = false;
				brett = new Spielfeld[groesse][groesse];

				for (int i = 0; i < brett.length; i++) {
					for (int j = 0; j < brett[i].length; j++) {
						this.brett[i][j] = new Spielfeld(this, feldSchwarz, i, j);
						feldSchwarz = !feldSchwarz;
					}
					feldSchwarz = !feldSchwarz;
				}
			} else {
				throw new RuntimeException("Das Spielfeld darf nur die Groessen 8, 10 oder 12 haben!");
			}
		} else {
			throw new RuntimeException("Es existiert bereits ein Spielbrett!");
		}
	}

	/**
	 * Gibt ein einzelnes Feld anhand der Pos im Array zurueck
	 * 
	 * @param c
	 *          ArrayIndex 1
	 * @param i
	 *          ArrayIndex 2
	 * 
	 * @return Gibt ein einzelnes Feld anhand des Index zurueck
	 */

	public Spielfeld getBrett(char c, int i) {
		int hilf;
		switch (c) {
		case 'a':// fall through
		case 'A':
			hilf = 0;
			break;
		case 'b':
		case 'B':
			hilf = 1;
			break;
		case 'c':
		case 'C':
			hilf = 2;
			break;
		case 'd':
		case 'D':
			hilf = 3;
			break;
		case 'e':
		case 'E':
			hilf = 4;
			break;
		case 'f':
		case 'F':
			hilf = 5;
			break;
		case 'g':
		case 'G':
			hilf = 6;
			break;
		case 'h':
		case 'H':
			hilf = 7;
			break;
		case 'i':
		case 'I':
			hilf = 8;
			break;
		case 'j':
		case 'J':
			hilf = 9;
			break;
		case 'k':
		case 'K':
			hilf = 10;
			break;
		case 'l':
		case 'L':
			hilf = 11;
			break;
		default:
			throw new RuntimeException("Eingabe ausserhalb Feld");
		}
		return this.brett[hilf][i];

	}

	/*
	 * 
	 * public Spielfeld getBrett(int x,int y){ return this.brett[x][y]; } public
	 * String getEinzelFeld(int x, int y) {
	 * 
	 * String s = ""; return s + brett[x][y]; }
	 */
	/**
	 * toString z.Z unnoetig
	 */
	public String toString() {

		return "";
	}

	/**
	 * Anzeigemethode für das Array der Spielfelder
	 * 
	 * Geht durch das Array und gibt jedes einzelne Feld aus
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
////////////////
	public int getBrettGroesse(){
		
		return 12;
		
	}
	///////////////
	/**
	 * VERALTET, ersetzt durch die DisplayMethode
	 * 
	 * @param s
	 *          wird benutzt um das Brett auszugeben
	 * @return Gibt das komplette Spielbrett mit seinen Feldern auf der Konsole
	 *         aus
	 * 
	 */
	public String printBrett() {

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
}
