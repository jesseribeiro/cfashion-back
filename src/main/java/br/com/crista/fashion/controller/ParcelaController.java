package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.service.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/parcela", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParcelaController {

    @Autowired
    ParcelaService parcelaService;

    @PostMapping(path = "/pagination")
    public Page<ParcelaDTO> pagination(@RequestBody PaginationFilterDTO<ParcelaDTO> paginationFilter) {
        return parcelaService.pagination(paginationFilter);
    }

    /*
    @PostMapping(path = "/calcular")
    public ResponseEntity calcularVenda(@RequestBody @Valid @NotNull CalcularVendaDTO dto) {
        try {
            return ResponseEntity.ok(vendaService.calcularVenda(dto));
        } catch (OperacaoNaoPermitidaException e) {
            return ResponseEntity.status(METHOD_NOT_ALLOWED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    */
}
