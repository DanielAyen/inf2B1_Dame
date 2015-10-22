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
	private boolean istKi=false;

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

		if (!istKi){
			
		
		if (anzSpieler < maxSpieler && (spielerPrüfen(name, farbe))) {
			anzSpieler++;
		} else if (anzSpieler >= maxSpieler) {
			System.out.println("Max Spieleranzahl erreicht");
		}
		if(istKi==true){
			erstelleKi(this.name,this.farbe);
		}
		}
		else{
			
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
			} else if ((farbe == FarbEnum.SCHWARZ && schwarzvergeben == false)) {
				this.setName(name);
				this.setFarbeSchwarz(farbe);
				erstelleFiguren(farbe);
				return true;
			} else {
				if ((farbe == FarbEnum.WEIß && weißvergeben == false)) {
					this.setFarbeWeiß(farbe);
					this.setName(name);
					erstelleFiguren(farbe);
					return true;
				} else {
					System.out.println("Farbe schon vergeben!\n");
					throw new RuntimeException("Error");
				}
			}
		}
	}

	/**
	 * Ruft den figuren konstruktor auf
	 * 
	 * @param farbeFiguren
	 */
	private void erstelleFiguren(FarbEnum farbeFiguren) {
		
		// bekommt die farbe gibt das an den figuren konst und erstellt dann alle
		// figuren dieser farbe

	}

	/**
	 * 
	 * Ki erstell Methode gibt alles an die Ki klasse weiter
	 * 
	 * @param nameKi
	 * @param farbeKi
	 */
	private void erstelleKi(String nameKi, FarbEnum farbeKi) {
		KI_Dame k1 = new KI_Dame(nameKi, farbeKi);
		// Sollte alles an KI gegeben werden
		// this.setFarbeWeiß(farbeKi);
		// this.setName(nameKi);

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

	/**
	 * 
	 * @param farbe
	 *          die Farbe der gewaehlten figuren
	 * @return gibt alle figuren zurueck
	 */
	public String getFiguren(FarbEnum farbe) {
		String s = "";
		for (int i = 1; i < this.figuren.size(); i++) {
			figuren.get(i);// pos der fig und name der fig bekommen dazu den dame
											// status alles in S schreiben

		}
		return s;

	}
	/**
	 * Methode um Spielfigur Objekte in das figurenArray hinzuzufügen
	 * 
	 * @param s
	 */
public void addSpielfigur(Spielfigur s){
	figuren.add(s);
}
	
	@Override
	public String toString() {
		return "Spieler: " + this.getName() + " mit der Farbe: " + this.getFarbe();

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
