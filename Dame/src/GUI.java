import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JPanel hauptp = new JPanel(new GridLayout(12, 12, 0, 0));
	private JButton[][] buttonArray = new JButton[12][12];
	private JTextArea ta = new JTextArea(5, 20);// fuer die Loggerfeld groesse
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
	private JTextField befehlFeld = new JTextField("                                                 ");// 12345678912345678912345678912345678912345
	private Spiel s = new Spiel();
	private int aufbaucnt = 1;
	private JFrame helpframe;
	private JTextArea helptxt;
	private JPanel helppanel;
	private JButton ziehen;
	private int spCnt = 0;
	Spielfigur fig;

	ImageIcon felds = new ImageIcon("Bilder//felds.png");
	ImageIcon feldw = new ImageIcon("Bilder//feldw.png");

	ImageIcon figurs = new ImageIcon("Bilder//FeldSSteinS.png");
	ImageIcon figurw = new ImageIcon("Bilder//FeldSSteinW.png");

	ImageIcon damew = new ImageIcon("Bilder//FeldSDameW.png");
	ImageIcon dames = new ImageIcon("Bilder//FeldSDameS.png");

	/**
	 * Konstruktor für die GUI
	 */
	public GUI() {
		super();

		eh = new EventHandler(this);

		spielAnzeigen();
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
		Ki = new JRadioButton("KI");

		ButtonGroup group2 = new ButtonGroup();
		group2.add(Mensch);
		group2.add(Ki);

		panel02.add(Mensch);
		panel02.add(Ki);

		Ki.setEnabled(false);// KI ZZ nicht möglich!//TODO

		panel02.add(button02, BorderLayout.SOUTH);

		spielerFrame.setVisible(true);
	}

	/**
	 * baut das brett auf un zeigt alles an
	 */
	public void spielAnzeigen() {
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
		hauptf.add(logger, BorderLayout.SOUTH);

		hauptf.setLocation(GetScreenWorkingWidth() / 9, GetScreenWorkingHeight() / 50);
		hauptf.setSize(GetScreenWorkingWidth() - 450, GetScreenWorkingHeight() - 50);
		hauptf.setMenuBar(this.getMenuOben()); // erstellt Menue oben

		feldButtons();// erstellt alle Buttons
		for (int zeile = buttonArray.length - 1; zeile >= 0; zeile--) {
			for (int spalte = 0; spalte <= buttonArray[zeile].length - 1; spalte++) {
				hauptp.add(buttonArray[zeile][spalte]);
			}
		}



		hauptf.add(hauptp, BorderLayout.CENTER);
		// rechte seite
		JPanel befehlPanel = new JPanel(new GridLayout(2, 1));
		//
		befehlPanel.add(befehlFeld);

		//
		JTextField fuellFeld3 = new JTextField("");
		befehlPanel.add(fuellFeld3);
		fuellFeld3.setBackground(Color.LIGHT_GRAY);
		fuellFeld3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		fuellFeld3.setEnabled(false);
		//
		ziehen = new JButton("Ziehen");
		ziehen.setEnabled(false);
		ziehen.addActionListener(eh);
		befehlPanel.add(ziehen);
		hauptf.add(befehlPanel, BorderLayout.EAST);
		//
		JTextField fuellFeld4 = new JTextField("");
		befehlPanel.add(fuellFeld4);
		fuellFeld4.setBackground(Color.LIGHT_GRAY);
		fuellFeld4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		fuellFeld4.setEnabled(false);
		//

		// Linke seite
		JTextField fuellFeld2 = new JTextField("                                                                                  ");// "12345678912345678912345678912345678912345"
		fuellFeld2.setEnabled(false);
		fuellFeld2.setBackground(Color.LIGHT_GRAY);
		fuellFeld2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel linksPanel = new JPanel(new GridLayout());
		linksPanel.add(fuellFeld2);
		hauptf.add(linksPanel, BorderLayout.WEST);
		//
		hauptf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hauptf.setVisible(true);
		hauptf.setResizable(false);
		// addComponentsToPane(hauptf.getContentPane());

	}

	/**
	 * baut die buttons auf
	 */
	public void feldButtons() {

		boolean ss = true;
		int cnt = 0;
		for (int zeile = 0; zeile < buttonArray.length; zeile++) {
			for (int spalte = 0; spalte < buttonArray[zeile].length; spalte++) {
				buttonArray[zeile][spalte] = new JButton("");
				buttonArray[zeile][spalte].setMargin(new Insets(0, 0, 0, 0));
				buttonArray[zeile][spalte].setSize(20, 20);
				
				String id = "" + (char) (65 + spalte) + (zeile + 1);
				buttonArray[zeile][spalte].setToolTipText(id);
				
//				buttonArray[zeile][spalte].addMouseListener(new MouseAdapter() {
//					
//          @Override
//          public void mouseEntered(MouseEvent me) {
//             	log("suck ");
//              
//          }
//      });
				
				// TODO
				// buttonArray[i][j].addActionListener(eh);
				// //////// MUSS WIEDER REIN WENN ÜBER BUTTON DRUCK!!
				// TODO

				cnt++;
				if (ss == false) {

					buttonArray[zeile][spalte].setIcon(feldw);

				} else {
					buttonArray[zeile][spalte].setIcon(felds);
				}
				ss = !ss;
				if (cnt == 12) {
					ss = !ss;
					cnt = 0;
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
			ta.setText(ta.getText() + "\n" + text);
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

		int startC = s.wandleUmvString(startp)[0];
		int startI = s.wandleUmvString(startp)[1];
		int endC = s.wandleUmvString(endp)[0];
		int endI = s.wandleUmvString(endp)[1];

		if (s.getBrett().getBrettFeldIndex(startC, startI).getIstBelegt()) {
			fig = s.getBrett().getBrettFeldIndex(startC, startI).getSpielfigur();
		}
		if (s.ziehen(startp, endp)) {

			log("Startposition: " + startp + " Endposition: " + endp);

			figSetIcon(startC, startI, endC, endI);
			if (s.getAmZug() == FarbEnum.SCHWARZ) {
				log("Schwarz am Zug");
			} else {
				log("Weiß am Zug");
			}
		} else {
			log("Dieser Zug war nicht möglich");
		}
	}

	/**
	 * alle leeren felder durchs backend werden leer gesetzt alle damen werden
	 * geprüft und gesetzt
	 * 
	 * @param startC
	 *          int
	 * @param startI
	 *          int
	 * @param endC
	 *          int
	 * @param endI
	 *          int
	 */
	public void figSetIcon(int startC, int startI, int endC, int endI) {

		for (int zeile = buttonArray.length - 1; zeile >= 0; zeile--) {
			for (int spalte = 0; spalte <= buttonArray[zeile].length - 1; spalte++) {
				if (s.getBrett().getBrettFeldIndex(zeile, spalte).getIstSchwarz()) {
					if (!s.getBrett().getBrettFeldIndex(zeile, spalte).getIstBelegt()) {

						iconSetFeld(zeile, spalte);


					} else {
						Spielfigur temp = s.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur();
						if (s.getBrett().getBrettFeldIndex(zeile, spalte).getSpielfigur().getDame(temp)) {
							iconSetDame(zeile, spalte);
						}
					}
				}
			}
		}

		iconSetFeld(startC, startI);

		if (fig.getFarbe() == FarbEnum.SCHWARZ) {

			buttonArray[endC][endI].setIcon(figurs);
			if (fig.getDame(fig)) {
				buttonArray[endC][endI].setIcon(dames);
			}

		} else {

			buttonArray[endC][endI].setIcon(figurw);
			if (fig.getDame(fig)) {
				buttonArray[endC][endI].setIcon(damew);
			}
		}
	}

	/**
	 * dame icon setzen
	 * 
	 * @param x
	 * @param y
	 */
	public void iconSetDame(int x, int y) {
		if (s.getBrett().getBrettFeldIndex(x, y).getSpielfigur().getFarbe() == FarbEnum.SCHWARZ) {
			// TODO
			buttonArray[x][y].setIcon(dames);

		} else {

			buttonArray[x][y].setIcon(damew);

		}
	}

	/**
	 * feld setzten
	 * 
	 * @param x
	 * @param y
	 */
	public void iconSetFeld(int x, int y) {
		buttonArray[x][y].setIcon(felds);

	}

	/**
	 * starten weitergeben
	 */
	public void startenWeitergeben() {
		spCnt++;
		if (spCnt == 2) {

			ziehen.setEnabled(true);

			s.starten();
			log("Das Spiel beginnt.");
			log("Der Spieler mit der farbe: " + s.getAmZug() + " beginnt.");

		} else {

		}

	}

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

}
// TODO
// Schachnotation erstellen
// Wenn 2 spieler erstellt und man spielt aber ein neues spiel machen will fehlt
// das löschen der alten spieler bzw. das komplett neu aufbauen des spiels.
// z.b. durch vollständiges ersetzten des vorhandenen mit einem neuen spiel