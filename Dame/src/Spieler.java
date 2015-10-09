public class Spieler {

	private String name;
	private static final int maxSpieler = 2;
	private static int anzSpieler = 0;
	private static boolean weiß = false;
	private static boolean schwarz = false;
	private FarbEnum farbe;

	public Spieler(String name, FarbEnum farbe) {
		if (anzSpieler < maxSpieler) {
			anzSpieler++;
			spielerPrüfen(name, farbe);

		} else
			System.out.println("Max Spieleranzahl erreicht");
	}

	public void spielerPrüfen(String name, FarbEnum farbe) {

		if (name == null) {
			anzSpieler--;
			System.out.println("Du musst einen Namen übergeben");
		} else {

			if ((farbe == FarbEnum.SCHWARZ && schwarz == false) && (name.length() >= 2)) {
				this.setName(name);
				this.setFarbeSchwarz(farbe);

			} else {
				if ((farbe == FarbEnum.WEIß && weiß == false) && (name.length() >= 2)) {
					this.setFarbeWeiß(farbe);
					this.setName(name);
				} else {
					System.out.println("Farbe schon vergeben oder Name ungültig");
					anzSpieler--;
				}
			}

		}
	}

	public void setFarbeSchwarz(FarbEnum farbe) {
		schwarz = true;
		this.farbe = farbe;
	}

	public void setFarbeWeiß(FarbEnum farbe) {
		{

			weiß = true;
			this.farbe = farbe;
		}
	}

	public void setName(String name) {

		this.name = name;

	}

	public String getName() {
		return name;
	}

	public FarbEnum getFarbe() {
		return this.farbe;
	}

	public static int getAnzahl() {
		return anzSpieler;
	}

	public boolean getWeiß() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return weiß;
	}

	public boolean getSchwarz() {
		System.out.print("Die Farbe Schwarz ist schon vergeben: ");
		return schwarz;
	}

}
