package br.com.crista.fashion.report.listaprodutos;

import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class ListaProdutosXLS extends RelatorioBaseXLS {

    private final List<ProdutoDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Nome", "Marca", "Código", "Categoria", "Cor", "Tamanho", "Qtd", "Valor Produto"
    };

    public ListaProdutosXLS(List<ProdutoDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {

        super("Relatório - Lista de Produtos", true, diretorio, titles.length);
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

        for (ProdutoDTO dto : dados){

            addRow(dto);
            total += dto.getQtd();
        }

        printNovaLinha(titles.length -1);

        printRow(total + " produtos em estoque", titles.length -1);

        close();
        return getFileLocation();
    }

    private void addRow(ProdutoDTO dto) {

        Row row = createRow();
        createCell(row, 0, dto.getNome(), STYLE_VALOR);
        createCell(row, 1, dto.getMarca(), STYLE_VALOR);
        createCell(row, 2, dto.getCodigo(), STYLE_VALOR);
        createCell(row, 3, EnumCategoria.valueOf(dto.getCategoria()).getLabel(), STYLE_VALOR);
        createCell(row, 4, dto.getCor(), STYLE_VALOR);
        createCell(row, 5, EnumTamanho.valueOf(dto.getTamanho()).getLabel(), STYLE_VALOR);
        createCell(row, 6, dto.getQtd().toString(), STYLE_VALOR);
        createCell(row, 7, (nonNull(dto.getValorCompra()) ? dto.getValorCompra().doubleValue() : 0D), STYLE_VALOR);
    }
}