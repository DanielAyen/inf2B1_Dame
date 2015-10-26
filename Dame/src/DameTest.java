/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class DameTest implements iBediener {

	public static void main(String[] args) {
	/*	
		System.out.println(Spieler.getAnzahl());
		try{
		Spieler s1 = new Spieler("werwer", FarbEnum.SCHWARZ,false);
		Spieler s2 = new Spieler("derder", FarbEnum.WEIß,false);
	
		
		}catch(RuntimeException e){
			System.out.println("Fehler in Eingabe");
		}
		System.out.println(Spieler.getAnzahl());
	Spielbrett b1=new Spielbrett(12);
	
b1.display();

*/

		System.out.println("________________________________________________________________________");
		
		Spiel spiel=new Spiel();
		spiel.spielStarten();
		
	}

	@Override
	public void spielStarten() {
		// TODO Auto-generated method stub
		
	}

}
