import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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

	private int zugRichtung() {
		if (spieler.getFarbe() == FarbEnum.WEIß) {
			return -1;
		} else {
			return +1;
		}
	}

	public int[] zug() {
		// Methode erfasst eigene Spielfiguren, um später mit getZuege mögliche Züge
		// zu ermitteln.

		// ArrayList<ArrayList<int[]>> alleZuge = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < brett.getBrettGroesse(); i++) {
			for (int j = 1; j <= brett.getBrettGroesse(); j++) {
				char a = (char) ('a' + i);
				Spielfeld feld = brett.getBrettFeldSchachnotation(a, j);
				if (feld.getIstSchwarz() && feld.getIstBelegt()) {
					Spielfigur figur = feld.getSpielfigur();
					if (figur.getFarbe() == spieler.getFarbe()) {
						ArrayList<int[]> zuege = this.getZuege(a, j, figur);
						if (!zuege.isEmpty()) {
							int[] zugZielKoords = zuege.get(0);
							int rückgabe[] = { a, j, zugZielKoords[0], zugZielKoords[1] };
							return rückgabe;
						}
					}
				}
			}
		}
		return null;
	}

	// fragt für Spielfigur ab welche züge möglich sind
	// TODO bewertung der Züge

	private ArrayList<int[]> getZuege(char a, int j, Spielfigur figur) {
		ArrayList<int[]> zuge = new ArrayList<int[]>();

		if (!figur.getDame(figur)) {
			// wäre ein schritt nach vorne noch im spielbrett?
			if (j + this.zugRichtung() >= 1 && j + this.zugRichtung() <= brett.getBrettGroesse()) {
				// ist schräger schritt nach rechts möglich?
				if (a + 1 <= 'a' + brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldSchachnotation((char) (a + 1), j + this.zugRichtung());
					if (zugFeld1.getIstBelegt()) {
						// auf farbe prüfen, überspringen möglich?
					} else {
						int[] koord = { a + 1, j + this.zugRichtung() };
						zuge.add(koord);

					}
				}
				// ist schräger schritt nach links möglich?
				if (a - 1 >= 'a') {
					Spielfeld zugFeld2 = brett.getBrettFeldSchachnotation((char) (a - 1), j + this.zugRichtung());
					if (zugFeld2.getIstBelegt()) {
						// auf farbe prüfen, überspringen möglich?
					} else {
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
