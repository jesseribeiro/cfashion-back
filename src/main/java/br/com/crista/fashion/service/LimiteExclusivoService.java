package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.LimiteExclusivoBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.repository.LimiteExclusivoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LimiteExclusivoService extends GenericService<LimiteExclusivoBean, LimiteExclusivoRepository> {

    public LimiteExclusivoBean addOrUpdateLimiteParaCliente(ClienteBean cliente, LojaBean loja, BigDecimal limite) {
        if (limite == null || BigDecimal.ZERO.compareTo(limite) == 0) {
            return null;
        }
        LimiteExclusivoBean limiteExclusivo = getLimiteClienteByLoja(cliente.getId(), loja.getId());
        if(limiteExclusivo == null) {
            limiteExclusivo = new LimiteExclusivoBean();
            limiteExclusivo.setCliente(cliente);
            limiteExclusivo.setLoja(loja);
            limiteExclusivo.setLimite(limite);
            save(limiteExclusivo);
        } else {
            // só atualiza o limite no caso de ter alterado
            if(limiteExclusivo.getLimite().compareTo(limite) != 0) {
                limiteExclusivo.setLimite(limite);
                update(limiteExclusivo);
            }
        }

        return limiteExclusivo;
    }

    public LimiteExclusivoBean getLimiteClienteByLoja(Long clienteId, Long lojaId) {
        return getRepository().findLimiteClienteByLoja(clienteId, lojaId).orElse(null);
    }

    public List<LimiteExclusivoBean> findAllLimiteCliente(Long clienteId) {
        return getRepository().findAllLimiteByClienteId(clienteId);
    }

    public List<LimiteExclusivoBean> findAllLimiteLoja(Long lojaId) {
        return getRepository().findAllLimiteByLojaId(lojaId);
    }
}
