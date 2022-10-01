package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.ProdutoBean;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ProdutoDTO;
import br.com.crista.fashion.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/produto", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull ProdutoDTO produtoDTO) {
        try {
            return ResponseEntity.ok(produtoService.salvar(produtoDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAll() {
        List<ProdutoBean> produtos = produtoService.findAll();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @PostMapping(path = "/pagination")
    public Page<ProdutoDTO> pagination(@RequestBody PaginationFilterDTO<ProdutoDTO> paginationFilter) {
        return produtoService.pagination(paginationFilter);
    }

    @GetMapping(path = "/{id}")
    public ProdutoDTO getById(@PathVariable("id") Long id) {
        return produtoService.getByIdDTO(id);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull ProdutoDTO dto) {
        try {
            return ResponseEntity.ok(produtoService.update(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            produtoService.delete(id);
            return ResponseEntity.ok("Loja excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/categorias/{id}")
    public List<String> getAllCategoriasByMarca(@PathVariable("id") Long id) {
        return produtoService.getCategoriasByMarca(id);
    }

    @PostMapping(path = "/{id}/codigos//{categoria}")
    public List<String> getAllCodigos(@PathVariable("id") Long id, @PathVariable("categoria") String categoria) {
        return produtoService.getCodigos(id, categoria);
    }

    @PostMapping(path = "/codigo/{codigo}")
    public ResponseEntity getProduto(@PathVariable("codigo") String codigo) {
        try {
            return ResponseEntity.ok(produtoService.findByCodigo(codigo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
