package br.com.crista.fashion.report.listavendas;

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
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.MathUtils;

public class ListaVendasPDF extends RelatorioBasePDF {

    private final List<VendaDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {10f,10f,10f,10f,10f,10f,10f,10f,10f,10f};
    private static final String[] titles = {
            "Categoria", "Data", "Vl Produto", "Tipo", "Frete Pg", "Frete Rec", "Desconto", "Comissão", "Total", "Status"
    };

    public ListaVendasPDF(List<VendaDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {

        super("Relatório - Lista de Vendas", PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {

        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        dados.forEach(vendaDTO -> addCell(table, vendaDTO));

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

    private void addCell(PdfPTable table, VendaDTO dto) {

        table.addCell(newCellNoBorder(EnumCategoria.valueOf(dto.getCategoria()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(DateUtils.getDiaMesAnoPortugues(dto.getData()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getVlProduto()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumTipoPagamento.valueOf(dto.getTipo()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getFretePagar()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getFreteReceber()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getDescontos()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getComissao()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getVlTotal()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumStatus.valueOf(dto.getStatus()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
    }
}