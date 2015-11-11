import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {
	private GUI gui;// kenntnisbeziehung herstellen!!!

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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}