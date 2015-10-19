/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class DameTest {

	public static void main(String[] args) {
		/*
		System.out.println(Spieler.getAnzahl());
		try{
		Spieler s1 = new Spieler("H", FarbEnum.SCHWARZ);
		Spieler s2 = new Spieler("B", FarbEnum.WEIß);
		Spieler s3 = new Spieler("Bz", FarbEnum.WEIß);
		System.out.println(s1.getName());
		System.out.println(s1.getFarbe());
		}catch(RuntimeException e){
			System.out.println("Fehler in Eingabe");
		}
		//System.out.println(s1.getSchwarz());
		//System.out.println(s1.getWeiß());
		System.out.println(Spieler.getAnzahl());
		

		//System.out.println(s2.getName());
		//System.out.println(s2.getFarbe());
*/
		Spielbrett b1=new Spielbrett(8);
		System.out.println(b1.getBrett(0,0).getPosX());
		System.out.println(b1.getEinzelFeld(0,0));
	b1.display();
	
	
		/*
		b1.display();
		System.out.println(b1.getEinzelFeld(4, 0));
		*/
	}

}
