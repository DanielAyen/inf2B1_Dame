import java.util.ArrayList;

/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spieler {
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
	 * @param schwarz
	 *          gibt an ob die Farbe schwarz bereits vergeben ist (False==nicht
	 *          vergeben)
	 * 
	 * @param farbe
	 *          Die Farbe aus dem FarbEnum
	 * 
	 * @param figuren
	 *          eine ArrayList für die Spielfiguren eines Spielers.// z.Z.
	 *          unbenutzt
	 * 
	 */
	private ArrayList<Spielfigur> figuren = new ArrayList<Spielfigur>();
	private String name;
	private static final int maxSpieler = 2;
	private static int anzSpieler = 0;
	private static boolean weiß = false;
	private static boolean schwarz = false;
	private FarbEnum farbe;

	/**
	 * erstellen der Spieler muss in der Spielklasse erfolgen
	 * 
	 * Konstruktor für einen Spieler
	 * 
	 * @param name
	 *          Spielername
	 * @param farbe
	 *          Spielerfarbe aus dem FarbEnum
	 */
	public Spieler(String name, FarbEnum farbe) {

		if (anzSpieler < maxSpieler && (spielerPrüfen(name, farbe))) {
			anzSpieler++;

		} else if (anzSpieler >= maxSpieler) {
			System.out.println("Max Spieleranzahl erreicht");

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
	public boolean spielerPrüfen(String name, FarbEnum farbe) {

		if (name == null) {

			System.out.println("Du musst einen Namen übergeben");
			throw new RuntimeException("Error");

		} else {
			if (name.length() < 2) {
				System.out.println("Name zu kurz!");
				throw new RuntimeException("Error");
			} else if ((farbe == FarbEnum.SCHWARZ && schwarz == false)) {
				this.setName(name);
				this.setFarbeSchwarz(farbe);
				// spielfigur.Stein(12,farbe);
				return true;
			} else {
				if ((farbe == FarbEnum.WEIß && weiß == false)) {
					this.setFarbeWeiß(farbe);
					this.setName(name);
					return true;
				} else {
					System.out.println("Farbe schon vergeben!");
					throw new RuntimeException("Error");
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
		schwarz = true;
		this.farbe = farbe;
	}

	/**
	 * Setter für die Farbe Weiß
	 * 
	 * @param farbe
	 *          Spielerfarbe
	 */
	private void setFarbeWeiß(FarbEnum farbe) {

		weiß = true;
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
		return weiß;
	}

	/**
	 * Gibt zurueck ob die Farbe schwarz verfuegbar ist
	 * 
	 * @return schwarz gibt einen bool Wert zürck über die Verfügbarkeit der Farbe
	 */
	public boolean getSchwarz() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return schwarz;
	}

}
// Boolean einbauen damit man Spielerprüfen nicht bescheissen kann...oder
// einfach private??!?!?!(SETTER)