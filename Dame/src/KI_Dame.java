import java.io.Serializable;

/**
 * 
 * @author Baris, Daniel, Simon, Hannes
 *
 */
class KI_Dame extends KI  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5485930708087137319L;
	public Spieler spieler;

	public KI_Dame(Spieler spieler) {
		super(spieler);
		this.spieler = spieler;
		
	}
}
