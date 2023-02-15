package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.ComissaoBean;
import br.com.crista.fashion.dto.ComissaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.ComissaoService;
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
@RequestMapping(path = "/v1/comissao", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComissaoController {

    @Autowired
    ComissaoService comissaoService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAll() {
        List<ComissaoBean> comissao = comissaoService.findAll();
        return new ResponseEntity<>(comissao, HttpStatus.OK);
    }

    @PostMapping(path = "/pagination")
    public Page<ComissaoDTO> pagination(@RequestBody PaginationFilterDTO<ComissaoDTO> paginationFilter) {
        return comissaoService.pagination(paginationFilter);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity editarComissao(@RequestBody @Valid @NotNull ComissaoDTO dto) {
        try {
            comissaoService.editarComissao(dto);
            return ResponseEntity.ok("Comissão atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
