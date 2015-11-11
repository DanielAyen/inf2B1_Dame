
public class SpielTestLaden {

	public static void main(String[] args) {
		 Spiel spiel = new Spiel();
		 spiel =  Spiel.ladenCSV("csv.csv");
		 spiel.spielStarten();
}
}
