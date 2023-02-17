package br.com.crista.fashion.report.vendas;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.util.List;

@Slf4j
public class VendasXLS extends RelatorioBaseXLS {

    private final List<VendaDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Categoria", "Data", "Vl Produto", "Tipo", "Frete Pg", "Frete Rec", "Desconto", "Comissão", "Total", "Status"
    };

    public VendasXLS(List<VendaDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {
        super("Relatório - Lista de Vendas", true, diretorio, titles.length);
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

        for (VendaDTO dto : dados){
            addRow(dto);
        }
        printNovaLinha(titles.length -1);


        close();
        return getFileLocation();
    }

    private void addRow(VendaDTO dto) {
        Row row = createRow();
        createCell(row, 0, EnumCategoria.valueOf(dto.getCategoria()).getLabel() + "", STYLE_VALOR);
        createCell(row, 1, DateUtils.getDiaMesAnoPortugues(dto.getData()) + "", STYLE_VALOR);
        createCell(row, 2, "R$ " + MathUtils.convertBigDecimalToString(dto.getVlProduto()), STYLE_VALOR);
        createCell(row, 3, EnumTipoPagamento.valueOf(dto.getTipo()).getLabel() + "", STYLE_VALOR);
        createCell(row, 4, "R$ " + MathUtils.convertBigDecimalToString(dto.getFretePagar()), STYLE_VALOR);
        createCell(row, 5, "R$ " + MathUtils.convertBigDecimalToString(dto.getFreteReceber()), STYLE_VALOR);
        createCell(row, 6, "R$ " + MathUtils.convertBigDecimalToString(dto.getDescontos()), STYLE_VALOR);
        createCell(row, 7, "R$ " + MathUtils.convertBigDecimalToString(dto.getComissao()), STYLE_VALOR);
        createCell(row, 8, "R$ " + MathUtils.convertBigDecimalToString(dto.getVlTotal()), STYLE_VALOR);
        createCell(row, 9, EnumStatus.valueOf(dto.getStatus()).getLabel() + "", STYLE_VALOR);
    }
}