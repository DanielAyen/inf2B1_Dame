import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Iterator;

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
	 * Erfasst eigene Spielfiguren, um später mit getZuege mögliche Züge zu
	 * ermitteln.
	 */
	public int[] zug() {

		// ArrayList<ArrayList<int[]>> alleZuge = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < brett.getBrettGroesse(); i++) {
			for (int j = 1; j <= brett.getBrettGroesse(); j++) {
				char a = (char) ('a' + i);
				Spielfeld feld = brett.getBrettFeldSchachnotation(a, j);
				if (feld.getIstSchwarz() && feld.getIstBelegt()) {
					Spielfigur figur = feld.getSpielfigur();
					if (figur.getFarbe() == spieler.getFarbe()) {
						ArrayList<int[]> schlaege = this.getSchlaege(a, j, figur);
						if (!schlaege.isEmpty()) {
							int[] zugZielKoords1 = schlaege.get(0);
							int rueckgabeSchlaege[] = { a, j, zugZielKoords1[0], zugZielKoords1[1] };
							System.out.println("Figur zog von " + a+j+" nach " + (char)zugZielKoords1[0] + zugZielKoords1[1]+".");
							return rueckgabeSchlaege;
						} else {
							ArrayList<int[]> zuege = this.getZuege(a, j, figur);
							if (!zuege.isEmpty()) {
								int[] zugZielKoords2 = zuege.get(0);
								int rueckgabeBewegung[] = { a, j, zugZielKoords2[0], zugZielKoords2[1] };
								System.out.println("Figur zog von " + a+j+" nach " + (char)zugZielKoords2[0] + zugZielKoords2[1]+".");
								return rueckgabeBewegung;
							}
						}
					}
				}
			}
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
								int[] koordSchlagen = { (a + 2), (j + (this.zugRichtung2())) };
								schlaege.add(koordSchlagen);
								System.out.println("Hat Figur an Stelle " + koordSchlagen +" geschlagen.");
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
								int[] koordSchlagen = { (a - 2), (j + (this.zugRichtung2())) };
								schlaege.add(koordSchlagen);
								System.out.println("Hat Figur an Stelle " + koordSchlagen +" geschlagen.");
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
						int[] koord = { a + 1, j + this.zugRichtung() };
						zuge.add(koord);
					}
				}
				// ist schräger schritt nach links möglich?
				if (a - 1 >= 'a') {
					Spielfeld zugFeld2 = brett.getBrettFeldSchachnotation((char) (a - 1), j + this.zugRichtung());
					if (zugFeld2.getIstBelegt() == false) {
						int[] koord = { a - 1, j + this.zugRichtung() };
						zuge.add(koord);
					}
				}
			}
		} else {
			// abfrage für dame
		}
		return zuge;

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
