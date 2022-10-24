package br.com.crista.fashion.report.comprasclientes;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class ComprasClientesXLS extends RelatorioBaseXLS {

    private final List<ComprasDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Data", "Cliente", "CPF", "Valor Compra", "Juros Compra", "Valor Entrada", "Parcelas", "Valor Parcela", "Rede", "Loja"
    };

    public ComprasClientesXLS(List<ComprasDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {
        super("Relatório - Compras por Clientes", true, diretorio, titles.length);
        this.dados = dados;
        this.filtro = filtro;
    }

    public String print() throws IOException {
        Row nomeRelatorioRow = createRow();
        createCell(nomeRelatorioRow, 0, getTitulo(), STYLE_TITLE, titles.length - 1);

        //header row
        Row headerRow = createRow();
        headerRow.setHeightInPoints(40);
        for (int i = 0; i < titles.length; i++) {
            createCell(headerRow, i, titles[i], STYLE_HEADER);
        }

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
        for (ComprasDTO dto : dados){
            if (!filtro.getTotalizarCliente()) {
                if (data.equalsIgnoreCase("0")) {
                    data = dto.getDataVenda();
                }
                if (!data.equalsIgnoreCase(dto.getDataVenda())) {
                    printRow("Total do dia: " + totalComprasDia + " compra(s) no valor de R$ " + printStringDouble(valorComprasDia), 4);
                    if (!valorParcDia.equals(BigDecimal.ZERO)) {
                        printRow("Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + printStringDouble(valorParcDia), 4);
                    }
                    if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                        printRow(totalJurosDia + " efetuada(s) com juros de R$ " + printStringDouble(valorJurosDia), 4);
                    }
                    if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                        printRow(totalEntradaDia + " entrada(s) no valor de R$ " + printStringDouble(valorEntradaDia), 4);
                    }
                    printNovaLinha(titles.length);

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
                    printRow(data + "  ( " + cpf + " ) ", titles.length);
                }
                if (!data.equalsIgnoreCase(dto.getNomeCliente())) {
                    if (totalComprasDia > 1) {
                        printRow("Total Cliente: " + totalComprasDia + " compra(s) no valor de R$ " + printStringDouble(valorComprasDia), 4);
                        if (!valorParcDia.equals(BigDecimal.ZERO)) {
                            printRow("Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + printStringDouble(valorParcDia), 4);
                        }
                        if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                            printRow(totalJurosDia + " efetuada(s) com juros de R$ " + printStringDouble(valorJurosDia), 4);
                        }
                        if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                            printRow(totalEntradaDia + " entrada(s) no valor de R$ " + printStringDouble(valorEntradaDia), 4);
                        }
                    }
                    printNovaLinha(titles.length);

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

                    printRow(data + "  ( " + cpf + " ) ", titles.length);
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
            addRow(dto);
            totalComprasRelatorio++;
        }
        if (!filtro.getTotalizarCliente()) {
            printRow("Total do dia: " + totalComprasDia + " compra(s) no valor de R$ " + printStringDouble(valorComprasDia), 4);
            if (!valorParcDia.equals(BigDecimal.ZERO)) {
                printRow("Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + printStringDouble(valorParcDia), 4);
            }
            if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                printRow(totalJurosDia + " efetuada(s) com juros de R$ " + printStringDouble(valorJurosDia), 4);
            }
            if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                printRow(totalEntradaDia + " entrada(s) no valor de R$ " + printStringDouble(valorEntradaDia), 4);
            }
        }
        else {
            if (totalComprasDia > 1) {
                printRow("Total Cliente: " + totalComprasDia + " compra(s) no valor de R$ " + printStringDouble(valorComprasDia), 4);
                if (!valorParcDia.equals(BigDecimal.ZERO)) {
                    printRow("Sendo: " + totalParcDia + " parcelada(s) no valor de R$ " + printStringDouble(valorParcDia), 4);
                }
                if (!valorJurosDia.equals(BigDecimal.ZERO)) {
                    printRow(totalJurosDia + " efetuada(s) com juros de R$ " + printStringDouble(valorJurosDia), 4);
                }
                if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
                    printRow(totalEntradaDia + " entrada(s) no valor de R$ " + printStringDouble(valorEntradaDia), 4);
                }
            }
        }
        printNovaLinha(titles.length);

        totalParcRelatorio += totalParcDia;
        totalJurosRelatorio += totalJurosDia;
        totalEntradaRelatorio += totalEntradaDia;
        valorComprasRelatorio = valorComprasRelatorio.add(valorComprasDia);
        valorParcRelatorio = valorParcRelatorio.add(valorParcDia);
        valorJurosRelatorio = valorJurosRelatorio.add(valorJurosDia);
        valorEntradaRelatorio = valorEntradaRelatorio.add(valorEntradaDia);

        printRow("Total Relatório: " + totalComprasRelatorio + " compra(s) no valor de R$ " + printStringDouble(valorComprasRelatorio), 4);
        if (!valorParcDia.equals(BigDecimal.ZERO)) {
            printRow("Sendo: " + totalParcRelatorio + " parcelada(s) no valor de R$ " + printStringDouble(valorParcRelatorio), 4);
        }
        if (!valorJurosDia.equals(BigDecimal.ZERO)) {
            printRow(totalJurosRelatorio + " efetuada(s) com juros de R$ " + printStringDouble(valorJurosRelatorio), 4);
        }
        if (!valorEntradaDia.equals(BigDecimal.ZERO)) {
            printRow(totalEntradaRelatorio + " entrada(s) no valor de R$ " + printStringDouble(valorEntradaRelatorio), 4);
        }

         */

        close();
        return getFileLocation();
    }

    private void addRow(ComprasDTO dto) {
        /*
        Row row = createRow();
        createCell(row, 0, dto.getDataVenda() + "", STYLE_VALOR);
        createCell(row, 1, dto.getNomeCliente() + "", STYLE_VALOR);
        createCell(row, 2, StringUtils.inserirMascaraCpfCnpj(dto.getCpf()) + "", STYLE_VALOR);
        createCell(row, 3, (dto.getValorProduto() != null ? dto.getValorProduto().doubleValue() : 0D), STYLE_VALOR);
        createCell(row, 4, seZeroRetornaVazio(dto.getJurosCompra()) + "", STYLE_VALOR);
        createCell(row, 5, seZeroRetornaVazio(dto.getValorEntrada()) + "", STYLE_VALOR);
        createCell(row, 6, dto.getQtParcela() + "", STYLE_VALOR);
        createCell(row, 7, (dto.getValorParcela() != null ? dto.getValorParcela().doubleValue() : 0D), STYLE_VALOR);
        createCell(row, 8, dto.getNomeRede() + "", STYLE_VALOR);
        createCell(row, 9, dto.getNomeLoja() + "", STYLE_VALOR);

         */
    }
}