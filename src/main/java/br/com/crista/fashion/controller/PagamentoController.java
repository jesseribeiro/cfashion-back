package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.PagamentoService;
import br.com.crista.fashion.dto.PagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping(path = "/{id}")
    public PagamentoDTO getById(@PathVariable("id") Long pagamentoId) {
        return service.getPagamentoDTO(pagamentoId);
    }

    @PostMapping(path = "/pagination")
    public Page pagination(@RequestBody PaginationFilterDTO<PagamentoDTO> paginationFilter) {
        return service.pagination(paginationFilter);
    }
}
