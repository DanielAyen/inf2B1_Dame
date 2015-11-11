import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.beans.EventHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {

	private JFrame hauptf = new JFrame("Dame Spiel der Gruppe B1");
	private JPanel hauptp = new JPanel(new BorderLayout());
	private JButton[] buttonArray = new JButton[144];
	private JTextArea ta = new JTextArea(8, 20);// fuer die Loggerfeld groesse
	private JScrollPane scroller;
	private EventHandler eh;
	private JFrame frame;
	private boolean first = false;

	public GUI() {
		super();
		
		eh = new EventHandler();

	}

	public void spielerErstellen() {

		frame = new JFrame("Spieler erstellen");
		JLabel label = new JLabel("Mit wie vielen Spielern soll das Spiel gespielt werden? ");
		frame.setSize(370, 100);
		frame.setResizable(false);
		JPanel panel = new JPanel(new BorderLayout());
		JButton button01 = new JButton("OK");
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
		// jf.getContentPane().add(logger);
		hauptf.add(logger, BorderLayout.SOUTH);

		hauptf.setLocation(GetScreenWorkingWidth() / 16, GetScreenWorkingHeight() / 14);
		hauptf.setMenuBar(this.getMenuOben()); // erstellt Menue oben

		hauptf.setSize(GetScreenWorkingWidth() - 450, GetScreenWorkingHeight() - 50);
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
			ta.setText(ta.getText() + "\n" + text);// Wenn etwas im Logger
			// gezweigt
			// werden soll einfach
		} // log("inhalt"); aufrufen!
	}

	/**
	 * Zum leeren des Loggers
	 */
	public void logClear() {
		ta.setText("");
	}

	public static int GetScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	}

	public static int GetScreenWorkingHeight() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	public void setButtonText(int nummer, String text) {
		buttonArray[nummer - 1].setText(text);
	}
}
