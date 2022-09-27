package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.repository.LojaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LojaService extends GenericService<LojaBean, LojaRepository> {

    @Autowired
    private CentralConfig centralConfig;

    @Autowired
    private CidadeService cidadeService;

    public List<LojaBean> findAll(){
        return convertIterableToList(getRepository().findAll());
    }

    public LojaDTO salvar(LojaDTO lojaDTO) {

        LojaBean loja = new LojaBean();
        loja = lojaDTO.converter(loja);
        save(loja);

        LojaDTO dto = new LojaDTO(loja);
        return dto;
    }

    public Page<LojaDTO> pagination(PaginationFilterDTO<LojaDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        LojaDTO filtros = paginationFilter.getFiltros();

        Page<LojaDTO> lojas = getRepository().pagination(filtros.getId(), filtros.getNomeFantasia(), paging);
        if(lojas.hasContent()) {
            return lojas;
        } else {
            return Page.empty();
        }
    }

    public LojaDTO getByIdDTO(Long id) {
       LojaBean loja = getLojaById(id);

       LojaDTO dto = new LojaDTO(loja);
       return dto;
    }

    public LojaDTO update(Long id, LojaDTO dto) {
        LojaBean loja = getLojaById(id);
        loja.setNomeFantasia(dto.getNomeFantasia());
        loja.setTelefone(dto.getTelefone());
        loja.setPessoaContato(dto.getPessoaContato());
        loja.setEmail(dto.getEmail());
        loja.setWhatsapp(dto.getWhatsapp());
        update(loja);

        LojaDTO lojaDTO = new LojaDTO(loja);
        return lojaDTO;
    }

    public LojaBean getLojaById(Long id) {
        return getRepository().findById(id).orElse(null);
    }

    public void delete(Long id) {
        LojaBean loja = getLojaById(id);
        delete(loja);
    }

    public LojaBean lojaByNomeFantasia (String nomeLoja) {
        return getRepository().findLojaByNomeFantasia(nomeLoja);
    }
}
