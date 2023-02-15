package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.enumeration.EnumMovimentacao;
import br.com.crista.fashion.repository.MovimentacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovimentacaoService extends GenericService<MovimentacaoBean, MovimentacaoRepository> {

    public List<MovimentacaoBean> findAll(){
        return convertIterableToList(getRepository().findAll());
    }

    public Page<MovimentacaoDTO> pagination(PaginationFilterDTO<MovimentacaoDTO> paginationFilter) {
        Pageable paging = PageRequest.of(0, paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));

        Page<MovimentacaoDTO> movimentacao = getRepository().pagination(paging);
        if (movimentacao.hasContent()) {
            return movimentacao;
        } else {
            return Page.empty();
        }
    }

    public ResponseEntity salvar(MovimentacaoDTO dto) {
        MovimentacaoBean movimentacaoBean = new MovimentacaoBean();
        movimentacaoBean.setTipo(EnumMovimentacao.valueOf(dto.getTipo()));
        movimentacaoBean.setValor(dto.getValor());
        save(movimentacaoBean);
        return ResponseEntity.ok().body(movimentacaoBean);
    }
}
