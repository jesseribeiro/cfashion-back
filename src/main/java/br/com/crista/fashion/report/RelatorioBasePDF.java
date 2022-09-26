package br.com.crista.fashion.report;

import br.com.crista.fashion.utils.FileUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Slf4j
public abstract class RelatorioBasePDF {

    public static final Font FONT_NORMAL_7 = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL);
    public static final Font FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
    public static final Font FONT_BOLD_7 = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD);
    public static final Font FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    public static final BaseColor ROW_GRAY = new BaseColor(205,205,205, 70);
    public static final BaseColor ROW_BLUE = new BaseColor(0,0,205, 70);
    public static final BaseColor ROW_GRAY_LIGHT = new BaseColor(140,140,140, 70);
    private String titulo;
    private String fileLocation;
    private Rectangle orientacao;
    private Document document;
    private PdfWriter writer;

    public RelatorioBasePDF(String titulo, Rectangle orientacao, String diretorio) throws FileNotFoundException, DocumentException {
        this.titulo = titulo;
        this.orientacao = orientacao;
        this.fileLocation = diretorio + FileUtils.generateCode() + ".pdf";
        this.init(fileLocation);
    }

    public RelatorioBasePDF(String titulo, Rectangle orientacao) throws FileNotFoundException, DocumentException {
        this(titulo, orientacao, "/tmp");
    }

    public RelatorioBasePDF(String titulo) throws FileNotFoundException, DocumentException {
        this(titulo, PageSize.A4.rotate());
    }

    private void init(String fileLocation) throws FileNotFoundException, DocumentException {
        document = new Document(orientacao);
        writer = PdfWriter.getInstance(document, new FileOutputStream(fileLocation));
        writer.setPageEvent(new HeaderFooterPageEvent(this));
        //document.open();
    }

    /**
     * Método responsável por iniciar a impressão do relatorio, é o ponto inicial do relatório
     * @throws DocumentException
     */
    public abstract String print() throws DocumentException;

    /**
     * Método responsável por imprimir algo no ínicio de cada página,
     * se não tiver um padrão para ser impresso em cada página apenas deixar o método em branco
     * @param document
     */
    public abstract void addStartPage(Document document) throws DocumentException;

    public void addTable(PdfPTable table) throws DocumentException {
        this.document.add(table);
    }

    public void newPage() throws DocumentException {
        document.newPage();
    }

    public void close() {
        this.document.close();
    }

    public void open() { this.document.open(); }

    public void printNovaLinha(PdfPTable table, Integer index) {
        PdfPCell cell = new PdfPCell(
                new Paragraph(" "));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(index);
        table.addCell(cell);
    }

    public void printCell(PdfPTable table, String text, Integer index){
        PdfPCell cell = new PdfPCell(
                new Paragraph(text + "", FONT_BOLD));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(index);
        table.addCell(cell);
    }

    public PdfPCell newCellTitle(String texto, Font font, boolean backgound, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        return cell;
    }

    public PdfPCell newCellNoBorder(String texto, Font font, int alignment) {
        return this.newCellNoBorder(texto, font, false, alignment);
    }

    public PdfPCell newCellNoBorder(String texto, Font font, boolean backgound, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        return cell;
    }

    public PdfPCell newCellNoBorder(String texto, Font font, boolean backgound, int alignment, boolean border) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        return cell;
    }

    public PdfPCell newCellNoBorder(String texto, Font font, boolean backgound, int alignment, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        cell.setColspan(colspan);
        return cell;
    }
}