/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class DameTest {

	public static void main(String[] args) {
		System.out.println(Spieler.getAnzahl());
		Spieler s1=new Spieler("Hui",FarbEnum.SCHWARZ);
		Spieler s2=new Spieler("Bui",FarbEnum.WEIß);
		System.out.println(s1.getSchwarz());
		System.out.println(s1.getWeiß());
		System.out.println("Test");
		

	System.out.println("MergeTest");

		System.out.println("Test...");


		
	
	
		
		
		
		System.out.println(Spieler.getAnzahl());
		System.out.println(s1.getName());
		System.out.println(s1.getFarbe());
	
		System.out.println(s2.getName());
		System.out.println(s2.getFarbe());
		
		
		
	}

}
