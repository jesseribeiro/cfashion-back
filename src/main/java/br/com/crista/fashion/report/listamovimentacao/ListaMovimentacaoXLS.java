package br.com.crista.fashion.report.listamovimentacao;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.enumeration.EnumMovimentacao;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import br.com.crista.fashion.utils.DateUtils;

public class ListaMovimentacaoXLS extends RelatorioBaseXLS {

    private final List<MovimentacaoDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Data", "Descrição", "Tipo", "Valor"
    };

    public ListaMovimentacaoXLS(List<MovimentacaoDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {

        super("Relatório - Lista de Movimentações", true, diretorio, titles.length);
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

        dados.forEach(this::addRow);

        printNovaLinha(titles.length -1);

        close();
        return getFileLocation();
    }

    private void addRow(MovimentacaoDTO dto) {

        Row row = createRow();
        createCell(row, 0, DateUtils.getDiaMesAnoPortugues(dto.getData()), STYLE_VALOR);
        createCell(row, 1, dto.getDescricao(), STYLE_VALOR);
        createCell(row, 2, EnumMovimentacao.valueOf(dto.getTipo()).getLabel(), STYLE_VALOR);
        createCell(row, 3, (nonNull(dto.getValor()) ? dto.getValor().doubleValue() : 0D), STYLE_VALOR);
    }
}