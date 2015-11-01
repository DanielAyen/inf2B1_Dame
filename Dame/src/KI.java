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
	private static final long serialVersionUID = -1239435568425931372L;
	private Spieler spieler;
	private Spielbrett brett;

	/**
	 * Konstruktor der KI
	 * 
	 * @param spieler
	 */

	public KI(Spieler spieler) {
		if (spieler == null) {
			throw new RuntimeException("Ki darf nicht ohne Spieler exisitieren");
		} else {
			this.spieler = spieler;
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

	private void zug() {

		// Methode erfasst eigene Spielfiguren, um später mit getZuege mögliche Züge
		// zu ermitteln.

		// ArrayList<ArrayList<int[]>> alleZuge = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < brett.getBrettGroesse(); i++) {
			for (int j = 0; j < brett.getBrettGroesse(); j++) {
				Spielfeld feld = brett.getBrettFeldIndex(i, j);
				if (feld.getIstSchwarz() && feld.getIstBelegt()) {
					Spielfigur figur = feld.getSpielfigur();
					if (figur.getFarbe() == spieler.getFarbe()) {
						ArrayList<int[]> zuege = this.getZuege(i, j, figur);
						if (!zuege.isEmpty()) {
							zuege.get(0);
							// TODO movemethode aufrufen
							return;
						}
					}
				}
			}
		}
	}

	// fragt für Spielfigur ab welche züge möglich sind
	// TODO bewertung der Züge

	private ArrayList<int[]> getZuege(int i, int j, Spielfigur figur) {
		ArrayList<int[]> zuge = new ArrayList<int[]>();
		if (!figur.getDame(figur)) {
			if (i < brett.getBrettGroesse() - 1 && j < brett.getBrettGroesse()) {

				Spielfeld zugFeld1 = brett.getBrettFeldIndex(i + 1, j + this.zugRichtung());
				if (zugFeld1.getIstBelegt()) {
					// auf farbe prüfen, überspringen möglich?
				} else {
					int[] koord = { i + 1, j + this.zugRichtung() };
					zuge.add(koord);

				}
			}
			if (i > 0 && j < brett.getBrettGroesse()) {
				Spielfeld zugFeld2 = brett.getBrettFeldIndex(i - 1, j + this.zugRichtung());
				if (zugFeld2.getIstBelegt()) {
					// auf farbe prüfen, überspringen möglich?
				} else {
					int[] koord = { i - 1, j + this.zugRichtung() };
					zuge.add(koord);
				}
			}
		} else {
			// abfrage für dame
		}
		return zuge;

	}

	// private KI zug(){
	// Iterator<Spielfigur> alleFiguren = spieler.getAlleFiguren().iterator();
	// while(alleFiguren.hasNext()){
	// }
	// return null;
	// }
}
