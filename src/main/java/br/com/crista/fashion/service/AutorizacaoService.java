package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.dto.AutorizacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.enumeration.EnumRecusaVenda;
import br.com.crista.fashion.enumeration.EnumStatusVenda;
import br.com.crista.fashion.repository.AutorizacaoRepository;
import br.com.crista.fashion.repository.impl.AutorizacaoImpl;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;

@Slf4j
@Service
public class AutorizacaoService extends GenericService<AutorizacaoBean, AutorizacaoRepository> {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutorizacaoImpl autorizacaoImpl;

    public Page<AutorizacaoDTO> paginationAutorizacoes(PaginationFilterDTO<AutorizacaoDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        AutorizacaoDTO filtros = paginationFilter.getFiltros();

        EnumStatusVenda enumStatusVenda = filtros.getSituacao() != null ? EnumStatusVenda.valueOf(filtros.getSituacao()) : null;
        Long clienteId = null;
        if(filtros.getCpf() != null && !filtros.getCpf().isEmpty()) {
            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }
        Page<AutorizacaoDTO> autorizacoes;
        Calendar dataInicial = null;
        Calendar dataFinal = null;
        if(filtros.getDataInicial() != null && !filtros.getDataInicial().isEmpty()) {
            dataInicial = DateUtils.getDiaMesAno(filtros.getDataInicial());
        }
        if(filtros.getDataFinal() != null && !filtros.getDataFinal().isEmpty()) {
            dataFinal = DateUtils.getDiaMesAno(filtros.getDataFinal());
        }

        autorizacoes = autorizacaoImpl.paginationAutorizacoes(
                filtros.getRedeId(),
                filtros.getEmpresaId(),
                filtros.getLojaId(),
                clienteId,
                dataInicial,
                dataFinal,
                enumStatusVenda,
                paging);

        if(autorizacoes.hasContent()) {
            return autorizacoes;
        } else {
            return Page.empty();
        }
    }

    public void salvar(VendaBean venda, ClienteBean cliente, LojaBean loja,
                       UsuarioBean vendedor, EnumRecusaVenda motivoRecusa, BigDecimal valorRecusado, BigDecimal limiteCliente) {

        AutorizacaoBean autorizacao = new AutorizacaoBean();
        autorizacao.setCliente(cliente);
        autorizacao.setLoja(loja);

        if (venda != null){
            autorizacao.setVenda(venda);
        }
        if (vendedor != null){
            autorizacao.setVendedor(vendedor);
        }
        if (motivoRecusa != null){
            autorizacao.setMotivoRecusa(motivoRecusa);
        }
        if (valorRecusado != null){
            autorizacao.setValorRecusado(valorRecusado);
        }
        if (limiteCliente != null){
            autorizacao.setLimiteCliente(limiteCliente);
        }

        save(autorizacao);
    }

}
