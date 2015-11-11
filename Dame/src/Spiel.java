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

	private int startC;
	private int endC;
	private int startI;
	private int endI;

	public void spielStarten() {

		// ///////////////////////////////////////////
		System.out.println("Bitte gebe die gewuenschte Spielbrett Groesse ein. ( 8 , 10 , 12 )");
		aufbauen(12);
		anzeigen();

		// ///////////////////////////////////////////
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String eingabe = "";

			do {
				eingabe = reader.readLine().toLowerCase();
				switch (eingabe) {
				// TEST CASES: //

				case "entf":
					brett.getBrettFeldSchachnotation('a', 11).removeSpielfigur(brett.getBrettFeldSchachnotation('a', 11).getSpielfigur());
					break;
				case "flip":
					if (spiellaeuft) {
						if (getAmZug() == FarbEnum.WEIß) {
							spielerHatGewonnen(FarbEnum.SCHWARZ);
						} else {
							spielerHatGewonnen(FarbEnum.WEIß);
						}
					}
					break;

				case "zug":
					System.out.println(getAmZug());
					break;
				case "dame":
					brett.getBrettFeldSchachnotation('c', 5).getSpielfigur().setDame(true);
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
					System.out.println("ki ziehen : Lässt die KI ziehen.");
					System.out.println("anzcsv: Ausgabe der aktuellen Spielbrett-Belegung in CSV-Notation.");
					break;

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
					if (!kifarbe.equals("w") && !kifarbe.equals("s")) {
						System.out.println("Gib bitte die Farbe an die an der Reihe ist(s/w)");
						break;
					}
					if (kifarbe.equals("s")) {
						int[] tmpZug = null;

						if (this.getAmZug() == FarbEnum.WEIß) {
							System.out.println("Weiß ist an der Reihe(w)");
							break;
						}
						if (k1 == null) {
							System.out.println("Schwarz ist keine KI");
							break;
						}
						int[] zuge = k1.zieh();
						if (zuge == null) {
							System.out.println("KI findet keine Züge");
							break;
						}
						brett.display();
						int zugPruefenKI = zugPruefen(zuge[0], zuge[1], zuge[2], zuge[3]);
						// zugPruefen == 1 Laufen
						// zugPruefen == 2 Schlagen
						// zugPruefen == -1 Zug ungueltig
						if (zugPruefenKI == 1) {
							figurBewegen(zuge[0], zuge[1], zuge[2], zuge[3]);
							dameWerden();
						}
						if (zugPruefenKI == 2) {
							figurSchlagen(zuge[0], zuge[1], zuge[2], zuge[3]);
							if (moeglicheZuegeStartposition(zuge[2], zuge[3])[1] == 0) {
								break;
							}
						}

						// FigurBewegen(zuge[0], zuge[1], zuge[2], zuge[3]);

						// if (k1.hatGeschlagen()) {
						// int[] zugee = zuge;
						// do {
						// Spielfigur figur = brett.getBrettFeldIndex((zugee[2]),
						// (zugee[3])).getSpielfigur();
						// zugee = k1.getWeitereSchlaege(((char) (zugee[3] + 97)), (zugee[2]
						// + 1), figur);
						// tmpZug = zugee;
						//
						// if (zugee != null) {
						// figurBewegen(zugee[0], zugee[1], zugee[2], zugee[3]);
						// }
						// } while (tmpZug != null);
						// }
						brett.display();
						zugBeenden();
						k1.setHatGeschlagen(false);
						System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");

						break;
					}

					if (kifarbe.equals("w")) {
						int[] tmpZug = null;
						if (this.getAmZug() == FarbEnum.SCHWARZ) {
							System.out.println("Schwarz ist an der Reihe (s)");
							break;
						}
						if (k2 == null) {
							System.out.println("Weiß ist keine KI");
							break;
						}
						int[] zuge = k2.zieh();
						if (zuge == null) {
							System.out.println("KI findet keine Züge");
							break;
						}
						brett.display();

						int zugPruefenKI = zugPruefen(zuge[0], zuge[1], zuge[2], zuge[3]);
						// zugPruefen == 1 Laufen
						// zugPruefen == 2 Schlagen
						// zugPruefen == -1 Zug ungueltig
						if (zugPruefenKI == 1) {
							figurBewegen(zuge[0], zuge[1], zuge[2], zuge[3]);
							dameWerden();
						}
						if (zugPruefenKI == 2) {
							figurSchlagen(zuge[0], zuge[1], zuge[2], zuge[3]);
							if (moeglicheZuegeStartposition(zuge[2], zuge[3])[1] == 0) {
								break;
							}
						}

						// if (k2.hatGeschlagen()) {
						// int[] zugee = zuge;
						// do {
						// Spielfigur figur = brett.getBrettFeldIndex((zugee[2]),
						// (zugee[3])).getSpielfigur();
						// zugee = k2.getWeitereSchlaege(((char) (zugee[3] + 97)), (zugee[2]
						// + 1), figur);
						// tmpZug = zugee;
						//
						// if (zugee != null) {
						// figurBewegen(zugee[0], zugee[1], zugee[2], zugee[3]);
						// }
						// } while (tmpZug != null);
						// }

						brett.display();
						zugBeenden();
						k2.setHatGeschlagen(false);
						System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
					}
					break;

				// zum erstellen von spielern

	
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

	public boolean pruefeStartposition(String startp) {
		startC = wandleUmvString(startp)[0];
		startI = wandleUmvString(startp)[1];
		if (wandleUmvString(startp)[2] == -1) {
			System.err.println("Falsche Eingabe, zurueck ins Menü");
			return false;
		}

		// überprüft ob Startposition belegt ist
		if (!brett.getBrettFeldIndex(startC, startI).getIstBelegt()) {
			System.err.println("Du brauchst eine Figur um zu ziehen! zurueck ins Menü");
			return false;
		}
		// überprüft ob Figur an der Startpsotion die eigene ist
		if (brett.getBrettFeldIndex(startC, startI).getSpielfigur().getFarbe() != getAmZug()) {
			System.err.println("Du kannst nur mit deinen Figuren ziehen! zurueck ins Menü");
			return false;
		}
		return true;
	}

	public int[] wandleUmvString(String Input) {
		int[] gueltig = new int[3];
		if (Input.length() > 1 && Input.length() < 4 && (IstBuchstabe(Input.substring(0, 1).toCharArray()) > -1) && (IstZahl(Input.substring(1, Input.length())) > -1)) {
			// System.out.println("Alles-OK");
			// System.out.println(IstBuchstabe(Input.substring(0, 1).toCharArray()) +
			// "  " + IstZahl(Input.substring(1, Input.length())));
			gueltig[0] = IstZahl(Input.substring(1, Input.length()));
			gueltig[1] = IstBuchstabe(Input.substring(0, 1).toCharArray());
			return gueltig;
		} else
			gueltig[2] = -1;
		System.out.println("Falsche Eingabe");
		return gueltig;
	}

	/**
	 * 
	 * @param Input
	 * @return int den Indexwert vom Brett (Spalte)
	 * @return -1 wenn Input (Index) ausserhalb Brett liegt
	 */
	public int IstZahl(String Input) {
		try {
			if (Integer.parseInt(Input) > 0 && Integer.parseInt(Input) <= brett.getBrettGroesse()) {
				return Integer.parseInt(Input) - 1;
			}
			return -1;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	/**
	 * 
	 * @param Input
	 * @return int den Indexwert von Brett (Zeile)
	 * @return -1 wenn Input (Index) ausserhalb vom Brett liegt
	 */
	public int IstBuchstabe(char[] Input) {

		Input[0] = Character.toUpperCase(Input[0]);

		if ((int) Input[0] >= 65 && (int) Input[0] <= (65 + brett.getBrettGroesse())) {
			return Input[0] - 65;
		}
		return -1;
	}

	/**
	 * gibt Anzahl Zuege in einem Array zurueck. Array [0] gibt Anzahl der
	 * Moeglichkeiten beim laufen. Array[1] gibt Anzahl der Moeglichkeiten beim
	 * schlagen.
	 * 
	 * @param x
	 *          Index Zeile von der Spielfigur auf Brett
	 * @param y
	 *          Index Spatlte von der Spielfigur auf Brett
	 * @return int[] gibt array mit moeglichen Zuegen zurueck
	 */

	private int[] moeglicheZuegeStartposition(int x, int y) {

		int xPosFig = brett.getBrettFeldIndex(x, y).getPosX();
		int yPosFig = brett.getBrettFeldIndex(x, y).getPosY();
		Spielfigur fig = brett.getBrettFeldIndex(x, y).getSpielfigur();
		int indexGroesse = brett.getBrettGroesse() - 1;

		// Index[0]=laufen, Index[1]=schlagen
		int[] anzMoeglichkeitenVonStartfeld = new int[2];
		int laufen = 0;
		int schlagen = 0;

		// Rechts oben
		int xPosNeu1 = xPosFig + 1;
		int yPosNeu1 = yPosFig + 1;

		// Links unten
		int xPosNeu2 = xPosFig - 1;
		int yPosNeu2 = yPosFig - 1;

		// Links oben
		int xPosNeu3 = xPosFig + 1;
		int yPosNeu3 = yPosFig - 1;

		// Rechts unten
		int xPosNeu4 = xPosFig - 1;
		int yPosNeu4 = yPosFig + 1;

		// Pruefungen ob Felder ringsherum vom Startfeld innerhalb vom Brett
		// liegen
		boolean rechtsObenImFeld = xPosNeu1 >= 0 && xPosNeu1 <= indexGroesse && yPosNeu1 >= 0 && yPosNeu1 <= indexGroesse;
		boolean linksUntenImFeld = xPosNeu2 >= 0 && xPosNeu2 <= indexGroesse && yPosNeu2 >= 0 && yPosNeu2 <= indexGroesse;
		boolean linksObenImFeld = xPosNeu3 >= 0 && xPosNeu3 <= indexGroesse && yPosNeu3 >= 0 && yPosNeu3 <= indexGroesse;
		boolean rechtsUntenImFeld = xPosNeu4 >= 0 && xPosNeu4 <= indexGroesse && yPosNeu4 >= 0 && yPosNeu4 <= indexGroesse;

		// laufen dame+figur

		// Laufen nach Rechts oben
		if (((fig.getDame(fig)) || ((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.SCHWARZ)) && rechtsObenImFeld) && !brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getIstBelegt()) {
			// Pruefung ob moegliches Zielfeld belegt ist.
			// System.out.println("(L RO) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getId());
			laufen++;
		}

		// Laufen nach Links unten
		if (((fig.getDame(fig)) || ((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.WEIß)) && linksUntenImFeld) && !brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getIstBelegt()) {
			// System.out.println("(L LU) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getId());
			laufen++;
		}

		// Laufen nach Links oben
		if (((fig.getDame(fig)) || ((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.SCHWARZ)) && linksObenImFeld) && !brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getIstBelegt()) {
			// System.out.println("(L LO) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getId());
			laufen++;
		}

		// Laufen nach Rechts unten
		if (((fig.getDame(fig)) || ((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.WEIß)) && rechtsUntenImFeld) && !brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getIstBelegt()) {
			// System.out.println("(L RU) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getId());
			laufen++;
		}

		// schlagen alle
		if (rechtsObenImFeld && brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getSpielfigur().getFarbe() != getAmZug() && (xPosNeu1 + 1 >= 0 && xPosNeu1 + 1 <= indexGroesse && yPosNeu1 + 1 >= 0 && yPosNeu1 + 1 <= indexGroesse)
				&& (!brett.getBrettFeldIndex(xPosNeu1 + 1, yPosNeu1 + 1).getIstBelegt())) {
			// System.out.println("(S RO) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu1 + 1, yPosNeu1 + 1).getId());
			schlagen++;
		}

		if (linksUntenImFeld && brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getSpielfigur().getFarbe() != getAmZug() && (xPosNeu2 - 1 >= 0 && xPosNeu2 - 1 <= indexGroesse && yPosNeu2 - 1 >= 0 && yPosNeu2 - 1 <= indexGroesse)
				&& (!brett.getBrettFeldIndex(xPosNeu2 - 1, yPosNeu2 - 1).getIstBelegt())) {
			// System.out.println("(S LU) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu2 + 1, yPosNeu2 + 1).getId());
			schlagen++;
		}

		if (linksObenImFeld && brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getSpielfigur().getFarbe() != getAmZug() && (xPosNeu3 - 1 >= 0 && xPosNeu3 - 1 <= indexGroesse && yPosNeu3 + 1 >= 0 && yPosNeu3 + 1 <= indexGroesse)
				&& (!brett.getBrettFeldIndex(xPosNeu3 - 1, yPosNeu3 + 1).getIstBelegt())) {
			// System.out.println("(S LO) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu3 + 1, yPosNeu3 + 1).getId());
			schlagen++;
		}

		if (rechtsUntenImFeld && brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getIstBelegt() && (brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getSpielfigur().getFarbe() != getAmZug()) && (xPosNeu4 - 1 >= 0 && xPosNeu4 - 1 <= indexGroesse && yPosNeu4 + 1 >= 0 && yPosNeu4 + 1 <= indexGroesse)
				&& (!brett.getBrettFeldIndex(xPosNeu4 - 1, yPosNeu4 + 1).getIstBelegt())) {
			// System.out.println("(S RU) Moeglicher Zug: " +
			// brett.getBrettFeldIndex(xPosNeu4 + 1, yPosNeu4 + 1).getId());
			schlagen++;
		}

		anzMoeglichkeitenVonStartfeld[0] = laufen;
		anzMoeglichkeitenVonStartfeld[1] = schlagen;
		return anzMoeglichkeitenVonStartfeld;
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
	 * prüft den gewollten zug
	 * 
	 * @param xa
	 *          alte x koord
	 * @param ya
	 *          alte y koord
	 * @param xn
	 *          neue x koord
	 * @param yn
	 *          neue y koord
	 */
	private int zugPruefen(int xa, int ya, int xn, int yn) {
		// return 1 figur bewegen
		// return 2 figur schlagen
		// return -1 Zug nicht gueltig

		int xPosAlt = brett.getBrettFeldIndex(xa, ya).getPosX();
		int yPosAlt = brett.getBrettFeldIndex(xa, ya).getPosY();
		int xPosNeu = brett.getBrettFeldIndex(xn, yn).getPosX();
		int yPosNeu = brett.getBrettFeldIndex(xn, yn).getPosY();

		int diffX = brett.getBrettFeldIndex(xa, ya).getPosX() - brett.getBrettFeldIndex(xn, yn).getPosX();
		int diffY = brett.getBrettFeldIndex(xa, ya).getPosY() - brett.getBrettFeldIndex(xn, yn).getPosY();

		int tempX = diffX;
		int tempY = diffY;
		int diff = diffX - diffY;

		tempX = Math.abs(diffX);

		tempY = Math.abs(diffY);

		if (!brett.getBrettFeldIndex(xn, yn).getIstSchwarz()) {
			System.out.println("Du kannst nur auf schwarze Felder springen!");
			return -1;
		}

		if (tempX != tempY) {
			System.out.println("Du darfts nicht querbeet durchs Feld springen!");
			// System.out.println(tempX + " " + tempY);
			return -1;

		}
		if (brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
			// System.out.println(brett.getBrettFeldIndex(xn, yn).getSpielfigur());
			System.out.println("Du kannst keine andere Figur besteigen!");
			return -1;
		}

		// TODO schauen ob bei dame sich
		// eigene spielfiguren auf dem weg befinden.
		Spielfigur fig = brett.getBrettFeldIndex(xa, ya).getSpielfigur();

		int hitX = (xa + xn) / 2;
		int hitY = (ya + yn) / 2;

		// Schlagen bei ZugLaenge 2
		if (tempX == 2 && tempY == 2 && brett.getBrettFeldIndex(hitX, hitY).getIstBelegt() && brett.getBrettFeldIndex(hitX, hitY).getSpielfigur().getFarbe() != getAmZug()) {
			// figurSchlagen(xa, ya, xn, yn, fig);
			return 2;
		}

		// Laufen bei Zueglaenge 1
		if (tempX == 1 && tempY == 1) {
			// figurBewegen(xa, ya, xn, yn);
			return 1;
		}

		// Wenn Stein mehr als ein Feld springen
		if (!fig.getDame(fig) && tempX >= 2 && tempY >= 2) {
			System.err.println("Du kannst mit einer Figur nicht mehr als ein Feld ziehen!");
			return -1;
		}

		/*
		 * R.O. diff=0 & diffX > -2 Ein Schritt------diffX=(xa-xn)---------- L.U.
		 * diff=0 & diffX > 0 Ein Schritt------diff=(diffX-diffY)------ R.U. diff=2
		 * & diffX > 0 Ein Schritt------------------------------ L.O. diff=2 & diffY
		 * > -2 Ein Schritt-----------------------------
		 * 
		 * R.O. diff=0 & diffX < -1 mehr als Ein Schritt-------------------- L.U.
		 * diff=0 & diffX > 0 mehr als Ein Schritt--------------------- R.U. diff>2
		 * & diffX > 0 mehr als Ein Schritt--------------------- L.O. diff>2 & diffX
		 * < -1 mehr als Ein Schritt--------------------
		 */

		if (fig.getDame(fig) && tempX >= 2 && tempY >= 2) {

			int alteX = brett.getBrettFeldIndex(xa, ya).getPosX();
			int alteY = brett.getBrettFeldIndex(xa, ya).getPosY();

			int neueX = brett.getBrettFeldIndex(xn, yn).getPosX();
			int neueY = brett.getBrettFeldIndex(xn, yn).getPosY();

			// RICHTUNG NACH OBEN RECHTS
			if (diffX < 0 && diffY < 0) {

				int j = alteY + 1;
				for (int i = alteX + 1; i <= neueX; i++) {

					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						// wenn etwas auf dem feld ist.
						if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == getAmZug()) {
							System.out.println("Eigene Figuren schlagen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (brett.getBrettFeldIndex(i + 1, j + 1).getIstBelegt()) {
							System.out.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX - 1, neueY - 1).getIstBelegt()) {
							System.out.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
							zugBeenden();
							return -1;
						}

					}
					j++;
				}
				return 1;
			}
			// RICHTUNG NACH OBEN LINKS
			if (diffX < 0 && diffY > 0) {

				int j = alteY - 1;
				for (int i = alteX + 1; i <= neueX; i++) {

					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						// wenn etwas auf dem feld ist.
						if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == getAmZug()) {
							System.out.println("Eigene Figuren schlagen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (brett.getBrettFeldIndex(i + 1, j - 1).getIstBelegt()) {
							System.out.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX - 1, neueY + 1).getIstBelegt()) {
							System.out.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
							zugBeenden();
							return -1;
						}

					}
					j--;
				}
				return 1;
			}

			// RICHTUNG NACH UNTEN RECHTS
			if (alteX > neueX && alteY < neueY) {

				int j = alteY + 1;
				for (int i = alteX - 1; i >= neueX; i++) {

					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						// wenn etwas auf dem feld ist.
						if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == getAmZug()) {
							System.out.println("Eigene Figuren schlagen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (brett.getBrettFeldIndex(i - 1, j + 1).getIstBelegt()) {
							System.out.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX + 1, neueY - 1).getIstBelegt()) {
							System.out.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
							zugBeenden();
							return -1;
						}

					}
					j++;
				}
				return 1;
			}

			// RICHTUNG NACH UNTEN LINKS
			if (alteX > neueX && alteX > neueX) {

				int j = alteY - 1;
				for (int i = alteX - 1; i >= neueX; i--) {

					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						// wenn etwas auf dem feld ist.
						if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == getAmZug()) {
							System.out.println("Eigene Figuren schlagen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (brett.getBrettFeldIndex(i - 1, j - 1).getIstBelegt()) {
							System.out.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							zugBeenden();
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX + 1, neueY + 1).getIstBelegt()) {
							System.out.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
							zugBeenden();
							return -1;
						}

					}
					j--;
				}
				return 1;
			}

		}

		// if (diff > 2 && diffX > 0) {
		// System.out.println("diff >2 & diffX > 0");
		// if (fig.getDame(fig)) {
		// System.out.println("ich bin dame");
		//
		// if (Rek_Dame_Moeglich(xa, ya, xn, yn, "ur")) {
		// System.out.println("Passt");
		// }
		// }
		// }
		// // oben Rechts
		// if (diff == 0 && diffX < -1) {
		// System.out.println("diff == 0 & diffX < -1");
		// if (fig.getDame(fig)) {
		// System.out.println("ich bin dame");
		//
		// if (Rek_Dame_Moeglich(xa, ya, xn, yn, "or")) {
		// System.out.println("Passt");
		// }
		// }
		// }
		// // oben Links
		// if (diff > 2 && diffX < -1) {
		// System.out.println("diff > 2 & diffX < -1");
		// if (fig.getDame(fig)) {
		// System.out.println("ich bin dame");
		//
		// if (Rek_Dame_Moeglich(xa, ya, xn, yn, "ol")) {
		// System.out.println("Passt");
		// }
		// }
		// }
		// // // unten Links
		// if (diff == 0 && diffX > 0) {
		// System.out.println("diff == 0 & diffX > 0");
		// if (fig.getDame(fig)) {
		// System.out.println("ich bin dame");
		//
		// if (Rek_Dame_Moeglich(xa, ya, xn, yn, "ul")) {
		// System.out.println("Passt");
		// }
		// }
		//
		// }

		return -1;
	}

	/**
	 * Der Spieler der am Zug ist darf seine Figur bewegen(wird durch zuggueltig
	 * geprueft) nur der SPieler der am zug ist darf auch ziehen
	 */
	private void figurBewegen(int xa, int ya, int xn, int yn) {

		int diffX = brett.getBrettFeldIndex(xa, ya).getPosX() - brett.getBrettFeldIndex(xn, yn).getPosX();
		int diffY = brett.getBrettFeldIndex(xa, ya).getPosY() - brett.getBrettFeldIndex(xn, yn).getPosY();
		int diff = diffX - diffY;

		Spielfigur fig = brett.getBrettFeldIndex(xa, ya).getSpielfigur();
		FarbEnum farbe = fig.getFarbe();

		/*
		 * Ein Schritt_________________________________________________
		 * 
		 * Rechts Oben: (diff == 0 && diffX > -2)-------------------------- Links
		 * Oben: (diff == 2 && diffX > -2)--------------------------- Rechts Unten:
		 * (diff == 2 && diffX > 0)-------------------------- Links Unten: (diff ==
		 * 0 && diffX > 0)---------------------------
		 */

		// Stein
		if (!fig.getDame(fig)) {
			switch (farbe) {
			case SCHWARZ:
				// Laufen nur nach oben moeglich
				if ((xa < xn || xa > xn) && diffX < 0) {

					brett.getBrettFeldIndex(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldIndex(xn, yn).setSpielfigur(fig);
				}
				if ((xa > xn || xa < xn) && diffX > 0) {

					if (!brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
						System.err.println("Nach hinten ziehen ist mit einem Stein nicht erlaubt!");
						break;
					}

				}
				break;

			case WEIß:
				// Laufen nur nach unten moeglich
				if ((xa > xn || xa < xn) && diffX > 0) {

					brett.getBrettFeldIndex(xa, ya).removeSpielfigur(fig);
					brett.getBrettFeldIndex(xn, yn).setSpielfigur(fig);
				}
				if ((xa < xn || xa > xn) && diffX > 0) {

					if (!brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
						System.err.println("Nach hinten ziehen ist mit einem Stein nicht erlaubt!");
						break;
					}
				}
				break;

			}
		}

		/*
		 * Mehr als Ein Schritt____________________________________________
		 * 
		 * Rechts Oben: (diff == 0 && diffX < -1)-------------------------- Links
		 * Oben: (diff > 2 && diffX < -1)---------------------------- Rechts Unten:
		 * (diff > 2 && diffX > 0)--------------------------- Links Unten: (diff ==
		 * 0 && diffX > 0)---------------------------
		 */
		else {
			// wenn dame dann alles zwischen end und startfeld chencken
			// sovie // end feld -- ++ +- -+ eine gegner figur ist falls
			// schlagen.

			int alteX = brett.getBrettFeldIndex(xa, ya).getPosX();
			int alteY = brett.getBrettFeldIndex(xa, ya).getPosY();
			int neueX = brett.getBrettFeldIndex(xn, yn).getPosX();
			int neueY = brett.getBrettFeldIndex(xn, yn).getPosY();

			// RICHTUNG NACH OBEN RECHTS
			if (diffX < 0 && diffY < 0) {

				int j = alteY + 1;
				// System.out.println("Angefangen zu laufen oben rechts");
				for (int i = alteX + 1; i <= neueX; i++) {
					if (!brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						brett.getBrettFeldIndex(i, j).setSpielfigur(brett.getBrettFeldIndex(i - 1, j - 1).getSpielfigur());
						brett.getBrettFeldIndex(i - 1, j - 1).removeSpielfigur(brett.getBrettFeldIndex(i - 1, j - 1).getSpielfigur());
						// System.out.println("naechstes feld. wenn nicht ende weiterlaufe");
					} else {
						figurSchlagen(i - 1, j - 1, i + 1, j + 1);

					}

					j++;
				}

			}

			// RICHTUNG NACH OBEN LINKS

			if (diffX < 0 && diffY > 0) {

				int j = alteY - 1;
				for (int i = alteX + 1; i <= neueX; i++) {
					// System.out.println("laufen nach oben links");
					if (!brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						brett.getBrettFeldIndex(i, j).setSpielfigur(brett.getBrettFeldIndex(i - 1, j + 1).getSpielfigur());
						brett.getBrettFeldIndex(i - 1, j + 1).removeSpielfigur(brett.getBrettFeldIndex(i - 1, j + 1).getSpielfigur());
					} else {
						figurSchlagen(i - 1, j + 1, i + 1, j - 1);

					}

					j--;
				}

			}

			// RICHTUNG NACH UNTEN RECHTS
			if (alteX > neueX && alteY < neueY) {

				int j = alteY + 1;
				for (int i = alteX - 1; i >= neueX; i--) {
					// System.out.println("laufen nach unten rechts");
					if (!brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						brett.getBrettFeldIndex(i, j).setSpielfigur(brett.getBrettFeldIndex(i + 1, j - 1).getSpielfigur());
						brett.getBrettFeldIndex(i + 1, j - 1).removeSpielfigur(brett.getBrettFeldIndex(i + 1, j - 1).getSpielfigur());
					} else {
						figurSchlagen(i + 1, j - 1, i - 1, j + 1);

					}

					j++;
				}

			}

			// RICHTUNG NACH UNTEN LINKS
			if (alteX > neueX && alteY > neueY) {

				int j = alteY - 1;
				for (int i = alteX - 1; i >= neueX; i--) {
					// System.out.println("Laufen nach unten links");
					if (!brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						brett.getBrettFeldIndex(i, j).setSpielfigur(brett.getBrettFeldIndex(i + 1, j + 1).getSpielfigur());
						brett.getBrettFeldIndex(i + 1, j + 1).removeSpielfigur(brett.getBrettFeldIndex(i + 1, j + 1).getSpielfigur());
					} else {
						figurSchlagen(i + 1, j + 1, i - 1, j - 1);

					}

					j--;
				}

			}

		}

		return;
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
	private void figurSchlagen(int altepx, int altepy, int neuepx, int neuepy) {

		Spielfigur fig = brett.getBrettFeldIndex(altepx, altepy).getSpielfigur();
		int alteX = brett.getBrettFeldIndex(altepx, altepy).getPosX();
		int alteY = brett.getBrettFeldIndex(altepx, altepy).getPosY();

		int neueX = brett.getBrettFeldIndex(neuepx, neuepy).getPosX();
		int neueY = brett.getBrettFeldIndex(neuepx, neuepy).getPosY();

		int diffX = alteX - neueX;
		int diffY = alteY - neueY;

		if (brett.getBrettFeldIndex(neueX, neueY).getIstSchwarz()) {
			if (!brett.getBrettFeldIndex(neueX, neueY).getIstBelegt()) {

				if (diffX < 0 && diffY < 0) {
					// System.out.println("nach oben rechts s1");
					// RICHTUNG NACH OBEN RECHTS
					if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getIstBelegt()) {
						if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
							// prüfen ob feld zwischen alt und neu leer ist wenn
							// nicht dann
							// farbe prüfen (Wenn alles korrekt die
							// figurEntfernen()aufrufen)
							// System.out.println("nach oben rechts s2");
							figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur());
							brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
							brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
							geschlagen = true;
						} else {
							System.out.println("Eigene Figuren schlagen geht nicht!");
							zugBeenden();
						}
					} else {
						System.out.println("Du darfst nich ueber ein leeres Feld springen.");
						zugBeenden();
					}
				}
				// RICHTUNG NACH OBEN LINKS
				if (diffX < 0 && diffY > 0) {
					// System.out.println("nach oben links s1");
					if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getIstBelegt()) {
						if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
							// prüfen ob feld zwischen alt und neu leer ist wenn
							// nicht dann
							// farbe prüfen (Wenn alles korrekt die
							// figurEntfernen()aufrufen)
							// System.out.println("nach oben links s2");
							figurEntfernen(brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur());
							brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
							brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
							geschlagen = true;
						} else {
							System.out.println("Eigene Figuren schlagen geht nicht!");
							zugBeenden();
						}
					} else {
						System.out.println("Du darfst nich ueber ein leeres Feld springen.");
						zugBeenden();
					}
				}
				// RICHTUNG NACH UNTEN RECHTS
				if (diffX > 0 && diffY < 0) {
					// System.out.println("nach unten rechts s1");
					if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getIstBelegt()) {
						if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
							// prüfen ob feld zwischen alt und neu leer ist wenn
							// nicht dann
							// farbe prüfen (Wenn alles korrekt die
							// figurEntfernen()aufrufen)
							// System.out.println("nach unten rechts s2");
							figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur());
							brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
							brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
							geschlagen = true;
						} else {
							System.out.println("Eigene Figuren schlagen geht nicht!");
							zugBeenden();
						}
					} else {
						System.out.println("Du darfst nich ueber ein leeres Feld springen.");
						zugBeenden();
					}
				}
				// RICHTUNG NACH UNTEN LINKS
				if (diffX > 0 && diffY > 0) {
					// System.out.println("nach unten links s1");
					if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getIstBelegt()) {
						if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur().getFarbe() != fig.getFarbe()) {
							// prüfen ob feld zwischen alt und neu leer ist wenn
							// nicht dann
							// farbe prüfen (Wenn alles korrekt die
							// figurEntfernen()aufrufen)
							// System.out.println("nach unten links s2");
							figurEntfernen(brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur());
							brett.getBrettFeldIndex(alteX, alteY).removeSpielfigur(fig);
							brett.getBrettFeldIndex(neueX, neueY).setSpielfigur(fig);
							geschlagen = true;
						} else {
							System.out.println("Eigene Figuren schlagen geht nicht!");
							zugBeenden();
						}
					} else {
						System.out.println("Du darfst nich ueber ein leeres Feld springen.");
						zugBeenden();
					}
				}

			} else {
				System.out.println("Zielfeld ist besetzt!");
				zugBeenden();
			}
		} else {

			System.out.println("Unmoeglich, das Zielfeld ist nicht schwarz.");
			zugBeenden();
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
	 * @param spielfigur
	 *          wenn eine Spielfigur die Moeglichkeit hat zu schlagen und diesen
	 *          Zug nicht wahrnimmt, wird er entfernt
	 * 
	 * 
	 */
	private void pusten(Spielfigur spielfigur) {
		figurEntfernen(spielfigur);
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
				Thread.sleep(1000);

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
				Thread.sleep(1000);

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

			getdZugriff().oeffnen(p);
			getdZugriff().speichern(p, this.brett.getBrettGroesse() + ","); // Speichert
			// die
			// Spielbrettgroesse

			getdZugriff().speichern(p, this.s1.getName() + "," + "\n" + this.s1.getFarbe() + "," + "\n" + this.s1.getIstKi() + ","); // Speichert
																																																																// Spieler
																																																																// 1
																																																																// mit
																																																																// Name,
																																																																// Farbe,
																																																																// KI
			getdZugriff().speichern(p, this.s2.getName() + "," + "\n" + this.s2.getFarbe() + "," + "\n" + this.s2.getIstKi() + ","); // Speichert
																																																																// Spieler
																																																																// 2
																																																																// mit
																																																																// Name,
																																																																// Farbe,
																																																																// KI

			for (Spielfigur s : s1.getAlleFiguren()) { // Speichere für jede
				// Spielfigur
				getdZugriff().speichern(p, s.getFarbe() + ","); // Farbe
				if (s.getFarbe() == FarbEnum.SCHWARZ) {
					getdZugriff().speichern(p, s.getIdS() + ","); // ID
				} else {
					getdZugriff().speichern(p, s.getIdW() + ","); // ID
				}
				getdZugriff().speichern(p, s.getDame(s) + ","); // Dame Ja oder
				// Nein
				getdZugriff().speichern(p, s.getPosX() + ","); // Position X
				getdZugriff().speichern(p, s.getPosY() + ","); // Position Y

			}
			for (Spielfigur s : s2.getAlleFiguren()) { // Speichere für jede
				// Spielfigur
				getdZugriff().speichern(p, s.getFarbe() + ","); // Farbe
				if (s.getFarbe() == FarbEnum.SCHWARZ) {
					getdZugriff().speichern(p, s.getIdS() + ","); // ID
				} else {
					getdZugriff().speichern(p, s.getIdW() + ","); // ID
				}
				getdZugriff().speichern(p, s.getDame(s) + ","); // Dame Ja oder
				// Nein
				getdZugriff().speichern(p, s.getPosX() + ","); // Position X
				getdZugriff().speichern(p, s.getPosY() + ","); // Position Y

			}
			getdZugriff().speichern(p, this.getAmZug() + ","); // Speichert
			// welche Farbe
			// am Zug ist

			System.out.println("Das Spiel wurde gespeichert: " + p.getName());
			getdZugriff().schliessen(p);
		} catch (Exception e) {
			System.out.println("Speichern CSV fehlgeschlagen.");
		}
	}

	// Noch fehlerhaft :-(
	public static Spiel ladenCSV(String string) {
		try {
			setdZugriff(new DatenzugriffCSV());
			File f = new File(string);
			getdZugriff().oeffnen(f);
			System.out.println("Das Spiel wird geladen.");
			Spiel o = new Spiel();

			String t = (String) getdZugriff().laden(f);
			String[] list = t.split(",");

			if (list[0].equals("8")) {
				o.spielBauen(8);
			} else if (list[0] == "10") {
				o.spielBauen(10);
			} else if (list[0].equals("12")) {
				o.spielBauen(12);

			}
			if (list[1].equals("WEIß")) {
				o.setAmZug(FarbEnum.WEIß); // Später in Weiß ändern!
			} else {
				o.setAmZug(FarbEnum.SCHWARZ);
			}
			while (list[3].equals("Spieler")) {

				FarbEnum a;
				boolean b;

				if (list[5].equals("SCHWARZ")) {
					a = FarbEnum.SCHWARZ;
				} else {
					a = FarbEnum.WEIß;
				}

				if (list[6].equals("true")) {
					b = true;
				} else {
					b = false;
				}
				Spieler s1 = new Spieler(list[4], a, b);
			}
			while (list[7].equals("Spieler")) {

				FarbEnum a;
				boolean b;

				if (list[9].equals("SCHWARZ")) {
					a = FarbEnum.SCHWARZ;
				} else {
					a = FarbEnum.WEIß;
				}

				if (list[10].equals("true")) {
					b = true;
				} else {
					b = false;
				}
				Spieler s2 = new Spieler(list[4], a, b);
				getdZugriff().schliessen(f);
			}
			return o;

			// später ändern

		} catch (Exception e) {
			System.out.println("Laden fehlgeschlagen!");
			return null;
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

	// SPIELER REIHENFOLGE
	// DAME MEHRERE FELDER ZIEHEN UND SCHLAGEN
	// STEIN MEHRERE SCHLAGEN
	// THEORETISCH DAFÜR EIN INT FÜR ZUG UND EIN INT FÜR SCHLAGEN IN DER
	// MÖGLICHEZÜGE
	// PUSTEN
	// AUFGEBEN???//FLIP TABLE

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Spielbrett aufbauen
	 * 
	 * @param groesse
	 *          groesse des bretts
	 * @return war es erfolgreich? true/false
	 */
	public boolean aufbauen(int groesse) {
		if (spielAufgebaut) {
			System.out.println("Das Spielbrett wurde bereits aufgebaut!");
			return false;
		}

		if (groesse == (8)) {
			spielBauen(8);
			return true;
		} else if (groesse == (10)) {
			spielBauen(10);
			return true;
		} else if (groesse == (12)) {
			spielBauen(12);
			return true;
		} else {
			System.out.println("Fehlerhafte Eingabe, bitte nur 8 , 10 oder 12 eingeben. Zurueck im Hauptmenue.\n");
			return false;
		}

	}

	/**
	 * Spielbrett anzeigen
	 */
	public void anzeigen() {

		if (spielAufgebaut) {
			brett.display();
			System.out.println("\n");

		} else {
			System.out.println("Du kannst kein Brett anzeigen das nicht existiert! Nutze aufbauen um ein Brett zu erstellen.");
			return;
		}

	}

	/**
	 * Zum erstellen der Spieler
	 * 
	 * @param name
	 *          name des Spielers
	 * @param farbe
	 *          farbe des Spielers (Enum)
	 * @param istKi
	 *          boolean ob ki true oder false
	 * @return gibt zurück ob erfolgreich oder nicht
	 */
	public boolean spielerErstellen(String name, FarbEnum farbe, boolean istKi) {

		if (!spielAufgebaut) {
			System.out.println("Du musst zuerst ein Spielbrett aufbauen!");
			return false;
		}
		if (spielerAnzahl == 2) {
			System.out.println("Es gibt bereits zwei Spieler.");
			return false;
		}

		if (name == null || name.length() < 2) {
			System.out.println("Name war fehlerhaft.");
			return false;
		}

		if (farbe == FarbEnum.SCHWARZ && schwarzvergeben == true || farbe == FarbEnum.WEIß && weissvergeben == true) {
			System.out.println("Farbe bereits vergeben.");
			return false;
		}

		if (farbe == FarbEnum.SCHWARZ) {

			if (istKi == false) {

				s1 = new Spieler(name, FarbEnum.SCHWARZ, false);
				schwarzvergeben = true;
				spielerAnzahl++;
				System.out.println(s1);
				System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
				erstelleFiguren(s1, brett);
				return true;
			} else {
				s1 = new Spieler(name, FarbEnum.SCHWARZ, true);
				k1 = new KI_Dame(s1, brett);
				spielerAnzahl++;
				schwarzvergeben = true;
				System.out.println(k1);
				System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
				erstelleFiguren(s1, brett);
				return true;
			}

		} else {

			if (istKi == false) {
				s2 = new Spieler(name, FarbEnum.WEIß, false);
				weissvergeben = true;
				spielerAnzahl++;
				System.out.println(s2);
				System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
				erstelleFiguren(s2, brett);
				return true;
			} else {
				s2 = new Spieler(name, FarbEnum.WEIß, true);
				k2 = new KI_Dame(s2, brett);
				weissvergeben = true;
				spielerAnzahl++;
				System.out.println(k2);
				System.out.println("Derzeitige Spieleranzahl:" + Spieler.getAnzahl());
				erstelleFiguren(s2, brett);
				return true;
			}

		}

	}

	/**
	 * zum starten des Spiels
	 * 
	 * @return läuft das spiel true oder nicht false
	 */
	public boolean starten() {

		if (spielAufgebaut == true && spielerAnzahl == 2) {
			spiellaeuft = true;

			System.out.println("Das Spiel beginnt!");

			setAmZug(FarbEnum.WEIß);
			System.out.println("Der Spieler mit der Farbe " + getAmZug() + " beginnt");
			// TODO
			return true;
		} else {
			System.err.println("Das Spiel kann noch nicht gestartet werden!!");
			return false;
		}

	}
	
	public boolean ziehen(){
		
		if (!spiellaeuft) {
			System.err.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
			break;
		} else {
			brett.display();

			// Startposition fragen
			System.out.println("\n Bitte gebe deine Startposition ein.");
			String startp = reader.readLine();
			if (pruefeStartposition(startp) == false) {
				break;
			}
			int[] tmp = moeglicheZuegeStartposition(startC, startI);
			int tempZuegeLaufen = tmp[0];
			int tempZuegeSchlagen = tmp[1];
			System.out.println("Anzahl Zuege Laufen: " + tempZuegeLaufen + " Anzahl Zuege Schlagen: " + tempZuegeSchlagen);

			if (s1.getAlleFiguren().size() == 1 && s1.getFarbe() == getAmZug() && (tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				// spieler 2 gewinnt
				System.err.println("Du hast keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s2.getFarbe());
				break;
			}
			if (s2.getAlleFiguren().size() == 1 && s2.getFarbe() == getAmZug() && (tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				// spieler 1 gewinnt
				System.err.println("Du hast keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s1.getFarbe());
				break;
			}
			if ((tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				System.err.println("Mit dieser Figur sind keine Zuege moeglich! zurueck ins Menue");
				System.out.println("Gib ziehen ein.");
				break;
			}

			// Endpos fragen
			System.out.println("Eingegebene Startposition: " + brett.getBrettFeldIndex(startC, startI).getId() + "\n");

			System.out.println("Bitte gebe deine Endposition ein.");
			String endp = reader.readLine();
			endC = wandleUmvString(endp)[0];
			endI = wandleUmvString(endp)[1];
			if (wandleUmvString(endp)[2] == -1) {
				System.err.println("Falsche Eingabe, zurueck im Menü");
				break;
			}

			System.out.println("Eingegebene Endposition: " + brett.getBrettFeldIndex(endC, endI).getId() + "\n");

			int zugPruefen = zugPruefen(startC, startI, endC, endI);
			// zugPruefen == 1 Laufen
			// zugPruefen == 2 Schlagen
			// zugPruefen == -1 Zug ungueltig
			if (zugPruefen == 1) {
				figurBewegen(startC, startI, endC, endI);
				dameWerden();
				zugBeenden();
				System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
				break;
			}
			if (zugPruefen == 2) {
				figurSchlagen(startC, startI, endC, endI);
				if (moeglicheZuegeStartposition(endC, endI)[1] == 0) {
					zugBeenden();
					dameWerden();
					System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
					break;
				}

				// if (geschlagen == true) {
				// if (moeglicheZuegeStartposition(endC, endI)[1] != 0) {
				// System.out.println("Weiterziehen (w) oder beenden (b)?");
				// String entscheidung = reader.readLine();
				//
				// if (entscheidung.equals("w")) {
				// startC = endC;
				// startI = endI;
				// // TODO ziehen nur mit der Bedingung zu
				// // schlagen
				// System.out.println("Sie duerfen nochmals ziehen. Geben sie die Endposition ein");
				// geschlagen = false;
				//
				// break;
				// }
				// if (entscheidung.equals("b")) {
				// System.out.println("Die Figur " + brett.getBrettFeldIndex(endC,
				// endI).getSpielfigur() + " wird entfernt(Pusten)!");
				// pusten(brett.getBrettFeldIndex(endC, endI).getSpielfigur());
				// startC = 0;
				// startI = 0;
				// endC = 0;
				// endI = 0;
				// zugBeenden();
				// System.out.println("Der Spieler mit der Farbe: " + getAmZug() +
				// " ist nun am Zug.");
				// geschlagen = false;
				// break;
				// }
				// }
				// }

			}
			if (zugPruefen == -1) {
				System.out.println("Zurueck im Menue. Gib nochmals ziehen ein.");
				break;
			}

			brett.display();
			System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
			System.out.println("Gib ziehen ein.");
			break;
		}
	}

}
