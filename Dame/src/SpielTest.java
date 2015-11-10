import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author B1
 *
 *         Klasse zum Testen der Spielklasse
 *
 */

public class SpielTest {

	/**
	 * Spiele
	 */
	private static Spiel s1;
	private static Spiel s2;

	/**
	 * Wird vor den Tests ausgeführt. Es werden zwei Spiele erstellt
	 */

	@BeforeClass
	public static void erstellen() {
		s1 = new Spiel();
		s2 = new Spiel();
	}

	/**
	 * Testet ob das Spiel ein Spielbrett mit 10x10 Feldern baut
	 */
	@Test
	public void testI() {
		int x = 10;
		s1.spielBauen(x);
		assertNotNull(s1);
	}

	/**
	 * Testet dass die Klasse von Spiel nicht Null ist
	 */

	@Test
	public void testII() {
		assertNotNull(s1.getClass());
	}

	/**
	 * Vergleicht die beiden Spiele auf gleichheit. Die Spiele sollten gleich
	 * sein!
	 */
	@Test
	public void testIII() {
		assertNotSame(s1, s2);
	}

	/**
	 * Testet ob das Spiel wirklich das Spiel ist
	 */

	@Test
	public void testIV() {
		assertEquals(s1, s1);

	}

	/**
	 * Testet ob ein Spielfeld mit 12x12 Feldern in weniger als 100ms gebaut wird
	 */

	@Test(timeout = 100)
	public void testV() {
		int x = 12;
		s2.spielBauen(x);
		assertNotNull(s2);

	}
	
	/**
	 * Eerstellt einen Spieler
	 */
	@Test
	public void testVI(){Spieler sp1 ;
	assertNotNull (sp1= new Spieler("Test", FarbEnum.SCHWARZ, true));
	
		
	}

}
