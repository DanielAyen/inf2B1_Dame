
public class SpielTestLaden {

	public static void main(String[] args) {
		 Spiel spiel = new Spiel();
		 spiel =  Spiel.ladenSerialisiert("NewGame.ser");
		 spiel.spielStarten();
}
}
