package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.dto.CalcularVendaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.repository.LojaRepository;
import br.com.crista.fashion.repository.ProdutoRepository;
import br.com.crista.fashion.repository.VendaRepository;
import br.com.crista.fashion.repository.impl.VendaRepositoryImpl;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class VendaService extends GenericService<VendaBean, VendaRepository> {

    @Autowired
    ClienteService clienteService;

    @Autowired
    LojaService lojaService;

    @Autowired
    VendaRepositoryImpl vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private UsuarioService usuarioService;

    public Page<VendaDTO> pagination(PaginationFilterDTO<VendaDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        VendaDTO filtros = paginationFilter.getFiltros();

        Long clienteId = null;
        Long marcaId = null;
        EnumStatus status = filtros.getStatus() != null ? EnumStatus.valueOf(filtros.getStatus()) : null;

        if (filtros.getCpf() != null && !filtros.getCpf().isEmpty()) {
            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }

        if (filtros.getMarcaId() != null) {
            LojaBean marca = lojaService.getById(filtros.getMarcaId());
            marcaId = marca.getId();
        }

        Calendar dataInicial = null;
        Calendar dataFinal = null;
        if (filtros.getDataInicial() != null && !filtros.getDataInicial().isEmpty()) {
            dataInicial = DateUtils.getDiaMesAno(filtros.getDataInicial());
        }
        if (filtros.getDataFinal() != null && !filtros.getDataFinal().isEmpty()) {
            dataFinal = DateUtils.getDiaMesAno(filtros.getDataFinal());
        }

        Page<VendaDTO> vendas = vendaRepository.pagination(
                clienteId,
                marcaId,
                status,
                dataInicial,
                dataFinal,
                paging);

        if (vendas.hasContent()) {
            return vendas;
        } else {
            return Page.empty();
        }
    }

    /*
    public List<ParcelaCompraDTO> calcularVenda(CalcularVendaDTO dto) {
        // O que quer dizer primeira compra?
        // R: É primeira compra na rede Pratico o
        // vefiricar se é a primeira compra para utilizar os limites pré aprovados inicial
        // R: Sim, mas isso vai ter q vir la da regra financeira dos campos de concessao de credito
        /**
         * limite této para compras
         *
         * Limite compartilhado por todas as lojas único
         * Limite exclusivo para uma loja especifica (N)
         *
         * O cliente poderá ter dois tipos de limite:
         *  - Limite Compartilhado
         *  - Limite Exclusivo ( serão limites exclusivos por lojas)
         */
/*
        Boolean permitir = false;
        if (dto.getFlagAutorizacao()) {
            permitir = true;
        }

        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean loja = lojaService.getLojaById(dto.getLojaId());

        BigDecimal peEntrada = null;

        BigDecimal valorEntrada = BigDecimal.ZERO; // scoreClienteService.calculaValorEntrada(dto.getVlProduto(), regraGrupoLimiteCreditoDTO);
        if(peEntrada != null && peEntrada.compareTo(BigDecimal.ZERO) > 0) {
            valorEntrada = MathUtils.percentage(dto.getVlProduto(), peEntrada);
        }

        if (getUsuarioLogado().getRoleAtiva() != EnumRole.ADMIN) {
            if (dto.getVlEntrada() != null && dto.getVlEntrada().compareTo(BigDecimal.ZERO) > 0) {
                if (valorEntrada.compareTo(dto.getVlEntrada()) == 1) {
                    dto.setVlEntrada(valorEntrada);
                    if (!permitir) {
                        throw new OperacaoNaoPermitidaException("Valor de entrada não pode ser menor do que R$ " + valorEntrada);
                    }
                } else if (dto.getVlEntrada().compareTo(valorEntrada) == 1) {
                    valorEntrada = dto.getVlEntrada();
                }
            }
        } else if (dto.getVlEntrada() != null) {
            valorEntrada = dto.getVlEntrada();
        }

        BigDecimal tarifaParcela = null;
        return null;
    }
    */

    @Transactional
    public VendaDTO vender(CalcularVendaDTO dto) throws IOException {

        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean loja = lojaService.getLojaById(dto.getLojaId());

        // Persistir a Venda
        VendaBean venda = new VendaBean();
        venda.setCliente(cliente);
        venda.setValorProduto(dto.getVlProduto());
        venda.setDataVenda(Calendar.getInstance());
        save(venda);

        Integer diasPrimeiroVencimento;
        if (dto.getDiasPrimeiraParcela() != null) {
            diasPrimeiroVencimento = dto.getDiasPrimeiraParcela();
        } else {
            diasPrimeiroVencimento = null;
        }
        Integer somaDiasParcelas = diasPrimeiroVencimento;
        Integer diasParaRepasse = null;
        boolean flgPrimeiraParcela = true;
        String dataVencimentoMsg = DateUtils.getDiaMesAnoPortugues(DateUtils.geraDataVencimento(somaDiasParcelas));

        Date dataParcela = DateUtils.convertStringInDate(dataVencimentoMsg);
        Integer mesParcela = 1;

        return null;
    }

    //
    @Transactional
    public void cancelarVenda(Long vendaId) {

        if (getUsuarioLogado().getRoleAtiva() == EnumRole.CREDIARISTA || getUsuarioLogado().getRoleAtiva() == EnumRole.SUPERVISOR ||
                getUsuarioLogado().getRoleAtiva() == EnumRole.ADMIN || getUsuarioLogado().getRoleAtiva() == EnumRole.SUPERVISOR) {

            VendaBean venda = getById(vendaId);
            if (!DateUtils.equalsDate(venda.getDataVenda(), Calendar.getInstance()) &&
                    getUsuarioLogado().getRoleAtiva() != EnumRole.ADMIN &&
                    getUsuarioLogado().getRoleAtiva() != EnumRole.SUPERVISOR) {
                throw new RuntimeException("Venda não pode ser cancelada. Não é permitido cancelar venda realizadas em outro dia.");
            }

            venda.setUsuarioExcluiu(getUsuarioLogado());
            update(venda);

            List<ParcelaBean> parcelas = null;

            for (ParcelaBean parcela : parcelas) {
                parcelaService.update(parcela);
            }
        }
    }

    public void pagarParcela(ParcelaBean parcelaBean, LojaBean loja, BigDecimal valorPago, BigDecimal multa,
                             BigDecimal jurosMora, BigDecimal desconto, TipoFormaPagamento tipoFormaPagamento, EnumTipoPagamento tipoPagamento, EnumBanco banco) {
        parcelaService.pagarParcela(parcelaBean, loja, valorPago, multa, jurosMora, desconto, tipoFormaPagamento, tipoPagamento, banco, Calendar.getInstance());
    }
}
