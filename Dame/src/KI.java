import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.Random;

/**
 * 
 * @author Baris, Daniel, Simon, Hannes
 *
 */
public abstract class KI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632008787864445195L;
	private Spieler spieler;
	private Spielbrett brett;

	/**
	 * Konstruktor der KI
	 * 
	 * @param spieler
	 * @param brett
	 */

	public KI(Spieler spieler, Spielbrett brett) {
		if (spieler == null) {
			throw new RuntimeException("Ki darf nicht ohne Spieler exisitieren");
		} else {
			this.spieler = spieler;
			this.brett = brett;
		}
	}

	public Spieler getSpieler() {
		return this.spieler;
	}

	/**
	 * Ermittelt die Zugrichtung.
	 */
	private int zugRichtung() {
		if (spieler.getFarbe() == FarbEnum.WEIß) {
			return -1;
		} else {
			return +1;
		}
	}

	private int zugRichtung2() {
		if (spieler.getFarbe() == FarbEnum.WEIß) {
			return -2;
		} else {
			return +2;
		}
	}

	/**
	 * Wählt aus den ArrayLists einen Zug aus
	 */

	public int[] zieh() {
		ArrayList<ArrayList<int[]>> zug = this.getZug();

		int limit = zug.size();
		int i = this.zufall(limit);
//		System.out.println("Limit: " + limit);
//		System.out.println("Zufallszahl: " + i);
		ArrayList<int[]> zugZiel = zug.get(i);

//		Object [] bla = zug.toArray();
//		for (int t = 0; t <= bla.length - 1; t++) {
//			System.out.println("in Zug steht dies: " + bla[t]);
//		}
//
//		int s3  = (int) bla[i];
//		int s2  = (int) bla[i+1];
//		char s1 = (char) s3;
//		
		int limit2 = zugZiel.size();
		int i2 = this.zufall(limit2);
//		System.out.println("Limit2: " + limit2);
//		System.out.println("Zufallszahl2: " + i2);
		
		int zielKoords[] = zugZiel.get(i2);
		for (int l = 0; l <= zielKoords.length - 1; l++) {
			System.out.println(zielKoords[l]);
		}

//		System.out.println("Zufallszahl2 holt die koordinaten");
//		System.out.println("Figur WIRD ZIEHEN von " + (char) ziehKoords[0] + ziehKoords[1] + " nach " + (char) ziehKoords[2] + ziehKoords[3] + ".");

//		System.out.println("von char" + (char) zielKoords[2]);
//		System.out.println("von Zahl" + zielKoords[3]);
//
//		System.out.println("nach char" + (char) zielKoords[0]);
//		System.out.println("nach zahl" + zielKoords[1]);

		// int[] zugZielKoords1 = schlaege.get(0);
		// int rueckgabeSchlaege[] = { a, j, zugZielKoords1[0], zugZielKoords1[1]
		// ------------------------------------------------------------------------
		int rueckgabeZieh[] = { zielKoords[2],  zielKoords[3] ,zielKoords[0], zielKoords[1]};
		// ------------------------------------------------------------------------
//		System.out.println("Figur WTF zog von " + (char) ziehKoords[0] + ziehKoords[1] + " nach " + (char) ziehKoords[2] + ziehKoords[3] + ".");
//		System.out.println("OMG");
		System.out.println(zielKoords[2]+" " +zielKoords[3] +" nach "+ zielKoords[0]+" "+zielKoords[1]);
		return rueckgabeZieh;
	}

	/**
	 * Erfasst eigene Spielfiguren, um später mit getZuege mögliche Züge und mit
	 * getSchlaege mögliche Schläge zu ermitteln.
	 */
	private ArrayList<ArrayList<int[]>> getZug() {

		ArrayList<ArrayList<int[]>> alleSchlaege = new ArrayList<ArrayList<int[]>>();
		ArrayList<ArrayList<int[]>> alleZuege = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < brett.getBrettGroesse(); i++) {
			for (int j = 1; j <= brett.getBrettGroesse(); j++) {
				char a = (char) ('a' + i);
				Spielfeld feld = brett.getBrettFeldSchachnotation(a, j);
				if (feld.getIstSchwarz() && feld.getIstBelegt()) {
					Spielfigur figur = feld.getSpielfigur();
					if (figur.getFarbe() == spieler.getFarbe()) {
						ArrayList<int[]> schlaege = this.getSchlaege(a, j, figur);
						if (!schlaege.isEmpty()) {
							for (int k = 0; k <= schlaege.size() - 1; k++) {
								int[] zugZielKoords1 = schlaege.get(k);
								if(zugZielKoords1!=null){
								zugZielKoords1[2] = a;
								zugZielKoords1[3] = j;
								}
								// int rueckgabeSchlaege[] = { a, j, zugZielKoords1[0],
								// zugZielKoords1[1] };
								System.out.println("Hat Figur an Stelle " + ((char) (a + 1)) + (j + 1) + " geschlagen.");
								System.out.println("Figur will SCHLÄGE von " + a + j + " nach " + (char) zugZielKoords1[0] + zugZielKoords1[1] + " GEBEN.");
								alleSchlaege.add(schlaege);
							}
						} else {
							ArrayList<int[]> zuege = this.getZuege(a, j, figur);
							if (!zuege.isEmpty()) {
								for (int k = 0; k <= zuege.size() - 1; k++) {

									int[] zugZielKoords2 = zuege.get(k);
									// int rueckgabeBewegung[] = { a, j, zugZielKoords2[0],
									// zugZielKoords2[1] };
									System.out.println("Figur will ZUEGE von " + a + j + " nach " + (char) zugZielKoords2[0] + zugZielKoords2[1] + " ziehen.");
									if(zugZielKoords2!=null){
										zugZielKoords2[2] = a;
										zugZielKoords2[3] = j;
										}
									alleZuege.add(zuege);
								}
							}
						}
					}
				}
			}
		}
		if (!alleSchlaege.isEmpty()) {
			return alleSchlaege;
		}
		if (!alleZuege.isEmpty()) {
			return alleZuege;
		}
		return null;
	}

	// fragt für Spielfigur ab welche züge möglich sind
	// TODO bewertung der Züge

	private ArrayList<int[]> getSchlaege(char a, int j, Spielfigur figur) {
		ArrayList<int[]> schlaege = new ArrayList<int[]>();

		if (!figur.getDame(figur)) {
			// wäre ein schritt nach vorne noch im spielbrett?
			if (j + this.zugRichtung2() >= 1 && j + this.zugRichtung2() <= brett.getBrettGroesse()) {
				// ist schräger schritt nach rechts möglich?
				if (a + 2 <= 'a' + brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldSchachnotation((char) (a + 1), j + this.zugRichtung());
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldSchachnotation((char) (a + 2), j + this.zugRichtung2());
							if (zugFeld2.getIstBelegt() == false) {
								// Spielfeld schlagbarerStein =
								// brett.getBrettFeldSchachnotation((char) (a + 1), j +
								// this.zugRichtung());
								int[] koordSchlagen = { (a + 2), (j + (this.zugRichtung2())), 0,0 };
								schlaege.add(koordSchlagen);
								// System.out.println("Hat Figur an Stelle " + schlagbarerStein
								// +" geschlagen.");
							}
						}
					}
				}
				// ist schräger schritt nach links möglich?
				if (a - 2 >= 'a') {
					Spielfeld zugFeld1 = brett.getBrettFeldSchachnotation((char) (a - 1), j + this.zugRichtung());
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();
						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldSchachnotation((char) (a - 2), j + this.zugRichtung2());
							if (zugFeld2.getIstBelegt() == false) {
								// Spielfeld schlagbarerStein =
								// brett.getBrettFeldSchachnotation((char) (a - 1), j +
								// this.zugRichtung());
								int[] koordSchlagen = { (a - 2), (j + (this.zugRichtung2())), 0, 0 };
								schlaege.add(koordSchlagen);
								// System.out.println("Hat Figur an Stelle " + schlagbarerStein
								// +" geschlagen.");
							}
						}
					}
				}
			}
		}
		return schlaege;
	}

	/**
	 * Findet für übergebene Figur die möglichen Züge
	 */
	private ArrayList<int[]> getZuege(char a, int j, Spielfigur figur) {
		ArrayList<int[]> zuge = new ArrayList<int[]>();

		if (!figur.getDame(figur)) {
			// wäre ein schritt nach vorne noch im spielbrett?
			if (j + this.zugRichtung() >= 1 && j + this.zugRichtung() <= brett.getBrettGroesse()) {
				// ist schräger schritt nach rechts möglich?
				if (a + 1 <= 'a' + brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldSchachnotation((char) (a + 1), j + this.zugRichtung());
					if (zugFeld1.getIstBelegt() == false) {
						int[] koord = { a + 1, j + this.zugRichtung(),0 ,0 };
						zuge.add(koord);
						System.out.println("nach rechts: " + (((char) a) + 1) + " " + (j + this.zugRichtung()));
					}
				}
				// ist schräger schritt nach links möglich?
				if (a - 1 >= 'a') {
					Spielfeld zugFeld2 = brett.getBrettFeldSchachnotation((char) (a - 1), j + this.zugRichtung());
					if (zugFeld2.getIstBelegt() == false) {
						int[] koord = { a - 1, j + this.zugRichtung(), 0,0 };
						zuge.add(koord);
						System.out.println("nach links: " + (((char) a) - 1) + " " + (j + this.zugRichtung()));
					}
				}
			}
		} else {
			// abfrage für dame
		}
		return zuge;

	}

	/**
	 * Ermittelt eine zufällige Stelle der ArrayList
	 */
	public int zufall(int limit) {
		int zufallszug = new Random().nextInt(limit);
		return zufallszug;
	}
	
	
	@Override
	public String toString() {
		return "KI: " + spieler.getName() + " mit der Farbe: " + spieler.getFarbe();

	}

	// private KI zug(){
	// Iterator<Spielfigur> alleFiguren = spieler.getAlleFiguren().iterator();
	// while(alleFiguren.hasNext()){
	// }
	// return null;
	// }
}
