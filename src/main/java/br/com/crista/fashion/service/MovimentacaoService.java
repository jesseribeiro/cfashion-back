package br.com.crista.fashion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.enumeration.EnumMovimentacao;
import br.com.crista.fashion.repository.MovimentacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovimentacaoService extends GenericService<MovimentacaoBean, MovimentacaoRepository> {

    public List<MovimentacaoBean> findAll() {

        return convertIterableToList(getRepository().findAll());
    }

    public Page<MovimentacaoDTO> pagination(PaginationFilterDTO<MovimentacaoDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));

        Page<MovimentacaoDTO> movimentacao = getRepository().pagination(paging);

        if (movimentacao.hasContent()) {

            return movimentacao;
        } else {

            return Page.empty();
        }
    }

    public void salvar(MovimentacaoDTO dto) {

        MovimentacaoBean movimentacaoBean = new MovimentacaoBean();
        movimentacaoBean.setTipo(EnumMovimentacao.valueOf(dto.getTipo()));
        movimentacaoBean.setValor(dto.getValor());
        movimentacaoBean.setDescricao(dto.getDescricao());
        movimentacaoBean.setDataLancamento(dto.getDataLancamento());

        save(movimentacaoBean);
    }

    public void delete(Long id) {

        MovimentacaoBean movimentacaoBean = getById(id);

        delete(movimentacaoBean);
    }

}
