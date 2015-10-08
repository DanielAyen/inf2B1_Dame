public class Spieler {

	private String name;
	private static final int maxSpieler = 2;
	private int anzSpieler = 0;
	private boolean weiß = false;
	private boolean schwarz = false;
	private boolean spielerGesetzt=false;

	public Spieler(String name, FarbEnum farbe) {
		if (anzSpieler < maxSpieler) {
			anzSpieler++;
			this.setName(name);
			this.setFarbe(farbe);
		} else
			System.out.println("Max Spieleranzahl erreicht");

	}

	public void setFarbe(FarbEnum farbe) {
		if ((farbe == FarbEnum.SCHWARZ && schwarz == false)&&(spielerGesetzt==true)) {
			schwarz = true;
			spielerGesetzt=false;
		} else {
			System.out.println("Farbe schon vergeben");
			anzSpieler--;
		}
		if ((farbe == FarbEnum.WEIß && weiß == false)&&(spielerGesetzt==true)) {
			weiß = true;
			spielerGesetzt=false;
		} else {
			System.out.println("Farbe schon vergeben");
			anzSpieler--;
		}
	}

	public void setName(String name) {
		if ((name == null) || (name.length() < 2)) {
			System.out.println("Falsch Eingabe");
			anzSpieler--;
		}
		this.name = name;
		spielerGesetzt=true;
	}

	public String getName() {
		return name;
	}

}
