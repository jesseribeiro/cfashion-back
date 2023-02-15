package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.MovimentacaoBean;
import br.com.crista.fashion.dto.MovimentacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.MovimentacaoService;
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

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/movimentacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovimentacaoController {

    @Autowired
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
    public ResponseEntity create(@RequestBody @Valid @NotNull MovimentacaoDTO dto) {
        try {
            return ResponseEntity.ok(movimentacaoService.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
