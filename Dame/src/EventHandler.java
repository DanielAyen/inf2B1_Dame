import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class EventHandler implements ActionListener {
	private GUI gui;// kenntnisbeziehung herstellen!!!
	private Spiel spiel;
	private int i = 1;

	/**
	 * 
	 * @param gui
	 */
	public EventHandler(GUI gui) {
		this.setGUI(gui);
	}

	/**
	 * 
	 * @param gui
	 */
	private void setGUI(GUI gui) {
		this.gui = gui;

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// try {
		switch (ae.getActionCommand()) {

		case "Ziehen":
			String eingabe = gui.getBefehlFeld().getText();

			// mit a-z xx
			// if ((Pattern.matches("[A-Za-z]\\d[-][A-Za-z]\\d", eingabe)) ||
			// (Pattern.matches("[A-Za-z]\\d\\d[-][A-Za-z]\\d\\d", eingabe)) ||
			// (Pattern.matches("[A-Za-z]\\d\\[-][A-Za-z]\\d", eingabe)) ||
			// (Pattern.matches("[A-Za-z]\\d[-][A-Za-z]\\d\\d", eingabe))) {
			// gui.log("jup");
			// }

			// nur a-l xx
			// if ((Pattern.matches("[a-l A-L]\\d[-][a-l A-L]\\d", eingabe)) ||
			// (Pattern.matches("[a-l A-L]\\d\\d[-][a-l A-L]\\d\\d", eingabe)) ||
			// (Pattern.matches("[a-l A-L]\\d\\[-][a-l A-L]\\d", eingabe)) ||
			// (Pattern.matches("[a-l A-L]\\d[-][a-l A-L]\\d\\d", eingabe))) {
			// gui.log("jup");
			// }
			
			// a=buchstabe X=zahl X=1-9 XX=1 0-2
			//aX-aX aXX-aXX aX-aXX aXX-aX
			if ((Pattern.matches("[a-l A-L][1-9][-][a-l A-L][1-9]", eingabe)) || (Pattern.matches("[a-l A-L][1][0-2][-][a-l A-L][1][0-2]", eingabe)) || (Pattern.matches("[a-l A-L][1][0-2][-][a-l A-L][1-9]", eingabe)) || (Pattern.matches("[a-l A-L][1-9][-][a-l A-L][1][0-2]", eingabe))) {
				gui.log("jup");
			} else {
				gui.log("nop");
			}

			break;

		case "neues Spiel erstellen":
			// gui.spielAufbauen();
			// anstatt größe wählbar zu machen erstmal nur 12x12
			gui.aufbauen(12);
			gui.spielerErstellen();
			break;

		case "Weiter":

			if (gui.getAcht().isSelected() == true) {
				gui.aufbauen(8);
				gui.log("Gewählte Größe: 8");
			} else if (gui.getZehn().isSelected() == true) {
				gui.aufbauen(10);
				gui.log("Gewählte Größe: 10");
			} else if (gui.getZwölf().isSelected() == true) {
				gui.aufbauen(12);
				gui.log("Gewählte Größe: 12");
			}

			gui.getBrettFrame().dispose();
			break;

		case "OK":
			String name = null;
			FarbEnum farbe = null;
			boolean istKi = false;
			name = gui.getNameFeld().getText();
			if (name.length() < 2) {
				gui.log("Der Spielername ist zu kurz!");
			} else {
				// Radio Button Abfrage Farbe
				if (gui.getSchwarz().isSelected() == true || gui.getWeiß().isSelected() == true) {

					if (gui.getSchwarz().isSelected() == true) {
						farbe = FarbEnum.SCHWARZ;

					}
					if (gui.getWeiß().isSelected() == true) {
						farbe = FarbEnum.WEIß;
					}

					// Radio Button Abfrage Art
					if (gui.getMensch().isSelected() == true) {
						istKi = false;
					}
					if (gui.getKi().isSelected() == true) {
						istKi = true;
					}
					gui.getSpielerFrame().dispose();
					// i=1 und i++ da 2 spieler erlaubt
					// sperren der bereits vergebenen farbe
					if (i != 0) {
						gui.spielerErstellen();
						i--;
						if (farbe == FarbEnum.SCHWARZ) {
							gui.getSchwarz().setEnabled(false);

						}
						if (farbe == FarbEnum.WEIß) {
							gui.getWeiß().setEnabled(false);

						}

					}

					gui.spielerWeitergeben(name, farbe, istKi);
					if (istKi == false) {
						gui.log("Ein Spieler mit dem Namen " + name + " und der Farbe " + farbe + " wurde erstellt.");
					} else {
						gui.log("Eine KI mit dem Namen " + name + " und der Farbe " + farbe + " wurde erstellt.");
					}

				} else {
					gui.log("Bitte Farbe Wählen");
				}
			}

			break;

		default:
			gui.log("Diese Funktion ist zu diesem Zeitpunkt nicht verfügbar.");
			break;
		}
		// } catch (Exception e) {
		// gui.log(e.getMessage());
		//
		// }

	}
}