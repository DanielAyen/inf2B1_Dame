import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Gruppe B1
 *
 */

public class SpielbrettTest {

	/**
	 * Spielbretter.
	 */

	private static Spielbrett sb;
	private static Spielbrett sb2;

	/**
	 * Wird vor den Tests ausgeführt. Spielbrett sb wird mit einer größe von 12x12
	 * erzeugt.
	 */

	@BeforeClass
	public static void Methode() {
		sb = new Spielbrett(12);
	}

	/**
	 * Test ob das Spielbrett sb Null ist.
	 */
	@Test
	public void NullTest() {
		assertNotNull(sb);
	}

	/**
	 * Hier wird Ueberprueft ob, die Ecken des Spielfeldes Null sind
	 */

	@Test
	public void FeldNichtNull() {
		assertNotNull(sb.getBrett('A', 0));
		assertNotNull(sb.getBrett('A', 11));
		assertNotNull(sb.getBrett('L', 0));
		assertNotNull(sb.getBrett('L', 11));

	}

	/**
	 * Ueberpruefung ob zwei Spielfelder auf dem Spielbrett identisch sind
	 */

	@Test
	public void Test() {
		assertNotSame(sb.getBrett('D', 9), sb.getBrett('G', 6));
	}

	/**
	 * Test ob man ein zweites Spielbrett erschaffen kann, dies sollte nicht
	 * machbar sein, da die maximale Anzahl an Spielbrettern Eins entspricht!
	 */
	@Test
	public void BrettAnzahl() {
		sb2 = new Spielbrett(8);
	}

}
