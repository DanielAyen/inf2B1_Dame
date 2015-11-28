
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class DatenzugriffCSV implements iDatenzugriff {
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	Spiel s;
	Spieler s2;
	Spieler s1;
	Spielbrett brett;

	/**
	 *
	 * @param f
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public Object laden(File f) throws IOException {
		reader = new BufferedReader(new FileReader(f));
		if (reader == null || f == null) {
			throw new RuntimeException("Der Reader ist nicht offen");
		}
		System.out.println("3!");
		try {
			String line;
			FarbEnum farbe;
			boolean istKi;
			int counter = 1;
			//
			// System.out.println("Löschen?");
			// s.allesLoeschen();
			// System.out.println("Gelöscht");

			while ((line = reader.readLine()) != null) {
				System.out.println("Counter: " + counter);
				line = reader.readLine();
				String[] gesplittet = line.split(";");
				String k = line;
				System.out.println(k);
				if (counter == 1) {
					System.out.println("In 1");
					if (gesplittet[0] == "12") {
						Spiel spiel = new Spiel();
						s.aufbauen(12);
					}
					if (gesplittet[0] == "10") {
						Spiel spiel = new Spiel();
						s.aufbauen(10);
					}
					if (gesplittet[0] == "8") {
						Spiel spiel = new Spiel();
						s.aufbauen(8);
					}
				}
				if (counter == 2) {
					System.out.println("In 2");
					if (gesplittet[1] == "SCHWARZ") {
						farbe = FarbEnum.SCHWARZ;
					} else {
						farbe = FarbEnum.WEIß;
					}

					if (gesplittet[2] == "false") {
						istKi = false;
					} else {
						istKi = true;
					}
					s1 = new Spieler(gesplittet[0], farbe, istKi);
					s1.getAlleFiguren().clear();
				}
				if (counter == 3) {
					System.out.println("In 3");
					if (gesplittet[1] == "SCHWARZ") {
						farbe = FarbEnum.SCHWARZ;
					} else {
						farbe = FarbEnum.WEIß;
					}

					if (gesplittet[2] == "false") {
						istKi = false;
					} else {
						istKi = true;
					}
					s2 = new Spieler(gesplittet[0], farbe, istKi);
					s2.getAlleFiguren().clear();
				}
				if (counter == 4) {
					System.out.println("In 4");
					for (int i = 0; i < gesplittet.length; i = i + 4) {

						s1.getAlleFiguren().add(new Spielfigur(FarbEnum.SCHWARZ, false));
						int x = Integer.parseInt(gesplittet[i++]);
						int y = Integer.parseInt(gesplittet[i + 2]);
						boolean dame;
						if (gesplittet[i + 3] == "false") {
							dame = false;
						} else {
							dame = true;
						}

						s1.getAlleFiguren().get(s1.getAlleFiguren().size()).setPosX(x);
						s1.getAlleFiguren().get(s1.getAlleFiguren().size()).setPosY(y);
						s1.getAlleFiguren().get(s1.getAlleFiguren().size()).setDame(dame);
						brett.getBrettFeldIndex(x, y).setSpielfigur(s1.getAlleFiguren().get(s1.getAlleFiguren().size()));
					}
				}
				if (counter == 5) {
					System.out.println("In 5");
					// figurenWeiß
					for (int i = 0; i < gesplittet.length; i = i + 4) {

						s2.getAlleFiguren().add(new Spielfigur(FarbEnum.WEIß, false));
						int x = Integer.parseInt(gesplittet[i++]);
						int y = Integer.parseInt(gesplittet[i + 2]);
						boolean dame;
						if (gesplittet[i + 3] == "false") {
							dame = false;
						} else {
							dame = true;
						}

						s2.getAlleFiguren().get(s2.getAlleFiguren().size()).setPosX(x);
						s2.getAlleFiguren().get(s2.getAlleFiguren().size()).setPosY(y);
						s2.getAlleFiguren().get(s2.getAlleFiguren().size()).setDame(dame);
						brett.getBrettFeldIndex(x, y).setSpielfigur(s2.getAlleFiguren().get(s2.getAlleFiguren().size()));
					}
				}
				if (counter == 6) {
					System.out.println("In 6");
					if (gesplittet[0] == "SCHWARZ") {
						farbe = FarbEnum.SCHWARZ;
					} else {
						farbe = FarbEnum.WEIß;
					}
					s.spielStarten();
					s.setAmZug(farbe);
				}
				counter++;
			}
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
			return null;
		}
		return null;
	}

	public void schliessen(File f) throws IOException {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println("Fehler bei Ein-/Ausgabe: " + e);
		}
		if (writer != null) {
			writer.close();
		}
	}

	@Override
	public void speichern(Object o) throws IOException {

		try {
			writer = new PrintWriter(new FileWriter("speicher.csv"));
			// } catch (Exception e) {
			// // !// System.err.println("Kein String übergeben");}
		} catch (NullPointerException e) {
			// !// throw new IOException("UNerwartetes Dateiende");
		} catch (NumberFormatException e) {
			// !// throw new IOException("Falsches Elementformat ");
		} catch (IndexOutOfBoundsException e) {
			// !// throw new IOException("zu wenig Datenelemente");
		}
		writer.write((String) (o));
		writer.flush();
		writer.close();
	}
}

// public void speichern(String dateiname, String dateiende, Object o) {
// ObjectOutputStream oos = null;
// try {
// oos = new ObjectOutputStream(new FileOutputStream("DameSER.ser"));
// oos.writeObject(o);
// } catch (FileNotFoundException e) {
// // !// System.err.println("konnte 'out.ser' nicht erzeugen");
// } catch (IOException e) {
// // !// System.err.println("Fehler bei der Ein-/Ausgabe" + e);
//
// } finally {
// try {
// oos.flush();
// oos.close();
// } catch (Exception e) {
// // !// System.err.println("Fehler beim Schliessen der Datei");
// }
// }
// }

