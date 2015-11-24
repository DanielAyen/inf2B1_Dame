import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * 
 * Die Klasse DatenzugriffPDF erzeugt eine PDF
 *
 */
public class DatenzugriffPDF implements iDatenzugriff {
//	private iMeldung logger;


	/**
	 * speichert / schreibt die PDF
	 * 
	 * @return
	 * @throws IOException
	 */
	@Override
	public void speichern(String dateiname, String dateiende, Object o)
			throws IOException {

		Document doc = new Document();
		try{
		PdfWriter.getInstance(doc, new FileOutputStream("MADN Spiel PDF.pdf"));
		doc.open();

		Paragraph p = new Paragraph ("Mensch ärgere dich nicht - Spielstand \n"); //Ueberschrift in der PDF
		doc.add(p);
		Image screenshot = Image.getInstance("screenshotSpiel.png"); //liest den Screenshot ein
		screenshot.scaleAbsolute(660f, 520f); // setzt Bild auf bestimmte Groesse
		
        doc.add(screenshot);

//		logger.log("PDF wurde erstellt. Projekt bitte refreshen.");
		doc.close();}
		catch(Exception e){
		      e.printStackTrace();}
		

	}

	/**
	 * laedt / liest die PDF
	 */
	@Override
	public Object laden(String dateiname) {
		return dateiname;
	}

}
