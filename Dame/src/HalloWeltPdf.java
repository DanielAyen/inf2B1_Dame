import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class HalloWeltPdf {

	public static void main(String[] args)
		throws FileNotFoundException, DocumentException {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("Timmy.pdf"));
			document.open();
			document.add(new Paragraph("Tiiiimmmmy"));
			document.close();
		}

	}


