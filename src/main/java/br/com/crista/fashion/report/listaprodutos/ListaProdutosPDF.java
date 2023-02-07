package br.com.crista.fashion.report.listaprodutos;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.MathUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
public class ListaProdutosPDF extends RelatorioBasePDF {

    private final List<ProdutoDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {15f,15f,13f,13f,11f,10f,10f,13f};
    private static final String[] titles = {
            "Nome", "Marca", "Código", "Categoria", "Cor", "Tamanho", "Qtd", "Valor Produto"
    };

    public ListaProdutosPDF(List<ProdutoDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {
        super("Relatório - Lista de Produtos", PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {
        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        Integer total = 0;

        for (ProdutoDTO dto : dados) {
            addCell(table, dto);
            total += dto.getQtd();
        }
        printNovaLinha(table,titles.length);

        printCell(table, total + " produtos em estoque", titles.length);
        addTable(table);

        close();
        return getFileLocation();
    }

    protected void addTableHeader(Document document) {
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        try {
            table.setWidths(columnWidths);
            for (int i = 0; i < titles.length; i++) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(titles[i], FONT_NORMAL));
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

    private void addCell(PdfPTable table, ProdutoDTO dto) {
        table.addCell(newCellNoBorder(dto.getNome() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getMarca() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getCodigo() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getCategoria() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getCor() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumTamanho.valueOf(dto.getTamanho()).getLabel() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getQtd() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getValorCompra()), FONT_NORMAL, Element.ALIGN_CENTER));
    }
}