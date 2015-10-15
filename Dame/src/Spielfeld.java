
/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public class Spielfeld {
	
	/**
	 * @param spielbrett Das Spielbrett
	 * @param spielfigur Die Spielfigur
	 * @param id die ID des Spielfelds
	 * @param istBelegt bool wert ob ein Spielfeld besetzt ist oder nicht
	 * @param farbe die Farbe des Spielfelds
	 * 
	 */
	private Spielbrett spielbrett;
	private Spielfigur spielfigur;
	private String id;
	private boolean istBelegt;
	private FarbEnum farbe;
 private boolean istSchwarz;
	/**
	 * Konstruktor für die Spielfelder
	 * 
	 * @param spielbrett Das Spielbrett
	 */
	public Spielfeld(Spielbrett spielbrett, boolean istSchwarz){
		this.spielbrett = spielbrett;
		this.istSchwarz=istSchwarz;
			
		}


	
	public String getid(){
		return id;
		
	}
	private void setid(String id) {
		this.id = id;
		
	}
	public Spielfigur getSpielfigur(){
		return this.spielfigur;
	}
	
	private void setSpielfigur(){
		this.spielfigur = spielfigur;
	}

	public boolean isIstBelegt() {
		return istBelegt;
	}

	public void setIstBelegt(boolean istBelegt) {
		this.istBelegt = istBelegt;
	}
	@Override
	public String toString(){
		return ("schwarz " + getIstSchwarz());
	}



	private boolean getIstSchwarz() {
		
		return this.istSchwarz;
	}

}
