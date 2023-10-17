package br.com.crista.fashion.report.parcelasclientes;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;

public class ParcelasClientesXLS extends RelatorioBaseXLS {

    private final List<ComprasDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Vencimento", "Categoria", "Parcela", "Valor", "Status", "Pagamento", "Venda"
    };

    public ParcelasClientesXLS(List<ComprasDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {

        super("Relatório - Lista de Parcelas por Clientes", true, diretorio, titles.length);
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

        Integer total = 0;
        BigDecimal soma = BigDecimal.ZERO;
        String nome = null;

        for (ComprasDTO dto : dados) {

            if (isNull(nome)) {

                nome = dto.getNomeCliente();
                printRow(nome + " - " + StringUtils.inserirMascaraCpfCnpj(dto.getCpf()), titles.length -1);
            }

            if (isFalse(nome.equalsIgnoreCase(dto.getNomeCliente()))) {

                printRow(total + " parcela(s) com valor de R$ " + soma + " para o cliente " + nome, titles.length -1);

                soma = BigDecimal.ZERO;
                total = 0;

                printNovaLinha(titles.length -1);
                printNovaLinha(titles.length -1);

                nome = dto.getNomeCliente();
                printRow(nome + " - " + StringUtils.inserirMascaraCpfCnpj(dto.getCpf()), titles.length -1);
            }

            addRow(dto);
            total++;
            soma = soma.add(dto.getValor());
        }

        printRow(total + " parcela(s) com valor de R$ " + soma + " para o cliente " + nome, titles.length -1);

        close();
        return getFileLocation();
    }

    private void addRow(ComprasDTO dto) {

        Row row = createRow();
        createCell(row, 0, DateUtils.getDiaMesAnoPortugues(dto.getVencimento()), STYLE_VALOR);
        createCell(row, 1, EnumCategoria.valueOf(dto.getCategoria()).getLabel(), STYLE_VALOR);
        createCell(row, 2, dto.getNumero().toString(), STYLE_VALOR);
        createCell(row, 3, (nonNull(dto.getValor()) ? dto.getValor().doubleValue() : 0D), STYLE_VALOR);
        createCell(row, 4, EnumStatus.valueOf(dto.getStatus()).getLabel(), STYLE_VALOR);
        createCell(row, 5, EnumTipoPagamento.valueOf(dto.getTipo()).getLabel(), STYLE_VALOR);
        createCell(row, 6, DateUtils.getDiaMesAnoPortugues(dto.getDataVenda()), STYLE_VALOR);
    }
}