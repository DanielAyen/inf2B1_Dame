
/**
 * 
 * @author Baris, Daniel, Simon
 *
 */
public abstract class KI {
	
	private String name;
	private FarbEnum farbe;


	
	
	KI(String name, FarbEnum farbe) {
		this.setName(name);
		this.setFarbe(farbe);


	}

	public String getName(){
		return name;
	}
	public FarbEnum getFarbe(){
		return farbe;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setFarbe(FarbEnum farbe){
		this.farbe=farbe;
	}
	
	@Override
	public String toString(){
		return "Name: "+getName()+"Farbe: "+getFarbe();
	}
}
