import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author B1
 *
 */
public class GUI extends JFrame {

	/**
             *
             */
	private static final long serialVersionUID = 1L;

	private JFrame hauptf = new JFrame(" Dame Spiel der Gruppe B1 ");

	private JTextArea ta = new JTextArea(10, 20);// fuer die Loggerfeld groesse
	private JPanel panel02 = new JPanel(new BorderLayout());
	private JPanel logger = new JPanel();
	private JScrollPane scroller;
	private EventHandler eh;
	private JFrame spielerFrame;
	private JFrame brettFrame;
	private boolean first = false;
	private JRadioButton Acht;
	private JRadioButton Zehn;
	private JRadioButton Zwölf;
	private JRadioButton Schwarz;
	private JRadioButton Weiß;
	private JRadioButton Mensch;
	private JRadioButton Ki;
	private JTextField nameFeld;
	private JTextField befehlFeld = new JTextField("     ");// 12345678912345678912345678912345678912345/
	private int ziehenAuswahl;
	private Spiel s = new Spiel();
	private int aufbaucnt = 1;
	private JFrame helpframe;
	private JTextArea helptxt;
	private JPanel helppanel;
	private JButton ziehen;
	private JButton kiziehen;
	private int spCnt = 0;
	private JFrame mailFrame;
	JTextField empfaengerFeld;
	Spielfigur fig;

	private int feldgroesse;// TODO
	private JPanel hauptp;
	private JButton[][] buttonArray;

	private JTextField fuellFeldx;
	private JTextField fuellFeld2;

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();

	ImageIcon felds = new ImageIcon("Bilder//felds.png");
	ImageIcon feldw = new ImageIcon("Bilder//feldw.png");

	ImageIcon figurs = new ImageIcon("Bilder//FeldSSteinS.png");
	ImageIcon figurw = new ImageIcon("Bilder//FeldSSteinW.png");

	ImageIcon damew = new ImageIcon("Bilder//dameW.png");
	ImageIcon dames = new ImageIcon("Bilder//dameS.png");

	ImageIcon figurSG = new ImageIcon("Bilder//SteinSG.png");
	ImageIcon figurWG = new ImageIcon("Bilder//SteinWG2.png");

	ImageIcon dameSG = new ImageIcon("Bilder//dameSG.png");
	ImageIcon dameWG = new ImageIcon("Bilder//dameWG.png");

	ImageIcon furinier = new ImageIcon("Bilder//furnier2.jpg");

	/**
	 * Konstruktor für die GUI
	 */
	public GUI(int groesse) {
		super();
		feldgroesse = groesse;
		eh = new EventHandler(this);

		spielAnzeigen();
		aufbauen(feldgroesse);
		//
	}

	/**
	 * zum aufbauen der variablen größe //UNBENUTZT
	 *
	 * @return true oder false
	 */
	public boolean spielAufbauen() {
		if (aufbaucnt != 0) {
			aufbaucnt--;

			brettFrame = new JFrame("Spiel aufbauen");
			JLabel label = new JLabel("Wie groß soll dein Spielbrett sein? ");
			brettFrame.setSize(370, 100);
			brettFrame.setResizable(false);
			JPanel panel = new JPanel(new BorderLayout());
			JButton button01 = new JButton("Weiter");
			panel.add(label, BorderLayout.WEST);
			brettFrame.add(panel, BorderLayout.NORTH);
			brettFrame.add(button01, BorderLayout.SOUTH);
			panel02.setLayout(new FlowLayout());
			brettFrame.add(panel02, BorderLayout.CENTER);
			button01.addActionListener(eh);
			brettFrame.setLocation(GetScreenWorkingWidth() / 2 - 370, GetScreenWorkingHeight() / 2 - 100);
			brettFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			Acht = new JRadioButton("8x8");
			Zehn = new JRadioButton("10x10");
			Zwölf = new JRadioButton("12x12", true);

			ButtonGroup group2 = new ButtonGroup();
			group2.add(Acht);
			group2.add(Zehn);
			group2.add(Zwölf);

			panel02.add(Acht);
			panel02.add(Zehn);
			panel02.add(Zwölf);

		} else {

			brettFrame.setVisible(true);
			return true;
		}
		brettFrame = new JFrame("Spiel aufbauen");
		JLabel label = new JLabel("Wie groß soll dein Spielbrett sein? ");
		brettFrame.setSize(370, 100);
		brettFrame.setResizable(false);
		JPanel panel = new JPanel(new BorderLayout());
		JButton button01 = new JButton("Weiter");
		panel.add(label, BorderLayout.WEST);
		brettFrame.add(panel, BorderLayout.NORTH);
		brettFrame.add(button01, BorderLayout.SOUTH);
		panel02.setLayout(new FlowLayout());
		brettFrame.add(panel02, BorderLayout.CENTER);
		button01.addActionListener(eh);
		brettFrame.setLocation(GetScreenWorkingWidth() / 2 - 370, GetScreenWorkingHeight() / 2 - 100);

		// Acht = new JRadioButton("8x8");
		// Zehn = new JRadioButton("10x10");
		// Zwölf = new JRadioButton("12x12", true);

		// ButtonGroup group2 = new ButtonGroup();
		// group2.add(Acht);
		// group2.add(Zehn);
		// group2.add(Zwölf);
		//
		panel02.add(Acht);
		panel02.add(Zehn);
		panel02.add(Zwölf);

		brettFrame.setVisible(true);
		return true;
	}

