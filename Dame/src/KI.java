
import java.io.Serializable;
import java.util.ArrayList;
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
	private boolean schlag = false;

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

	// private int zugRichtung2() {
	// if (spieler.getFarbe() == FarbEnum.WEIß) {
	// return -2;
	// } else {
	// return +2;
	// }
	// }

	/**
	 * Wählt aus den ArrayLists einen Zug aus
	 */

	public int[] zieh() {
		ArrayList<ArrayList<int[]>> zug = this.getZug();
		if (zug != null) {
			int limit = zug.size();
			int i = this.zufall(limit);
			// System.out.println("Limit: " + limit);
			// System.out.println("Zufallszahl: " + i);
			ArrayList<int[]> zugZiel = zug.get(i);

			// int s3 = (int) bla[i];
			// int s2 = (int) bla[i+1];
			// char s1 = (char) s3;

			int limit2 = zugZiel.size();
			int i2 = this.zufall(limit2);
			// System.out.println("Limit2: " + limit2);
			// System.out.println("Zufallszahl2: " + i2);

			int zielKoords[] = zugZiel.get(i2);
			// for (int l = 0; l <= zielKoords.length - 1; l++) {
			// System.out.println(zielKoords[l]);
			// }

			// ------------------------------------------------------------------------
			int rueckgabeZieh[] = { (zielKoords[2]), (zielKoords[3]), (zielKoords[0]), (zielKoords[1]) };
			// ------------------------------------------------------------------------
			System.out.println(((char) (zielKoords[3] + 97)) + "" + (zielKoords[2] + 1) + " nach " + ((char) (zielKoords[1] + 97)) + "" + (zielKoords[0] + 1));
			return rueckgabeZieh;
		} else {
			return null;
		}

	}

	/**
	 * Erfasst eigene Spielfiguren, um später mit getZuege mögliche Züge und mit
	 * getSchlaege mögliche Schläge zu ermitteln.
	 */
	private ArrayList<ArrayList<int[]>> getZug() {

		ArrayList<ArrayList<int[]>> alleSchlaege = new ArrayList<ArrayList<int[]>>();
		ArrayList<ArrayList<int[]>> alleZuege = new ArrayList<ArrayList<int[]>>();
		for (int zeile = 0; zeile < brett.getBrettGroesse(); zeile++) {
			for (int spalte = 0; spalte < brett.getBrettGroesse(); spalte++) {
				Spielfeld feld = brett.getBrettFeldIndex(zeile, spalte);
				if (feld.getIstSchwarz() && feld.getIstBelegt()) {
					Spielfigur figur = feld.getSpielfigur();
					if (figur.getFarbe() == spieler.getFarbe()) {
						ArrayList<int[]> schlaege = this.getSchlaege(zeile, spalte, figur);
						if (!schlaege.isEmpty()) {
							for (int k = 0; k <= schlaege.size() - 1; k++) {
								int[] zugZielKoords1 = schlaege.get(k);
								if (zugZielKoords1 != null) {
									zugZielKoords1[2] = zeile;
									zugZielKoords1[3] = spalte;
								}
								alleSchlaege.add(schlaege);
							}
						} else {
							ArrayList<int[]> zuege = this.getZuege(zeile, spalte, figur);
							if (!zuege.isEmpty()) {
								for (int k = 0; k <= zuege.size() - 1; k++) {

									int[] zugZielKoords2 = zuege.get(k);
									if (zugZielKoords2 != null) {
										zugZielKoords2[2] = zeile;
										zugZielKoords2[3] = spalte;
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
			this.setHatGeschlagen(true);
			return alleSchlaege;
		}
		if (!alleZuege.isEmpty()) {
			this.setHatGeschlagen(false);
			return alleZuege;
		}
		return null;
	}

	// fragt für Spielfigur ab welche züge möglich sind

	private ArrayList<int[]> getSchlaege(int zeile, int spalte, Spielfigur figur) {
		ArrayList<int[]> schlaege = new ArrayList<int[]>();
		ArrayList<int[]> dameSchlaege = new ArrayList<int[]>();

		if (figur.getDame(figur)) {
			int tmpx1 = zeile;
			int tmpy1 = spalte;
			int tmpx2 = zeile;
			int tmpy2 = spalte;
			int tmpx3 = zeile;
			int tmpy3 = spalte;
			int tmpx4 = zeile;
			int tmpy4 = spalte;
			// ist schräger schritt nach OBEN RECHTS möglich?
			while (tmpx1 + 2 > 0 && tmpx1 + 2 <= (brett.getBrettGroesse() - 1) && (tmpy1 + 2) < brett.getBrettGroesse()) {
				Spielfeld zugFeld1 = brett.getBrettFeldIndex(tmpx1 + 1, (tmpy1 + 1));
				if (zugFeld1.getIstBelegt() == true) {
					Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

					// schaut ob Figur andere Farbe hat
					if (figurAufBelegtemFeld.getFarbe() == spieler.getFarbe()) {
						break;
					}
					if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
						// überprüft ob Feld hinter GegnerFigur frei ist
						Spielfeld zugFeld2 = brett.getBrettFeldIndex(tmpx1 + 2, (tmpy1 + 2));

						if (zugFeld2.getIstBelegt() == false) {
							int[] koordSchlagen = { (tmpx1 + 2), (tmpy1 + 2), 0, 0 };
							dameSchlaege.add(koordSchlagen);
							break;
						} else {
							break;
						}
					}
				}
				tmpy1++;
				tmpx1++;
			}

			// ist schräger schritt nach OBEN LINKS möglich?
			while (tmpx2 + 2 > 0 && tmpx2 + 2 <= (brett.getBrettGroesse() - 1) && tmpy2 - 2 >= 0) {
				Spielfeld zugFeld1 = brett.getBrettFeldIndex(tmpx2 + 1, (tmpy2 - 1));
				if (zugFeld1.getIstBelegt() == true) {
					Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

					// schaut ob Figur andere Farbe hat
					if (figurAufBelegtemFeld.getFarbe() == spieler.getFarbe()) {
						break;
					}
					if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
						// überprüft ob Feld hinter GegnerFigur frei ist
						Spielfeld zugFeld2 = brett.getBrettFeldIndex(tmpx2 + 2, (tmpy2 - 2));
						if (zugFeld2.getIstBelegt() == false) {
							int[] koordSchlagen = { (tmpx2 + 2), (tmpy2 - 2), 0, 0 };
							dameSchlaege.add(koordSchlagen);
							break;
						} else {
							break;
						}
					}
				}
				tmpy2--;
				tmpx2++;
			}
			// wäre ein schritt nach UNTEN noch im spielbrett?
			// ist schräger schritt nach UNTEN RECHTS möglich?
			while (tmpx3 - 2 > 0 && tmpx3 - 2 <= (brett.getBrettGroesse() - 1) && (tmpy3 + 2) < brett.getBrettGroesse()) {
				Spielfeld zugFeld1 = brett.getBrettFeldIndex(tmpx3 - 1, (tmpy3 + 1));
				if (zugFeld1.getIstBelegt() == true) {
					Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

					// schaut ob Figur andere Farbe hat
					if (figurAufBelegtemFeld.getFarbe() == spieler.getFarbe()) {
						break;
					}
					if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
						// überprüft ob Feld hinter GegnerFigur frei ist
						Spielfeld zugFeld2 = brett.getBrettFeldIndex(tmpx3 - 2, (tmpy3 + 2));
						if (zugFeld2.getIstBelegt() == false) {
							int[] koordSchlagen = { (tmpx3 - 2), (tmpy3 + 2), 0, 0 };
							dameSchlaege.add(koordSchlagen);
							break;
						} else {
							break;
						}
					}
				}
				tmpy3++;
				tmpx3--;
			}

			// ist schräger schritt nach UNTEN LINKS möglich?
			while (tmpx4 - 2 > 0 && tmpx4 - 2 <= (brett.getBrettGroesse() - 1) && tmpy4 - 2 >= 0) {
				Spielfeld zugFeld1 = brett.getBrettFeldIndex(tmpx4 - 1, (tmpy4 - 1));
				if (zugFeld1.getIstBelegt() == true) {
					Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

					// schaut ob Figur andere Farbe hat
					if (figurAufBelegtemFeld.getFarbe() == spieler.getFarbe()) {
						break;
					}
					if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
						// überprüft ob Feld hinter GegnerFigur frei ist
						Spielfeld zugFeld2 = brett.getBrettFeldIndex(tmpx4 - 2, (tmpy4 - 2));
						if (zugFeld2.getIstBelegt() == false) {
							int[] koordSchlagen = { (tmpx4 - 2), (tmpy4 - 2), 0, 0 };
							dameSchlaege.add(koordSchlagen);
							break;
						} else {
							break;
						}
					}
				}
				tmpy4--;
				tmpx4--;
			}
			return dameSchlaege;
		} else {

			// wäre ein schritt nach vorne noch im spielbrett?
			if (zeile + 2 >= 0 && zeile + 2 <= (brett.getBrettGroesse() - 1)) {
				// ist schräger schritt nach VORNE RECHTS möglich?
				if (spalte + 2 <= brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile + 1, (spalte + 1));
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile + 2, (spalte + 2));
							if (zugFeld2.getIstBelegt() == false) {
								int[] koordSchlagen = { (zeile + (2)), (spalte + 2), 0, 0 };
								schlaege.add(koordSchlagen);
							}
						}
					}
				}
				// ist schräger schritt nach VORNE LINKS möglich?
				if (spalte - 2 >= 0) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile + 1, (spalte - 1));
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile + 2, (spalte - 2));
							if (zugFeld2.getIstBelegt() == false) {
								int[] koordSchlagen = { (zeile + (2)), (spalte - 2), 0, 0 };
								schlaege.add(koordSchlagen);
							}
						}
					}
				}
			}
			// wäre ein schritt nach HINTEN noch im spielbrett?
			if (zeile - 2 >= 0 && zeile - 2 < (brett.getBrettGroesse() - 1)) {
				// ist schräger schritt nach HINTEN RECHTS möglich?
				if (spalte + 2 <= brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile - 1, (spalte + 1));
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile - 2, (spalte + 2));
							if (zugFeld2.getIstBelegt() == false) {
								int[] koordSchlagen = { (zeile - (2)), (spalte + 2), 0, 0 };
								schlaege.add(koordSchlagen);
							}
						}
					}
				}

				// ist schräger schritt nach HINTEN LINKS möglich?
				if (spalte - 2 >= 0) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile - 1, (spalte - 1));
					if (zugFeld1.getIstBelegt() == true) {
						Spielfigur figurAufBelegtemFeld = zugFeld1.getSpielfigur();

						// schaut ob Figur andere Farbe hat
						if (figurAufBelegtemFeld.getFarbe() != spieler.getFarbe()) {
							// überprüft ob Feld hinter GegnerFigur frei ist
							Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile - 2, (spalte - 2));
							if (zugFeld2.getIstBelegt() == false) {
								int[] koordSchlagen = { (zeile - (2)), (spalte - 2), 0, 0 };
								schlaege.add(koordSchlagen);
							}
						}
					}
				}
			}
			return schlaege;
		}
	}

	/**
	 * Findet für übergebene Figur die möglichen Züge
	 */
	private ArrayList<int[]> getZuege(int zeile, int spalte, Spielfigur figur) {
		ArrayList<int[]> zuge = new ArrayList<int[]>();
		ArrayList<int[]> dameZuge = new ArrayList<int[]>();

		if (figur.getDame(figur)) {
			// wäre ein schritt nach vorne noch im spielbrett?
			if (zeile + 1 >= 0 && zeile + 1 <= (brett.getBrettGroesse() - 1)) {
				// ist schräger schritt nach VORNE RECHTS möglich?
				if (spalte + 1 <= brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile + 1, (spalte + 1));
					if (zugFeld1.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile + (1)), (spalte + 1), 0, 0 };
						zuge.add(koordZiehen);
					}
				}

				// ist schräger schritt nach VORNE LINKS möglich?
				if (spalte - 1 >= 0) {
					Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile + 1, (spalte - 1));
					if (zugFeld2.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile + (1)), (spalte - 1), 0, 0 };
						dameZuge.add(koordZiehen);
					}
				}
			}
			// wäre ein schritt nach HINTEN noch im spielbrett?
			if (zeile - 1 >= 0 && zeile - 1 <= (brett.getBrettGroesse() - 1)) {
				// ist schräger schritt nach HINTEN RECHTS möglich?
				if (spalte + 1 <= brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld3 = brett.getBrettFeldIndex(zeile - 1, (spalte + 1));
					if (zugFeld3.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile - 1), (spalte + 1), 0, 0 };
						dameZuge.add(koordZiehen);
					}
				}
				// ist schräger schritt nach HINTEN LINKS möglich?
				if (spalte - 1 >= 0) {
					Spielfeld zugFeld4 = brett.getBrettFeldIndex(zeile - 1, (spalte - 1));
					if (zugFeld4.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile - 1), (spalte - 1), 0, 0 };
						dameZuge.add(koordZiehen);
					}
				}
			}
			return dameZuge;
		} else {
			// wäre ein schritt nach vorne noch im spielbrett?
			if (zeile + this.zugRichtung() >= 0 && zeile + this.zugRichtung() <= (brett.getBrettGroesse() - 1)) {
				// ist schräger schritt nach VORNE RECHTS möglich?
				if (spalte + 1 <= brett.getBrettGroesse() - 1) {
					Spielfeld zugFeld1 = brett.getBrettFeldIndex(zeile + this.zugRichtung(), (spalte + 1));
					if (zugFeld1.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile + (this.zugRichtung())), (spalte + 1), 0, 0 };
						zuge.add(koordZiehen);
					}
				}

				// ist schräger schritt nach VORNE LINKS möglich?
				if (spalte - 1 >= 0) {
					Spielfeld zugFeld2 = brett.getBrettFeldIndex(zeile + this.zugRichtung(), (spalte - 1));
					if (zugFeld2.getIstBelegt() == false) {
						int[] koordZiehen = { (zeile + (this.zugRichtung())), (spalte - 1), 0, 0 };
						zuge.add(koordZiehen);
					}
				}
			}
			return zuge;
		}
	}

	/**
	 * ermittelt weitere schläge für gezogene Figur
	 *
	 * @param a
	 * @param j
	 * @param figur
	 * @return
	 */
	public int[] getWeitereSchlaege(int zeile, int spalte, Spielfigur figur) {
		System.out.println("ich suche weitere schlaege");
		ArrayList<ArrayList<int[]>> alleWeiterenSchlaege = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> weitereSchlaege = this.getSchlaege(zeile, spalte, figur);
		int[] rueckgabeWeitere = { 0, 0, 0, 0 };

		if (!weitereSchlaege.isEmpty()) {
			for (int k = 0; k <= weitereSchlaege.size() - 1; k++) {
				int[] zugZielKoords1 = weitereSchlaege.get(k);
				if (zugZielKoords1 != null) {
					zugZielKoords1[2] = zeile;
					zugZielKoords1[3] = spalte;

				}
				alleWeiterenSchlaege.add(weitereSchlaege);
			}

			// ArrayList<ArrayList<int[]>> zug = this.getZug();

			int limit = alleWeiterenSchlaege.size();
			int i = this.zufall(limit);
			ArrayList<int[]> weitereZiele = alleWeiterenSchlaege.get(i);
			int limit2 = weitereZiele.size();
			int i2 = this.zufall(limit2);
			int zielKoords[] = weitereZiele.get(i2);

			// ------------------------------------------------------------------------
			rueckgabeWeitere[0] = (zielKoords[2]);
			rueckgabeWeitere[1] = (zielKoords[3]);
			rueckgabeWeitere[2] = (zielKoords[0]);
			rueckgabeWeitere[3] = (zielKoords[1]);
			// ------------------------------------------------------------------------
			System.out.println(((char) (zielKoords[3] + 97)) + "" + (zielKoords[2] + 1) + " nach " + ((char) (zielKoords[1] + 97)) + "" + (zielKoords[0] + 1));
			return rueckgabeWeitere;
		}
		return null;
	}

	/**
	 * Ermittelt eine zufällige Stelle der ArrayList
	 */
	public int zufall(int limit) {
		int zufallszug = new Random().nextInt(limit);
		return zufallszug;
	}

	/**
	 * Setzt ob geschlagen wurde
	 *
	 * @param b
	 */
	public void setHatGeschlagen(boolean b) {
		this.schlag = b;
	}

	/**
	 * Vermittelt ob geschlagen wurde
	 *
	 */

	public boolean hatGeschlagen() {
		return this.schlag;
	}

	@Override
	public String toString() {
		return "KI: " + spieler.getName() + " mit der Farbe: " + spieler.getFarbe();

	}
}
