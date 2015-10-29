
/**
 * 
 * @author Baris, Daniel, Simon, Hannes
 *
 */
public abstract class KI {
	
	
	public Spieler spieler;


	/**
	 * Konstruktor der KI
	 * 
	 * @param spieler
	 */
	
	public KI(Spieler spieler) {
		if(spieler==null){
			throw new RuntimeException("Ki darf nicht ohne Spieler exisitieren");
		}else{
		this.spieler=spieler;
		}
	}
	
	public Spieler getSpieler(){
		return this.spieler;
	}
}
