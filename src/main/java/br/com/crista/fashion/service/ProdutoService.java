package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ProdutoBean;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProdutoService extends GenericService<ProdutoBean, ProdutoRepository> {

    public List<ProdutoBean> findAll(){
        return convertIterableToList(getRepository().findAll());
    }

    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {
        ProdutoBean produto = new ProdutoBean();
        produto = produtoDTO.converter(produto);
        save(produto);

        ProdutoDTO dto = new ProdutoDTO(produto);
        return dto;
    }

    public Page<ProdutoDTO> pagination(PaginationFilterDTO<ProdutoDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ProdutoDTO filtros = paginationFilter.getFiltros();

        EnumCategoria categoria = null;
        if (filtros.getCategoria() != null) {
            categoria = EnumCategoria.valueOf(filtros.getCategoria());
        }

        EnumTamanho tamanho = null;
        if (filtros.getTamanho() != null) {
            tamanho = EnumTamanho.valueOf(filtros.getTamanho());
        }

        Page<ProdutoDTO> lojas = getRepository().pagination(filtros.getId(), categoria, tamanho, paging);
        if (lojas.hasContent()) {
            return lojas;
        } else {
            return Page.empty();
        }
    }

    public ProdutoDTO getByIdDTO(Long id) {
       ProdutoBean produto = getProdutoById(id);

       ProdutoDTO dto = new ProdutoDTO(produto);
       return dto;
    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        ProdutoBean produto = getProdutoById(id);
        produto.setNome(dto.getNome());
        produto.setTamanho(EnumTamanho.valueOf(dto.getTamanho()));
        produto.setCategoria(EnumCategoria.valueOf(dto.getCategoria()));
        produto.setCor(dto.getCor());
        produto.setCodigo(dto.getCodigo());
        produto.setQtd(dto.getQtd());
        produto.setValorProduto(dto.getValorProduto());
        produto.setValorCompra(dto.getValorCompra());
        update(produto);

        ProdutoDTO produtoDTO = new ProdutoDTO(produto);
        return produtoDTO;
    }

    public ProdutoBean getProdutoById(Long id) {
        return getRepository().findById(id).orElse(null);
    }

    public void delete(Long id) {
        ProdutoBean produto = getProdutoById(id);
        delete(produto);
    }

    public ProdutoBean produtoByNome (String nome) {
        return getRepository().findByProduto(nome);
    }
}
