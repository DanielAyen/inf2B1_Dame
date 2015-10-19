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
		assertNotNull(sb.getBrett('A', 0));
		assertNotNull(sb.getBrett('A', 11));
		assertNotNull(sb.getBrett('L', 0));
		assertNotNull(sb.getBrett('L', 11));
		
		
	}
	
	@Test
	public void Test(){
		assertNotSame(sb.getBrett('D', 9), sb.getBrett('G', 6));
	}
	
	@Test // Sollte nicht machbar sein
	public void BrettAnzahl(){
		sb2 = new Spielbrett(8);
	}
	
	@Test
	public void  FeldNull(){
		 assertNull(sb.getBrett('P', 13));
	 }

}
