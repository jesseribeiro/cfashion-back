package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.ClienteLojaBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.dto.ClienteLojaDTO;
import br.com.crista.fashion.enumeration.EnumStatusCliente;
import br.com.crista.fashion.repository.ClienteLojaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ClienteLojaService extends GenericService<ClienteLojaBean, ClienteLojaRepository> {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ParcelaService parcelaService;

    @Autowired
    LojaService lojaService;

    @Autowired
    LimiteExclusivoService limiteExclusivoService;

    public ResponseEntity inserirClienteLoja(ClienteLojaDTO dto) {
        try {
            ClienteBean clienteBean = clienteService.getById(dto.getClienteId());
            this.inserirClienteLoja(clienteBean, dto.getLojaId());
            return ResponseEntity.ok("Cliente cadastrado na loja com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao tentar cadastrar o cliente na loja.");
        }
    }

    public ClienteLojaBean inserirClienteLoja(ClienteBean cliente, Long lojaId) {
        ClienteLojaBean clienteLoja = findByClienteLoja(cliente.getId(), lojaId);
        if(clienteLoja == null) {
            LojaBean loja = lojaService.getLojaById(lojaId);
            clienteLoja = ClienteLojaBean.builder()
                    .loja(loja)
                    .cliente(cliente)
                    .status(EnumStatusCliente.NORMAL)
                    .dataStatus(Calendar.getInstance())
                    .build();
            save(clienteLoja);
        }
        return clienteLoja;
    }

    public Boolean isExistsClienteNaLoja(Long clienteId, Long lojaId) {
        return getRepository().existsClienteIdAndLojaId(clienteId, lojaId);
    }

    public ClienteLojaBean findByClienteLoja(Long clienteId, Long lojaId) {
        return getRepository().findByClienteIdAndLojaId(clienteId, lojaId);
    }

    public List<ClienteLojaBean> findOutrasLojasCliente(Long clienteId, Long lojaId) {
        return getRepository().findOutrasLojasCliente(clienteId, lojaId);
    }

    public List<LojaBean> findAllLojasCliente(Long clienteId) {
        return getRepository().findAllLojasCliente(clienteId);
    }

    public List<ClienteLojaBean> findByClienteId (Long clienteId) {
        return getRepository().findLojaByClienteId(clienteId);
    }
}
