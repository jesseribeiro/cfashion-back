package br.com.crista.fashion.controller;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesPDF;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesXLS;
import br.com.crista.fashion.report.listaclientes.ListaClientesPDF;
import br.com.crista.fashion.report.listaclientes.ListaClientesXLS;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoPDF;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoXLS;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosPDF;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosXLS;
import br.com.crista.fashion.report.listavendas.ListaVendasPDF;
import br.com.crista.fashion.report.listavendas.ListaVendasXLS;
import br.com.crista.fashion.repository.impl.*;
import br.com.crista.fashion.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelatorioController {

    @Autowired
    CentralConfig centralConfig;

    @Autowired
    ListaProdutosRepositoryImpl listaProdutosRepository;

    @Autowired
    ListaClientesRepositoryImpl listaClientesRepository;

    @Autowired
    ListaMovimentacaoRepositoryImpl listaMovimentacaoRepository;

    @Autowired
    ListaVendasRepositoryImpl listaVendasRepository;

    @Autowired
    ComprasClientesRepositoryImpl comprasClientesRepository;

    @ResponseBody
    @RequestMapping(value = "/lista-produtos", method = RequestMethod.POST)
    public ResponseEntity getListaProdutos(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<ProdutoDTO> dados = listaProdutosRepository.getListaProdutos(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

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

            List<ClienteDTO> dados = listaClientesRepository.getListaClientes(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

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

            List<MovimentacaoDTO> dados = listaMovimentacaoRepository.getListaMovimentacao(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

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
    public ResponseEntity getListaVendas(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<VendaDTO> dados = listaVendasRepository.getListaVendas(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

                switch (filtro.getTipoRel()) {

                    case XLS:
                        ListaVendasXLS reportXLS = new ListaVendasXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);

                    default: // PDF
                        ListaVendasPDF reportPDF = new ListaVendasPDF(dados,
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
    @RequestMapping(value = "/compras-clientes", method = RequestMethod.POST)
    public ResponseEntity getComprasClientes(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<ComprasDTO> dados = comprasClientesRepository.getComprasClientes(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

                switch (filtro.getTipoRel()) {

                    case XLS:
                        ComprasClientesXLS reportXLS = new ComprasClientesXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);

                    default: // PDF
                        ComprasClientesPDF reportPDF = new ComprasClientesPDF(dados,
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