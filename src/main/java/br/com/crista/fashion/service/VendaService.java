package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.dto.CalcularVendaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class VendaService extends GenericService<VendaBean, VendaRepository> {

    @Autowired
    ClienteService clienteService;

    @Autowired
    LojaService lojaService;

    @Autowired
    ComissaoService comissaoService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    VendaRepositoryImpl vendaRepository;

    @Autowired
    ParcelaService parcelaService;

    public Page<VendaDTO> pagination(PaginationFilterDTO<VendaDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        VendaDTO filtros = paginationFilter.getFiltros();

        Long clienteId = null;
        Long marcaId = null;

        EnumStatus status = nonNull(filtros.getStatus()) ? EnumStatus.valueOf(filtros.getStatus()) : null;

        if (nonNull(filtros.getCpf()) && !filtros.getCpf().isEmpty()) {

            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }

        if (nonNull(filtros.getMarcaId())) {

            LojaBean marca = lojaService.getById(filtros.getMarcaId());
            marcaId = marca.getId();
        }

        Page<VendaDTO> vendas = vendaRepository.pagination(
                clienteId,
                marcaId,
                status,
                filtros.getDataInicial(),
                filtros.getDataFinal(),
                paging);

        if (vendas.hasContent()) {

            return vendas;
        } else {

            return Page.empty();
        }
    }

    @Transactional
    public ResponseEntity vender(CalcularVendaDTO dto) {

        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean marca = lojaService.getLojaById(dto.getMarcaId());
        ProdutoBean produto = produtoService.getById(dto.getProdutoId());

        VendaBean venda = new VendaBean();
        venda.setCliente(cliente);
        venda.setProduto(produto);
        venda.setLoja(marca);

        venda.setDataVenda(dto.getDataVenda());
        venda.setStatus(EnumStatus.NAO_PAGA);
        venda.setTipo(EnumTipoPagamento.valueOf(dto.getTipo()));

        BigDecimal valor = dto.getValorVenda();
        venda.setValorProduto(dto.getValorProduto());
        venda.setValorTarifa(dto.getValorTarifa());
        venda.setComissao(dto.getComissao());
        venda.setFreteReceber(nonNull(dto.getFreteReceber()) ? dto.getFreteReceber() : BigDecimal.ZERO);
        venda.setFretePagar(nonNull(dto.getFretePagar()) ? dto.getFretePagar() : BigDecimal.ZERO);
        venda.setDescontos(dto.getDesconto());
        venda.setQtdParcela(dto.getQtdParcela());
        venda.setDataVenda(dto.getDataVenda());

        if (nonNull(dto.getComissao())) {

            valor = dto.getValorVenda().subtract(dto.getComissao());
        }

        venda.setValorTotal(valor);
        save(venda);

        Calendar dataVencimento = dto.getDataVenda();

        for (int x = 1; x <= dto.getQtdParcela(); x++) {

            ParcelaBean parcela = new ParcelaBean();
            parcela.setCliente(cliente);
            parcela.setProduto(produto);
            parcela.setLoja(marca);

            parcela.setValorParcela(dto.getValorParcela());
            parcela.setNumero(x);
            parcela.setStatus(EnumStatus.NAO_PAGA);

            if (venda.getTipo() == EnumTipoPagamento.CARTAO_CREDITO) {

                parcela.setDataVencimento(DateUtils.proximoMes(dataVencimento, x));

            } else {

                dataVencimento.add(Calendar.DATE, 1);
                Integer dia = Integer.parseInt(DateUtils.getDia(dataVencimento));

                if (venda.getTipo() == EnumTipoPagamento.MAGALU) {

                    while (dia != 2) {

                        dataVencimento.add(Calendar.DATE, 1);
                        dia = Integer.parseInt(DateUtils.getDia(dataVencimento));
                    }

                } else if (venda.getTipo() == EnumTipoPagamento.AMERICANAS || venda.getTipo() == EnumTipoPagamento.MERCADO_LIVRE) {

                    while (dia != 14) {

                        dataVencimento.add(Calendar.DATE, 1);
                        dia = Integer.parseInt(DateUtils.getDia(dataVencimento));
                    }
                }
                parcela.setDataVencimento(dataVencimento);
            }

            parcela.setVenda(venda);
            parcelaService.save(parcela);
        }

        produtoService.atualizaProduto(dto.getProdutoId());

        return ResponseEntity.ok().body("Sucesso");
    }

    @Transactional
    public void cancelarVenda(Long vendaId) {

        VendaBean venda = getRepository().findById(vendaId).get();
        venda.setStatus(EnumStatus.CANCELADA);
        update(venda);

        parcelaService.updateParcelasCanceladas(venda.getParcelas());
    }

    @Transactional
    public void pagarVenda(Long vendaId) {

        VendaBean venda = getRepository().findById(vendaId).get();
        venda.setStatus(EnumStatus.PAGA);
        update(venda);

        parcelaService.updateParcelasPagas(venda.getParcelas());
    }

    public CalcularVendaDTO calcularFreteDesconto(CalcularVendaDTO dto) {

        BigDecimal valorVenda = dto.getValorProduto();
        valorVenda = valorVenda.add(dto.getFreteReceber());
        valorVenda = valorVenda.subtract(dto.getFretePagar());
        valorVenda = valorVenda.subtract(dto.getDesconto());
        dto.setValorVenda(valorVenda);

        return dto;
    }

    public CalcularVendaDTO calcularParcela(CalcularVendaDTO dto) {

        BigDecimal valorTarifa = BigDecimal.ZERO;

        if (dto.getQtdParcela() == 1) {

            valorTarifa = dto.getValorVenda().multiply(new BigDecimal(0.0449)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        } else if (dto.getQtdParcela() <= 6) {

            valorTarifa = dto.getValorVenda().multiply(new BigDecimal(0.0520)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        } else if (dto.getQtdParcela() > 6) {

            valorTarifa = dto.getValorVenda().multiply(new BigDecimal(0.0570)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }

        dto.setValorTarifa(valorTarifa);
        BigDecimal valorParcela = dto.getValorVenda().subtract(valorTarifa);

        valorParcela = valorParcela.divide(new BigDecimal(dto.getQtdParcela()), 2, BigDecimal.ROUND_HALF_EVEN);
        dto.setValorParcela(valorParcela);

        return dto;
    }

    public CalcularVendaDTO calcularComissao(CalcularVendaDTO dto) {

        EnumTipoPagamento tipo = EnumTipoPagamento.valueOf(dto.getTipo());

        BigDecimal comissao = dto.getValorVenda().multiply(
                comissaoService.pegaComissao(tipo)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        dto.setComissao(comissao);
        return dto;
    }
}
