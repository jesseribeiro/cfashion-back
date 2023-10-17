package br.com.crista.fashion.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.MovimentacaoService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/movimentacao", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovimentacaoController {

    private final @NonNull
    MovimentacaoService movimentacaoService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAll() {

        List<MovimentacaoBean> movimentacao = movimentacaoService.findAll();

        return new ResponseEntity<>(movimentacao, HttpStatus.OK);
    }

    @PostMapping(path = "/pagination")
    public Page<MovimentacaoDTO> pagination(@RequestBody PaginationFilterDTO<MovimentacaoDTO> paginationFilter) {

        return movimentacaoService.pagination(paginationFilter);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull MovimentacaoDTO movimentacaoDTO) {

        try {

            movimentacaoService.salvar(movimentacaoDTO);

            return ResponseEntity.ok("Movimentação inserida com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        try {

            movimentacaoService.delete(id);

            return ResponseEntity.ok("Movimentação excluída com sucesso!");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
