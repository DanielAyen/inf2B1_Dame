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
	 */
	private ArrayList<Spielfigur> figuren = new ArrayList<Spielfigur>();
	private String name;
	private static final int maxSpieler = 2;
	private static int anzSpieler = 0;
	private static boolean weiß = false;
	private static boolean schwarz = false;
	private FarbEnum farbe;
	private Stein stein;

	/**
	 * Konstruktor für einen Spieler
	 * 
	 * @param name
	 *          Spielername
	 * @param farbe
	 *          Spielerfarbe aus dem FarbEnum
	 */
	public Spieler(String name, FarbEnum farbe) {
		if (anzSpieler < maxSpieler) {
			anzSpieler++;
			spielerPrüfen(name, farbe);

		} else
			System.out.println("Max Spieleranzahl erreicht");
	}

	/**
	 * Überprüft den Spielernamen auf Gültigkeit, die Spielerfarbe wird auf
	 * Verfügbarkeit geprüft
	 *
	 * @param name
	 *          Spielername
	 * @param farbe
	 *          Spielerfarbe aus dem FarbEnum
	 */
	public void spielerPrüfen(String name, FarbEnum farbe) {

		if (name == null) {
			anzSpieler--;
			System.out.println("Du musst einen Namen übergeben");
		} else {

			if ((farbe == FarbEnum.SCHWARZ && schwarz == false) && (name.length() >= 2)) {
				this.setName(name);
				this.setFarbeSchwarz(farbe);
				stein.Stein(12,farbe);
			} else {
				if ((farbe == FarbEnum.WEIß && weiß == false) && (name.length() >= 2)) {
					this.setFarbeWeiß(farbe);
					this.setName(name);
					stein.
				} else {
					System.out.println("Farbe schon vergeben oder Name ungültig");
					anzSpieler--;
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
	public void setFarbeSchwarz(FarbEnum farbe) {
		schwarz = true;
		this.farbe = farbe;
	}

	/**
	 * Setter für die Farbe Weiß
	 * 
	 * @param farbe
	 *          Spielerfarbe
	 */
	public void setFarbeWeiß(FarbEnum farbe) {

		weiß = true;
		this.farbe = farbe;

	}

	/**
	 * Setter für den Spielernamen
	 * 
	 * @param name
	 *          Spielername
	 */
	public void setName(String name) {

		this.name = name;

	}

	/**
	 * 
	 * @return name Gibt Spielername zurück
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return farbe Gibt Spielerfarbe zurück
	 */
	public FarbEnum getFarbe() {
		return this.farbe;
	}

	/**
	 * 
	 * @return anzSpieler Gibt die Aktuelle Spieleranzahl zurück
	 */
	public static int getAnzahl() {
		return anzSpieler;
	}

	/**
	 * 
	 * 
	 * @return weiß gibt einen bool Wert zürck über die Verfügbarkeit der Farbe
	 */
	public boolean getWeiß() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return weiß;
	}

	/**
	 * 
	 * 
	 * @return schwarz gibt einen bool Wert zürck über die Verfügbarkeit der Farbe
	 */
	public boolean getSchwarz() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return schwarz;
	}

}
// Boolean einbauen damit man Spielerprüfen nicht bescheissen kann...oder einfach private??!?!?!(SETTER)