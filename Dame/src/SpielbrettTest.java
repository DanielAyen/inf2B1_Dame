import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class SpielbrettTest {

	private static Spielbrett sb;
	private static Spielbrett sb2; 
	
	@BeforeClass
	public static void Methode(){
		sb = new Spielbrett(12); 
	}
	
	
	@Test
	public void NullTest() {
		assertNotNull(sb);
	}
	
	@Test 
	public void FeldNichtNull(){
		assertNotNull(sb.getEinzelFeld(0, 0));
		assertNotNull(sb.getEinzelFeld(0, 11));
		assertNotNull(sb.getEinzelFeld(11, 0));
		assertNotNull(sb.getEinzelFeld(11, 11));
		
		
	}
	
	@Test
	public void Test(){
		assertNotSame(sb.getEinzelFeld(3, 9), sb.getEinzelFeld(6, 6));
	}
	
	@Test // Sollte nicht machbar sein
	public void BrettAnzahl(){
		sb2 = new Spielbrett(8);
	}
	
	@Test
	public void  FeldNull(){
		 assertNull(sb.getEinzelFeld(15, 13));
	 }

}
