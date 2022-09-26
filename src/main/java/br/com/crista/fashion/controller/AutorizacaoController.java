package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.AutorizacaoDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.AutorizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/autorizacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutorizacaoController {

    @Autowired
    private AutorizacaoService autorizacaoService;

    @PostMapping(path = "/autorizacoes")
    public ResponseEntity paginationAutorizacoes(@RequestBody PaginationFilterDTO<AutorizacaoDTO> paginationFilter) {
        try {
            Page<AutorizacaoDTO> result = autorizacaoService.paginationAutorizacoes(paginationFilter);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
