package br.com.crista.fashion.report.listamovimentacao;

import java.io.FileNotFoundException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.enumeration.EnumMovimentacao;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.MathUtils;

public class ListaMovimentacaoPDF extends RelatorioBasePDF {

    private final List<MovimentacaoDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {20f,35f,20f,25f};
    private static final String[] titles = {
            "Data", "Descrição", "Tipo", "Valor"
    };

    public ListaMovimentacaoPDF(List<MovimentacaoDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {

        super("Relatório - Lista de Movimentações", PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {

        open();

        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        dados.forEach(movimentacaoDTO -> addCell(table, movimentacaoDTO));

        printNovaLinha(table,titles.length);

        addTable(table);

        close();
        return getFileLocation();
    }

    protected void addTableHeader(Document document) {

        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);

        try {

            table.setWidths(columnWidths);

            for (String title : titles) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(title, FONT_NORMAL));
                header.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                table.addCell(header);
            }

            document.add(table);

        } catch (DocumentException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void addStartPage(Document document) {

        addTableHeader(document);
    }

    private void addCell(PdfPTable table, MovimentacaoDTO dto) {

        table.addCell(newCellNoBorder(DateUtils.getDiaMesAnoPortugues(dto.getData()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getDescricao(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumMovimentacao.valueOf(dto.getTipo()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getValor()), FONT_NORMAL, Element.ALIGN_CENTER));
    }
}