import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Gruppe B1
 *
 */

public class SpielerTest {

	/**
	 * Spieler
	 */

	private static Spieler s1;
	private static Spieler s2;

	/**
	 * Wird vor den Tests ausgeführt. Es werden zwei Spieler erstellt, einer mit
	 * der Farbe Weiß und einer mit der Farbe Schwarz
	 */
	@BeforeClass
	public static void VorMethode() {
		s1 = new Spieler("Werner", FarbEnum.WEIß, false);
		s2 = new Spieler("Eckhart", FarbEnum.SCHWARZ, false);
	}

	/**
	 * Hier wird getestet, ob zwei Spieler identisch sind. Spieler dürfen den
	 * selben Namen haben aber nicht die selbe Farbe.
	 */
	@Test
	public void vergleichsTest() {
		assertNotSame(s1, s2);
	}

	/**
	 * Ueberprueft ob die Klasse Spieler existiert
	 */

	@Test
	public void spielerKlassenTest() {
		assertNotNull(s1.getClass());

	}

	/**
	 * Testet ob zwei spieler die selbe Farbe haben.
	 */

	@Test
	public void farbTest() {
		assertTrue(s1.getFarbe() != s2.getFarbe());
	}

	/**
	 * Testet ob die Farbe des Spielers noch verfuegbar ist
	 */

	@Test
	public void farbeVerfügbar() {
		assertTrue(s1.getWeiß() == true);

	}

	/**
	 * Testet ob es moeglich ist, einen dritten Spieler zu erstellen. Dies sollte
	 * nicht moeglich sein, da die Anzahl der Spieler im Spiel Dame auf Zwei
	 * begrenzt ist.
	 */

	@Test
	public void anzahltest() {
		Spieler s3 = new Spieler("Röhrich", FarbEnum.WEIß, false);
		assertTrue(Spieler.getAnzahl() <= 2);

	}

}
