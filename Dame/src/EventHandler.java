import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class EventHandler implements ActionListener {
	private GUI gui;// kenntnisbeziehung herstellen!!!
	private int i = 1;
	private int anzahlSpiele = 0;
	private int klickcnt = 0;
	private String startp;

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

	private boolean prüfen(int zeile, int spalte) {

		if (!gui.getBrett().getBrettFeldIndex(zeile, spalte).getIstSchwarz()) {
			// gui.log("Nur schwarze Felder möglich.");

			klickcnt = 0;
			gui.log("Nur schwarze Felder möglich.\nNeue Startposition wählen.");

			return false;
		}

		if (gui.getBrett().getBrettFeldIndex(zeile, spalte).getIstBelegt() && klickcnt == 1) {
			// gui.log("Auf Figuren steigen geht nicht!");

			klickcnt = 0;
			gui.log("Auf Figuren steigen geht nicht!\nNeue Startposition wählen.");

			return false;
		}

		if (gui.getBrett().getBrettFeldIndex(zeile, spalte).getIstBelegt() && gui.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur().getFarbe() != gui.ggetAmZug() && klickcnt == 0) {
			// gui.log("Nimm deine eigenen Figuren!");

			klickcnt = 0;
			gui.log("Nimm deine eigenen Figuren!\nNeue Startposition wählen.");

			return false;
		}

		if (!gui.getBrett().getBrettFeldIndex(zeile, spalte).getIstBelegt() && klickcnt == 0) {
			// gui.log("Es ist keine Figur auf dem Feld!");

			klickcnt = 0;
			gui.log("Es ist keine Figur auf dem Feld!\nNeue Startposition wählen.");

			return false;
		}
		return true;
	}

	/**
	 * Der action Listener verarbeitet alle aktionen
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		switch (ae.getActionCommand()) {

		case "":

			if (gui.getK1() != null && gui.getK1().getSpieler().getFarbe() == gui.ggetAmZug() || gui.getK2() != null && gui.getK2().getSpieler().getFarbe() == gui.ggetAmZug()) {
				gui.log("Für die KI ziehen geht nicht!");
			} else {

				for (int zeile = gui.getButtonArray().length - 1; zeile >= 0; zeile--) {
					for (int spalte = 0; spalte < gui.getButtonArray()[zeile].length; spalte++) {

						if (ae.getSource() == gui.getButtonArray()[zeile][spalte]) {

							if (prüfen(zeile, spalte)) {
								gui.log("Gedrücktes Feld: " + gui.getButtonArray()[zeile][spalte].getToolTipText());
								klickcnt++;
								if (klickcnt == 2) {
									gui.posWeitergeben(startp, gui.getButtonArray()[zeile][spalte].getToolTipText());
									klickcnt = 0;
									startp = null;
								}
								startp = gui.getButtonArray()[zeile][spalte].getToolTipText();

							}
						}
					}
				}
			}
			break;

		case "Spiel laden":
			gui.spielLaden();
			break;

		case "Mail senden":
			gui.mailAbfrage();
			break;

		case "Ziehen":
			String eingabe = gui.getBefehlFeld().getText();

			// a=buchstabe X=zahl X=1-9 XX=1 0-2
			// aX-aX aXX-aXX aX-aXX aXX-aX
			if ((Pattern.matches("[a-l A-L][1-9][-][a-l A-L][1-9]", eingabe)) || (Pattern.matches("[a-l A-L][1][0-2][-][a-l A-L][1][0-2]", eingabe)) || (Pattern.matches("[a-l A-L][1][0-2][-][a-l A-L][1-9]", eingabe)) || (Pattern.matches("[a-l A-L][1-9][-][a-l A-L][1][0-2]", eingabe))) {
				gui.log("Eingabe: ( " + eingabe + " ) ");
				String[] list = eingabe.split("-");

				gui.posWeitergeben(list[0], list[1]);
				gui.getBefehlFeld().setText("");

			} else {

				gui.log("Inkorrekte Eingabe");
				gui.getBefehlFeld().setText("");
			}

			break;

		case "Ki ziehen":
			gui.kiSpieleruebergeben();
			break;

		case "neues Spiel erstellen":
			if (anzahlSpiele == 1) {

				gui.loeschen();
				gui.log("Neues Spiel wird erstellt.");
				anzahlSpiele--;
				i = 1;
				klickcnt = 0;
				gui.log("Neues Spiel aufgebaut.");
				gui.spielerErstellen();
				anzahlSpiele++;
				break;
			}
			anzahlSpiele++;
			// gui.spielAufbauen();
			// anstatt größe wählbar zu machen erstmal nur 12x12
			gui.spielerErstellen();
			// gui.aufbauen(12);

			break;

		case "Weiter":

			if (gui.getAcht().isSelected() == true) {

				gui.aufbauen(8);
				gui.log("Gewählte Größe: 8");
				gui.setfeldgroesse(8);
				gui.spielerErstellen();

			}
			if (gui.getZehn().isSelected() == true) {

				gui.aufbauen(10);
				gui.log("Gewählte Größe: 10");
				gui.setfeldgroesse(10);
				gui.spielerErstellen();

			}
			if (gui.getZwölf().isSelected() == true) {

				gui.aufbauen(12);
				gui.log("Gewählte Größe: 12");
				gui.setfeldgroesse(12);
				gui.spielerErstellen();

			}

			gui.getBrettFrame().dispose();
			break;

		case "OK":
			String name = null;
			FarbEnum farbe = null;
			boolean istKi = false;
			name = gui.getNameFeld().getText();
			if (name.length() < 2) {
				gui.log("Der Name ist zu kurz!");
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
					gui.steineErstellen(farbe);
					gui.startenWeitergeben();
					if (istKi == false) {
						gui.log("Mensch " + name + " mit der Farbe\n" + farbe + " erstellt.");
					} else {
						gui.log("KI " + name + " mit der Farbe\n" + farbe + " erstellt.");
					}

				} else {
					gui.log(" Farbe wählen!");
				}
			}

			break;

		case "Anzeigen":
			gui.hilfeAnz();
			break;
		//
		// case "als PDF speichern":
		// try {
		// gui.screenshotErstellen();
		// gui.spielSpeichern();
		// gui.log("Spiel wurde gespeichert (PDF)");
		//
		// } catch (IOException e1) {
		// gui.log("Fehler beim speichern");// e1.printStackTrace();
		// }
		// break;

		case "senden":
			// String an = gui.getEmpfaenger();
			//
			// final String username = "madnb4@gmail.com";
			// final String password = "DanielJudithVerena";
			//
			// Properties p = new Properties();
			// p.put("mail.smtp.auth", "true");
			// p.put("mail.smtp.starttls.enable", "true");
			// p.put("mail.smtp.host", "smtp.gmail.com");
			// p.put("mail.smtp.port", "587");
			//
			// Session session = Session.getInstance(p,
			// new javax.mail.Authenticator() {
			// protected PasswordAuthentication getPasswordAuthentication() {
			// return new PasswordAuthentication(username,
			// password);
			// }
			// });
			//
			// // if (anhangPfad1 == null) { // pdf & ser
			// // p.put("anhangPfad1", "MADN Spiel PDF.pdf");
			// // } else {
			// // p.put("anhangPfad1", anhangPfad1);
			// // }
			// // if (anhangName1 == null) {
			// // p.put("anhangName1", "MADN Spiel PDF");
			// // } else {
			// // p.put("anhangName1", anhangName1);
			// // }
			// // if ("SpielSerialisiert.ser" == null) {
			// // p.put("anhangPfad2", "");
			// // } else {
			// // p.put("anhangPfad2", "SpielSerialisiert.ser");
			// // }
			// // if ("MADN Spiel Serialisiert" == null) {
			// // p.put("anhangNam2", "");
			// // } else {
			// // p.put("anhangNam2", "MADN Spiel Serialisiert");
			// // }
			//
			// try {
			//
			// Message message = new MimeMessage(session);
			// message.setFrom(new InternetAddress("madnb4@gmail.com"));
			// message.setRecipients(Message.RecipientType.TO,
			// InternetAddress.parse(an));
			// message.setSubject("Mensch aergere dich nicht - Spielstand");
			//
			// Multipart mp = new MimeMultipart();
			// MimeBodyPart text = new MimeBodyPart();
			// text.setText("Lieber Spieler! \nIm Anhang findest du deinen Spielstand als PDF oder in serialisierter Form. \nViel Spass weiterhin beim Spielen.");
			//
			// MimeBodyPart anhangPDF = new MimeBodyPart();
			// try {
			// anhangPDF.attachFile("MADN Spiel PDF.pdf");
			// } catch (IOException e) {
			// gui.log("Fehler beim PDF anhaengen");
			// //e.printStackTrace();
			// }
			//
			// MimeBodyPart anhangSer = new MimeBodyPart();
			// try {
			// anhangSer.attachFile("SpielSerialisiert.ser");
			// } catch (IOException e) {
			// gui.log("Fehler beim SER anhaengen");
			// //e.printStackTrace();
			// }
			//
			// mp.addBodyPart(text);
			// mp.addBodyPart(anhangPDF);
			// mp.addBodyPart(anhangSer);
			//
			// message.setContent(mp);
			//
			// Transport.send(message);
			//
			//
			//
			// } catch (MessagingException e) {
			// gui.log("Fehler beim senden");
			// //throw new RuntimeException(e);
			// }
			//
			// gui.log("E-Mail wurde gesendet.");
			// gui.getMailFrame().dispose();
			break;

		default:
			gui.log("Nicht verfügbar.");
			break;
		}
		// } catch (Exception e) {
		// gui.log(e.getMessage());
		//
		// }

	}
}
