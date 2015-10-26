import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Gruppe B1
 *
 */

public class SpielfeldTest {

	/**
	 * Spielfeld Spielbrett
	 */

	private static Spielfeld sf;
	private static Spielbrett sb;

	/**
	 * Wird vor den Tests ausgeführt. Es wird ein Spielbrett erstellt, dass aus
	 * 12x12 Feldern besteht
	 */

	@BeforeClass
	public static void Methode() {

		sb = new Spielbrett(12);
		sf = new Spielfeld(sb, true, 0, 9);
	}

	/**
	 * Test ob die Klasse Spielfeld exisitert
	 */
	@Test
	public void Klassentest() {
		assertNotNull(sf.getClass());
	}

	/**
	 * Test zur Ueberprufeung ob das Spielfeld an der Position X nicht Null ist
	 */

	@Test
	public void posXnichtNulltest() {
		assertNotNull(sb.getBrettFeldSchachnotation('A', 1) != null);
	}

	/**
	 * Vergleicht zwei Felder ob sie nicht identisch sind
	 */

	@Test
	public void vergleichsTest() {
		assertNotSame(sb.getBrettFeldSchachnotation('A', 1), sb.getBrettFeldSchachnotation('A', 11));
	}

	/**
	 * Vergleicht zwei Felder ob sie nicht identisch sind
	 */

	@Test
	public void nochEinVergleich() {
		assertSame(sb.getBrettFeldSchachnotation('L', 10), sb.getBrettFeldSchachnotation('L', 10));
	}

	/**
	 * Test ob es moeglich ist, ein Feld zu belegen
	 */
	@Test
	public void feldbelegttest() {
		sf.setIstBelegt(true);
		assertTrue(sf.getIstBelegt());
	}

	/**
	 * Test ob es moeglich ist, ein Feld wieder frei zu geben.
	 */
	@Test
	public void feldfreitest() {
		sf.setIstBelegt(false);
		assertFalse(sf.getIstBelegt());
	}

}
