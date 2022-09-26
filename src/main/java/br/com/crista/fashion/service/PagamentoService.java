package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.PagamentoBean;
import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.dto.ClienteLojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.PagamentoDTO;
import br.com.crista.fashion.repository.PagamentoRepository;
import br.com.crista.fashion.repository.impl.PagamentoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService extends GenericService<PagamentoBean, PagamentoRepository> {

    @Autowired
    PagamentoRepositoryImpl pagamentoRepository;

    @Autowired
    UsuarioLogadoService usuarioLogadoService;

    public PagamentoDTO getPagamentoDTO(Long pagamentoId) {
        return new PagamentoDTO(getById(pagamentoId));
    }


    public Page pagination(PaginationFilterDTO<PagamentoDTO> filter) {
        UsuarioBean usuario = usuarioLogadoService.getUsuarioLogado();
        Long lojaId = null;
        return pagamentoRepository.paginationPagamento(filter,lojaId);
    }

    public List<PagamentoDTO> quitacaoDeCompra(ClienteLojaDTO dto) {
        return pagamentoRepository.quitacaoDeCompra(dto);
    }
    public PagamentoBean getById(Long pagamentoId){
        return getRepository().getById(pagamentoId);
    }
}
