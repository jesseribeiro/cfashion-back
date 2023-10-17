package br.com.crista.fashion.report.listaclientes;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.Row;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.report.RelatorioBaseXLS;
import br.com.crista.fashion.utils.StringUtils;

public class ListaClientesXLS extends RelatorioBaseXLS {

    private final List<ClienteDTO> dados;
    FiltroRelatorioDTO filtro;
    private static final String[] titles = {
            "Nome", "CPF", "Celular", "Cidade", "Estado", "Qtd Compras"
    };

    public ListaClientesXLS(List<ClienteDTO> dados, FiltroRelatorioDTO filtro, String diretorio) {

        super("Relatório - Lista de Clientes", true, diretorio, titles.length);
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

        AtomicReference<Integer> total = new AtomicReference<>(0);
        AtomicReference<Integer> clientes = new AtomicReference<>(0);

        dados.forEach(clienteDTO -> {

            addRow(clienteDTO);
            total.updateAndGet(v -> v + clienteDTO.getQtd().intValue());
            clientes.getAndSet(clientes.get() + 1);
        });

        printNovaLinha(titles.length -1);

        printRow(total + " venda(s) para " + clientes + " cliente(s)", titles.length -1);

        close();
        return getFileLocation();
    }

    private void addRow(ClienteDTO dto) {

        Row row = createRow();
        createCell(row, 0, dto.getNome(), STYLE_VALOR);
        createCell(row, 1, StringUtils.inserirMascaraCpfCnpj(dto.getCpf()), STYLE_VALOR);
        createCell(row, 2, dto.getCelular(), STYLE_VALOR);
        createCell(row, 3, dto.getCidade(), STYLE_VALOR);
        createCell(row, 4, dto.getEstado(), STYLE_VALOR);
        createCell(row, 5, dto.getQtd().toString(), STYLE_VALOR);
    }
}