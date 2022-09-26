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
public abstract class RelatorioBaseCarnePDF {

    public static final Font FONT_NORMAL = FontFactory.getFont(FontFactory.COURIER, 7, Font.NORMAL);
    public static final Font FONT_BOLD = FontFactory.getFont(FontFactory.COURIER, 7, Font.BOLD);
    public static final BaseColor ROW_GRAY = new BaseColor(205,205,205, 70);
    private String titulo;
    private String fileLocation;
    private Rectangle orientacao;
    private Document document;
    private PdfWriter writer;

    public RelatorioBaseCarnePDF(String titulo, Rectangle orientacao, String diretorio) throws FileNotFoundException, DocumentException {
        this.titulo = titulo;
        this.orientacao = orientacao;
        this.fileLocation = diretorio + FileUtils.generateCode() + ".pdf";
        this.init(fileLocation);
    }

    public RelatorioBaseCarnePDF(String titulo, Rectangle orientacao) throws FileNotFoundException, DocumentException {
        this(titulo, orientacao, "/tmp");
    }

    public RelatorioBaseCarnePDF(String titulo) throws FileNotFoundException, DocumentException {
        this(titulo, PageSize.A5.rotate());
    }

    private void init(String fileLocation) throws FileNotFoundException, DocumentException {
        document = new Document(orientacao);
        writer = PdfWriter.getInstance(document, new FileOutputStream(fileLocation));
        writer.setPageEvent(new HeaderFooterPageEventCarne(this));
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
    public abstract void addStartPage(Document document);

    public void addTable(PdfPTable table) throws DocumentException {
        this.document.add(table);
    }

    public void printNovaLinha(PdfPTable table, Integer tam){
        PdfPCell cell = new PdfPCell(
                new Paragraph(" "));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(tam);
        table.addCell(cell);
    }

    public void close() {
        this.document.close();
    }

    public void open() { this.document.open(); }
}


