import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JPanel hauptp = new JPanel(new BorderLayout());
	private JButton[] buttonArray = new JButton[144];
	private JTextArea ta = new JTextArea(8, 20);// fuer die Loggerfeld groesse
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
	private JPanel panel02 = new JPanel(new BorderLayout());
	private Spiel s = new Spiel();
	private int aufbaucnt = 1;

	public GUI() {
		super();

		eh = new EventHandler(this);

		spielAnzeigen();
	}

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

	public void spielerErstellen() {

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

		panel02.add(button02, BorderLayout.SOUTH);

		spielerFrame.setVisible(true);
	}

	public void spielAnzeigen() {

		JPanel logger = new JPanel();
		logger.setLayout(new BorderLayout());
		ta.setFont(new Font("Arial", Font.PLAIN, 14));
		ta.setOpaque(true);
		ta.setEditable(false);
		scroller = new JScrollPane(ta);
		logger.add(new JLabel("Log-Fenster:"), BorderLayout.NORTH);
		logger.add(scroller, BorderLayout.CENTER);
		hauptf.add(logger, BorderLayout.SOUTH);

		hauptf.setLocation(GetScreenWorkingWidth() / 9, GetScreenWorkingHeight() / 50);
		hauptf.setMenuBar(this.getMenuOben()); // erstellt Menue oben

		hauptf.setSize(GetScreenWorkingWidth() - 450, GetScreenWorkingHeight() - 50);

		hauptf.add(hauptp);
		// hauptf.add(SPIELFELD, BorderLayout.CENTER);

		hauptf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hauptf.setVisible(true);
		hauptf.setResizable(false);
		// addComponentsToPane(hauptf.getContentPane());
	}

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
		MenuItem xxx = new MenuItem("xxx");
		hilfe.add(xxx);
		xxx.addActionListener(eh);
		menueLeiste.add(hilfe);

		return menueLeiste;
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

	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	public void setButtonText(int nummer, String text) {
		buttonArray[nummer - 1].setText(text);
	}

	// /////////////////////////////////////////
	public void aufbauen(int x) {
		s.aufbauen(x);
	}

	public void spielerWeitergeben(String name, FarbEnum farbe, boolean istKi) {
		s.spielerErstellen(name, farbe, istKi);

	}

	// ///////GETTER UND SETTER ////////////////

	public JRadioButton getAcht() {
		return Acht;
	}

	public JRadioButton getZehn() {
		return Zehn;
	}

	public JRadioButton getZwölf() {
		return Zwölf;
	}

	public JFrame getBrettFrame() {
		return brettFrame;
	}

	public JTextField getNameFeld() {
		return nameFeld;
	}

	public JRadioButton getSchwarz() {
		return Schwarz;
	}

	public JRadioButton getWeiß() {
		return Weiß;
	}

	public JRadioButton getMensch() {

		return Mensch;
	}

	public JRadioButton getKi() {

		return Ki;
	}

	public JFrame getSpielerFrame() {

		return spielerFrame;
	}

}