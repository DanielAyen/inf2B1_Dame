import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spiel implements iBediener {

	/**
	 * Attribute
	 * 
	 * @param spielaufgebaut
	 *          Boolean überprüfung ob das Spiel aufgebaut wurde oder nicht.
	 * @param spielerAnzahl
	 *          Anzahl der aktuellen Spieler
	 * @param name
	 *          Name des Spielers
	 * @param farbe
	 *          Farbe der Spieler
	 * @param Ki
	 * 
	 * @param spiellaeuft
	 *          Boolean überprüfung ob das Spiel bereits läuft oder nicht.
	 * 
	 * @param schwarzvergeben
	 *          gibt an ob die Farbe schwarz bereits vergeben ist (False==nicht
	 *          vergeben)
	 * 
	 * @param weiß
	 *          gibt an ob die Farbe weiß bereits vergeben ist (False==nicht
	 *          vergeben)
	 * 
	 * @param brett
	 *          das zugehörige Spielbrett
	 * 
	 * @param fig
	 *          die zugehörigen Spielfiguren
	 * 
	 * @param spieler
	 *          die zugehörigen Spieler
	 * 
	 * @param spielfeld
	 *          die zugehörigen Spielfelder
	 */

	private boolean spielAufgebaut = false;
	private int spielerAnzahl = 0;
	private String name;
	private String farbe;
	private String ki;
	private boolean spiellaeuft = false;
	private boolean schwarzvergeben = false;
	private boolean weissvergeben = false;

	private Spielbrett brett;
	private Spieler spieler;
	private Spielfeld spielfeld;

	public void spielStarten() {
		System.out.println("Bitte erstelle zuerst ein neues Spielbrett. Falls du Hilfe benoetigst gebe 'help' in die konsole ein");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String eingabe = "";

			do {
				eingabe = reader.readLine().toLowerCase();
				switch (eingabe) {
				case "help":
					System.out.println("aufbauen : Erstellt ein Spielbrett, wird zum spielen benoetigt.");
					System.out.println("spieler erstellen : Erlaubt dir einen Spieler zu erstellen, es werden 2 Spieler zum spielen benoetigt.");
					System.out.println("start: Startet das Spiel, es wird ein erstelltes Spielbrett und zwei Spieler benoetigt.");
					System.out.println("beenden : Das Spiel wird geschlossen.");
					System.out.println("ziehen : Erlaubt dir eine Spielfigur zu bewegen. Nicht moeglich solange das Spiel nicht laeuft.");
					System.out.println("anzeigen : Zeigt dir das Spielbrett.");
					System.out.println("speichern : erlaubt es dir das Spiel zu speichern.");
					System.out.println("laden : erlaubt es dir ein Spielstand zu laden.");
					break;
				// zum erstellen des spielfelds
				case "aufbauen":
					if (spielAufgebaut)
						break;
					System.out.println("Bitte gebe die gewuenschte Spielbrett Groesse ein. ( 8 , 10 , 12 )");
					String groesse = reader.readLine();
					if (groesse.equals("8")) {
						spielBauen(8);
						break;
					} else if (groesse.equals("10")) {
						spielBauen(10);
						break;
					} else if (groesse.equals("12")) {
						spielBauen(12);
						break;
					} else {
						System.out.println("Fehlerhafte Eingabe, bitte nur 8 , 10 oder 12 eingeben. Zurueck im Hauptmenue.\n");
						break;
					}
					// zum anzeigen des Bretts
				case "anzeigen":
					if (spielAufgebaut) {
						brett.display();
						System.out.println("\n");
						break;
					} else {
						System.out.println("Du kannst kein Brett anzeigen das nicht existiert! Nutze aufbauen um ein Brett zu erstellen.");
						break;
					}
					// zum erstellen von spielern
				case "spieler erstellen":
					if (!spielAufgebaut) {
						System.out.println("Du musst zuerst ein Spielbrett aufbauen! (aufbauen)");
						break;
					}
					System.out.println("Spielernamen eingeben (min 2 Zeichen!)\n");
					name = reader.readLine();
					if (name == null || name.length() < 2) {
						System.out.println("Name war fehlerhaft, zurueck im Hauptmenue");
						break;
					}
					System.out.println("Spielerfarbe eingeben (s fuer schwarz w fuer weiss)\n");
					farbe = reader.readLine();
					if (farbe.equals("s") && schwarzvergeben == true || farbe.equals("w") && weissvergeben == true) {
						System.out.println("Farbe bereits vergeben, zurueck im Hauptmenue");
						break;
					}
					if ((farbe.equals("s")) || (farbe.equals("w"))) {
						if ((farbe.equals("s"))) {

							System.out.println("Spielerart eingeben (m fuer mensch k fuer ki)");
							ki = reader.readLine();
							if (ki.equals("m") || ki.equals("k")) {
								if (ki.equals("m")) {

									Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ, false);
									schwarzvergeben = true;
									System.out.println(s1);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s1, brett);
									break;
								} else {
									Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ, true);
									schwarzvergeben = true;
									System.out.println(s1);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s1, brett);
									break;
								}

							} else {
								System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
								break;
							}

						} else {
							System.out.println("Spielerart eingeben (m fuer mensch k fuer ki)");
							ki = reader.readLine();
							if (ki.equals("m") || ki.equals("k")) {
								if (ki.equals("m")) {
									Spieler s2 = new Spieler(name, FarbEnum.WEIß, false);
									weissvergeben = true;
									System.out.println(s2);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s2, brett);
									break;
								} else {
									Spieler s2 = new Spieler(name, FarbEnum.WEIß, true);
									weissvergeben = true;
									System.out.println(s2);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s2, brett);
									break;
								}

							} else {
								System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
								break;
							}

						}

					} else {
						System.out.println("Eingabe war fehlerhaft, zurueck im Hauptmenue");
						break;
					}

					// lässt das spiel beginnen
				case "start":
					if (spielAufgebaut == true && spielerAnzahl == 2) {
						spiellaeuft = true;
						System.out.println("Das Spiel beginnt!");
						// display?

					} else {
						System.err.println("Das Spiel kann noch nicht gestartet werden!!");
					}
					break;
				case "ziehen":
					if (!spiellaeuft) {
						System.out.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
						break;
					} else {
						// zugGueltig(int x, int y, boolean istDame);
					}
					break;
				// beendet das Spiel
				case "beenden":
					System.out.println("\n\n\t\tDas Spiel wird beendet.");
					break;
				// speichern
				case "speichern":
					System.out.println("Derzeit nicht implementiert -Sorry.");
					break;
				// laden
				case "laden":
					System.out.println("Derzeit nicht implementiert -Sorry.");
					break;
				// falls falsche eingaben erfolgen
				default:
					System.err.println("Befehl nicht erkannt.");

				}
				// solange man spielt läuft es weiter.
			} while (!eingabe.equals("beenden"));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * erste Methode erstellt Spielbrett,
	 */
	private void spielBauen(int x) {
		if (spielAufgebaut == true) {
			System.out.println("Spielbrett wurde bereits aufgebaut!");
		} else {
			spielAufgebaut = true;
			brett = new Spielbrett(x);
			System.out.println("Das Spielbrett wurde aufgebaut!\n");
			brett.display();
		}

	}

	/**
	 * Der Spieler der am Zug ist darf seine Figur bewegen(wird durch zuggueltig
	 * geprueft) nur der SPieler der am zug ist darf auch ziehen
	 */
	private void figurBewegen() {

	}

	/**
	 * falls mit der derzeitigen figur noch weitere zuege moeglich sind
	 */
	private void figurWeiterbewegen() {

	}

	/**
	 * wenn eine figur die eind pos erreicht hat wird sie zur dame(true)
	 */
	private void dameWerden(Spielfigur fig) {
		// TODO
		FarbEnum temp = fig.getFarbe();
		switch (temp) {

		case SCHWARZ:// müssen oben ankommen also bei 8/10/12////// 0|0 =A1
			break;

		case WEIß:// müssen unten ankommen 1
			break;

		}

	}

	/**
	 * einen gegnerischen stein aus dem Spiel werfen
	 */
	private void steinSchlagen() {

	}

	/**
	 * pusten halt
	 */
	private void pusten() {

	}

	/**
	 * setzt den derzeitigen Spieler der am zug ist
	 */
	private void setAmZug() {

	}

	/**
	 * gibt den derzeitigen Spieler der am zug ist
	 */
	private void getAmZug() {

	}

	/**
	 * der nächste spieler der am zug ist vlt unnoetig
	 */
	private void naechster() {

	}

	/**
	 * zug fruehzeitig beenden
	 */
	private void zugBeenden() {

	}

	private void erstelleFiguren(KI_Dame ki, Spielbrett brett) {

	}

	private void erstelleFiguren(Spieler spieler, Spielbrett brett) {

		if (spieler.getFarbe() == FarbEnum.SCHWARZ) {
			for (int i = 0; i < brett.getBrettGroesse() / 2 - 1; i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeld(i, j).getIstBelegt() == false && brett.getBrettFeld(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.SCHWARZ, i, j, brett, false));

						brett.getBrettFeld(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);
					}
				}
			}
		} else {
			for (int i = brett.getBrettGroesse() - 1; i > brett.getBrettGroesse() / 2; i--) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeld(i, j).getIstBelegt() == false && brett.getBrettFeld(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.WEIß, i, j, brett, false));
						brett.getBrettFeld(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);

					}
				}
			}
		}
	}

	/**
	 * ueberprueft ob der gewollte Zug gueltig ist
	 */

	// (fig.getPosX() fig.getPosY())

	// eingabe ist die fig pos diese geht an das spielbrett, dieses gibt das feld
	// zurueck an der pos x,y. uber getposX u posY bekommt man dann index werte.
	private boolean zugGueltig(int x, int y, boolean istDame) {

		int xPosFig;
		int yPosFig;

		int xPosNeu1;
		int yPosNeu1;

		int xPosNeu2;
		int yPosNeu2;

		int xPosNeu3;
		int yPosNeu3;

		int xPosNeu4;
		int yPosNeu4;

		xPosFig = brett.getBrettFeld(x, y).getPosX();
		yPosFig = brett.getBrettFeld(x, y).getPosY();

		if (istDame) {
			xPosNeu1 = xPosFig + 1;
			yPosNeu1 = yPosFig + 1;

			xPosNeu2 = xPosFig - 1;
			yPosNeu2 = yPosFig - 1;

			xPosNeu3 = xPosFig + 1;
			yPosNeu3 = yPosFig - 1;

			xPosNeu4 = xPosFig - 1;
			yPosNeu4 = yPosFig + 1;

			if (xPosNeu1 > 0 && xPosNeu1 < brett.getBrettGroesse() && yPosNeu1 > 0 && yPosNeu1 < brett.getBrettGroesse()) {// OK

			} else if (xPosNeu2 > 0 && xPosNeu2 < brett.getBrettGroesse() && yPosNeu2 > 0 && yPosNeu2 < brett.getBrettGroesse()) {// OK

			} else if (xPosNeu3 > 0 && xPosNeu3 < brett.getBrettGroesse() && yPosNeu3 > 0 && yPosNeu3 < brett.getBrettGroesse()) {// OK

			} else if (xPosNeu4 > 0 && xPosNeu4 < brett.getBrettGroesse() && yPosNeu4 > 0 && yPosNeu4 < brett.getBrettGroesse()) {// OK

			}

		} else {// NICH OK

			// STEIN
			xPosNeu1 = xPosFig + 1;
			yPosNeu1 = yPosFig + 1;

			xPosNeu2 = xPosFig - 1;
			yPosNeu2 = yPosFig + 1;
		}

		if (xPosNeu1 > 0 && xPosNeu1 < brett.getBrettGroesse() && yPosNeu1 > 0 && yPosNeu1 < brett.getBrettGroesse()) {

		} else if (xPosNeu2 > 0 && xPosNeu2 < brett.getBrettGroesse() && yPosNeu2 > 0 && yPosNeu2 < brett.getBrettGroesse()) {

		} else {

		}

		return false;

	}

	/**
	 * speichern
	 */
	private void speichern() {

	}

	/**
	 * laden
	 */
	private void laden() {

	}

	/*
	 * System.out.println("Spielernamen eingeben\n"); name = reader.readLine();
	 * System.out.println("spielerfarbe eingeben (s für schwarz w für weiß)" );
	 * 
	 * do { farbe = reader.readLine(); // farben switch switch (farbe) {
	 * 
	 * // case 1 farbe case "s":
	 * 
	 * System.out.println(
	 * "ist der Spieler eine Ki oder ein Mensch? (m für Mensch k für Ki)" ); do {
	 * ki = reader.readLine(); // innerrer ki switch switch (ki) { // case 1 ki
	 * case "m": Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ, false); break;
	 * // case 2 ki case "k": Spieler s2 = new Spieler(name, FarbEnum.SCHWARZ,
	 * true); break; // default für ki default:
	 * System.out.println("weder k noch m eingeben."); } } while (!ki.equals("m")
	 * || (!ki.equals("k")));
	 * 
	 * break; // case 2 farbe case "w": System.out.println(
	 * "ist der Spieler eine Ki oder ein Mensch? (m für Mensch k für Ki)" ); ki =
	 * reader.readLine(); // innerer ki switch do { switch (ki) { // case 1 ki
	 * case "m": Spieler s1 = new Spieler(name, FarbEnum.SCHWARZ, false); break;
	 * // case 2 ki case "k": Spieler s2 = new Spieler(name, FarbEnum.SCHWARZ,
	 * true); break; // default für ki default:
	 * System.out.println("weder k noch m eingeben."); } } while (!ki.equals("m")
	 * || (!ki.equals("k"))); break;
	 * 
	 * // default für farbe default:
	 * System.out.println("weder s noch w eingeben.");
	 * 
	 * } } while (!farbe.equals("w") || (!farbe.equals("s")));
	 */

}
