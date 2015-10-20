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
		//System.out.println(b1.getBrett('l',0));
		Spieler s1=new Spieler("Werner",FarbEnum.SCHWARZ);
		System.out.println(s1.getFarbe());
		//System.out.println(b1.getEinzelFeld('A',0));
	//b1.display();
		System.out.println(b1.getBrettGroesse());
		Spielfigur ss1=new Spielfigur(FarbEnum.WEIß, 5,5,b1,false);
	
	
		/*
		b1.display();
		System.out.println(b1.getEinzelFeld(4, 0));
		*/
	}

}
