import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class SpielfeldTest {


		private static Spielfeld sf;
		private static Spielbrett sb;
		
		@BeforeClass
		public static void Methode(){
			
			sb = new Spielbrett(12);
			sf = new Spielfeld(sb, true, 0, 9);
		}
		@Test 
		public void Klassentest(){
			assertNotNull(sf.getClass());
		}
		
		
		@Test
		public void posXnichtNulltest() {
		assertNotNull(sf.getPosX());
		}
		
		@Test
		public void posXtestkorrekt(){
			assertTrue(sf.getPosX() == 0);
		}
		
		@Test
		public void posXtestfalsch(){
			assertFalse(sf.getPosX() == 6);
		}
		
		
		@Test
		public void posYnichtNulltest() {
		assertNotNull(sf.getPosY());
		}
		
		@Test
		public void posYtestkorrekt(){
			assertTrue(sf.getPosY() == 9);
		}
		
		@Test
		public void posYtestfalsch(){
			assertFalse(sf.getPosY() == 14);
		}
		
		@Test
		public void feldbelegttest(){
			sf.setIstBelegt(true);
			assertTrue(sf.getIstBelegt());
		}
		
		@Test
		public void feldfreitest(){
			sf.setIstBelegt(false);
			assertFalse(sf.getIstBelegt());
		}


}
