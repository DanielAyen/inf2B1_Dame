/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class DameTest {

	public static void main(String[] args) {
		/*
		 * System.out.println(Spieler.getAnzahl()); try{ Spieler s1 = new
		 * Spieler("werwer", FarbEnum.SCHWARZ,false); Spieler s2 = new
		 * Spieler("derder", FarbEnum.WEIß,false);
		 * 
		 * 
		 * }catch(RuntimeException e){ System.out.println("Fehler in Eingabe"); }
		 * System.out.println(Spieler.getAnzahl()); Spielbrett b1=new
		 * Spielbrett(12);
		 * 
		 * b1.display();
		 */

		System.out.println("________________________________________________________________________");

		// iBediener spiel=new Spiel();
		// spiel.spielStarten();
		Spielbrett b1 = new Spielbrett(12);
		Spiel spiel = new Spiel();
		Spieler s1 = new Spieler("Test", FarbEnum.SCHWARZ, false);
		Spieler s2 = new Spieler("Test2", FarbEnum.WEIß, false);
		erstelleFiguren(s1, b1);
		erstelleFiguren(s2, b1);
		b1.display();
		System.out.println(s1.getFiguren(FarbEnum.SCHWARZ));
	}

	private static void erstelleFiguren(Spieler spieler, Spielbrett brett) {

		if (spieler.getFarbe() == FarbEnum.SCHWARZ) {
			for (int i = 0; i < brett.getBrettGroesse() / 2 - 1; i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false && brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.SCHWARZ, false));

						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);
					}
				}
			}
		} else {
			for (int i = brett.getBrettGroesse() - 1; i > brett.getBrettGroesse() / 2; i--) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {
					if (brett.getBrettFeldIndex(i, j).getIstBelegt() == false && brett.getBrettFeldIndex(i, j).getIstSchwarz() == true) {

						Spielfigur fig = (new Spielfigur(FarbEnum.WEIß, false));
						brett.getBrettFeldIndex(i, j).setSpielfigur(fig);

						spieler.addSpielfigur(fig);

					}
				}
			}
		}

	}

}
