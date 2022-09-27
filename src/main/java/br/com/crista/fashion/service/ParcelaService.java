package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ParcelaBean;
import br.com.crista.fashion.enumeration.EnumBanco;
import br.com.crista.fashion.enumeration.EnumStatusParcela;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.enumeration.TipoFormaPagamento;
import br.com.crista.fashion.repository.ParcelaRepository;
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
    private LojaService lojaService;

    @Autowired
    private ClienteService clienteService;

    @Transactional
    public void pagarParcela(ParcelaBean parcelaBean, LojaBean loja, BigDecimal valorPago, BigDecimal multa, BigDecimal jurosMora,
                             BigDecimal desconto, TipoFormaPagamento tipoFormaPagamento, EnumTipoPagamento tipoPagamento,
                             EnumBanco banco, Calendar dataPagamento) {
        parcelaBean.setStatus(EnumStatusParcela.PAGA);
        save(parcelaBean);
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
