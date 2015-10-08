
public class Spielfeld {
	
	private Spielbrett Brett;
	private Spielfigur spielfigur;
	private String id;
	private boolean istBelegt;
	private FarbEnum farbe;
	
	public Spielfeld(String id){
		Spielfeld[][] brett = new Spielfeld[12][12];
		for ( int i = 0; i<=11; i++){
			
			for (int j = 0; j<=11; j++){
				
			}
		}
		this.setid(id);
	}
	
	public Spielfeld ( String id, Spieler spieler){
		
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

}
