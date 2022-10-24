package br.com.crista.fashion.report.comprasclientes;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.report.RelatorioBasePDF;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class ComprasClientesPDF extends RelatorioBasePDF {

    private final List<ComprasDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final float[] columnWidths = {8f,16f,10f,8f,8f,8f,6f,8f,14f,14f};
    private static final String[] titles = {
            "Data", "Cliente", "CPF", "Valor Compra", "Juros Compra", "Valor Entrada", "Parcelas", "Valor Parcela", "Rede", "Loja"
    };

    public ComprasClientesPDF(List<ComprasDTO> dados, FiltroRelatorioDTO filtro, String diretorio) throws FileNotFoundException, DocumentException {
        super("Relatório - Compras por Clientes "  , PageSize.A4.rotate(), diretorio);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws DocumentException {
        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        Long totalComprasDia = 0L;
        Long totalComprasRelatorio = 0L;
        Long totalParcDia = 0L;
        Long totalParcRelatorio = 0L;
        Long totalJurosDia = 0L;
        Long totalJurosRelatorio = 0L;
        Long totalEntradaDia = 0L;
        Long totalEntradaRelatorio = 0L;
        BigDecimal valorComprasDia = BigDecimal.ZERO;
        BigDecimal valorComprasRelatorio = BigDecimal.ZERO;
        BigDecimal valorParcDia = BigDecimal.ZERO;
        BigDecimal valorParcRelatorio = BigDecimal.ZERO;
        BigDecimal valorJurosDia = BigDecimal.ZERO;
        BigDecimal valorJurosRelatorio = BigDecimal.ZERO;
        BigDecimal valorEntradaDia = BigDecimal.ZERO;
        BigDecimal valorEntradaRelatorio = BigDecimal.ZERO;
        String data = "0";
        String cpf = "";

        /*
        for (ComprasClientesDTO dto : dados) {
            if (!filtro.getTotalizarCliente()) {
                if (data.equalsIgnoreCase("0")) {
                    data = dto.getDataVenda();
                }
                if (!data.equalsIgnoreCase(dto.getDataVenda())) {
                    printCell(table, "Total do dia: " + totalComprasDia + " compra(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorComprasDia), titles.length);
                    if (!valorParcDia.equals(BigDecimal.ZERO)) {
                        printCell(table, "Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorParcDia), titles.length);
                    }
                    if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                        printCell(table, totalJurosDia + " efetuada(s) com juros de R$ " + MathUtils.convertBigDecimalToString(valorJurosDia), titles.length);
                    }
                    if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                        printCell(table, totalEntradaDia + " entrada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorEntradaDia), titles.length);
                    }
                    printNovaLinha(table,titles.length);

                    data = dto.getDataVenda();
                    totalParcRelatorio += totalParcDia;
                    totalJurosRelatorio += totalJurosDia;
                    totalEntradaRelatorio += totalEntradaDia;
                    valorComprasRelatorio = valorComprasRelatorio.add(valorComprasDia);
                    valorParcRelatorio = valorParcRelatorio.add(valorParcDia);
                    valorJurosRelatorio = valorJurosRelatorio.add(valorJurosDia);
                    valorEntradaRelatorio = valorEntradaRelatorio.add(valorEntradaDia);
                    totalComprasDia = 0L;
                    totalParcDia = 0L;
                    totalJurosDia = 0L;
                    totalEntradaDia = 0L;
                    valorComprasDia = BigDecimal.ZERO;
                    valorParcDia = BigDecimal.ZERO;
                    valorJurosDia = BigDecimal.ZERO;
                    valorEntradaDia = BigDecimal.ZERO;
                }
            }
            else {
                if (data.equalsIgnoreCase("0")){
                    data = dto.getNomeCliente();
                    cpf = dto.getCpf();

                    printCell(table, data + "  ( " + cpf + " ) ", titles.length);
                }
                if (!data.equalsIgnoreCase(dto.getNomeCliente())) {
                    if (totalComprasDia > 1) {
                        printCell(table, "Total Cliente: " + totalComprasDia + " compra(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorComprasDia), titles.length);
                        if (!valorParcDia.equals(BigDecimal.ZERO)) {
                            printCell(table, "Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorParcDia), titles.length);
                        }
                        if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                            printCell(table, totalJurosDia + " efetuada(s) com juros de R$ " + MathUtils.convertBigDecimalToString(valorJurosDia), titles.length);
                        }
                        if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                            printCell(table, totalEntradaDia + " entrada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorEntradaDia), titles.length);
                        }
                    }
                    printNovaLinha(table,titles.length);

                    data = dto.getNomeCliente();
                    cpf = dto.getCpf();
                    totalParcRelatorio += totalParcDia;
                    totalJurosRelatorio += totalJurosDia;
                    totalEntradaRelatorio += totalEntradaDia;
                    valorComprasRelatorio = valorComprasRelatorio.add(valorComprasDia);
                    valorParcRelatorio = valorParcRelatorio.add(valorParcDia);
                    valorJurosRelatorio = valorJurosRelatorio.add(valorJurosDia);
                    valorEntradaRelatorio = valorEntradaRelatorio.add(valorEntradaDia);
                    totalComprasDia = 0L;
                    totalParcDia = 0L;
                    totalJurosDia = 0L;
                    totalEntradaDia = 0L;
                    valorComprasDia = BigDecimal.ZERO;
                    valorParcDia = BigDecimal.ZERO;
                    valorJurosDia = BigDecimal.ZERO;
                    valorEntradaDia = BigDecimal.ZERO;

                    printCell(table, data + "  ( " + cpf + " ) ", titles.length);
                }
            }
            totalComprasDia++;
            valorComprasDia = valorComprasDia.add(dto.getValorProduto());
            if (dto.getQtParcela() > 0) {
                totalParcDia++;
                valorParcDia = valorParcDia.add(dto.getValorProduto());
            }
            if (dto.getJurosCompra().compareTo(BigDecimal.ZERO) > 0) {
                totalJurosDia++;
                valorJurosDia = valorJurosDia.add(dto.getJurosCompra());
            }
            if (dto.getValorEntrada().compareTo(BigDecimal.ZERO) > 0) {
                totalEntradaDia++;
                valorEntradaDia = valorEntradaDia.add(dto.getValorEntrada());
            }
            addCell(table, dto);
            totalComprasRelatorio++;
        }
        if (!filtro.getTotalizarCliente()) {
            printCell(table, "Total do dia: " + totalComprasDia + " compra(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorComprasDia), titles.length);
            if (!valorParcDia.equals(BigDecimal.ZERO)) {
                printCell(table, "Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorParcDia), titles.length);
            }
            if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                printCell(table, totalJurosDia + " efetuada(s) com juros de R$ " + MathUtils.convertBigDecimalToString(valorJurosDia), titles.length);
            }
            if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                printCell(table, totalEntradaDia + " entrada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorEntradaDia), titles.length);
            }
        }
        else {
            if (totalComprasDia > 1) {
                printCell(table, "Total Cliente: " + totalComprasDia + " compra(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorComprasDia), titles.length);
                if (!valorParcDia.equals(BigDecimal.ZERO)) {
                    printCell(table, "Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorParcDia), titles.length);
                }
                if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                    printCell(table, totalJurosDia + " efetuada(s) com juros de R$ " + MathUtils.convertBigDecimalToString(valorJurosDia), titles.length);
                }
                if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                    printCell(table, totalEntradaDia + " entrada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorEntradaDia), titles.length);
                }
            }
        }
        printNovaLinha(table,titles.length);

        totalParcRelatorio += totalParcDia;
        totalJurosRelatorio += totalJurosDia;
        totalEntradaRelatorio += totalEntradaDia;
        valorComprasRelatorio = valorComprasRelatorio.add(valorComprasDia);
        valorParcRelatorio = valorParcRelatorio.add(valorParcDia);
        valorJurosRelatorio = valorJurosRelatorio.add(valorJurosDia);
        valorEntradaRelatorio = valorEntradaRelatorio.add(valorEntradaDia);

        printCell(table, "Total Relatório: " + totalComprasRelatorio + " compra(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorComprasRelatorio), titles.length);
        if (!valorParcDia.equals(BigDecimal.ZERO)) {
            printCell(table, "Sendo: " + totalParcRelatorio + " parcelada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorParcRelatorio), titles.length);
        }
        if (!valorJurosDia.equals(BigDecimal.ZERO)) {
            printCell(table, totalJurosRelatorio + " efetuada(s) com juros de R$ " + MathUtils.convertBigDecimalToString(valorJurosRelatorio), titles.length);
        }
        if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
            printCell(table, totalEntradaRelatorio + " entrada(s) no valor de R$ " + MathUtils.convertBigDecimalToString(valorEntradaRelatorio), titles.length);
        }

        addTable(table);
*/
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
    public void addStartPage(Document document) throws DocumentException {
        if (filtro != null) {
            //PdfPTable table = printInfoFiltroSelecionado(filtro);
            //addTable(table);
        }
        addTableHeader(document);
    }

    private void addCell(PdfPTable table, ComprasDTO dto) {
        /*
        table.addCell(newCellNoBorder(dto.getDataVenda() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getNomeCliente() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(StringUtils.inserirMascaraCpfCnpj(dto.getCpf()) + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getValorProduto()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(seZeroRetornaVazio(dto.getJurosCompra()) + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(seZeroRetornaVazio(dto.getValorEntrada()) + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getQtParcela() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder("R$ " + MathUtils.convertBigDecimalToString(dto.getValorParcela()), FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getNomeRede() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        table.addCell(newCellNoBorder(dto.getNomeLoja() + "", FONT_NORMAL, Element.ALIGN_CENTER));

         */
    }
}