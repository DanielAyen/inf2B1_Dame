public class Spielbrett {

	/**
	 * 
	 */

	private String[][] Brett = new String[12][12];

	public void sysoBrett() {

		for (int i = 0; i < Brett.length; i++) {
			System.out.println(Brett[i]);

			for (int j = 0; j < Brett[i].length; j++) {
				System.out.println(Brett[i][j]);
			}
		}

	}
}
