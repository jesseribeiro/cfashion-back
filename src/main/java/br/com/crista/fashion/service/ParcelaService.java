package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.repository.ParcelaRepository;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ParcelaService extends GenericService<ParcelaBean, ParcelaRepository> {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private CarneService carneService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private ClienteService clienteService;

    public Long findParcelaNaoPagaComMaiorAtrasoDoCliente(Long clienteId) {
        return getRepository().findParcelaNaoPagaComMaiorAtrasoDoCliente(clienteId, Calendar.getInstance());
    }

    public List<ParcelaBean> findParcelasByCarneId(Long carneId) {
        return getRepository().findParcelasByCarneId(carneId);
    }

    public boolean existParcelaNaoPagaDoCarne(Long carneId) {
        return getRepository().existParcelaNaoPagaDoCarne(carneId);
    }

    public boolean existParcelaAtrasada(Long clienteId) {
        return getRepository().existParcelaAtrasada(clienteId, Calendar.getInstance());
    }

    @Transactional
    public ParcelaBean persistParcelaEntrada(BigDecimal valorEntrada, Calendar dataVencimento, CarneBean carne, LojaBean loja, boolean flgPagar, EnumTipoPagamento tipoPagamento, Calendar dataRepasse) {
        if (valorEntrada != null && valorEntrada.compareTo(BigDecimal.ZERO) != 0) {
            ParcelaBean parcelaEntrada = new ParcelaBean();
            parcelaEntrada.setNumero(Constants.PARCELA_ENTRADA);
            parcelaEntrada.setValor(valorEntrada);
            if(dataRepasse != null) {
                parcelaEntrada.setValorRepasse(valorEntrada);
                parcelaEntrada.setDataParaRepasse(dataRepasse);
            }
            parcelaEntrada.setCarne(carne);
            parcelaEntrada.setDataVencimento(dataVencimento);
            parcelaEntrada.setStatus(EnumStatusParcela.NAO_PAGA);
            parcelaEntrada.setVlJuros(BigDecimal.ZERO);
            parcelaEntrada.setVlTarifa(BigDecimal.ZERO);
            parcelaEntrada.setVlParcelaSemTaxaJuros(valorEntrada);
            save(parcelaEntrada);
            if (flgPagar) {
                pagarParcela(parcelaEntrada, loja, parcelaEntrada.getValor(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, carne.getFormaPagamento(), tipoPagamento, null, Calendar.getInstance());
            }
            return parcelaEntrada;
        }
        return null;
    }

    @Transactional
    public void pagarParcela(ParcelaBean parcelaBean, LojaBean loja, BigDecimal valorPago, BigDecimal multa, BigDecimal jurosMora,
                             BigDecimal desconto, TipoFormaPagamento tipoFormaPagamento, EnumTipoPagamento tipoPagamento,
                             EnumBanco banco, Calendar dataPagamento) {
        parcelaBean.setStatus(EnumStatusParcela.PAGA);
        save(parcelaBean);

        CarneBean carneCompra = parcelaBean.getCarne();
        if (carneCompra != null && carneCompra.getStatus() != EnumStatusCarne.CANCELADO) {
            if (!existParcelaNaoPagaDoCarne(carneCompra.getId())) {
                carneCompra.setStatus(EnumStatusCarne.PAGO);
                carneService.update(carneCompra);
            }
        }

        ClienteBean cliente;
        if (carneCompra.getVenda() != null) {
            cliente = carneCompra.getVenda().getCliente();
        }

        // persistir o pagamento
        PagamentoBean pagamento = new PagamentoBean();
        pagamento.setTipoPagamento(tipoPagamento);
        if (tipoPagamento == EnumTipoPagamento.BANCO) {
            pagamento.setBanco(banco);
        }
        if (tipoPagamento == EnumTipoPagamento.LOJA) {
            pagamento.setLoja(loja);
        }

        pagamento.setDataPagto(dataPagamento);
        Long diferencaDatas = DateUtils.diffDateInDays(parcelaBean.getDataVencimento(), dataPagamento);
        if (diferencaDatas < 0L) {
            diferencaDatas = 0L;
        }
        pagamento.setDiferencaDias(diferencaDatas.intValue());
        pagamento.setParcela(parcelaBean);
        pagamento.setTipo(tipoFormaPagamento);
        pagamento.setMulta(multa);
        pagamento.setJurosMora(jurosMora);
        pagamento.setDesconto(desconto);
        pagamento.setValorPago(valorPago);
        pagamentoService.save(pagamento);
    }

    public void updateStatusParcelasByIds(EnumStatusParcela status, List<Long> parcelasIds) {
        getRepository().updateStatusParcelasByIds(status, parcelasIds);
    }

    @Transactional
    public void baixarParcelasRenegociada(List<ParcelaBean> parcelas) {
        for (ParcelaBean parcela : parcelas) {
            if (EnumStatusParcela.NAO_PAGA.equals(parcela.getStatus())) {
                parcela.setStatus(EnumStatusParcela.RENEGOCIADA);
                update(parcela);
            }
        }
    }

    @Transactional
    public void cancelarRenegociacao(List<ParcelaBean> parcelas) {
        for (ParcelaBean parcela : parcelas) {
            if (EnumStatusParcela.RENEGOCIADA.equals(parcela.getStatus())) {
                parcela.setStatus(EnumStatusParcela.NAO_PAGA);
                update(parcela);
            }
        }
    }

    @Transactional
    public void cancelarParcelas(List<ParcelaBean> parcelas) {
        for (ParcelaBean parcela : parcelas) {
            cancelarParcela(parcela);
        }
    }

    @Transactional
    public void cancelarParcela(ParcelaBean parcela) {
        if (!EnumStatusParcela.PAGA.equals(parcela.getStatus())) {
            parcela.setStatus(EnumStatusParcela.CANCELADA);
            update(parcela);
        }
    }
}
