package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ComissaoBean;
import br.com.crista.fashion.dto.ComissaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.repository.ComissaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ComissaoService extends GenericService<ComissaoBean, ComissaoRepository> {

    public List<ComissaoBean> findAll() {

        return convertIterableToList(getRepository().findAll());
    }

    public Page<ComissaoDTO> pagination(PaginationFilterDTO<ComissaoDTO> paginationFilter) {

        Pageable paging = PageRequest.of(0, paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));

        Page<ComissaoDTO> comissao = getRepository().pagination(paging);
        if (comissao.hasContent()) {
            return comissao;
        } else {
            return Page.empty();
        }
    }

    @Transactional
    public void editarComissao(ComissaoDTO dto) {

        ComissaoBean comissaoBean = getById(dto.getId());
        comissaoBean.setComissao(dto.getComissao());
        update(comissaoBean);
    }

    public void adicaoComissao(ComissaoDTO dto) {

        ComissaoBean comissaoBean = new ComissaoBean();
        comissaoBean.setTipo(EnumTipoPagamento.valueOf(dto.getTipo()));
        comissaoBean.setComissao(dto.getComissao());
        save(comissaoBean);

    }

    public BigDecimal pegaComissao(EnumTipoPagamento tipo) {

        return getRepository().findComissao(tipo);
    }
}