	/**
	 * spieler erstellen fenster
	 */
	public void spielerErstellen() {
		befehlFeld.setText("");
		JButton button02 = new JButton("OK");
		spielerFrame = new JFrame("Spieler erstellen");
		spielerFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		spielerFrame.setLocation(GetScreenWorkingWidth() / 2 - 370, GetScreenWorkingHeight() / 2 - 100);
		spielerFrame.setLayout(new GridLayout(2, 0));
		spielerFrame.setSize(400, 210);
		spielerFrame.setResizable(false);

		JLabel label02 = new JLabel(" Gebe deinen Spielernamen deine Farbe und die Art ein.");
		JPanel panel02 = new JPanel(new BorderLayout());
		nameFeld = new JTextField("Spielername");

		panel02.add(label02, BorderLayout.NORTH);
		label02.setLayout(new BorderLayout());
		panel02.add(button02, BorderLayout.SOUTH);
		button02.addActionListener(eh);

		panel02 = new JPanel();
		panel02.setLayout(new GridLayout(2, 0));
		panel02.add(nameFeld, BorderLayout.SOUTH);

		spielerFrame.add(label02, BorderLayout.NORTH);
		spielerFrame.add(panel02, BorderLayout.SOUTH);

		// RadioButtons für Farbe
		Schwarz = new JRadioButton("Schwarz");
		Weiß = new JRadioButton("Weiß");

		ButtonGroup group = new ButtonGroup();
		group.add(Schwarz);
		group.add(Weiß);

		panel02.add(Schwarz);
		panel02.add(Weiß);

		// RadioButtons für Art
		Mensch = new JRadioButton("Mensch", true);
		Ki = new JRadioButton("KI", true);

		ButtonGroup group2 = new ButtonGroup();
		group2.add(Mensch);
		group2.add(Ki);

		panel02.add(Mensch);
		panel02.add(Ki);

		panel02.add(button02, BorderLayout.SOUTH);

		spielerFrame.setVisible(true);
	}

