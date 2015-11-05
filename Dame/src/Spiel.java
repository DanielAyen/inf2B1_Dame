import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spiel implements iBediener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632008787864445195L;
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

	private static iDatenzugriff daten;
	private boolean spielAufgebaut = false;
	private int spielerAnzahl = 0;
	private String name;
	private String farbe;
	private String ki;
	private boolean spiellaeuft = false;
	private boolean schwarzvergeben = false;
	private boolean weissvergeben = false;
	private boolean geschlagen = false;

	private Spielbrett brett;
	private Spieler spieler;
	private FarbEnum farbeAmZug;
	private Spieler s1;
	private Spieler s2;
	private KI_Dame k1;
	private KI_Dame k2;
	private String dateiname = "csv";

	private char startC;
	private char endC;
	private int startI;
	private int endI;

	public void spielStarten() {
		System.out.println("Bitte erstelle zuerst ein neues Spielbrett. Falls du Hilfe benoetigst gebe 'help' in die konsole ein");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String eingabe = "";

			do {
				eingabe = reader.readLine().toLowerCase();
				switch (eingabe) {
				// TEST CASES: //

				// case "entf":
				// figurEntfernen(s1.getAlleFiguren().get(0));
				// break;
				case "win":
					spielerHatGewonnen(FarbEnum.SCHWARZ);
					break;

				// TEST CASES ENDE //

				case "help":
					System.out.println("aufbauen : Erstellt ein Spielbrett, wird zum spielen benoetigt.");
					System.out.println("erstellen : Erlaubt dir einen Spieler zu erstellen, es werden 2 Spieler zum spielen benoetigt.");
					System.out.println("start: Startet das Spiel, es wird ein erstelltes Spielbrett und zwei Spieler benoetigt.");
					System.out.println("beenden : Das Spiel wird geschlossen.");
					System.out.println("ziehen : Erlaubt dir eine Spielfigur zu bewegen. Nicht moeglich solange das Spiel nicht laeuft.");
					System.out.println("anzeigen : Zeigt dir das Spielbrett.");
					System.out.println("ser : erlaubt es dir das Spiel zu serialisieren.");
					System.out.println("csv : erlaubt es dir das Spiel als CSV Datei zu speichern.");
					System.out.println("laden : erlaubt es dir ein Spielstand zu laden.");
					System.out.println("ki ziehen : Lässt die KI ziehen.");
					break;
				// zum erstellen des spielfelds
				case "aufbauen":
					if (spielAufgebaut) {
						System.out.println("Das Spielbrett wurde bereits aufgebaut!");
						break;
					}
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
					// zum aufrufen der Ki
				case "ki ziehen":
					if (!spiellaeuft) {
						System.out.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
						break;
					}
					if (k1 == null && k2 == null) {
						System.out.println("Kein Spieler ist eine KI");
						break;
					}
					System.out.println("Spielerfarbe eingeben (s fuer schwarz w fuer weiss)\n");
					String kifarbe = reader.readLine();
					if (kifarbe.equals("s")) {
						if (k1 == null) {
							System.out.println("Schwarz ist keine KI");
							break;
						}
						int[] zuge = k1.zug();
						if (zuge == null) {
							System.out.println("KI findet keine Züge");
							break;
						}
						figurBewegen((char) zuge[0], zuge[1], (char) zuge[2], zuge[3]);
						break;
					}
					if (kifarbe.equals("w")) {
						if (k2 == null) {
							System.out.println("Weiß ist keine KI");
							break;
						}
						int[] zuge = k2.zug();
						if (zuge == null) {
							System.out.println("KI findet keine Züge");
							break;
						}
						figurBewegen((char) zuge[0], zuge[1], (char) zuge[2], zuge[3]);
					}
					break;

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
				case "erstellen":
					if (!spielAufgebaut) {
						System.out.println("Du musst zuerst ein Spielbrett aufbauen! (aufbauen)");
						break;
					}
					if (spielerAnzahl == 2) {
						System.out.println("Es gibt bereits zwei Spieler.");
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

									s1 = new Spieler(name, FarbEnum.SCHWARZ, false);
									schwarzvergeben = true;
									spielerAnzahl++;
									System.out.println(s1);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s1, brett);
									break;
								} else {
									s1 = new Spieler(name, FarbEnum.SCHWARZ, true);
									k1 = new KI_Dame(s1, brett);
									spielerAnzahl++;
									schwarzvergeben = true;
									System.out.println(k1);
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
									s2 = new Spieler(name, FarbEnum.WEIß, false);
									weissvergeben = true;
									spielerAnzahl++;
									System.out.println(s2);
									System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
									erstelleFiguren(s2, brett);
									break;
								} else {
									s2 = new Spieler(name, FarbEnum.WEIß, true);
									k2 = new KI_Dame(s2, brett);
									weissvergeben = true;
									spielerAnzahl++;
									System.out.println(k2);
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

						setAmZug(FarbEnum.WEIß);
						System.out.println("Der Spieler mit der Farbe " + getAmZug() + " beginnt");
						// TODO

					} else {
						System.err.println("Das Spiel kann noch nicht gestartet werden!!");
					}
					break;
				case "ziehen":
					if (!spiellaeuft) {
						System.out.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
						break;
					} else {
						brett.display();

						// Startpos fragen
						System.out.println("\n Bitte gebe deine Startposition ein.");

						String startp = reader.readLine();
						// ///
						if (startp.length() == 2 || startp.length() == 3) {

							if (!charPruefenUndSetzenA(startp)) {
								System.out.println("Fehler in der Eingabe (Buchstabe)! Zurueck im Hauptmenue.");
								startC = 0;
								startI = 0;
								endC = 0;
								endI = 0;
								break;
							}

							if (!zahlPruefenUndSetzenA(startp)) {
								System.out.println("Fehler in der Eingabe (Zahl)! Zurueck im Hauptmenue.");
								startC = 0;
								startI = 0;
								endC = 0;
								endI = 0;
								break;
							}

						} else {
							System.out.println("So kannst du nicht ziehen! Zurueck im Hauptmenue.");
							startC = 0;
							startI = 0;
							endC = 0;
							endI = 0;
							break;
						}
						// TODO überprufen ob startpos figur gleiche farbe wie der spieler
						// der am zug ist hat. wenn nicht der fall dann startp ungültig.

						// if (!(getAmZug() == brett.getBrettFeldSchachnotation(startC,
						// startI).getSpielfigur().getFarbe())) {
						// System.out.println("Du kannst nur deine eigenen Figuren bewegen! Zurueck im Hauptmenue.");
						// break;
						// }

						if (s1.getAlleFiguren().size() == 1 && s1.getFarbe() == getAmZug() && moeglicheZuege(startC, startI) == 0) {
							// spieler 2 gewinnt
							spielerHatGewonnen(s2.getFarbe());
							break;
						}

						if (s2.getAlleFiguren().size() == 1 && s2.getFarbe() == getAmZug() && moeglicheZuege(startC, startI) == 0) {
							// spieler 1 gewinnt
							spielerHatGewonnen(s1.getFarbe());
							break;
						}

						if (moeglicheZuege(startC, startI) == 0) {
							System.out.println("Mit dieser Figur sind keine Zuege moeglich! Zurueck im Hauptmenue.");

							break;
						}

						// Endpos fragen
						System.out.println("Eingegebene Startposition: " + startC + startI + "\n");

						System.out.println("Bitte gebe deine Endposition ein.");

						String endp = reader.readLine();

						if (endp.length() == 2 || endp.length() == 3) {

							if (!charPruefenUndSetzenN(endp)) {
								System.out.println("Fehler in der Eingabe (Buchstabe)! Zurueck im Hauptmenue.");
								startC = 0;
								startI = 0;
								endC = 0;
								endI = 0;

								break;
							}

							if (!zahlPruefenUndSetzenN(endp)) {
								System.out.println("Fehler in der Eingabe (Zahl)! Zurueck im Hauptmenue.");
								startC = 0;
								startI = 0;
								endC = 0;
								endI = 0;

								break;
							}

						} else {
							System.out.println("So kannst du nicht ziehen! Zurueck im Hauptmenue.");
							startC = 0;
							startI = 0;
							endC = 0;
							endI = 0;

							break;
						}
						System.out.println("Eingegebene EndPosition: " + endC + endI + "\n");

						figurBewegen(startC, startI, endC, endI);
						startC = 0;
						startI = 0;
						endC = 0;
						endI = 0;

						dameWerden();
						brett.display();
						System.out.println("\n");

					}
					break;
				// beendet das Spiel
				case "beenden":
					System.out.println("\n\n\t\tDas Spiel wird beendet.");
					break;
				// speichern
				case "ser":
					speichernSerial(this);
					break;
				case "csv":
					speichernCSV(dateiname);
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
	 * prueft den alten positions char
	 * 
	 * @param posi
	 *          String uebergabe
	 * @return gibt einen char wert zurueck
	 */
	private boolean charPruefenUndSetzenA(String posi) {

		int brettGroesse = brett.getBrettGroesse();

		switch (brettGroesse) {

		case 8:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				startC = posi.charAt(0);
				return true;
			}
			return false;

		case 10:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("I") || posi.startsWith("i")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("J") || posi.startsWith("j")) {
				startC = posi.charAt(0);
				return true;
			}
			return false;

		case 12:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("I") || posi.startsWith("i")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("J") || posi.startsWith("j")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("K") || posi.startsWith("k")) {
				startC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("L") || posi.startsWith("l")) {
				startC = posi.charAt(0);
				return true;
			}
			return false;

		}
		return false;
	}

	/**
	 * prueft die eingegebene zahl
	 * 
	 * @param posi
	 *          eingabe ueber string
	 * @return gibt einen int wert zurueck
	 */
	private boolean zahlPruefenUndSetzenA(String posi) {

		int brettGroesse = brett.getBrettGroesse();

		switch (brettGroesse) {

		case 8:

			if (posi.endsWith("1")) {
				startI = 1;

			}
			if (posi.endsWith("2")) {
				startI = 2;

			}
			if (posi.endsWith("3")) {
				startI = 3;

			}
			if (posi.endsWith("4")) {
				startI = 4;

			}
			if (posi.endsWith("5")) {
				startI = 5;

			}
			if (posi.endsWith("6")) {
				startI = 6;

			}
			if (posi.endsWith("7")) {
				startI = 7;

			}
			if (posi.endsWith("8")) {
				startI = 8;

			}
			if (startI != 0)
				return true;

			return false;

		case 10:

			if (posi.endsWith("1")) {
				startI = 1;

			}
			if (posi.endsWith("2")) {
				startI = 2;

			}
			if (posi.endsWith("3")) {
				startI = 3;

			}
			if (posi.endsWith("4")) {
				startI = 4;

			}
			if (posi.endsWith("5")) {
				startI = 5;

			}
			if (posi.endsWith("6")) {
				startI = 6;

			}
			if (posi.endsWith("7")) {
				startI = 7;

			}
			if (posi.endsWith("8")) {
				startI = 8;

			}
			if (posi.endsWith("9")) {
				startI = 9;

			}
			if (posi.endsWith("10")) {
				startI = 10;

			}
			if (startI != 0)
				return true;

			return false;

		case 12:

			if (posi.endsWith("1")) {
				startI = 1;

			}
			if (posi.endsWith("2")) {
				startI = 2;

			}
			if (posi.endsWith("3")) {
				startI = 3;

			}
			if (posi.endsWith("4")) {
				startI = 4;

			}
			if (posi.endsWith("5")) {
				startI = 5;

			}
			if (posi.endsWith("6")) {
				startI = 6;

			}
			if (posi.endsWith("7")) {
				startI = 7;

			}
			if (posi.endsWith("8")) {
				startI = 8;

			}
			if (posi.endsWith("9")) {
				startI = 9;

			}
			if (posi.endsWith("10")) {
				startI = 10;

			}
			if (posi.endsWith("11")) {
				startI = 11;

			}
			if (posi.endsWith("12")) {
				startI = 12;

			}
			if (startI != 0)
				return true;

			return false;
		}
		return false;
	}

	/**
	 * prueft den neuen posi char
	 * 
	 * @param posi
	 *          eingabe ueber String
	 * @return gibt char zurueck
	 */
	private boolean charPruefenUndSetzenN(String posi) {
		int brettGroesse = brett.getBrettGroesse();

		switch (brettGroesse) {

		case 8:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				endC = posi.charAt(0);
				return true;
			}
			return false;

		case 10:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("I") || posi.startsWith("i")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("J") || posi.startsWith("j")) {
				endC = posi.charAt(0);
				return true;
			}
			return false;

		case 12:

			if (posi.startsWith("A") || posi.startsWith("a")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("B") || posi.startsWith("b")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("C") || posi.startsWith("c")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("D") || posi.startsWith("d")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("E") || posi.startsWith("e")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("F") || posi.startsWith("f")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("G") || posi.startsWith("g")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("H") || posi.startsWith("h")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("I") || posi.startsWith("i")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("J") || posi.startsWith("j")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("K") || posi.startsWith("k")) {
				endC = posi.charAt(0);
				return true;
			}
			if (posi.startsWith("L") || posi.startsWith("l")) {
				endC = posi.charAt(0);
				return true;
			}
			return false;

		}
		return false;
	}

	/**
	 * neue zahl in int wandlung und pruefung
	 * 
	 * @param posi
	 *          bekommt einen String
	 * @return gibt ein char zurueck
	 */
	private boolean zahlPruefenUndSetzenN(String posi) {
		int brettGroesse = brett.getBrettGroesse();

		switch (brettGroesse) {

		case 8:

			if (posi.endsWith("1")) {
				endI = 1;

			}
			if (posi.endsWith("2")) {
				endI = 2;

			}
			if (posi.endsWith("3")) {
				endI = 3;

			}
			if (posi.endsWith("4")) {
				endI = 4;

			}
			if (posi.endsWith("5")) {
				endI = 5;

			}
			if (posi.endsWith("6")) {
				endI = 6;

			}
			if (posi.endsWith("7")) {
				endI = 7;

			}
			if (posi.endsWith("8")) {
				endI = 8;

			}
			if (startI != 0)
				return true;

			return false;

		case 10:

			if (posi.endsWith("1")) {
				endI = 1;

			}
			if (posi.endsWith("2")) {
				endI = 2;

			}
			if (posi.endsWith("3")) {
				endI = 3;

			}
			if (posi.endsWith("4")) {
				endI = 4;

			}
			if (posi.endsWith("5")) {
				endI = 5;

			}
			if (posi.endsWith("6")) {
				endI = 6;

			}
			if (posi.endsWith("7")) {
				endI = 7;

			}
			if (posi.endsWith("8")) {
				endI = 8;

			}
			if (posi.endsWith("9")) {
				endI = 9;

			}
			if (posi.endsWith("10")) {
				endI = 10;

			}
			if (startI != 0)
				return true;

			return false;

		case 12:

			if (posi.endsWith("1")) {
				endI = 1;

			}
			if (posi.endsWith("2")) {
				endI = 2;

			}
			if (posi.endsWith("3")) {
				endI = 3;

			}
			if (posi.endsWith("4")) {
				endI = 4;

			}
			if (posi.endsWith("5")) {
				endI = 5;

			}
			if (posi.endsWith("6")) {
				endI = 6;

			}
			if (posi.endsWith("7")) {
				endI = 7;

			}
			if (posi.endsWith("8")) {
				endI = 8;

			}
			if (posi.endsWith("9")) {
				endI = 9;

			}
			if (posi.endsWith("10")) {
				endI = 10;

			}
			if (posi.endsWith("11")) {
				endI = 11;

			}
			if (posi.endsWith("12")) {
				endI = 12;

			}
			if (startI != 0)
				return true;

			return false;

		}
		return false;
	}

	/**
	 * erste Methode erstellt Spielbrett,
	 */
	public void spielBauen(int x) {
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
	private void figurBewegen(char xa, int ya, char xn, int yn) {

		// if (xa > brett.getBrettGroesse() || ya > brett.getBrettGroesse()) {
		// System.out.println("Außerhalb des Brett's gibt es keine Figuren!");
		// return;
		// }

		// if (xn > brett.getBrettGroesse() || yn > brett.getBrettGroesse() || xa <
		// brett.getBrettGroesse() || ya < brett.getBrettGroesse()) {
		// System.out.println("Du kannst doch nicht vom Spielbrett springen!");
		// return;
		// }

		if (!brett.getBrettFeldSchachnotation(xa, ya).getIstBelegt()) {
			System.out.println("Ohne Figur kannst du nicht ziehen!");
			return;
		}
		if (!brett.getBrettFeldSchachnotation(xn, yn).getIstSchwarz()) {
			System.out.println("Du kannst nur auf schwarze Felder springen!");
			return;
		}
		if (brett.getBrettFeldSchachnotation(xn, yn).getIstBelegt()) {
			System.out.println(brett.getBrettFeldSchachnotation(xn, yn).getSpielfigur());
			System.out.println("Du kannst keine andere Figur besteigen!");
			return;
		}
		int x = brett.getBrettFeldSchachnotation(xa, ya).getPosX() - brett.getBrettFeldSchachnotation(xn, yn).getPosX();
		int y = brett.getBrettFeldSchachnotation(xa, ya).getPosY() - brett.getBrettFeldSchachnotation(xn, yn).getPosY();

		if (x - y > 2) {
			figurSchlagen(xa, ya, xn, yn, brett.getBrettFeldSchachnotation(xa, ya).getSpielfigur());

			return;
		}

		Spielfigur fig = brett.getBrettFeldSchachnotation(xa, ya).getSpielfigur();

		FarbEnum farbe = fig.getFarbe();
		int tempx = brett.getBrettFeldSchachnotation(xa, ya).getPosX() - brett.getBrettFeldSchachnotation(xn, yn).getPosX();
		switch (farbe) {
		case SCHWARZ:
			if (fig.getDame(fig)) {

				if (tempx == -1 || tempx == 1) {// zieh möglichkeiten dame

					brett.getBrettFeldSchachnotation(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldSchachnotation(xn, yn).setSpielfigur(fig);

				} else {
					figurSchlagen(xa, ya, xn, yn, fig);
				}

			} else {
				if (tempx == -1) {// zieh möglichkeiten stein

					brett.getBrettFeldSchachnotation(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldSchachnotation(xn, yn).setSpielfigur(fig);

				} else {
					figurSchlagen(xa, ya, xn, yn, fig);
				}

			}

			break;

		case WEIß:

			if (fig.getDame(fig)) {

				if (tempx == -1 || tempx == 1) {// zieh möglivchkeiten dame

					brett.getBrettFeldSchachnotation(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldSchachnotation(xn, yn).setSpielfigur(fig);

				} else {
					figurSchlagen(xa, ya, xn, yn, fig);
				}

			} else {
				if (tempx == 1) {// zieh möglichkeiten stein

					brett.getBrettFeldSchachnotation(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldSchachnotation(xn, yn).setSpielfigur(fig);

				} else {
					figurSchlagen(xa, ya, xn, yn, fig);
				}

			}

			break;
		}

	}

	/**
	 * falls mit der derzeitigen figur noch weitere zuege moeglich sind
	 */
	private void figurWeiterbewegen(Spielfigur fig) {

	}

	/**
	 * wenn eine figur die eind pos erreicht hat wird sie zur dame(true)
	 */
	private void dameWerden() {
		// TODO

		for (int i = 0; i < brett.getBrettGroesse(); i++) {

			for (int j = 0; j < brett.getBrettGroesse(); j++) {
				if (brett.getBrettFeldIndex(i, j).getSpielfigur() == null) {
					// Tu nichts falls null, ansonsten...
				} else {

					Spielfigur fig = brett.getBrettFeldIndex(i, j).getSpielfigur();

					int x = fig.getPosX();
					int y = fig.getPosY();
					int brettgroesse = 0;
					FarbEnum farbe = fig.getFarbe();
					brettgroesse = brett.getBrettGroesse() - 1;
					// brettgroesse -1 da das brett ja 8/10/12
					// sein kann aber index nur 7/9/11 ist
					switch (farbe) {

					case SCHWARZ:// müssen oben ankommen also bei 8/10/12////// 0|0 =A1
						if (x == brettgroesse)
							fig.setDame(true);

						break;

					case WEIß:// müssen unten ankommen 1
						if (x == 0)
							fig.setDame(true);

						break;
					}
				}

			}

		}

	}

	/**
	 * Einen gegnerischen stein aus dem Spiel werfen
	 */
	private void figurSchlagen(char altepx, int altepy, char neuepx, int neuepy, Spielfigur fig) {

		int alteX = brett.getBrettFeldSchachnotation(altepx, altepy).getPosX();
		int alteY = brett.getBrettFeldSchachnotation(altepx, altepy).getPosY();

		int neueX = brett.getBrettFeldSchachnotation(neuepx, neuepy).getPosX();
		int neueY = brett.getBrettFeldSchachnotation(neuepx, neuepy).getPosY();

		int diffX = alteX - neueX;
		int diffY = alteY - neueY;

		FarbEnum farbe = fig.getFarbe();

		if (brett.getBrettFeldIndex(neueX, neueY).getIstSchwarz()) {
			if (!brett.getBrettFeldIndex(neueX, neueY).getIstBelegt()) {

				switch (farbe) {

				case SCHWARZ:
					if (fig.getDame(fig)) {// schlagen in 4richtungen mögl
						// alte pos minus neue pos gibt mittleres feld

						if (diffX < 0 && diffY < 0) {
							System.out.println("nach oben rechts s1");
							// RICHTUNG NACH OBEN RECHTS
							if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								System.out.println("nach oben rechts s2");
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}
						// RICHTUNG NACH OBEN LINKS
						if (diffX < 0 && diffY > 0) {
							System.out.println("nach oben links s1");
							if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								System.out.println("nach oben links s2");
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}
						// RICHTUNG NACH UNTEN RECHTS
						if (diffX > 0 && diffY < 0) {
							System.out.println("nach unten rechts s1");
							if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								System.out.println("nach unten rechts s2");
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}
						// RICHTUNG NACH UNTEN LINKS
						if (diffX > 0 && diffY > 0) {
							System.out.println("nach unten links s1");
							if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								System.out.println("nach unten links s2");
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

					} else {

						if (neueX < alteX) {// schlagen nur nach vorne mögl

							System.out.println("Nach hinten schlagen nur mit einer Dame möglich.");
							return;
						}

						if (diffX < 0 && diffY < 0) {

							if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

						if (diffX < 0 && diffY > 0) {

							if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

					}

					break;

				case WEIß:

					if (fig.getDame(fig)) {// schlagen in 4richtungen mögl
						// alte pos minus neue pos gibt mittleres feld

						if (diffX < 0 && diffY < 0) {

							if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

						if (diffX < 0 && diffY > 0) {

							if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

						if (diffX > 0 && diffY < 0) {

							if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

						if (diffX > 0 && diffY > 0) {

							if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

					} else {

						if (neueX > alteX) {// schlagen nur nach vorne mögl

							System.out.println("Nach hinten schlagen nur mit einer Dame möglich.");
							return;
						}

						if (diffX > 0 && diffY > 0) {

							if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;
							}

						}

						if (diffX > 0 && diffY < 0) {

							if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getIstBelegt() && brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
								// prüfen ob feld zwischen alt und neu leer ist wenn nicht dann
								// farbe prüfen (Wenn alles korrekt die
								// figurEntfernen()aufrufen)
								figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur());
								brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
								brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
								geschlagen = true;

							}

						}

					}

					break;

				}

			} else {
				System.out.println("Zielfeld ist besetzt!");
			}
		} else {

			System.out.println("Unmöglich, das Zielfeld ist nicht schwarz.");
			return;
		}

	}

	/**
	 * Methode zum entfernen einer Spielfigur vom brett/feld und vom Spieler
	 * 
	 * @param spielfigur
	 *          die zu entfernende Figur
	 */
	private void figurEntfernen(Spielfigur spielfigur) {

		if (s1.getAlleFiguren().contains(spielfigur)) {
			s1.getAlleFiguren().remove(spielfigur);

			brett.getBrettFeldIndex(spielfigur.getPosX(), spielfigur.getPosY()).removeSpielfigur(spielfigur);

			if (s1.getAlleFiguren().isEmpty())
				spielerHatGewonnen(s2.getFarbe());

		} else {
			if (s2.getAlleFiguren().contains(spielfigur)) {
				s2.getAlleFiguren().remove(spielfigur);

				brett.getBrettFeldIndex(spielfigur.getPosX(), spielfigur.getPosY()).removeSpielfigur(spielfigur);

				if (s2.getAlleFiguren().isEmpty())
					spielerHatGewonnen(s1.getFarbe());

			}

		}

	}

	/**
	 * pusten halt
	 */
	private void pusten() {

	}

	/**
	 * setzt den derzeitigen Spieler der am zug ist
	 */
	private void setAmZug(FarbEnum farbe) {
		farbeAmZug = farbe;
	}

	/**
	 * gibt den derzeitigen Spieler der am zug ist
	 */
	private FarbEnum getAmZug() {

		return farbeAmZug;
	}

	/**
	 * der nächste spieler der am zug ist vlt unnoetig
	 */
	private void naechster() {

	}

	/**
	 * zug beenden
	 */
	private void zugBeenden() {// TODO
		if (getAmZug() == FarbEnum.SCHWARZ) {
			setAmZug(FarbEnum.WEIß);
		} else {
			setAmZug(FarbEnum.SCHWARZ);
		}

	}

	private void spielerHatGewonnen(FarbEnum farbe) {

		if (s1.getFarbe() == farbe) {

			System.out.println("Spieler " + s1.getName() + " hat das Spiel gewonnen!");
			try {
				Thread.sleep(1500);

				System.err.println("\n	(╯°□°)╯︵ ┻━┻ \n");
				System.out.println("Spieler " + s2.getName() + " flipped the table!");

				// throw new RuntimeException("Spieler " + s2.getName() +
				// " flipped the table!");

			} catch (InterruptedException e) {

			}

			System.exit(0);

		} else {

			System.out.println("Spieler " + s2.getName() + " hat das Spiel gewonnen!");
			try {
				Thread.sleep(1500);

				System.err.println("\n	(╯°□°)╯︵ ┻━┻ \n");
				System.out.println(" Spieler " + s1.getName() + " flipped the table!");

				// throw new RuntimeException("Spieler " + s2.getName() +
				// " flipped the table!");

			} catch (InterruptedException e) {

			}

			System.exit(0);

		}

	}

	/**
	 * methode zum erstellen der Spielfiguren
	 * 
	 * @param spieler
	 *          der spieler wessen figuren erstellt werden
	 * @param brett
	 *          das Spielbrett woruf die figuren kommen
	 */
	private void erstelleFiguren(Spieler spieler, Spielbrett brett) {

		if (spieler.getFarbe() == FarbEnum.SCHWARZ) {
			for (int i = 0; i < brett.getBrettGroesse() / 2 - 1; i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false && brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.SCHWARZ, false));
						fig.setPosX(i);
						fig.setPosY(j);

						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);
					}
				}
			}
		} else {
			for (int i = brett.getBrettGroesse() - 1; i > brett.getBrettGroesse() / 2; i--) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false && brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.WEIß, false));
						fig.setPosX(i);
						fig.setPosY(j);
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);

					}
				}
			}
		}

	}

	// if (spieler.getFarbe() == FarbEnum.SCHWARZ) {
	// for (int i = 0; i < brett.getBrettGroesse() / 2 - 1; i++) {
	// for (int j = 0; j < brett.getBrettGroesse(); j++) {
	// if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false &&
	// brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {
	//
	// Spielfigur fig = (new Spielfigur(FarbEnum.SCHWARZ, false));
	//
	// brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
	//
	// spieler.addSpielfigur(fig);
	// }
	// }
	// }
	// } else {
	// for (int i = brett.getBrettGroesse() - 1; i > brett.getBrettGroesse() /
	// 2; i--) {
	// for (int j = 0; j < brett.getBrettGroesse(); j++) {
	// if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false &&
	// brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {
	//
	// Spielfigur fig = (new Spielfigur(FarbEnum.WEIß, false));
	// brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
	//
	// spieler.addSpielfigur(fig);
	//
	// }
	// }
	// }
	// }

	/**
	 * ueberprueft die moeglichkeit an zuegen mit der gewaehlten figur
	 */

	private int moeglicheZuege(char x, int y) {

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

		xPosFig = brett.getBrettFeldSchachnotation(x, y).getPosX();
		yPosFig = brett.getBrettFeldSchachnotation(x, y).getPosY();
		Spielfigur fig = brett.getBrettFeldSchachnotation(x, y).getSpielfigur();

		xPosNeu1 = xPosFig + 1;
		yPosNeu1 = yPosFig + 1;

		xPosNeu2 = xPosFig - 1;
		yPosNeu2 = yPosFig - 1;

		xPosNeu3 = xPosFig + 1;
		yPosNeu3 = yPosFig - 1;

		xPosNeu4 = xPosFig - 1;
		yPosNeu4 = yPosFig + 1;

		int anzMoeglichkeiten = 0;

		if (fig.getDame(fig) || fig.getFarbe() == FarbEnum.SCHWARZ) {
			if (xPosNeu1 >= 0 && xPosNeu1 <= brett.getBrettGroesse() - 1 && yPosNeu1 >= 0 && yPosNeu1 <= brett.getBrettGroesse() - 1) {
				if (!brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getIstBelegt()) {
					anzMoeglichkeiten++;
				} else {

					if (brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getSpielfigur().getFarbe() != getAmZug()) {
						if (!brett.getBrettFeldIndex(xPosNeu1 + 1, yPosNeu1 + 1).getIstBelegt()) {
							anzMoeglichkeiten++;
						}
					}
				}
			}
		}

		if (fig.getDame(fig) || fig.getFarbe() == FarbEnum.WEIß) {
			if (xPosNeu2 >= 0 && xPosNeu2 <= brett.getBrettGroesse() - 1 && yPosNeu2 >= 0 && yPosNeu2 <= brett.getBrettGroesse() - 1) {
				if (!brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getIstBelegt()) {
					anzMoeglichkeiten++;
				} else {

					if (brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getSpielfigur().getFarbe() != getAmZug()) {
						if (!brett.getBrettFeldIndex(xPosNeu2 + 1, yPosNeu2 + 1).getIstBelegt()) {
							anzMoeglichkeiten++;
						}
					}
				}
			}
		}

		if (fig.getDame(fig) || fig.getFarbe() == FarbEnum.SCHWARZ) {
			if (xPosNeu3 >= 0 && xPosNeu3 <= brett.getBrettGroesse() - 1 && yPosNeu3 >= 0 && yPosNeu3 <= brett.getBrettGroesse() - 1) {
				if (!brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getIstBelegt()) {
					anzMoeglichkeiten++;
				} else {

					if (brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getSpielfigur().getFarbe() != getAmZug()) {
						if (!brett.getBrettFeldIndex(xPosNeu3 + 1, yPosNeu3 + 1).getIstBelegt()) {
							anzMoeglichkeiten++;
						}
					}
				}
			}
		}

		if (fig.getDame(fig) || fig.getFarbe() == FarbEnum.WEIß) {
			if (xPosNeu4 >= 0 && xPosNeu4 <= brett.getBrettGroesse() - 1 && yPosNeu4 >= 0 && yPosNeu4 <= brett.getBrettGroesse() - 1) {
				if (!brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getIstBelegt()) {
					anzMoeglichkeiten++;
				} else {

					if (brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getSpielfigur().getFarbe() != getAmZug()) {
						if (!brett.getBrettFeldIndex(xPosNeu4 + 1, yPosNeu4 + 1).getIstBelegt()) {
							anzMoeglichkeiten++;
						}
					}
				}
			}
		}

		return anzMoeglichkeiten;

	}

	/**
	 * 
	 * @param daten
	 *          uebergabe
	 */
	public static void setdZugriff(iDatenzugriff daten) {
		Spiel.daten = daten;
	}

	/**
	 * 
	 * @return gibt daten zurueck
	 */
	public static iDatenzugriff getdZugriff() {
		return daten;
	}

	/**
	 * serializiert das Spiel
	 */
	public void speichernSerial(Spiel s) {
		try {
			setdZugriff(new DatenzugriffSerialisiert());
			File p = new File(s + ".ser");
			if (p.length() > 0) {
				p.delete();
				p = new File(s + ".ser");
			}
			getdZugriff().oeffnen(p);
			getdZugriff().speichern(p, this);
			System.out.println("Das Spiel wurde gespeichert: " + p.getName());
			getdZugriff().schliessen(p);
		} catch (Exception e) {
			System.out.println("Speichern Serialisiert fehlgeschlagen!");
		}

	}

	/**
	 * laden
	 * 
	 * @return
	 */
	public static Spiel ladenSerialisiert(String string) {
		try {
			setdZugriff(new DatenzugriffSerialisiert());
			File f = new File(string);
			getdZugriff().oeffnen(f);
			Spiel o = (Spiel) getdZugriff().laden(f);
			System.out.println("Das Spiel " + " wurde geladen.");
			getdZugriff().schliessen(f);
			return o;
		} catch (Exception e) {
			System.out.println("Laden Serialisiert fehlgeschlagen!");
			return null;
		}

	}

	/**
	 * speichert das Spiel
	 * 
	 * @param string
	 *          name der Datei
	 */
	public void speichernCSV(String string) {
		try {
			setdZugriff(new DatenzugriffCSV());
			File f = new File(string + ".csv");
			File p = new File(f.getAbsolutePath());
			// if (f.length() > 0) {
			// p = new File(f.getAbsolutePath());
			// f.delete();
			// }

			getdZugriff().oeffnen(p);
			getdZugriff().speichern(p, Spieler.getAnzahl()); // AnzahlSpieler

			getdZugriff().speichern(p, spieler.toString()); // Spielername
			getdZugriff().speichern(p, spieler.getAlleFiguren()); // Liste der Figuren
			getdZugriff().speichern(p, spieler.getIsIstAmZug());
			for (int i = 0; i < spieler.getAlleFiguren().size(); i++) {
				getdZugriff().speichern(p, spieler.getAlleFiguren().get(i)); // Positionen
																																			// der
																																			// Figuren
			}
			// TODO

			System.out.println("Das Spiel wurde gespeichert: " + p.getName());
			getdZugriff().schliessen(p);
		} catch (Exception e) {
			System.out.println("Speichern CSV fehlgeschlagen.");
		}
	}

	/**
	 * die toString.
	 */
	@Override
	public String toString() {
		return "NewGame";
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
