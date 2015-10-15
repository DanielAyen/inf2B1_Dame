import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SpielerTest {

	private Spieler s1;
	private Spieler s2;

	@Before
	public void VorMethode() {
		s1 = new Spieler("Werner", FarbEnum.WEIß);
		s2 = new Spieler("Eckhart", FarbEnum.SCHWARZ);
	}

	@Test
	// Vergleicht ob zwei Spieler Objekte gleich sind
	public void vergleichsTest() {
		assertNotSame(s1, s2);
	}

	@Test
	// Überprüft ob die Spielerklasse existiert
	public void spielerKlassenTest() {
		assertNotNull(s1.getClass());

	}

	@Test
	// Überprüft ob zwei spieler die selbe Farbe haben
	public void farbTest() {
		assertTrue(s1.getFarbe() != s2.getFarbe()); // zeigt momentan noch failure an
													// 
	}

	@Test
	// Testet ob die Farbe des Spielers noch verfügbar ist.
	public void farbeVerfügbar() {
		assertTrue(s1.getWeiß() == true);

	}

	@Test
	public void anzahltest() {
		Spieler s3 = new Spieler("Röhrich", FarbEnum.WEIß);
		assertTrue(Spieler.getAnzahl() <= 2);

	}

}
