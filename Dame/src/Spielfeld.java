
public class Spielfeld {
	
	
	private Spielbrett Brett;
	private Spielfigur spielfigur;
	private String id;
	private boolean istBelegt;
	private FarbEnum farbe;
	
	public Spielfeld(String id){
		Spielfeld[][] brett = new Spielfeld[12][12];
		for ( int i = 1; i<=12; i++){
			for (int j = 1; j<=12; j++){
		brett[i][j].setid("A" + i);
		if (i >= 12 && j>= 12 ){
			brett[i][j].setid("B" + i);
		}
		if (i >= 12 && j>= 12 ){
			brett[i][j].setid("C" + i);	
			}
		if (i >= 12 && j>= 12 ){
			brett[i][j].setid("D" + i);
		}
			}
	}}
	
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