	/**
	 * baut das brett auf un zeigt alles an
	 */
	public void spielAnzeigen() {// TODO
		// LOGGER PANE HINTERGRUND LIGHT_GRAY
		logger.setBackground(Color.LIGHT_GRAY);
		logger.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		logger.setLayout(new BorderLayout());
		ta.setFont(new Font("Arial", Font.PLAIN, 14));
		ta.setOpaque(true);
		ta.setEditable(false);
		scroller = new JScrollPane(ta);
		logger.add(new JLabel("Log-Fenster:"), BorderLayout.NORTH);
		logger.add(scroller, BorderLayout.CENTER);
		hauptf.add(logger, BorderLayout.WEST);

		hauptf.setLocation(GetScreenWorkingWidth() / 9, GetScreenWorkingHeight() / 50);
		hauptf.setSize(GetScreenWorkingWidth() - 450, GetScreenWorkingHeight() - 90);
		hauptf.setMenuBar(this.getMenuOben()); // erstellt Menue oben

		// rechte seite

		//
		JLabel lbl = new JLabel();
		lbl.setIcon(furinier);
		lbl.setBackground(Color.white);
		lbl.setLayout(gbl);
		lbl.add(befehlFeld);

		ziehen = new JButton("Ziehen");
		ziehen.setEnabled(false);
		ziehen.addActionListener(eh);
		lbl.add(ziehen);

		kiziehen = new JButton("Ki Ziehen");
		kiziehen.setEnabled(false);
		kiziehen.addActionListener(eh);
		lbl.add(kiziehen);
		hauptf.add(lbl, BorderLayout.EAST);

		befehlFeld.setPreferredSize(new Dimension(200, 20));
		befehlFeld.setMinimumSize(new Dimension(100, 20));
		gbc.gridx = 2;
		gbc.gridy = 0;

		JLabel lbl2 = new JLabel(" Hier Start- und Endpositon eingeben. ");
		lbl.add(lbl2, gbc);
		gbc.gridy++;
		lbl.add(befehlFeld, gbc);
		gbc.gridy++;
		lbl.add(ziehen, gbc);
		gbc.gridy++;
		lbl.add(new JLabel(" "), gbc);
		gbc.gridy++;
		JLabel lbl3 = new JLabel(" Ki ziehen lassen. ");
		lbl.add(lbl3, gbc);
		gbc.gridy++;
		lbl.add(kiziehen, gbc);
		gbc.gridy++;

		setfeldgroesse(feldgroesse);

		hauptf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hauptf.setVisible(true);
		hauptf.setResizable(false);

	}

