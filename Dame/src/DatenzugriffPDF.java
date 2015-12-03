import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * Die Klasse DatenzugriffPDF erzeugt eine PDF
 *
 */
public class DatenzugriffPDF implements iDatenzugriff {
	// private iMeldung logger;

	/**
	 * speichert / schreibt die PDF
	 * 
	 * @return
	 * @throws IOException
	 */
	@Override
	public void speichern(Object o, String pfad) throws IOException {

		Document doc = new Document(PageSize.LETTER.rotate());
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(pfad));
			doc.open();

			Paragraph p = new Paragraph("Dame - Spielstand \n"); // Ueberschrift in
																														// der PDF
			doc.add(p);
			Image screenshot = Image.getInstance("screenshotSpiel.png"); // liest den
																																		// Screenshot
																																		// ein
			screenshot.scaleAbsolute(750f, 420f); // setzt Bild auf bestimmte Groesse

			doc.add(screenshot);

			// logger.log("PDF wurde erstellt. Projekt bitte refreshen.");
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * laedt / liest die PDF
	 */
	@Override
	public Object laden(File selectedFile) {
		return selectedFile.getName();
	}

}
