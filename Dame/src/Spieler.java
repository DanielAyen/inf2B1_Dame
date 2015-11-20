import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Baris, Daniel, Simon,Hannes
 *
 */
public class Spieler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1632008787864445195L;
	/**
	 * 
	 * @param name
	 *          Name des Spielers
	 * @param maxSpieler
	 *          Maximale Anzahl an Spielern
	 * @param anzSpieler
	 *          Derzeitige Anzahl an Spielern
	 * @param weiß
	 *          gibt an ob die Farbe weiß bereits vergeben ist (False==nicht
	 *          vergeben)
	 * @param schwarzvergeben
	 *          gibt an ob die Farbe schwarz bereits vergeben ist (False==nicht
	 *          vergeben)
	 * 
	 * @param farbe
	 *          Die Farbe aus dem FarbEnum
	 * 
	 * @param figuren
	 *          eine ArrayList für die Spielfiguren eines Spielers.
	 *
	 */
	private ArrayList<Spielfigur> figuren = new ArrayList<Spielfigur>();
	private String name;
	private static final int maxSpieler = 2;
	private static int anzSpieler = 0;
	private static boolean weißvergeben = false;
	private static boolean schwarzvergeben = false;
	private FarbEnum farbe;
	private boolean istAmZug = false;
	private boolean istKi = false;

	/**
	 * erstellen der Spieler muss in der Spielklasse erfolgen
	 * 
	 * Konstruktor für einen Spieler
	 * 
	 * @param name
	 *          Spielername
	 * @param farbe
	 *          Spielerfarbe aus dem FarbEnum
	 * @param istKi
	 *          ob der zu erstellende Spieler eine Ki sein soll oder nicht
	 */
	public Spieler(String name, FarbEnum farbe, boolean istKi) {
		if (anzSpieler < maxSpieler && (spielerPrüfen(name, farbe)) == 0) {
			anzSpieler++;
		}
		if (spielerPrüfen(name, farbe) == 1) {
			throw new RuntimeException("Du musst einen Namen übergeben");
		}
		if (spielerPrüfen(name, farbe) == 2) {
			throw new RuntimeException("Name zu kurz!");
		}
		if (spielerPrüfen(name, farbe) == 3) {
			throw new RuntimeException("Farbe schon vergeben!");
		}
		if (istKi == true) {
			this.istKi = true;
			this.setName(name);
		} else {
			this.setName(name);
		}
		if (farbe == FarbEnum.SCHWARZ) {
			this.setFarbeSchwarz(farbe);
		} else if (farbe == FarbEnum.WEIß) {
			this.setFarbeWeiß(farbe);

		} else if (anzSpieler >= maxSpieler) {
			throw new RuntimeException("Max Spieleranzahl erreicht");
		}
	}

	/**
	 * Überprüft den Spielernamen auf Gültigkeit, die Spielerfarbe wird auf
	 * Verfügbarkeit geprüft
	 *
	 * @param name
	 *          Spielername
	 * @param farbe
	 *          Spielerfarbe aus dem FarbEnum
	 * @return gibt einen boolean Wert zurueck ob das erstellen erfolgreich war
	 *         oder nicht
	 */
	public int spielerPrüfen(String name, FarbEnum farbe) {

		if (name == null) {
			System.out.println("Du musst einen Namen übergeben");
			return 1;
		} else {
			if (name.length() < 2) {
				System.out.println("Name zu kurz!");
				return 2;
			} else if ((farbe == FarbEnum.SCHWARZ && schwarzvergeben == false)) {
				return 0;
			} else {
				if ((farbe == FarbEnum.WEIß && weißvergeben == false)) {
					return 0;
				} else {
					System.out.println("Farbe schon vergeben!\n");
					return 3;
				}
			}
		}
	}

	/**
	 * Setter für die Farbe Schwarz
	 * 
	 * @param farbe
	 *          Spielerfarbe
	 */
	private void setFarbeSchwarz(FarbEnum farbe) {
		schwarzvergeben = true;
		this.farbe = farbe;
	}

	/**
	 * Setter für die Farbe Weiß
	 * 
	 * @param farbe
	 *          Spielerfarbe
	 */
	private void setFarbeWeiß(FarbEnum farbe) {
		weißvergeben = true;
		this.farbe = farbe;
	}

	/**
	 * Setter für den Spielernamen
	 * 
	 * @param name
	 *          Spielername
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter für den Spielernamen
	 * 
	 * @return name Gibt Spielername zurück
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return farbe Gibt Spielerfarbe zurück
	 */
	public FarbEnum getFarbe() {
		return this.farbe;
	}

	/**
	 * Gibt die derzeitige Anzahl der Spieler zurück
	 * 
	 * @return anzSpieler Gibt die Aktuelle Spieleranzahl zurück
	 */
	public static int getAnzahl() {
		return anzSpieler;
	}

	/**
	 * Gibt zurueck ob die Farbe weiss verfuegbar ist
	 * 
	 * 
	 * @return weiß gibt einen bool Wert zürck über die Verfügbarkeit der Farbe
	 */
	public boolean getWeiß() {
		System.out.print("Die Farbe Weiß ist schon vergeben: ");
		return weißvergeben;
	}

	/**
	 * Gibt zurueck ob die Farbe schwarz verfuegbar ist
	 * 
	 * @return schwarz gibt einen bool Wert zürck über die Verfügbarkeit der Farbe
	 */
	public boolean getSchwarz() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return schwarzvergeben;
	}

	public void spielerLoeschen() {
		this.farbe = null;
		this.schwarzvergeben = false;
		this.weißvergeben = false;
		this.setIstAmZug(false);
		this.setName(null);
		figuren.clear();
		this.istKi = false;
		this.anzSpieler = 0;
	}

	/**
	 * 
	 * @param farbe
	 *          die Farbe der gewaehlten figuren
	 * @return gibt alle figuren zurueck
	 */
	public String getFiguren(FarbEnum farbe) {
		String s = "";
		for (int i = 0; i < this.figuren.size(); i++) {
			s += "" + figuren.get(i);// pos der fig und name der fig bekommen dazu den
																// dame
			// status alles in S schreiben

		}
		return s;

	}

	public ArrayList<Spielfigur> getAlleFiguren() {

		return this.figuren;
	}

	/**
	 * Methode um Spielfigur Objekte in das figurenArray hinzuzufügen
	 * 
	 * @param s
	 */
	public void addSpielfigur(Spielfigur s) {
		figuren.add(s);
	}

	public boolean getIsIstAmZug() {
		return istAmZug;
	}

	public void setIstAmZug(boolean istAmZug) {
		this.istAmZug = istAmZug;
	}

	@Override
	public String toString() {
		return "Spieler: " + this.getName() + " mit der Farbe: " + this.getFarbe();

	}

	public boolean getIstKi() {
		return istKi;

	}

}
// //
//
// TODO erstelleFiguren(farbe)
// erstelleKi(name,farbe)
//
//
//
//
// //
