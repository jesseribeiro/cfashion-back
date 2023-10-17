package br.com.crista.fashion.controller;

import static br.com.crista.fashion.utils.Constants.ERRO_ENCONTRADO;
import static br.com.crista.fashion.utils.Constants.SEM_REGISTRO;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.ComprasDTO;
import br.com.crista.fashion.dto.FiltroRelatorioDTO;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.dto.RespostaErroDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesPDF;
import br.com.crista.fashion.report.comprasclientes.ComprasClientesXLS;
import br.com.crista.fashion.report.compraslojas.ComprasLojasPDF;
import br.com.crista.fashion.report.compraslojas.ComprasLojasXLS;
import br.com.crista.fashion.report.comprasorigem.ComprasOrigemPDF;
import br.com.crista.fashion.report.comprasorigem.ComprasOrigemXLS;
import br.com.crista.fashion.report.listaclientes.ListaClientesPDF;
import br.com.crista.fashion.report.listaclientes.ListaClientesXLS;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoPDF;
import br.com.crista.fashion.report.listamovimentacao.ListaMovimentacaoXLS;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosPDF;
import br.com.crista.fashion.report.listaprodutos.ListaProdutosXLS;
import br.com.crista.fashion.report.listavendas.ListaVendasPDF;
import br.com.crista.fashion.report.listavendas.ListaVendasXLS;
import br.com.crista.fashion.report.parcelasclientes.ParcelasClientesPDF;
import br.com.crista.fashion.report.parcelasclientes.ParcelasClientesXLS;
import br.com.crista.fashion.repository.impl.ComprasClientesRepositoryImpl;
import br.com.crista.fashion.repository.impl.ComprasLojasRepositoryImpl;
import br.com.crista.fashion.repository.impl.ComprasOrigemRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaClientesRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaMovimentacaoRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaProdutosRepositoryImpl;
import br.com.crista.fashion.repository.impl.ListaVendasRepositoryImpl;
import br.com.crista.fashion.repository.impl.ParcelasClientesRepositoryImpl;
import br.com.crista.fashion.utils.FileUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelatorioController {

    private final @NonNull
    CentralConfig centralConfig;

    private final @NonNull
    ListaProdutosRepositoryImpl listaProdutosRepository;

    private final @NonNull
    ListaClientesRepositoryImpl listaClientesRepository;

    private final @NonNull
    ListaMovimentacaoRepositoryImpl listaMovimentacaoRepository;

    private final @NonNull
    ListaVendasRepositoryImpl listaVendasRepository;

    private final @NonNull
    ComprasClientesRepositoryImpl comprasClientesRepository;

    private final @NonNull
    ComprasLojasRepositoryImpl comprasLojasRepository;

    private final @NonNull
    ComprasOrigemRepositoryImpl comprasOrigemRepository;

    private final @NonNull
    ParcelasClientesRepositoryImpl parcelasClientesRepository;

    @ResponseBody
    @PostMapping(value = "/lista-produtos")
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

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/lista-clientes")
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

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/lista-movimentacao")
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

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/lista-vendas")
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

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/compras-clientes")
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

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/compras-lojas")
    public ResponseEntity getComprasLojas(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<ComprasDTO> dados = comprasLojasRepository.getComprasLojas(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

                switch (filtro.getTipoRel()) {

                    case XLS:
                        ComprasLojasXLS reportXLS = new ComprasLojasXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);

                    default: // PDF
                        ComprasLojasPDF reportPDF = new ComprasLojasPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/compras-origem")
    public ResponseEntity getComprasOrigem(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<ComprasDTO> dados = comprasOrigemRepository.getComprasOrigem(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

                switch (filtro.getTipoRel()) {

                    case XLS:
                        ComprasOrigemXLS reportXLS = new ComprasOrigemXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);

                    default: // PDF
                        ComprasOrigemPDF reportPDF = new ComprasOrigemPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/parcelas-clientes")
    public ResponseEntity getParcelasClientes(@RequestBody FiltroRelatorioDTO filtro) {

        try {

            List<ComprasDTO> dados = parcelasClientesRepository.getParcelasClientes(filtro);

            if (nonNull(dados) && !dados.isEmpty()) {

                switch (filtro.getTipoRel()) {

                    case XLS:
                        ParcelasClientesXLS reportXLS = new ParcelasClientesXLS(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportXLS.print(), false);

                    default: // PDF
                        ParcelasClientesPDF reportPDF = new ParcelasClientesPDF(dados,
                                filtro, centralConfig.getCentralTempPath());
                        return FileUtils.getFile(reportPDF.print(), true);
                }
            }

            return ResponseEntity.badRequest().body(new RespostaErroDTO(SEM_REGISTRO));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new RespostaErroDTO(ERRO_ENCONTRADO + e.getMessage()));
        }
    }
}