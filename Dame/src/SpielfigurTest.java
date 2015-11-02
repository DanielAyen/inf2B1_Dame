import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Gruppe B1
 *
 */

public class SpielfigurTest {
	/**
	 * Stein Dame
	 */
	private static Spielbrett b1;
	private static Spielfigur s;
	private static Spielfigur d;
	

	/**
	 * Wird vor den Tests ausgeführt. Es werden ein Stein mit der Farbe Schwarz
	 * und eine Dame mit der Farbe Weiß erstellt. Beide erben von der Klasse
	 * Spielfigur.
	 */

	@BeforeClass
	public static void Methode() {
		b1 = new Spielbrett(12);
		s = new Spielfigur(FarbEnum.SCHWARZ, false);
		d = new Spielfigur(FarbEnum.WEIß, true);
		
	}

	/**
	 * Vergleicht die beiden Spielfiguren
	 */

	@Test
	public void vergleich() {
		assertNotSame(s, d);
	}

	/**
	 * Testet ob die Klasse von Stein existiert
	 */

	@Test
	public void nullTestStein() {
		assertNotNull(s.getClass());
	}

	/**
	 * Testet ob die Klasse von Dame existiert
	 */
	@Test
	public void nullTestDame() {
		assertNotNull(d.getClass());
	}

	/**
	 * Testet ob die Farbe des Steins der gewünschten Farbe entspricht
	 */

	@Test
	public void Farbtest() {
		assertTrue(s.getFarbe() == FarbEnum.SCHWARZ);
	}

}
