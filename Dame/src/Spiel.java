import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spiel implements iBediener {

	private boolean spielAufgebaut = false;
	private int spielerAnzahl = 0;
	private boolean erstellenBeendet = false;
	private String name;
	private String farbe = "ungesetzt";
	private String ki = "ungesetzt";
	private Spieler spieler;
	private boolean spiellaeft = false;

	public void spielStarten() {

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String eingabe = "";

			do {
				eingabe = reader.readLine().toLowerCase();
				switch (eingabe) {
				case "help":
					System.out.println("aufbauen : Erstellt ein Spielbrett, wird zum spielen benoetigt.\n");
					System.out.println("spieler erstellen : Erlaubt dir einen Spieler zu erstellen, es werden 2 Spieler zum spielen benoetigt.\n");
					System.out.println("start: Startet das Spiel, es wird ein erstelltes Spielbrett und zwei Spieler benoetigt.\n");
					System.out.println("beenden : Das Spiel wird geschlossen.\n");
					break;
				// zum erstellen des spielfelds
				case "aufbauen":
					spielBauen();
					break;
				// zum erstellen von spielern
				case "spieler erstellen":

					System.out.println("Spielernamen eingeben (min 2 Zeichen!)\n");
					name = reader.readLine();
					System.out.println("Spielerfarbe eingeben (s fuer schwarz w fuer weiss)\n");
					farbe = reader.readLine();
					if ((farbe.equals("s")) || (farbe.equals("w"))) {
						if ((farbe.equals("s"))) {

							System.out.println("Spielerart eingeben (m fuer mensch k fuer ki)");
							ki = reader.readLine();
							if (ki.equals("m") || ki.equals("k")) {
								if (ki.equals("m")) {

									Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ);
									System.out.println(s1);

								} else {
									Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ, true);
									System.out.println(s1);
								}

							} else {
								System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
								break;
							}

						} else {
							System.out.println("Spielerart eingeben (m fuer mensch k fuer ki)");
							ki = reader.readLine();
							if (ki.equals("m") || ki.equals("k")) {
								if (ki.equals("k")) {

									Spieler s2 = new Spieler(name, FarbEnum.WEIß);
									System.out.println(s2);
								} else {
									Spieler s2 = new Spieler(name, FarbEnum.WEIß, true);
									System.out.println(s2);
								}

							} else {
								System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
								break;
							}

						}

						// farbe zuweisen +ki
						break;
					} else {
						System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
						break;
					}

					/*
					 * System.out.println("Spielernamen eingeben\n"); name =
					 * reader.readLine();
					 * System.out.println("spielerfarbe eingeben (s für schwarz w für weiß)"
					 * );
					 * 
					 * do { farbe = reader.readLine(); // farben switch switch (farbe) {
					 * 
					 * // case 1 farbe case "s":
					 * 
					 * System.out.println(
					 * "ist der Spieler eine Ki oder ein Mensch? (m für Mensch k für Ki)"
					 * ); do { ki = reader.readLine(); // innerrer ki switch switch (ki) {
					 * // case 1 ki case "m": Spieler s1 = new Spieler(name,
					 * FarbEnum.SCHWARZ, false); break; // case 2 ki case "k": Spieler s2
					 * = new Spieler(name, FarbEnum.SCHWARZ, true); break; // default für
					 * ki default: System.out.println("weder k noch m eingeben."); } }
					 * while (!ki.equals("m") || (!ki.equals("k")));
					 * 
					 * break; // case 2 farbe case "w": System.out.println(
					 * "ist der Spieler eine Ki oder ein Mensch? (m für Mensch k für Ki)"
					 * ); ki = reader.readLine(); // innerer ki switch do { switch (ki) {
					 * // case 1 ki case "m": Spieler s1 = new Spieler(name,
					 * FarbEnum.SCHWARZ, false); break; // case 2 ki case "k": Spieler s2
					 * = new Spieler(name, FarbEnum.SCHWARZ, true); break; // default für
					 * ki default: System.out.println("weder k noch m eingeben."); } }
					 * while (!ki.equals("m") || (!ki.equals("k"))); break;
					 * 
					 * // default für farbe default:
					 * System.out.println("weder s noch w eingeben.");
					 * 
					 * } } while (!farbe.equals("w") || (!farbe.equals("s")));
					 */

					// lässt das spiel beginnen
				case "start":
					if (spielAufgebaut == true && spielerAnzahl == 2) {
						spiellaeft = true;
						System.out.println("Das Spiel beginnt!");
						// display?

					} else {
						System.err.println("Das Spiel kann noch nicht gestartet werden!!");
					}
					break;
				// beendet das Spiel
				case "beenden":
					System.out.println("\n\n\t\tDas Spiel wird beendet.");
					break;
				// falls falsche eingaben erfolgen
				default:
					System.err.println("Befehl nicht erkannt");

				}

			} while (!eingabe.equals("beenden"));

			// Keeps going unless you enter "finish game"
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * erste Methode erstellt Spielbrett,
	 */
	public void spielBauen() {
		if (spielAufgebaut == true) {
			System.out.println("Spiel laueft bereits!");
		} else {
			spielAufgebaut = true;
			Spielbrett brett = new Spielbrett(12);
			System.out.println("Das Spielbrett wurde aufgebaut!\n");
			brett.display();
		}

	}

	/**
	 * fuegt spieler hinzu
	 */
	public void spielerhinzufuegen() {

	}

	/**
	 * Der Spieler der am Zug ist darf seine Figur bewegen(wird durch zuggueltig
	 * geprueft) nur der SPieler der am zug ist darf auch ziehen
	 */
	public void figurBewegen() {

	}

	/**
	 * falls mit der derzeitigen figur noch weitere zuege moeglich sind
	 */
	public void figurWeiterbewegen() {

	}

	/**
	 * wenn eine figur die eind pos erreicht hat wird sie zur dame(true)
	 */
	public void dameWerden() {

	}

	/**
	 * einen gegnerischen stein aus dem Spiel werfen
	 */
	public void steinSchlagen() {

	}

	/**
	 * pusten halt
	 */
	public void pusten() {

	}

	/**
	 * setzt den derzeitigen Spieler der am zug ist
	 */
	public void amZug() {

		/**
		 * der nächste spieler der am zug ist vlt unnoetig
		 */
	}

	public void naechster() {

	}

	/**
	 * zug fruehzeitig beenden
	 */
	public void zugBeenden() {

	}

	/**
	 * wird aufgerufen um spieler hinzuzufuegen
	 */
	public void spielerHinzufuegen() {

	}

	/**
	 * Hierdurch wird das Spielbrett erstellt
	 */
	public void brettErstellen() {

	}

	/**
	 * ueberprueft ob der gewollte Zug gueltig ist
	 */
	public void zugGueltig() {

	}

	/**
	 * speichern
	 */
	public void speichern() {

	}

	/**
	 * laden
	 */
	public void laden() {

	}
}
