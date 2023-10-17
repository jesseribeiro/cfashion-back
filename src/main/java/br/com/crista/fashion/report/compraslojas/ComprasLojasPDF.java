package br.com.crista.fashion.report.compraslojas;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
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

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.MathUtils;

public class ComprasLojasPDF extends RelatorioBasePDF {

    private final List<ComprasDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {20f,20f,20f,20f,20f};
    private static final String[] titles = {
            "Data", "Categoria", "Valor", "Pagamento", "Status"
    };

    public ComprasLojasPDF(List<ComprasDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {

        super("Relatório - Lista de Compras por Lojas", PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {

        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        Integer total = 0;
        BigDecimal soma = BigDecimal.ZERO;
        Long lojaId = null;
        String nomeLoja = null;

        for (ComprasDTO dto : dados) {

            if (isNull(lojaId)) {

                lojaId = dto.getLojaId();
                nomeLoja = dto.getNomeLoja();
                printCell(table, nomeLoja, titles.length);
            }

            if (isFalse(lojaId.equals(dto.getLojaId()))) {

                printCell(table, total + " venda(s) com valor de R$ " + MathUtils.convertBigDecimalToString(soma) + " para a loja " + nomeLoja, titles.length);

                soma = BigDecimal.ZERO;
                total = 0;

                printNovaLinha(table,titles.length);
                printNovaLinha(table,titles.length);

                lojaId = dto.getLojaId();
                nomeLoja = dto.getNomeLoja();
                printCell(table, nomeLoja, titles.length);
            }

            addCell(table, dto);
            total++;
            soma = soma.add(dto.getValor());
        }

        printCell(table, total + " venda(s) com valor de R$ " + MathUtils.convertBigDecimalToString(soma) + " para a loja " + nomeLoja, titles.length);
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

    private void addCell(PdfPTable table, ComprasDTO dto) {

        table.addCell(newCellNoBorder(DateUtils.getDiaMesAnoPortugues(dto.getDataVenda()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumCategoria.valueOf(dto.getCategoria()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getValor()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumTipoPagamento.valueOf(dto.getTipo()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(EnumStatus.valueOf(dto.getStatus()).getLabel(), FONT_NORMAL, Element.ALIGN_CENTER));
    }
}