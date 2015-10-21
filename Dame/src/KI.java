
/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
abstract class KI extends Spieler {

	
	
	KI(String name, FarbEnum farbe) {
		super(name, farbe);

	}

	
	@Override
	public String toString(){
		return "Name: "+super.getName()+"Farbe: "+super.getFarbe();
	}
}
