import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		case "neues Spiel erstellen":
			gui.spielAufbauen();

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

			gui.spielerErstellen();
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