package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.dto.CalcularVendaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumRole;
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
import java.util.List;

@Slf4j
@Service
public class VendaService extends GenericService<VendaBean, VendaRepository> {

    @Autowired
    ClienteService clienteService;

    @Autowired
    LojaService lojaService;

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
        EnumStatus status = filtros.getStatus() != null ? EnumStatus.valueOf(filtros.getStatus()) : null;

        if (filtros.getCpf() != null && !filtros.getCpf().isEmpty()) {
            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }

        if (filtros.getMarcaId() != null) {
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
        ProdutoBean produto = produtoService.getById(dto.getMarcaId());

        VendaBean venda = new VendaBean();
        venda.setCliente(cliente);
        venda.setProduto(produto);
        venda.setLoja(marca);

        venda.setDataVenda(dto.getDataVenda());
        venda.setStatus(EnumStatus.NAO_PAGA);
        venda.setTipo(EnumTipoPagamento.valueOf(dto.getTipo()));

        venda.setValorProduto(dto.getValorProduto());
        // calcular valorTotal
        venda.setFrete(dto.getFrete());
        venda.setComissao(dto.getComissao());
        venda.setDescontos(dto.getDesconto());
        venda.setQtdParcela(dto.getQtdParcela());

        venda.setDataVenda(Calendar.getInstance());
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
            log.info(DateUtils.getDiaMesAnoPortugues(parcela.getDataVencimento()));

            parcela.setVenda(venda);
            parcelaService.save(parcela);
        }

        produtoService.atualizaProduto(dto.getProdutoId());

        return ResponseEntity.ok().body("Sucesso");
    }

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
                             BigDecimal jurosMora, BigDecimal desconto, EnumTipoPagamento tipoPagamento) {
        parcelaService.pagarParcela(parcelaBean, loja, valorPago, multa, jurosMora, desconto, tipoPagamento, Calendar.getInstance());
    }

}