	/**
	 * baut die buttons auf
	 */
	public void feldButtons() {
		boolean ss = false;

		if (feldgroesse == 12) {
			ss = true;
		} else {
			ss = false;
		}

		int cnt = 0;
		if (feldgroesse == 12) {
			for (int zeile = 0; zeile < buttonArray.length; zeile++) {
				for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
					buttonArray[zeile][spalte] = new JButton("");
					buttonArray[zeile][spalte].setMargin(new Insets(0, 0, 0, 0));
					// buttonArray[zeile][spalte].setSize(50, 50);
					// buttonArray[zeile][spalte].setBounds(70 * zeile, 70 * spalte, 70,
					// 70);
					String id = "" + (char) (65 + spalte) + (zeile + 1);
					buttonArray[zeile][spalte].setToolTipText(id);
					buttonArray[zeile][spalte].addActionListener(eh);
					buttonArray[zeile][spalte].setEnabled(false);
					// buttonArray[zeile][spalte].addMouseListener(new MouseAdapter() {
					//
					// @Override
					// public void mouseEntered(MouseEvent me) {
					// log("luck ");
					//
					// }
					// });
					// TODO

					cnt++;
					if (ss == false) {

						buttonArray[zeile][spalte].setIcon(feldw);

					} else {
						buttonArray[zeile][spalte].setIcon(felds);
					}
					ss = !ss;
					if (cnt == feldgroesse) {
						ss = !ss;
						cnt = 0;
					}
				}
			}

		} else {
			for (int zeile = buttonArray.length - 1; zeile >= 0; zeile--) {
				for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
					buttonArray[zeile][spalte] = new JButton("");
					buttonArray[zeile][spalte].setMargin(new Insets(0, 0, 0, 0));
					// buttonArray[zeile][spalte].setSize(50, 50);
					// buttonArray[zeile][spalte].setBounds(70 * zeile, 70 * spalte, 70,
					// 70);
					String id = "" + (char) (65 + spalte) + (zeile + 1);
					buttonArray[zeile][spalte].setToolTipText(id);

					// buttonArray[zeile][spalte].addMouseListener(new MouseAdapter() {
					//
					// @Override
					// public void mouseEntered(MouseEvent me) {
					// log("luck ");
					//
					// }
					// });

					// TODO
					// buttonArray[i][j].addActionListener(eh);
					// //////// MUSS WIEDER REIN WENN ÜBER BUTTON DRUCK!!

					cnt++;
					if (ss == false) {

						buttonArray[zeile][spalte].setIcon(feldw);

					} else {
						buttonArray[zeile][spalte].setIcon(felds);
					}
					ss = !ss;
					if (cnt == feldgroesse) {
						ss = !ss;
						cnt = 0;
					}
				}
			}
		}
	}

	/**
	 * erstellt die steine
	 *
	 * @param farbe
	 *          erstellt die steine der bestimmten farbe
	 */
	public void steineErstellen(FarbEnum farbe) {
		Spielbrett brett = s.getBrett();

		if (farbe == FarbEnum.SCHWARZ) {

			for (int i = 0; i < brett.getBrettGroesse(); i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {

					if (brett.getBrettFeldIndex(i, j).getIstSchwarz()) {

						if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
							if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == farbe) {

								buttonArray[i][j].setIcon(figurs);

							}
						}
					}
				}
			}

		} else {
			for (int i = 0; i < brett.getBrettGroesse(); i++) {
				for (int j = 0; j < brett.getBrettGroesse(); j++) {

					if (brett.getBrettFeldIndex(i, j).getIstSchwarz()) {

						if (brett.getBrettFeldIndex(i, j).getIstBelegt()) {
							if (brett.getBrettFeldIndex(i, j).getSpielfigur().getFarbe() == farbe) {

								buttonArray[i][j].setIcon(figurw);
								buttonArray[i][j].setIcon(figurWG);
							}
						}
					}
				}
			}

		}
		hauptf.repaint();
	}

	/**
	 * baut die menuebar
	 *
	 * @return
	 */
	protected MenuBar getMenuOben() {
		MenuBar menueLeiste = new MenuBar();
		Menu spiel = new Menu("Spiel"); // erster Knopf
		MenuItem laden = new MenuItem("Spiel laden"); // Unterknopf 1
		spiel.add(laden);
		laden.addActionListener(eh);
		MenuItem neu = new MenuItem("neues Spiel erstellen"); // Unterknopf 2
		spiel.add(neu);
		neu.addActionListener(eh);
		MenuItem speichern = new MenuItem("als PDF speichern");
		spiel.add(speichern);
		speichern.addActionListener(eh);
		menueLeiste.add(spiel);
		MenuItem speichernCSV = new MenuItem("als CSV speichern");
		spiel.add(speichernCSV);
		speichernCSV.addActionListener(eh);
		menueLeiste.add(spiel);
		MenuItem speichernSER = new MenuItem("Serialisiert speichern");
		spiel.add(speichernSER);
		speichernSER.addActionListener(eh);
		menueLeiste.add(spiel);

		Menu mail = new Menu("Mail");
		MenuItem senden = new MenuItem("Mail senden");
		mail.add(senden);
		senden.addActionListener(eh);
		menueLeiste.add(mail);

		Menu hilfe = new Menu("Hilfe"); // dritter Knopf
		MenuItem Anzeigen = new MenuItem("Anzeigen");
		hilfe.add(Anzeigen);
		Anzeigen.addActionListener(eh);
		menueLeiste.add(hilfe);

		return menueLeiste;
	}

	/**
	 * zeigt ein hilfefenster
	 */
	public void hilfeAnz() {

		helpframe = new JFrame("Hilfe");
		helptxt = new JTextArea(
				"Um ein neues Spiel zu erstellen, müssen sie im Menu Spiel die Funktion Neues Spiel erstellen wählen  \nAnschließend werden sie aufgefordert zwei Spieler zu erstellen.\n Ein Spieler braucht einen Namen und eine Farbe. Sie können zwischen den Farben Schwarz und Weiß wählen.\nSobald Beide Spieler erstellt wurden, beginnt das Spiel.\nUm einen Stein zu bewegen, geben sie die zu erst die Startposition ein und dann die Endposition, z.B. B8-C7");
		helptxt.setEditable(false);
		helppanel = new JPanel();
		helppanel.setLayout(new GridLayout(1, 1));
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		helptxt.setFont(f);
		helptxt.add(new JScrollBar());
		helppanel.add(new JScrollPane(helptxt));
		helpframe.setContentPane(helppanel);
		helpframe.pack();
		helpframe.setVisible(true);

		helpframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	// LOGGER
	/**
	 *
	 * @param text
	 *          wird im Logger gezeigt
	 */
	public void log(String text) {

		if (first == false) {
			ta.setText(ta.getText() + text);
			first = true;
		} else {
			ta.setText(ta.getText() + "\n––––––––––––––––––––––––––––––––––\n" + text);
			// Wenn etwas im Logger
			// gezeigt
			// werden soll einfach
		} // log("inhalt"); aufrufen!
	}

	/**
	 * Zum leeren des Loggers
	 */
	public void logClear() {
		ta.setText("");
	}

	/**
	 * Um die Bildschirmbreite zu ermitteln
	 *
	 * @return
	 */
	public static int GetScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	}

	/**
	 * bildschirmhöhe
	 *
	 * @return
	 */
	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	// /////////////////////////////////////////
	/**
	 * übergibt an spiel
	 *
	 * @param x
	 *          größe
	 */
	public void aufbauen(int x) {
		s.aufbauen(x);
	}

	/**
	 * übergibt den spieler
	 *
	 * @param name
	 * @param farbe
	 * @param istKi
	 */
	public void spielerWeitergeben(String name, FarbEnum farbe, boolean istKi) {
		s.spielerErstellen(name, farbe, istKi);
	}

	/**
	 * gibt die pos weiter
	 *
	 * @param startp
	 * @param endp
	 */

	public void posWeitergeben(String startp, String endp) {// Zug/ziehen/bewegen/..

		if (s.ziehen(startp, endp)) {

			log("Startposition: " + startp + " Endposition: " + endp);

			brettAktualisieren();

			if (s.kannWeiterZiehen()) {
				Object[] options = { "JA", "NEIN" };
				ziehenAuswahl = JOptionPane.showOptionDialog(hauptf, "Willst du weiter ziehen oder nicht?", "Ziehen oder nicht?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

				if (ziehenAuswahl == 0) {
					log("Bitte erneut ziehen.");

					s.willWeiterZiehen();

					ziehenAuswahl = 0;
				} else {
					log("Du wurdest gepustet.");

					s.willNichtWeiterZiehen();

					if (s.getAmZug() == FarbEnum.SCHWARZ) {

						log("Schwarz am Zug");

						if (s.getK1() != null) {
							ziehen.setEnabled(false);
							kiziehen.setEnabled(true);
						} else {
							ziehen.setEnabled(true);
							kiziehen.setEnabled(false);
						}

					} else {
						log("Weiß am Zug");

						if (s.getK2() != null) {
							ziehen.setEnabled(false);
							kiziehen.setEnabled(true);
						} else {
							ziehen.setEnabled(true);
							kiziehen.setEnabled(false);
						}
						brettAktualisieren();
						ziehenAuswahl = 0;
					}
				}

			} else {

				if (s.getAmZug() == FarbEnum.SCHWARZ) {

					log("Schwarz am Zug");

					if (s.getK1() != null) {
						ziehen.setEnabled(false);
						kiziehen.setEnabled(true);
					} else {
						ziehen.setEnabled(true);
						kiziehen.setEnabled(false);
					}

				} else {
					log("Weiß am Zug");

					if (s.getK2() != null) {
						ziehen.setEnabled(false);
						kiziehen.setEnabled(true);
					} else {
						ziehen.setEnabled(true);
						kiziehen.setEnabled(false);
					}
				}

			}
			brettAktualisieren();
		} else {
			log("Dieser Zug war nicht möglich");
		}
	}

	public void kiSpieleruebergeben() {

		if (s.kizieh()) {

			if (s.getZugFurLog().size() != 0) {

				for (int i = 0; i < s.getZugFurLog().size(); i++) {

					log(s.getZugFurLog().get(i));

				}
				s.getZugFurLog().clear();

			} else {

			}

			this.brettAktualisieren();

			if (s.getAmZug() == FarbEnum.SCHWARZ) {
				log("Schwarz am Zug");

				if (s.getK1() != null) {
					ziehen.setEnabled(false);
					kiziehen.setEnabled(true);
				} else {
					ziehen.setEnabled(true);
					kiziehen.setEnabled(false);
				}

			} else {
				log("Weiß am Zug");

				if (s.getK2() != null) {
					ziehen.setEnabled(false);
					kiziehen.setEnabled(true);
				} else {
					ziehen.setEnabled(true);
					kiziehen.setEnabled(false);
				}

			}
		} else {
			log("Dieser Zug war nicht möglich");
		}
	}

	/**
	 * Um das Brett nach ki zug neuanzuzeigen
	 */

	public void brettAktualisieren() {
		for (int zeile = 0; zeile <= buttonArray.length - 1; zeile++) {
			for (int spalte = 0; spalte <= buttonArray[zeile].length - 1; spalte++) {
				if (s.getBrett().getBrettFeldIndex(zeile, spalte).getIstSchwarz()) {
					if (!s.getBrett().getBrettFeldIndex(zeile, spalte).getIstBelegt()) {

						buttonArray[zeile][spalte].setIcon(felds);

					} else {

						Spielfigur fig = s.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur();

						if (fig.getFarbe() == FarbEnum.SCHWARZ) {

							if (FarbEnum.SCHWARZ == s.getAmZug()) {

								buttonArray[zeile][spalte].setIcon(figurSG);
								if (s.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur().getDame(fig)) {
									buttonArray[zeile][spalte].setIcon(dameSG);
								}

							} else {

								buttonArray[zeile][spalte].setIcon(figurs);
								if (fig.getDame(fig)) {
									buttonArray[zeile][spalte].setIcon(dames);
								}
							}
						} else {

							if (FarbEnum.WEIß == s.getAmZug()) {

								buttonArray[zeile][spalte].setIcon(figurWG);
								if (s.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur().getDame(fig)) {
									buttonArray[zeile][spalte].setIcon(dameWG);
								}
							} else {

								buttonArray[zeile][spalte].setIcon(figurw);
								if (fig.getDame(fig)) {
									buttonArray[zeile][spalte].setIcon(damew);
								}
							}
						}
					}
				}
			}
		}
		hauptf.repaint();
	}

	/**
	 * starten weitergeben
	 */
	public void startenWeitergeben() {
		spCnt++;
		if (spCnt == 2) {

			for (int zeile = 0; zeile < buttonArray.length; zeile++) {
				for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {

					buttonArray[zeile][spalte].setEnabled(true);
				}
			}

			ziehen.setEnabled(true);
			kiziehen.setEnabled(true);

			if (s.getK1() != null && s.getK1().getSpieler().getFarbe() == FarbEnum.WEIß || s.getK2() != null && s.getK2().getSpieler().getFarbe() == FarbEnum.WEIß) {

				ziehen.setEnabled(false);

			} else {

				kiziehen.setEnabled(false);
			}

			s.starten();
			log("Das Spiel beginnt.");
			log(s.getAmZug() + " beginnt.");
		} else {

		}

	}

	public static BufferedImage getScreenShot(Component component) {

		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
		// ruft die Komponente der zeichnen Methoden auf
		// Graphics Object vom Image
		component.paint(image.getGraphics());
		return image;
	}

	public void screenshotErstellen() {

		BufferedImage img = getScreenShot(hauptf.getContentPane());

		try {
			// macht das Bild zu PNG
			ImageIO.write(img, "png", new File("screenshotSpiel.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		log("Screenshot wurde erstellt.");
	}

	public void spielLaden() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
	}

	public void spielSpeichern() throws IOException {
		// .Speichern("MADN Spiel", "PDF");
	}

	public void spielSpeichernCSV() throws IOException {
		// .Speichern("MADN Spiel", "CSV");
	}

	public void spielSpeichernSER() throws IOException {
		// .Speichern("MADN Spiel", "SER");
	}

	public void mailAbfrage() {
		mailFrame = new JFrame();
		mailFrame.setSize(500, 150);
		mailFrame.setTitle("Mail");
		mailFrame.setLocation(GetScreenWorkingWidth() / 2 - 370, GetScreenWorkingHeight() / 2 - 100);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		// JLabel mailAdresse = new JLabel("E-Mail:");
		// JLabel pwLabel = new JLabel("Passwort:");
		JLabel empfaenger = new JLabel("Empfaenger E-Mail:");

		// JTextField mail = new JTextField(
		// "nur Adressen von reutlingen-university.de");
		// JPasswordField pwField = new JPasswordField("");
		// pwField.setEchoChar('*'); // macht * bei Passworteingabe
		empfaengerFeld = new JTextField("");

		JButton senden = new JButton("senden");
		senden.addActionListener(eh);

		// panel.add(mailAdresse);
		// panel.add(mail);
		// panel.add(pwLabel);
		// panel.add(pwField);
		panel.add(empfaenger);
		panel.add(empfaengerFeld);
		panel.add(senden);

		mailFrame.add(panel);
		mailFrame.setResizable(false);
		mailFrame.setVisible(true);
	}

	// public void mailSenden(){
	// bediener.mailSenden();
	// }

	// ///////GETTER UND SETTER ////////////////
	/**
	 * getter acht
	 *
	 * @return
	 */
	public JRadioButton getAcht() {
		return Acht;
	}

	/**
	 * getter zehn
	 *
	 * @return
	 */
	public JRadioButton getZehn() {
		return Zehn;
	}

	/**
	 * getter zwölf
	 *
	 * @return
	 */
	public JRadioButton getZwölf() {
		return Zwölf;
	}

	/**
	 * get brett
	 *
	 * @return
	 */
	public JFrame getBrettFrame() {
		return brettFrame;
	}

	/**
	 * get name
	 *
	 * @return
	 */
	public JTextField getNameFeld() {
		return nameFeld;
	}

	/**
	 * get feld
	 *
	 * @return
	 */
	public JTextField getBefehlFeld() {
		return befehlFeld;
	}

	/**
	 * get schwarz
	 *
	 * @return
	 */
	public JRadioButton getSchwarz() {
		return Schwarz;
	}

	/**
	 * get weiß
	 *
	 * @return
	 */
	public JRadioButton getWeiß() {
		return Weiß;
	}

	/**
	 * get mensch
	 *
	 * @return
	 */
	public JRadioButton getMensch() {

		return Mensch;
	}

	/**
	 * get ki
	 *
	 * @return
	 */
	public JRadioButton getKi() {

		return Ki;
	}

	/**
	 * get frame
	 *
	 * @return
	 */
	public JFrame getSpielerFrame() {

		return spielerFrame;
	}

	/**
	 * get ziehen
	 *
	 * @return
	 */
	public JButton getZiehen() {

		return ziehen;
	}

	/**
	 * get kiziehen
	 *
	 * @return
	 */
	public JButton getKiZiehen() {

		return kiziehen;
	}

	public String getEmpfaenger() {
		return empfaengerFeld.getText();
	}

	public JButton[][] getButtonArray() {
		return buttonArray;
	}

	public JPanel getHauptp() {
		return hauptp;
	}

	public JFrame getHauptf() {
		return hauptf;
	}

	public KI getK1() {
		return s.getK1();
	}

	public KI getK2() {
		return s.getK2();
	}

	public FarbEnum ggetAmZug() {
		return s.getAmZug();
	}

	public Spielbrett getBrett() {
		return s.getBrett();
	}

	public void loeschen() {
		s.allesLoeschen();
		spielerFrame.dispose();
		spCnt = 0;
		aufbauen(feldgroesse);
		brettAktualisieren();
		ziehen.setEnabled(false);
		kiziehen.setEnabled(false);
		logClear();
		for (int zeile = 0; zeile < buttonArray.length; zeile++) {
			for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
				buttonArray[zeile][spalte].setEnabled(false);
			}
		}
	}

	public void setfeldgroesse(int groesse) {
		feldgroesse = groesse;
		buttonArray = new JButton[feldgroesse][feldgroesse];
		hauptp = new JPanel(new GridLayout(feldgroesse, feldgroesse, 0, 0));
		hauptp.setOpaque(true);
		hauptp.setVisible(true);

		feldButtons();

		for (int zeile = buttonArray.length - 1; zeile >= 0; zeile--) {
			for (int spalte = 0; spalte <= buttonArray[zeile].length - 1; spalte++) {
				buttonArray[zeile][spalte].setBackground(new Color(250, 225, 175, 227));
				hauptp.add(buttonArray[zeile][spalte]);
			}
		}
		hauptf.add(hauptp, BorderLayout.CENTER);
		hauptp.repaint();
		hauptf.repaint();
	}

}
