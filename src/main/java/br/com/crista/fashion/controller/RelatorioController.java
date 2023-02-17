package br.com.crista.fashion.controller;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.report.listaclientes.ListaClientesPDF;
import br.com.crista.fashion.report.listaclientes.ListaClientesXLS;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoPDF;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoXLS;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosPDF;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosXLS;
import br.com.crista.fashion.report.vendas.VendasPDF;
import br.com.crista.fashion.report.vendas.VendasXLS;
import br.com.crista.fashion.repository.impl.ListaClientesRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaMovimentacaoRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaProdutosRepositoryImpl;
import br.com.crista.fashion.repository.impl.VendasRepositoryImpl;
import br.com.crista.fashion.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelatorioController {

    @Autowired
    CentralConfig centralConfig;

    @Autowired
    ListaProdutosRepositoryImpl listaProdutos;

    @Autowired
    ListaClientesRepositoryImpl listaClientes;

    @Autowired
    ListaMovimentacaoRepositoryImpl listaMovimentacao;

    @Autowired
    VendasRepositoryImpl vendas;

    @ResponseBody
    @RequestMapping(value = "/lista-produtos", method = RequestMethod.POST)
    public ResponseEntity getListaProdutos(@RequestBody FiltroRelatorioDTO filtro) {
        try {
            List<ProdutoDTO> dados = listaProdutos.getListaProdutos(filtro);
            if (dados != null && !dados.isEmpty()) {
                switch (filtro.getTipoRel()) {
                    case XLS:
                        ListaProdutosXLS reportXLS = new ListaProdutosXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);
                    default: // PDF
                        ListaProdutosPDF reportPDF = new ListaProdutosPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Nenhum registro foi encontrado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Erro encontrado: " + e.getMessage()));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/lista-clientes", method = RequestMethod.POST)
    public ResponseEntity getListaClientes(@RequestBody FiltroRelatorioDTO filtro) {
        try {
            List<ClienteDTO> dados = listaClientes.getListaClientes(filtro);
            if (dados != null && !dados.isEmpty()) {
                switch (filtro.getTipoRel()) {
                    case XLS:
                        ListaClientesXLS reportXLS = new ListaClientesXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);
                    default: // PDF
                        ListaClientesPDF reportPDF = new ListaClientesPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Nenhum registro foi encontrado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Erro encontrado: " + e.getMessage()));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/lista-movimentacao", method = RequestMethod.POST)
    public ResponseEntity getListaMovimentacao(@RequestBody FiltroRelatorioDTO filtro) {
        try {
            List<MovimentacaoDTO> dados = listaMovimentacao.getListaMovimentacao(filtro);
            if (dados != null && !dados.isEmpty()) {
                switch (filtro.getTipoRel()) {
                    case XLS:
                        ListaMovimentacaoXLS reportXLS = new ListaMovimentacaoXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);
                    default: // PDF
                        ListaMovimentacaoPDF reportPDF = new ListaMovimentacaoPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Nenhum registro foi encontrado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Erro encontrado: " + e.getMessage()));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/lista-vendas", method = RequestMethod.POST)
    public ResponseEntity getVendas(@RequestBody FiltroRelatorioDTO filtro) {
        try {
            List<VendaDTO> dados = vendas.getListaVendas(filtro);
            if (dados != null && !dados.isEmpty()) {
                switch (filtro.getTipoRel()) {
                    case XLS:
                        VendasXLS reportXLS = new VendasXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);
                    default: // PDF
                        VendasPDF reportPDF = new VendasPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Nenhum registro foi encontrado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RespostaErroDTO("Erro encontrado: " + e.getMessage()));
        }
    }
}