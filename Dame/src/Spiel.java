import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
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
	private DatenzugriffSerialisiert ser = new DatenzugriffSerialisiert();
	private DatenzugriffPDF p = new DatenzugriffPDF();
	private DatenzugriffCSV csv = new DatenzugriffCSV();
	private boolean spielAufgebaut = false;
	private int spielerAnzahl = 0;
	private String name;
	private String farbe;
	private String ki;
	private boolean spiellaeuft = false;
	private boolean schwarzvergeben = false;
	private boolean weissvergeben = false;
	private boolean geschlagen = false;
	private boolean kannWeiterSchlagen = false;
	private int counter = 0;
	private Spieler gewonnenerSpieler = null;

	private Spielbrett brett;
	private Spieler spieler;
	private FarbEnum farbeAmZug;
	private Spieler s1;
	private Spieler s2;
	private KI_Dame k1;
	private KI_Dame k2;
	private String dateiname = "csv";
	private GUI gui;

	private int startC;
	private int endC;
	private int startI;
	private int endI;
	private int startCNS;
	private int startINS;

	private boolean RekHatGeschlagen;
	private ArrayList<String> FigurenLaufen = new ArrayList<String>();
	private ArrayList<String> FigurenSchlagen = new ArrayList<String>();

	private ArrayList<Spielfigur> FigurDieSchlagenKoennen = new ArrayList<Spielfigur>();

	private ArrayList<String> zugFurLog = new ArrayList<String>();

	public void spielStarten() {

		// ///////////////////////////////////////////
		System.out.println("Bitte gebe die gewuenschte Spielbrett Groesse ein. ( 8 , 10 , 12 )");
		aufbauen(12);
		anzeigen();
	}

	// ///////////////////////////////////////////
	// try {
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(System.in));
	// String eingabe = "";
	//
	// do {
	// eingabe = reader.readLine().toLowerCase();
	// switch (eingabe) {
	// // TEST CASES: //
	//
	// case "entf":
	// brett.getBrettFeldSchachnotation('a',
	// 11).removeSpielfigur(brett.getBrettFeldSchachnotation('a',
	// 11).getSpielfigur());
	// break;
	// case "flip":
	// if (spiellaeuft) {
	// if (getAmZug() == FarbEnum.WEIß) {
	// spielerHatGewonnen(FarbEnum.SCHWARZ);
	// } else {
	// spielerHatGewonnen(FarbEnum.WEIß);
	// }
	// }
	// break;
	//
	// case "zug":
	// System.out.println(getAmZug());
	// break;
	// case "dame":
	// brett.getBrettFeldSchachnotation('c', 5).getSpielfigur().setDame(true);
	// break;
	//
	// // TEST CASES ENDE //
	//
	// case "help":
	// System.out.println("aufbauen : Erstellt ein Spielbrett, wird zum spielen benoetigt.");
	// System.out.println("erstellen : Erlaubt dir einen Spieler zu erstellen, es werden 2 Spieler zum spielen benoetigt.");
	// System.out.println("start: Startet das Spiel, es wird ein erstelltes Spielbrett und zwei Spieler benoetigt.");
	// System.out.println("beenden : Das Spiel wird geschlossen.");
	// System.out.println("ziehen : Erlaubt dir eine Spielfigur zu bewegen. Nicht moeglich solange das Spiel nicht laeuft.");
	// System.out.println("anzeigen : Zeigt dir das Spielbrett.");
	// System.out.println("ser : erlaubt es dir das Spiel zu serialisieren.");
	// System.out.println("csv : erlaubt es dir das Spiel als CSV Datei zu speichern.");
	// System.out.println("ki ziehen : Lässt die KI ziehen.");
	// System.out.println("anzcsv: Ausgabe der aktuellen Spielbrett-Belegung in CSV-Notation.");
	// break;
	//
	// // zum erstellen von spielern
	//
	// }
	// } while (!eingabe.equals("beenden"));
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

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

	public boolean pruefeEndposition(String endp) {
		endC = wandleUmvString(endp)[0];
		endI = wandleUmvString(endp)[1];
		if (wandleUmvString(endp)[2] == -1) {
			System.err.println("Falsche Eingabe, Gib nochmal ziehen ein");
			return false;
		}
		return true;
	}

	public int[] wandleUmvString(String Input) {
		int[] gueltig = new int[3];
		if (Input.length() > 1 && Input.length() < 4 && (IstBuchstabe(Input.substring(0, 1).toCharArray()) > -1) && (IstZahl(Input.substring(1, Input.length())) > -1)) {
			// System.out.println("Alles-OK");
			// System.out.println(IstBuchstabe(Input.substring(0,
			// 1).toCharArray()) +
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

		FigurenLaufen.clear();
		FigurenSchlagen.clear();

		Spielfigur fig = brett.getBrettFeldIndex(x, y).getSpielfigur();
		int xPosFig = brett.getBrettFeldIndex(x, y).getPosX();
		int yPosFig = brett.getBrettFeldIndex(x, y).getPosY();
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

		// laufen Stein

		// Laufen nach Rechts oben
		if ((((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.SCHWARZ)) && rechtsObenImFeld) && !brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getIstBelegt()) {
			// Pruefung ob moegliches Zielfeld belegt ist.
			FigurenLaufen.add(brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getId());
			laufen++;
		}

		// Laufen nach Links unten
		if ((((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.WEIß)) && linksUntenImFeld) && !brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getIstBelegt()) {
			FigurenLaufen.add(brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getId());
			laufen++;
		}

		// Laufen nach Links oben
		if ((((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.SCHWARZ)) && linksObenImFeld) && !brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getIstBelegt()) {
			FigurenLaufen.add(brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getId());
			laufen++;
		}

		// Laufen nach Rechts unten
		if ((((!fig.getDame(fig) && fig.getFarbe() == FarbEnum.WEIß)) && rechtsUntenImFeld) && !brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getIstBelegt()) {
			FigurenLaufen.add(brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getId());
			laufen++;
		}

		// schlagen Stein im einer Schritt

		// Rechts oben
		if ((!fig.getDame(fig) && rechtsObenImFeld && brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu1, yPosNeu1).getSpielfigur().getFarbe() != getAmZug()
				&& (xPosNeu1 + 1 >= 0 && xPosNeu1 + 1 <= indexGroesse && yPosNeu1 + 1 >= 0 && yPosNeu1 + 1 <= indexGroesse) && (!brett.getBrettFeldIndex(xPosNeu1 + 1, yPosNeu1 + 1).getIstBelegt()))) {
			FigurenSchlagen.add(brett.getBrettFeldIndex(xPosNeu1 + 1, yPosNeu1 + 1).getId());
			schlagen++;
		}

		// Links unten
		if ((!fig.getDame(fig) && linksUntenImFeld && brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu2, yPosNeu2).getSpielfigur().getFarbe() != getAmZug()
				&& (xPosNeu2 - 1 >= 0 && xPosNeu2 - 1 <= indexGroesse && yPosNeu2 - 1 >= 0 && yPosNeu2 - 1 <= indexGroesse) && (!brett.getBrettFeldIndex(xPosNeu2 - 1, yPosNeu2 - 1).getIstBelegt()))) {
			FigurenSchlagen.add(brett.getBrettFeldIndex(xPosNeu2 - 1, yPosNeu2 - 1).getId());
			schlagen++;
		}

		// Links oben
		if ((!fig.getDame(fig) && linksObenImFeld && brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getIstBelegt() && brett.getBrettFeldIndex(xPosNeu3, yPosNeu3).getSpielfigur().getFarbe() != getAmZug()
				&& (xPosNeu3 + 1 >= 0 && xPosNeu3 + 1 <= indexGroesse && yPosNeu3 - 1 >= 0 && yPosNeu3 - 1 <= indexGroesse) && (!brett.getBrettFeldIndex(xPosNeu3 + 1, yPosNeu3 - 1).getIstBelegt()))) {
			FigurenSchlagen.add(brett.getBrettFeldIndex(xPosNeu3 + 1, yPosNeu3 - 1).getId());
			schlagen++;
		}

		// Rechts unten
		if ((!fig.getDame(fig) && rechtsUntenImFeld && brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getIstBelegt() && (brett.getBrettFeldIndex(xPosNeu4, yPosNeu4).getSpielfigur().getFarbe() != getAmZug())
				&& (xPosNeu4 - 1 >= 0 && xPosNeu4 - 1 <= indexGroesse && yPosNeu4 + 1 >= 0 && yPosNeu4 + 1 <= indexGroesse) && (!brett.getBrettFeldIndex(xPosNeu4 - 1, yPosNeu4 + 1).getIstBelegt()))) {
			FigurenSchlagen.add(brett.getBrettFeldIndex(xPosNeu4 - 1, yPosNeu4 + 1).getId());
			schlagen++;
		}

		// Laufen & Schlagen Dame - Dame kann mehr als ein Schritt laufen

		if (fig.getDame(fig)) {
			// System.out.println("ich bin dame1");
			int[] ur = Rek_Dame_Moeglich(x, y, "ur", 0, 0);
			// System.out.println("Passt");
			laufen += ur[0];
			schlagen += ur[1];

			// System.out.println("ich bin dame2");
			int[] ul = Rek_Dame_Moeglich(x, y, "ul", 0, 0);
			laufen += ul[0];
			schlagen += ul[1];

			// System.out.println("ich bin dame3");
			int[] ol = Rek_Dame_Moeglich(x, y, "ol", 0, 0);
			laufen += ol[0];
			schlagen += ol[1];

			// System.out.println("ich bin dame4");
			int[] or = Rek_Dame_Moeglich(x, y, "or", 0, 0);
			laufen += or[0];
			schlagen += or[1];

		}

		anzMoeglichkeitenVonStartfeld[0] = laufen;
		anzMoeglichkeitenVonStartfeld[1] = schlagen;
		return anzMoeglichkeitenVonStartfeld;
	}

	private int[] Rek_Dame_Moeglich(int x, int y, String richtung, int laufen, int schlagen) {
		// System.out.print(x+"  "+y);
		int[] amk = new int[2];
		RekHatGeschlagen = geschlagen;

		// Richtung unten Rechts
		if (richtung.equalsIgnoreCase("ur")) {
			// 1.Pruefung: schauen ob nächstes Feld innerhalb des Brettes liegt
			if (x - 1 >= 0 && y + 1 <= brett.getBrettGroesse() - 1) {
				// 2.Pruefung: schauen ob ne Figur drauf ist. Ist keine Figur
				// drauf
				if (!brett.getBrettFeldIndex(x - 1, y + 1).getIstBelegt()) {
					FigurenLaufen.add(brett.getBrettFeldIndex(x - 1, y + 1).getId());
					// Rekursionsaufruf mit laufen++
					amk = Rek_Dame_Moeglich(x - 1, y + 1, richtung, laufen + 1, schlagen);
					return amk;
					// 3.Pruefung: wenn Feld belegt vom Gegner
				} else if (brett.getBrettFeldIndex(x - 1, y + 1).getIstBelegt() && brett.getBrettFeldIndex(x - 1, y + 1).getSpielfigur().getFarbe() != getAmZug()) {
					// 4.Pruefung: wenn Feld dahinter innerhalb Brett & wenn
					// Feld dahinter frei
					if (x - 2 >= 0 && y + 2 <= brett.getBrettGroesse() - 1 && !brett.getBrettFeldIndex(x - 2, y + 2).getIstBelegt()) {
						FigurenSchlagen.add(brett.getBrettFeldIndex(x - 2, y + 2).getId());
						// Rekursionsaufruf mit schlagen++
						amk = Rek_Dame_Moeglich(x - 2, y + 2, richtung, laufen, schlagen + 1);
						return amk;
					}
				}
			}
		}
		// Richtung unten Links
		if (richtung.equalsIgnoreCase("ul")) {
			// 1.Pruefung: schauen ob nächstes Feld innerhalb des Brettes liegt
			if (x - 1 >= 0 && y - 1 >= 0) {
				// 2.Pruefung: schauen ob ne Figur drauf ist. Ist keine Figur
				// drauf
				if (!brett.getBrettFeldIndex(x - 1, y - 1).getIstBelegt()) {
					FigurenLaufen.add(brett.getBrettFeldIndex(x - 1, y - 1).getId());
					// Rekursionsaufruf mit laufen++
					amk = Rek_Dame_Moeglich(x - 1, y - 1, richtung, laufen + 1, schlagen);
					return amk;
					// 3.Pruefung: wenn Feld belegt vom Gegner
				} else if (brett.getBrettFeldIndex(x - 1, y - 1).getIstBelegt() && brett.getBrettFeldIndex(x - 1, y - 1).getSpielfigur().getFarbe() != getAmZug()) {
					// 4.Pruefung: wenn Feld dahinter innerhalb Brett & wenn
					// Feld dahinter frei
					if (x - 2 >= 0 && y - 2 >= 0 && !brett.getBrettFeldIndex(x - 2, y - 2).getIstBelegt()) {
						FigurenSchlagen.add(brett.getBrettFeldIndex(x - 2, y - 2).getId());
						// Rekursionsaufruf mit schlagen++
						amk = Rek_Dame_Moeglich(x - 2, y - 2, richtung, laufen, schlagen + 1);
						return amk;
					}
				}
			}
		}
		// Richtung oben Links
		if (richtung.equalsIgnoreCase("ol")) {
			// 1.Pruefung: schauen ob nächstes Feld innerhalb des Brettes liegt
			if (x + 1 <= brett.getBrettGroesse() - 1 && y - 1 >= 0) {
				// 2.Pruefung: schauen ob ne Figur drauf ist. Ist keine Figur
				// drauf
				System.out.println("oben links belegt: " + brett.getBrettFeldIndex(x + 1, y - 1).getIstBelegt());
				if (!brett.getBrettFeldIndex(x + 1, y - 1).getIstBelegt()) {
					FigurenLaufen.add(brett.getBrettFeldIndex(x + 1, y - 1).getId());
					// Rekursionsaufruf mit laufen++
					amk = Rek_Dame_Moeglich(x + 1, y - 1, richtung, laufen + 1, schlagen);
					return amk;
					// 3.Pruefung: wenn Feld belegt vom Gegner
				} else if (brett.getBrettFeldIndex(x + 1, y - 1).getIstBelegt() && brett.getBrettFeldIndex(x + 1, y - 1).getSpielfigur().getFarbe() != getAmZug()) {
					System.out.println("oben links belegt: " + brett.getBrettFeldIndex(x + 1, y - 1).getIstBelegt());
					// 4.Pruefung: wenn Feld dahinter innerhalb Brett & wenn
					// Feld dahinter frei
					if (x + 2 <= brett.getBrettGroesse() - 1 && y - 2 >= 0 && !brett.getBrettFeldIndex(x + 2, y - 2).getIstBelegt()) {
						FigurenSchlagen.add(brett.getBrettFeldIndex(x + 2, y - 2).getId());
						// Rekursionsaufruf mit schlagen++
						amk = Rek_Dame_Moeglich(x + 2, y - 2, richtung, laufen, schlagen + 1);
						return amk;
					}
				}
			}
		}
		// Richtung oben Rechts
		if (richtung.equalsIgnoreCase("or")) {
			// 1.Pruefung: schauen ob nächstes Feld innerhalb des Brettes liegt
			if (x + 1 <= brett.getBrettGroesse() - 1 && y + 1 <= brett.getBrettGroesse() - 1) {
				// 2.Pruefung: schauen ob ne Figur drauf ist. Ist keine Figur
				// drauf
				if (!brett.getBrettFeldIndex(x + 1, y + 1).getIstBelegt()) {
					FigurenLaufen.add(brett.getBrettFeldIndex(x + 1, y + 1).getId());
					// Rekursionsaufruf mit laufen++
					amk = Rek_Dame_Moeglich(x + 1, y + 1, richtung, laufen + 1, schlagen);
					return amk;
					// 3.Pruefung: wenn Feld belegt vom Gegner
				} else if (brett.getBrettFeldIndex(x + 1, y + 1).getIstBelegt() && brett.getBrettFeldIndex(x + 1, y + 1).getSpielfigur().getFarbe() != getAmZug()) {
					// 4.Pruefung: wenn Feld dahinter innerhalb Brett & wenn
					// Feld dahinter frei
					if (x + 2 <= brett.getBrettGroesse() - 1 && y + 2 <= brett.getBrettGroesse() - 1 && !brett.getBrettFeldIndex(x + 2, y + 2).getIstBelegt()) {
						FigurenSchlagen.add(brett.getBrettFeldIndex(x + 2, y + 2).getId());
						// Rekursionsaufruf mit schlagen++
						amk = Rek_Dame_Moeglich(x + 2, y + 2, richtung, laufen, schlagen + 1);
						return amk;
					}
				}
			}
		}

		amk[0] = laufen;
		amk[1] = schlagen;
		if (!geschlagen) {
			return amk;
		} else if (geschlagen & schlagen == 0) {
			amk[0] = 0;
			amk[1] = 0;
			return amk;
		} else {
			return amk;
		}
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
	 * prueft den gewollten zug
	 *
	 * @param xa
	 *          alte x koord
	 * @param ya
	 *          alte y koord
	 * @param xn
	 *          neue x koord
	 * @param yn
	 *          neue y koord
	 * @return int
	 *
	 */
	private int zugPruefen(int xa, int ya, int xn, int yn) {
		// return 1 figur bewegen
		// return 2 figur schlagen
		// return -1 Zug nicht gueltig

		Spielfigur fig = brett.getBrettFeldIndex(xa, ya).getSpielfigur();
		FarbEnum farbe = fig.getFarbe();

		int diffX = brett.getBrettFeldIndex(xa, ya).getPosX() - brett.getBrettFeldIndex(xn, yn).getPosX();
		int diffY = brett.getBrettFeldIndex(xa, ya).getPosY() - brett.getBrettFeldIndex(xn, yn).getPosY();

		int tempX = diffX;
		int tempY = diffY;
		// int diff = diffX - diffY;

		tempX = Math.abs(diffX);

		tempY = Math.abs(diffY);

		if (!brett.getBrettFeldIndex(xn, yn).getIstSchwarz()) {
			System.out.println("Dieser Zug ist nicht gültig. Du kannst nur auf schwarze Felder springen!");
			return -1;
		}

		if (tempX != tempY) {
			System.out.println("Dieser Zug ist nicht gültig. Du darfts nicht querbeet durchs Feld springen!");
			return -1;

		}
		if (brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
			System.out.println("Dieser Zug ist nicht gültig. Du kannst keine andere Figur besteigen!");
			return -1;
		}

		if (!fig.getDame(fig)) {
			if (farbe == FarbEnum.SCHWARZ && xa > xn && tempX == 1 && tempY == 1) {

				if (!brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
					System.err.println("Nach hinten ziehen ist mit einem Stein nicht erlaubt!ssiilikkSchwarz");
					return -1;
				}

			}
		}

		if (!fig.getDame(fig)) {
			if (farbe == FarbEnum.WEIß && xa < xn && tempX == 1 && tempY == 1) {

				if (!brett.getBrettFeldIndex(xn, yn).getIstBelegt()) {
					System.err.println("Nach hinten ziehen ist mit einem Stein nicht erlaubt!ssssssilllliiikkWeiss");
					return -1;
				}
			}
		}

		// Spielfigur fig = brett.getBrettFeldIndex(xa, ya).getSpielfigur();

		int hitX = (xa + xn) / 2;
		int hitY = (ya + yn) / 2;

		// Schlagen bei ZugLaenge 2
		if (tempX == 2 && tempY == 2 && brett.getBrettFeldIndex(hitX, hitY).getIstBelegt() && brett.getBrettFeldIndex(hitX, hitY).getSpielfigur().getFarbe() != getAmZug()) {
			return 2;
		}

		// Laufen bei Zueglaenge 1
		if (tempX == 1 && tempY == 1) {
			return 1;
		}

		// Wenn Stein mehr als ein Feld laufen will
		if (!fig.getDame(fig) && tempX >= 2 && tempY >= 2) {
			System.err.println("Du kannst mit einer Figur nicht mehr als ein Feld ziehen!");
			return -1;
		}

		// Alles was mehr als ein Schritt lang ist
		// Dame
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
							System.err.println("Eigene Figuren schlagen geht nicht.");
							return -1;
						}
						if (brett.getBrettFeldIndex(i + 1, j + 1).getIstBelegt()) {
							System.err.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX - 1, neueY - 1).getIstBelegt()) {
							System.err.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
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
							System.err.println("Eigene Figuren schlagen geht nicht.");
							return -1;
						}
						if (brett.getBrettFeldIndex(i + 1, j - 1).getIstBelegt()) {
							System.err.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX - 1, neueY + 1).getIstBelegt()) {
							System.err.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
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
				for (int i = alteX - 1; i >= neueX; i--) {

					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
						// wenn etwas auf dem feld ist.
						if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == getAmZug()) {
							System.err.println("Eigene Figuren schlagen geht nicht.");
							return -1;
						}
						if (brett.getBrettFeldIndex(i - 1, j + 1).getIstBelegt()) {
							System.err.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX + 1, neueY - 1).getIstBelegt()) {
							System.err.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
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
							System.err.println("Eigene Figuren schlagen geht nicht.");
							return -1;
						}
						if (brett.getBrettFeldIndex(i - 1, j - 1).getIstBelegt()) {
							System.err.println("Zwei Figuren aufeinmal ueberspringen geht nicht.");
							return -1;
						}
						if (!brett.getBrettFeldIndex(neueX + 1, neueY + 1).getIstBelegt()) {
							System.err.println("Du kannst nachdem du geschlagen hast nicht einfach weiter gehen.");
							return -1;
						}
					}
					j--;
				}
				return 1;
			}
		}
		return -1;
	}

	/**
	 ** figurBewegen wir aufgerufen nachdem zugPruefen einen returnWert von 1 gibt
	 */
	private void figurBewegen(int xa, int ya, int xn, int yn) {

		int diffX = brett.getBrettFeldIndex(xa, ya).getPosX() - brett.getBrettFeldIndex(xn, yn).getPosX();
		int diffY = brett.getBrettFeldIndex(xa, ya).getPosY() - brett.getBrettFeldIndex(xn, yn).getPosY();
		// int diff = diffX - diffY;

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

		// Dame
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
						// nach der figurSchlagen Methode wird die Spielfigur
						// auf das Feld hinter dem GegnerStein bewegt. Damit die
						// Schleife aber richtig funktioniert muss die Dame
						// wieder ein Feld nach hinten bewegt werden
						brett.getBrettFeldIndex(i + 1, j + 1).removeSpielfigur(fig);
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
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
						// nach der figurSchlagen Methode wird die Spielfigur
						// auf das Feld hinter dem GegnerStein bewegt. Damit die
						// Schleife aber richtig funktioniert muss die Dame
						// wieder ein Feld nach hinten bewegt werden
						brett.getBrettFeldIndex(i + 1, j - 1).removeSpielfigur(fig);
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
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
						// nach der figurSchlagen Methode wird die Spielfigur
						// auf das Feld hinter dem GegnerStein bewegt. Damit die
						// Schleife aber richtig funktioniert muss die Dame
						// wieder ein Feld nach hinten bewegt werden
						brett.getBrettFeldIndex(i - 1, j + 1).removeSpielfigur(fig);
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
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
						// nach der figurSchlagen Methode wird die Spielfigur
						// auf das Feld hinter dem GegnerStein bewegt. Damit die
						// Schleife aber richtig funktioniert muss die Dame
						// wieder ein Feld nach hinten bewegt werden
						brett.getBrettFeldIndex(i - 1, j - 1).removeSpielfigur(fig);
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);
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

		for (int i = 0; i < brett.getBrettGroesse(); i++) {
			for (int j = 0; j < brett.getBrettGroesse(); j++) {
				if (brett.getBrettFeldIndex(i, j).getSpielfigur() == null) {
					// Tu nichts falls null, ansonsten...
				} else {
					Spielfigur fig = brett.getBrettFeldIndex(i, j).getSpielfigur();

					int x = fig.getPosX();
					int brettgroesse = 0;
					FarbEnum farbe = fig.getFarbe();
					brettgroesse = brett.getBrettGroesse() - 1;
					// brettgroesse -1 da das brett ja 8/10/12
					// sein kann aber index nur 7/9/11 ist
					switch (farbe) {

					case SCHWARZ:// müssen oben ankommen [8|10|12] 0|0=A1
						// TODO schauen ob das für schwarz funktioniert oder ob
						// es brettgroesse -1 heissen muss
						if (x == brettgroesse)
							fig.setDame(true);
						break;
					case WEIß:// müssen unten ankommen 0|0=A1
						if (x == 0)
							fig.setDame(true);
						break;
					}
				}

			}

		}

	}

	/**
	 * Ermittelt alle Spielfiguren eines Spielers auf dem Brett. Schreibt diese in
	 * die ArrayList FigurDieSchlagenKoennen.
	 *
	 */
	private void ermittleAlleSpielfiguren() {

		FigurDieSchlagenKoennen.clear();

		for (int zeile = 0; zeile < brett.getBrettGroesse(); zeile++) {
			for (int spalte = 0; spalte < brett.getBrettGroesse(); spalte++) {
				if (brett.getBrettFeldIndex(zeile, spalte).getIstBelegt() == true && brett.getBrettFeldIndex(zeile, spalte).getIstSchwarz() == true) {
					Spielfigur figur = brett.getBrettFeldIndex(zeile, spalte).getSpielfigur();
					if (figur.getFarbe() == getAmZug()) {
						moeglicheZuegeStartposition(zeile, spalte);
						if (moeglicheZuegeStartposition(zeile, spalte)[1] != 0) {
							FigurDieSchlagenKoennen.add(figur);
						}

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

		// RICHTUNG NACH OBEN RECHTS
		if (diffX < 0 && diffY < 0) {
			// 1.Pruefung ob Feld nach aktuellem Feld belegt ist
			if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getIstBelegt()) {
				// 2.Pruefung wenn ja ist es Genger Figur
				if (brett.getBrettFeldIndex(alteX + 1, alteY + 1).getSpielfigur().getFarbe() != getAmZug()) {
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
				if (brett.getBrettFeldIndex(alteX + 1, alteY - 1).getSpielfigur().getFarbe() != getAmZug()) {
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
				if (brett.getBrettFeldIndex(alteX - 1, alteY + 1).getSpielfigur().getFarbe() != getAmZug()) {
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
				if (brett.getBrettFeldIndex(alteX - 1, alteY - 1).getSpielfigur().getFarbe() != getAmZug()) {
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
	public void setAmZug(FarbEnum farbe) {
		farbeAmZug = farbe;
	}

	/**
	 * gibt den derzeitigen Spieler der am zug ist
	 */
	public FarbEnum getAmZug() {

		return farbeAmZug;
	}

	/**
	 * zug beenden
	 */
	public void zugBeenden() {// TODO
		geschlagen = false;
		if (getAmZug() == FarbEnum.SCHWARZ) {
			setAmZug(FarbEnum.WEIß);
		} else {
			setAmZug(FarbEnum.SCHWARZ);
		}

	}

	private void spielerHatGewonnen(FarbEnum farbe) {

		if (s1.getFarbe() == farbe) {
			gewonnenerSpieler = s1;
			System.out.println("Spieler " + s1.getName() + " hat das Spiel gewonnen!");

		} else {
			gewonnenerSpieler = s2;
			System.out.println("Spieler " + s2.getName() + " hat das Spiel gewonnen!");

		}

	}

	public Spieler getGewonnenerSpieler() {
		return gewonnenerSpieler;
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
	 * speichert das Spiel
	 *
	 * @param string
	 *          name der Datei
	 */

	public void Speichern(String dateiname, String dateiende) throws IOException {
		dateiende = dateiende.toLowerCase(); // groß klein schreibung
		switch (dateiende) {
		case "csv":
			try {
				// setdZugriff(new DatenzugriffCSV());
				// File f = new File(dateiname + ".csv");
				Object o;

				String brett = this.brett.getBrettGroesse() + ";\n";
				o = brett;

				o = o + this.s1.getName() + ";" + this.s1.getFarbe() + ";" + this.s1.getIstKi() + ";\n" + this.s2.getName() + ";" + this.s2.getFarbe() + ";" + this.s2.getIstKi() + ";\n";
				// String farbes=s1.getFarbe() + ";" ;
				// o= o+farbes;
				for (Spielfigur s : s1.getAlleFiguren()) {
					String figurs = s.getIdS() + ";";
					String posx = s.getPosX() + ";";
					String posy = s.getPosY() + ";";
					String dame = s.getDame(s) + ";";
					o = o + figurs + posx + posy + dame;

				}
				o = o + "\n";
				// String farbew=s2.getFarbe() + ";";
				// o= o+farbew;
				for (Spielfigur w : s2.getAlleFiguren()) {
					String figurw = w.getIdW() + ";";
					String posx = w.getPosX() + ";";
					String posy = w.getPosY() + ";";
					String dame = w.getDame(w) + ";";
					o = o + figurw + posx + posy + dame;
				}
				o = o + "\n";
				String amZug = this.getAmZug() + ";";
				o = o + amZug;
				csv.speichern(o);
				break;
			} catch (Exception e) {
				System.out.println("Speichern CSV fehlgeschlagen.");
			}
			break;

		case "ser":
			ser.speichern(dateiname);
			break;
		case "pdf":
			p.speichern(dateiname);
			break;
		}
	}

	@Override
	public boolean laden(String dateiname) {
		String s = dateiname;
		if (s.endsWith(".csv")) {
			this.ladenCSV(s);
			return true;
		} else if (s.endsWith(".ser")) {
			this.laden(s);
			return true;
		} else {
			System.out.println("NOP");
			return false;
		}
	}

	// TODO laden CSV
	@SuppressWarnings("unchecked")
	public void ladenCSV(String dateiname) {
		// String f = dateiname;
		// TODO kann sein, dass arraylist da mit 10 initalisiert auf 6 runter muss
		// und derzeit fehler wirft
		// ArrayList<String[]> o = new ArrayList<String[]>();
		FarbEnum farbe;
		boolean istKi;

		try {
			System.out.println(dateiname);
			System.out.println("vor zuweisung");
			ArrayList<String[]> o = (ArrayList<String[]>) csv.laden(dateiname);

			System.out.println("Löschen?");
			if (this.starten()) {
				this.allesLoeschen();
			}
			System.out.println("Gelöscht");
			this.spielStarten();

			// Abfrage welche Brettgröße gespeichert war
			System.out.println("Vor groese");
			String brettGroese[] = o.get(0);
			System.out.println("In 1");
			if (brettGroese[0].equals("12")) {
				this.aufbauen(12);
			}
			if (brettGroese[0].equals("10")) {
				this.aufbauen(10);
			}
			if (brettGroese[0].equals("8")) {
				this.aufbauen(8);
			}

			String spielerZ[] = o.get(1);
			System.out.println("In 2");
			{
				if (spielerZ[1].equals("SCHWARZ")) {
					farbe = FarbEnum.SCHWARZ;
				} else {
					farbe = FarbEnum.WEIß;
				}

				if (spielerZ[2].equals("false")) {
					istKi = false;
				} else {
					istKi = true;
				}
				this.spielerErstellen(spielerZ[0], farbe, istKi);
				if (farbe == FarbEnum.SCHWARZ) {
					s1.getAlleFiguren().clear();
				} else {
					s2.getAlleFiguren().clear();
				}
			}

			String spielerZZ[] = o.get(2);
			System.out.println("In 3");

			{
				if (spielerZZ[1].equals("WEIß")) {
					farbe = FarbEnum.WEIß;
				} else {
					farbe = FarbEnum.SCHWARZ;
				}

				if (spielerZZ[2].equals("false")) {
					istKi = false;
				} else {
					istKi = true;
				}
				this.spielerErstellen(spielerZZ[0], farbe, istKi);
				if (farbe == FarbEnum.SCHWARZ) {
					s1.getAlleFiguren().clear();
				} else {
					s2.getAlleFiguren().clear();
				}
			}
			this.anzeigen();
			
			
			for (int i = 0; i < brett.getBrettGroesse(); i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {

					if (brett.getBrettFeldIndex(i, j).getIstSchwarz()) {
						if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
							brett.getBrettFeldIndex(i, j).removeSpielfigur(brett.getBrettFeldIndex(i, j).getSpielfigur());
						}
					}
				}
			}
			
			
			this.anzeigen();
			String figurenZ[] = o.get(3);
			{
				System.out.println("In 4");

				for (int i = 0; i < figurenZ.length; i = i + 4) {

					int IdS = Integer.parseInt(figurenZ[i]);
					int x = Integer.parseInt(figurenZ[i + 1]);
					int y = Integer.parseInt(figurenZ[i + 2]);
					boolean dame;
					if (figurenZ[i + 3].equals("false")) {
						dame = false;
					} else {
						dame = true;
					}
					System.out.println("In 4 nach dame prüfung");
					System.out.println(IdS);
					// System.out.println(x);
					// System.out.println(y);
					// System.out.println(dame);

					Spielfigur figur = new Spielfigur(FarbEnum.SCHWARZ, dame);
					figur.setPosX(x);
					figur.setPosY(y);
					figur.setIdS(IdS);
					brett.getBrettFeldIndex(x,y).setSpielfigur(figur);
					s1.getAlleFiguren().add(figur);
				}
			}
			this.anzeigen();
			String figurenZZ[] = o.get(4);
			{
				System.out.println("In 5");

				for (int i = 0; i < figurenZZ.length; i = i + 4) {

					int IdW = Integer.parseInt(figurenZZ[i]);
					int x = Integer.parseInt(figurenZZ[i + 1]);
					int y = Integer.parseInt(figurenZZ[i + 2]);
					boolean dame;
					if (figurenZZ[i + 3].equals("false")) {
						dame = false;
					} else {
						dame = true;
					}
					System.out.println("In 5 nach dame prüfung");
					System.out.println(IdW);
					// System.out.println(x);
					// System.out.println(y);
					// System.out.println(dame);

					Spielfigur figur = new Spielfigur(FarbEnum.WEIß, dame);
					figur.setPosX(x);
					figur.setPosY(y);
					figur.setIdW(IdW);
					brett.getBrettFeldIndex(x,y).setSpielfigur(figur);
					s2.getAlleFiguren().add(figur);
				}
			}
			this.anzeigen();
			String dran[] = o.get(5);
			{
				System.out.println("In 6");
				if (dran[0].equals("SCHWARZ")) {
					farbe = FarbEnum.SCHWARZ;
				} else {
					farbe = FarbEnum.WEIß;
				}
				this.starten();
				this.setAmZug(farbe);
				this.anzeigen();
			}
			this.anzeigen();
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Laden fehlgeschlagen!");
		}
		this.anzeigen();
//TODO
//		for (int i = 0; i < brett.getBrettGroesse() - 1; i++) {
//			for (int j = 0; j < brett.getBrettGroesse() - 1; i++) {
//
//				if (brett.getBrettFeldIndex(i, j).getIstSchwarz()) {
//					if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
//						brett.getBrettFeldIndex(i, j).removeSpielfigur(brett.getBrettFeldIndex(i, j).getSpielfigur());
//					}
//				}
//			}
//		}
//TODO
	}
	
	public void ladenSER(String dateiname){
		
		GUI g = new GUI(12);
		g = (GUI) ser.laden(dateiname);
		g.startenWeitergeben();
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

	public boolean ziehen(String startp, String endp) {
		if (!spiellaeuft) {
			System.err.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
			return false;
		} else {
			brett.display();
			kannWeiterSchlagen = false;

			// Diese Methode schreibt alle Figuren die schlagen
			// koennen in die FigurDieSchlagenKoennen ArrayList
			ermittleAlleSpielfiguren();

			// splittet den String zb.A1 in char und int und
			// ueberprueft auf Gueltigkeit
			if (pruefeStartposition(startp) == false) {
				return false;
			}

			// Wenn geschlagen und nochmals ziehen eingegeben wurde,
			// wird die Eingabe Startposition auf Gueltigkeit
			// ueberpruft, heisst Startposition muss die Endposition
			// vom letzten Zug sein
			if (geschlagen == true && (startC != startCNS || startI != startINS)) {
				// System.out.println("startC: " + startC
				// + " |startCNS: " + startCNS + " / startI: "
				// + startI + "|startINS: " + startINS);
				System.out.println("Du  musst mit der Figur " + brett.gibBrettFeldSchachnotation(startINS, startCNS) + " ziehen");
				startC = 0;
				startI = 0;
				return false;
			}

			// speichert die moeglichen Zuege int tmpS. [0] fuer
			// Laufen, [1 fuer Schlagen]
			int[] tmpS = moeglicheZuegeStartposition(startC, startI);
			int tempZuegeLaufen = tmpS[0];
			int tempZuegeSchlagen = tmpS[1];
			System.out.println("Anzahl Zuege Laufen: " + tempZuegeLaufen + " " + FigurenLaufen.toString() + "\nAnzahl Zuege Schlagen: " + tempZuegeSchlagen + " " + FigurenSchlagen.toString());

			// Wenn eine Spielfigur uebrig und keine gueltigen Zuege
			// mehr vorhanden, dann hat Spieler 2 gewonnen
			if (s1.getAlleFiguren().size() == 1 && s1.getFarbe() == getAmZug() && (tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				// spieler 2 gewinnt
				System.err.println("Du hast keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s2.getFarbe());
				return false;
			}

			// Wenn eine Spielfigur uebrig und keine gueltigen Zuege
			// mehr vorhanden, dann hat Spieler 1 gewonnen
			if (s2.getAlleFiguren().size() == 1 && s2.getFarbe() == getAmZug() && (tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				// spieler 1 gewinnt
				System.err.println("Du hast keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s1.getFarbe());
				return false;
			}

			// Wenn von der eingegebenen Startposition keine Zuege
			// moeglich
			if ((tempZuegeLaufen == 0 && tempZuegeSchlagen == 0)) {
				System.err.println("Mit dieser Figur sind keine Zuege moeglich! zurueck ins Menue");
				System.out.println("Gib ziehen ein.");
				return false;
			}

			System.out.println("Eingegebene Startposition: " + brett.getBrettFeldIndex(startC, startI).getId() + "\n");

			// splittet den String zb.A1 in char und int und
			// ueberprueft auf Gueltigkeit
			if (pruefeEndposition(endp) == false) {
				return false;
			}

			System.out.println("Eingegebene Endposition: " + brett.getBrettFeldIndex(endC, endI).getId() + "\n");

			// Ueberprueft den Zug auf Gueltigkeit
			int zugPruefen = zugPruefen(startC, startI, endC, endI);
			// zugPruefen == 1 Laufen
			// zugPruefen == 2 Schlagen
			// zugPruefen == -1 Zug ungueltig

			if (zugPruefen == 1) {
				// TODO
				if (geschlagen == true) {

					int anzFDSK = FigurDieSchlagenKoennen.size();
					for (int i = 0; i < anzFDSK; i++) {
						if (startCNS == FigurDieSchlagenKoennen.get(i).getPosX() && startINS == FigurDieSchlagenKoennen.get(i).getPosY()) {
							counter++;
						}
					}
					if (counter > 0) {
						geschlagen = false;
						counter = 0;
						figurBewegen(startC, startI, endC, endI);
						dameWerden();
					} else {
						System.out.println(geschlagen);
						System.out.println("Du darfst mit der Figur " + brett.gibBrettFeldSchachnotation(startINS, startCNS) + " nurnoch schlagen!");
						// System.out.println("startC: " + startC
						// + " |startCNS: " + startCNS +
						// " / startI: "
						// + startI + "|startINS: " + startINS);
						System.out.println("Gib ziehen ein.");
						return false;
					}
				}

				// TODO

				if (geschlagen == false) {
					figurBewegen(startC, startI, endC, endI);
					dameWerden();
				}

				// Diese Abfrage muss erfolgen weil Dame kann laufen
				// und schlagen
				if (geschlagen == true && moeglicheZuegeStartposition(endC, endI)[1] != 0) {
					kannWeiterSchlagen = true;
					brett.display();
					return true;

				}
				if (geschlagen == false && FigurDieSchlagenKoennen.size() != 0) {
					System.out.println("Die Figur an der Stelle " + brett.gibBrettFeldSchachnotation(FigurDieSchlagenKoennen.get(0).getPosY(), FigurDieSchlagenKoennen.get(0).getPosX()) + " wird gepustet");
					pusten(FigurDieSchlagenKoennen.get(0));
				}
				zugBeenden();
				dameWerden();
				brett.display();
				System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
				System.out.println("Gib ziehen ein");
				return true;
			}

			if (zugPruefen == 2) {
				figurSchlagen(startC, startI, endC, endI);

				if (geschlagen == true && moeglicheZuegeStartposition(endC, endI)[1] != 0) {
					kannWeiterSchlagen = true;
					brett.display();
				} else {
					dameWerden();
					zugBeenden();
					brett.display();
					System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
					System.out.println("Gib ziehen ein");
				}
				return true;
			}

			if (zugPruefen == -1) {
				System.out.println("Gib nochmals ziehen ein.");
				return false;
			}
		}
		return false;

	}

	public boolean kannWeiterZiehen() {
		if (kannWeiterSchlagen == true) {
			return true;
		} else
			return false;
	}

	public void willWeiterZiehen() {
		startCNS = endC;
		startINS = endI;
		dameWerden();
		brett.display();
		System.out.println("Du darfst nochmals ziehen. Gib ziehen ein");
	}

	public void willNichtWeiterZiehen() {
		System.out.println("Die Figur " + brett.gibBrettFeldSchachnotation(endI, endC) + " wird entfernt(Pusten)!");
		pusten(brett.getBrettFeldIndex(endC, endI).getSpielfigur());
		startC = 0;
		startI = 0;
		endC = 0;
		endI = 0;
		// Zug beenden setzt geschlagen auf false
		zugBeenden();
		dameWerden();
		brett.display();
		System.out.println("Der Spieler mit der Farbe: " + getAmZug() + " ist nun am Zug.");
		System.out.println("Gib ziehen ein.");
	}

	public void allesLoeschen() {
		if (s1 == null || s2 == null) {
			return;
		} else {
			System.out.println("Neues Spiel.");
			spielAufgebaut = false;
			spielerAnzahl = 0;
			// name=null;
			// farbe=null;
			// ki=null;
			spiellaeuft = false;
			schwarzvergeben = false;
			weissvergeben = false;
			geschlagen = false;

			// private Spielbrett brett;
			// Spieler spieler=null;
			// FarbEnum farbeAmZug = null;
			gewonnenerSpieler = null;
			s1.spielerLoeschen();
			s2.spielerLoeschen();
			if (k1 != null) {
				k1 = null;
			}
			if (k2 != null) {
				k2 = null;
			}

			brett.brettLoeschen();
			// k1 = null;
			// k2 = null;

			startC = 0;
			endC = 0;
			startI = 0;
			endI = 0;
			startCNS = 0;
			startINS = 0;
			counter = 0;

			// private boolean RekHatGeschlagen;
			FigurenLaufen.clear();
			FigurenSchlagen.clear();
			FigurDieSchlagenKoennen.clear();
		}
	}

	public Spielbrett getBrett() {
		return this.brett;
	}

	public boolean kizieh() {
		if (!spiellaeuft) {
			System.out.println("Spiel hat noch nicht begonnen! Zurueck in Hauptmenue");
			return false;
		}
		if (this.getAmZug() == FarbEnum.SCHWARZ) {
			if (k1 == null) {
				System.out.println("Schwarz ist keine KI");
				return false;
			}
			int[] tmpZug = null;
			int[] zuge = k1.zieh();
			if (zuge == null) {
				System.err.println("Ki hat keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s2.getFarbe());
				return false;
			}
			int zugPruefenKI = zugPruefen(zuge[0], zuge[1], zuge[2], zuge[3]);
			if (zugPruefenKI == 1) {
				figurBewegen(zuge[0], zuge[1], zuge[2], zuge[3]);
				setZugFurLog(zuge[0], zuge[1], zuge[2], zuge[3]);
				dameWerden();
			}
			if (zugPruefenKI == 2) {
				Spielfigur figur = brett.getBrettFeldIndex((zuge[0]), (zuge[1])).getSpielfigur();
				if (!figur.getDame(figur)) {
					if (zuge[2] == 0 || zuge[2] == brett.getBrettGroesse() - 1) {
						k1.setHatGeschlagen(false);
					}
				}
				figurSchlagen(zuge[0], zuge[1], zuge[2], zuge[3]);
				setZugFurLog(zuge[0], zuge[1], zuge[2], zuge[3]);
				dameWerden();
			}
			if (k1.hatGeschlagen()) {
				int[] zugee = zuge;
				do {
					Spielfigur figur = brett.getBrettFeldIndex((zugee[2]), (zugee[3])).getSpielfigur();
					zugee = k1.getWeitereSchlaege(zugee[2], zugee[3], figur);
					tmpZug = zugee;

					if (zugee != null) {
						int zugdanachPruefenKI = zugPruefen(zugee[0], zugee[1], zugee[2], zugee[3]);
						if (zugdanachPruefenKI == 1) {
							figurBewegen(zugee[0], zugee[1], zugee[2], zugee[3]);
							setZugFurLog(zugee[0], zugee[1], zugee[2], zugee[3]);
							dameWerden();
						}
						if (zugdanachPruefenKI == 2) {
							Spielfigur figure = brett.getBrettFeldIndex((zugee[0]), (zugee[1])).getSpielfigur();
							if (!figur.getDame(figure)) {
								if (zugee[2] == 0 || zugee[2] == brett.getBrettGroesse() - 1) {
									k1.setHatGeschlagen(false);
								}
							}
							figurSchlagen(zugee[0], zugee[1], zugee[2], zugee[3]);
							setZugFurLog(zugee[0], zugee[1], zugee[2], zugee[3]);
							dameWerden();
						}
					}
				} while (tmpZug != null && k1.hatGeschlagen());

			}
			brett.display();
			zugBeenden();
			k1.setHatGeschlagen(false);
			return true;

		}
		// ---------------------------------------------------
		if (this.getAmZug() == FarbEnum.WEIß) {
			if (k2 == null) {
				System.out.println("Weiß ist keine KI");
				return false;
			}
			int[] tmpZug = null;
			int[] zuge = k2.zieh();
			if (zuge == null) {
				System.err.println("Ki hat keine Zugmoeglichkeiten mehr.");
				spielerHatGewonnen(s1.getFarbe());
				return false;
			}
			int zugPruefenKI = zugPruefen(zuge[0], zuge[1], zuge[2], zuge[3]);
			if (zugPruefenKI == 1) {
				figurBewegen(zuge[0], zuge[1], zuge[2], zuge[3]);
				setZugFurLog(zuge[0], zuge[1], zuge[2], zuge[3]);
				dameWerden();
			}
			if (zugPruefenKI == 2) {
				Spielfigur figur = brett.getBrettFeldIndex((zuge[0]), (zuge[1])).getSpielfigur();
				if (!figur.getDame(figur)) {
					if (zuge[2] == 0 || zuge[2] == brett.getBrettGroesse() - 1) {
						k2.setHatGeschlagen(false);
					}
				}
				figurSchlagen(zuge[0], zuge[1], zuge[2], zuge[3]);
				setZugFurLog(zuge[0], zuge[1], zuge[2], zuge[3]);
				dameWerden();
			}
			if (k2.hatGeschlagen()) {
				int[] zugee = zuge;
				do {
					Spielfigur figur = brett.getBrettFeldIndex((zugee[2]), (zugee[3])).getSpielfigur();
					zugee = k2.getWeitereSchlaege(zugee[2], zugee[3], figur);
					tmpZug = zugee;

					if (zugee != null) {
						int zugdanachPruefenKI = zugPruefen(zugee[0], zugee[1], zugee[2], zugee[3]);
						if (zugdanachPruefenKI == 1) {
							figurBewegen(zugee[0], zugee[1], zugee[2], zugee[3]);
							setZugFurLog(zugee[0], zugee[1], zugee[2], zugee[3]);
							dameWerden();
						}
						if (zugdanachPruefenKI == 2) {
							Spielfigur figure = brett.getBrettFeldIndex((zugee[0]), (zugee[1])).getSpielfigur();
							if (!figur.getDame(figure)) {
								if (zugee[2] == 0 || zugee[2] == brett.getBrettGroesse() - 1) {
									k2.setHatGeschlagen(false);
								}
							}
							figurSchlagen(zugee[0], zugee[1], zugee[2], zugee[3]);
							setZugFurLog(zugee[0], zugee[1], zugee[2], zugee[3]);
							dameWerden();
						}
					}
				} while (tmpZug != null && k2.hatGeschlagen());

			}
			brett.display();
			zugBeenden();
			k2.setHatGeschlagen(false);
			return true;
		}
		return false;
	}

	private void setZugFurLog(int zugee, int zugee2, int zugee3, int zugee4) {
		char a = (char) (zugee2 + 97);
		int b = zugee + 1;
		char c = (char) (zugee4 + 97);
		int d = zugee3 + 1;
		// ((char) (zielKoords[3] + 97)) + "" + (zielKoords[2] + 1)
		String ausgabe = "Startposition: " + a + b + " Endposition: " + c + d;
		System.out.println(ausgabe);
		zugFurLog.add(ausgabe);
	}

	public ArrayList<String> getZugFurLog() {

		return zugFurLog;

	}

	public KI getK1() {
		if (k1 == null)
			return null;
		return k1;
	}

	public KI getK2() {
		if (k2 == null)
			return null;
		return k2;
	}
}
