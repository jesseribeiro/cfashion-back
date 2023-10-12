package br.com.crista.fashion.report.listaclientes;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.StringUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.FileNotFoundException;
import java.util.List;

public class ListaClientesPDF extends RelatorioBasePDF {

    private final List<ClienteDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {20f,20f,20f,20f,10f,10f};
    private static final String[] titles = {
            "Nome", "CPF", "Celular", "Cidade", "Estado", "Qtd Compras"
    };

    public ListaClientesPDF(List<ClienteDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {

        super("Relatório - Lista de Clientes", PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {

        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        Integer total = 0;
        Integer clientes = 0;

        for (ClienteDTO dto : dados) {

            addCell(table, dto);
            total += dto.getQtd().intValue();
            clientes++;
        }

        printNovaLinha(table,titles.length);

        printCell(table, total + " venda(s) para " + clientes + " cliente(s)", titles.length);
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

    private void addCell(PdfPTable table, ClienteDTO dto) {

        table.addCell(newCellNoBorder(dto.getNome(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(StringUtils.inserirMascaraCpfCnpj(dto.getCpf()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getCelular(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getCidade(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getEstado(), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getQtd().toString(), FONT_NORMAL, Element.ALIGN_CENTER));
    }
}