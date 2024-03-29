package br.com.crista.fashion.service;

import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.ProdutoBean;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.repository.ProdutoRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoService extends GenericService<ProdutoBean, ProdutoRepository> {

    private final @NonNull
    LojaService lojaService;

    public List<ProdutoBean> findAll() {

        return convertIterableToList(getRepository().findAll());
    }

    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {

        ProdutoBean produto = new ProdutoBean();
        produto = produtoDTO.converter(produto);

        LojaBean lojaBean = lojaService.getById(produtoDTO.getMarcaId());
        produto.setMarca(lojaBean);

        save(produto);

        return new ProdutoDTO(produto);
    }

    public Page<ProdutoDTO> pagination(PaginationFilterDTO<ProdutoDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ProdutoDTO filtros = paginationFilter.getFiltros();

        Long marcaId = null;

        EnumCategoria categoria = nonNull(filtros.getCategoria()) ? EnumCategoria.valueOf(filtros.getCategoria()) : null;

        EnumTamanho tamanho = nonNull(filtros.getTamanho()) ? EnumTamanho.valueOf(filtros.getTamanho()) : null;

        if (nonNull(filtros.getMarcaId())) {

            LojaBean marca = lojaService.getById(filtros.getMarcaId());
            marcaId = marca.getId();
        }

        Page<ProdutoDTO> lojas = getRepository().pagination(filtros.getId(), marcaId, categoria, tamanho, paging);

        if (lojas.hasContent()) {

            return lojas;
        } else {

            return Page.empty();
        }
    }

    public ProdutoDTO getByIdDTO(Long id) {

       ProdutoBean produto = getProdutoById(id);

        return new ProdutoDTO(produto);
    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {

        ProdutoBean produto = getProdutoById(id);
        produto.setNome(dto.getNome());
        produto.setTamanho(EnumTamanho.valueOf(dto.getTamanho()));
        produto.setCategoria(EnumCategoria.valueOf(dto.getCategoria()));
        produto.setCor(dto.getCor());
        produto.setCodigo(dto.getCodigo());
        produto.setQtd(dto.getQtd());
        produto.setValorCompra(dto.getValorCompra());

        LojaBean lojaBean = lojaService.getById(dto.getMarcaId());
        produto.setMarca(lojaBean);

        save(produto);

        return new ProdutoDTO(produto);
    }

    public ProdutoBean getProdutoById(Long id) {

        return getRepository().findById(id)
                .orElse(null);
    }

    public void delete(Long id) {

        ProdutoBean produto = getProdutoById(id);

        delete(produto);
    }

    public ProdutoBean produtoByNome (String nome) {

        return getRepository().findByProduto(nome);
    }

    public List<String> getCategoriasByMarca (Long marcaId) {

        return getRepository().findCategoriasByMarca(marcaId);
    }

    public List<String> getCodigos (Long marcaId, String categoria) {

        EnumCategoria cat = EnumCategoria.valueOf(categoria);

        return getRepository().findCodigos(marcaId, cat);
    }

    public ProdutoDTO findByCodigo (String codigo) {

        return getRepository().findByCodigo(codigo);
    }

    public void atualizaProduto (Long produtoId) {

        ProdutoBean produto = getById(produtoId);
        Integer qtd = produto.getQtd();
        produto.setQtd(--qtd);

        update(produto);
    }
}
